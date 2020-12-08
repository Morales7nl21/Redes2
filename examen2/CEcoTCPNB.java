
/**
 *
 * @author LENOVO 720
 */
import java.awt.image.BufferedImage;
import java.nio.channels.*;
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;
import java.util.Iterator;
import javax.imageio.ImageIO;
//El cliente puede ser no bloqueante

public class CEcoTCPNB {

    public static void main(String[] args) {
        try {

            String dir = "127.0.0.1";
            int pto = 9999;
            ByteBuffer b1 = null, b2 = null;
            InetSocketAddress dst = new InetSocketAddress(dir, pto);
            //se crea el socketchanel
            SocketChannel cl = SocketChannel.open();
            boolean pedirarchivos = true, verificararchivos = false;
            int correctosArchivos = 0;
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            //lo hacemos no bloqueante
            cl.configureBlocking(false);
            //Creamos selector
            Selector sel = Selector.open();
            //Lo registramos con conect (vamos a establecer conexion)
            cl.register(sel, SelectionKey.OP_CONNECT);
            //intentamos establecer conexion
            cl.connect(dst);
            while (true) {
                //Se guarda la lista, el unico evento que puede ocurrir es conect, aqui ya la conexion se realizo
                //En el momento que exista un evento se hace la lista
                sel.select();
                //instancia la iterador
                Iterator<SelectionKey> it = sel.selectedKeys().iterator();
                //Recorre la lista
                while (it.hasNext()) {
                    SelectionKey k = (SelectionKey) it.next();
                    //eliminamos el evento pero antes se recupera
                    it.remove();
                    if (k.isConnectable()) {

                        SocketChannel ch = (SocketChannel) k.channel();
                        //El selectionkey se ejecuta en el momento que la conexion se inicia
                        //Aseguramos que el handshake termino
                        if (ch.isConnectionPending()) {
                            System.out.println("Estableciendo conexion con el servidor... espere..");
                            try {
                                ch.finishConnect();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }//catch

                            System.out.println("Conexion establecida...\n");
                        }//if
                        //una vez establecida la conexion se agrega al selector mi canal y se le pone lo de leer y escirbir
                        ch.register(sel, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                        continue;
                    }//if
                    //Ya no solo aceptamos sino podemos tener eventos de lectura y escritura por la linea 63
                    //el servidor envio respuesta AQUI ya los eventos recurrieron
                    if (k.isReadable()) {
                        //vemos en que canal ocurrio
                        SocketChannel ch = (SocketChannel) k.channel();
                        //Creamso buffer
                        if (verificararchivos/* && correctosArchivos < 21*/) {
                            Path path = Paths.get("C:\\Users\\LENOVO 720\\Desktop\\IPN Documents\\6toSemestre\\Redes\\RMI\\examen2Redes\\Clientes\\123.jpg");
                            FileChannel fileChannel = FileChannel.open(path, EnumSet.of(
                                    StandardOpenOption.CREATE,
                                    StandardOpenOption.TRUNCATE_EXISTING,
                                    StandardOpenOption.WRITE)
                            );

                            ByteBuffer buffer = ByteBuffer.allocate(1024);
                            buffer.clear();
                            while (ch.read(buffer) > 0) {
                                buffer.flip();//No sé bien que hace
                                fileChannel.write(buffer);
                                buffer.clear();
                            }
                            fileChannel.close();
                            System.out.println("Recivido de manera exitosa");
                            verificararchivos = false;
                            k.interestOps(SelectionKey.OP_WRITE);
                        }

                        continue;
                        //aqui hacemos operacion de escribir
                        //AQUI LOS EVENTOS YA OCURRIERON
                    } else if (k.isWritable()) {
                        SocketChannel ch = (SocketChannel) k.channel();
                        //String datos = "";
                        //datos = br.readLine();
                        //Si lo qu se escribio es salir usuario quiere terminar la comunicacion
                        System.out.println("Pedir archivos: " + pedirarchivos);
                        if (pedirarchivos) {
                            String pedir = "solImagenes";
                            System.out.println("solicitando imagenes: " + pedir);
                            byte[] envio = pedir.getBytes();
                            b2 = ByteBuffer.wrap(envio);
                            ch.write(b2);
                            verificararchivos = true;
                            pedirarchivos = false;
                            k.interestOps(SelectionKey.OP_READ);

                        }//if                                                
                        continue;
                    } //if
                }//while   
            }//while
        } catch (Exception e) {
            e.printStackTrace();
        }//catch

    }//main
}
