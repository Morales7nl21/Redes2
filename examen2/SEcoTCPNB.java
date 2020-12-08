
/**
 *
 * @author LENOVO 720
 */
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import jdk.dynalink.linker.support.Guards;

public class SEcoTCPNB {

    public static void main(String[] args) {
        try {
            //Variables necesarias para imagenes
            Path path = null;
            String nombreImg = "";
            long sizeFile = 0;
            File imagen = null;
            boolean solImg = false;

            int iteradorImg = 0;

            String EECO = "";
            int pto = 9999;
            ServerSocketChannel s = ServerSocketChannel.open();
            s.configureBlocking(false);
            s.socket().bind(new InetSocketAddress(pto));
            System.out.println("Esperando clientes...");
            Selector sel = Selector.open();
            //Se liga el server socket al sel
            s.register(sel, SelectionKey.OP_ACCEPT);
            while (true) {
                //Aqui esperamos a que ocurra un evento
                sel.select();
                //Nuestro iterador va a recorrer la lista generada en sel.select
                //Cuando se pasa a esta linea ya sabemos que por lo menos un evento
                //ocurrio y la recorremos la lista con iterator interface
                //Una vez recorrido se regresa al while(true)
                Iterator<SelectionKey> it = sel.selectedKeys().iterator();
                while (it.hasNext()) {
                    //Se recupera el evento 
                    SelectionKey k = (SelectionKey) it.next();
                    //Se borra de la lista ya que ya se recupero
                    it.remove();

                    if (k.isAcceptable()) {
                        //El selector solo avisa ya es cuestion nuestra hacer el accept
                        SocketChannel cl = s.accept();
                        System.out.println("Cliente conectado desde" + cl.socket().getInetAddress() + ":" + cl.socket().getPort());
                        System.out.println("Se procedera a enviarle todas las fotos! ");
                        //Se hace el socket no bloqueante para que entren mas clientes
                        cl.configureBlocking(false);

                        // como el cliente tiene su propio socket chanel se vincula su socket chanel al register de aqui
                        // en este caso se hace al de lectura y escritura.
                        cl.register(sel, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                        continue;
                    }//if
                    //Lectura el cliente escirbio algo en mi buffer
                    if (k.isReadable()) {
                        try {
                            //Nos interesa saber que canal fue el que lo hizo
                            SocketChannel ch = (SocketChannel) k.channel();
//                            Se creea un buffer como intermediario
                            ByteBuffer b = ByteBuffer.allocate(2000);
                            b.clear();
                            int n = 0;
                            String msj = "";
                            n = ch.read(b);
//                            Ajusta mi buffer ára trabajar con el
                            b.flip();
                            if (n > 0) {
                                msj = new String(b.array(), 0, n);
                            }
                            System.out.println("Mensaje de " + n + " bytes recibido:" + msj);
                            if (msj.equals("solImagenes")) {
                                String dir = "C:\\Users\\LENOVO 720\\Desktop\\IPN Documents\\6toSemestre\\Redes\\Nueva carpeta\\2a_evaluacion_2021_1" + "\\imagen1";
                                path = Paths.get(dir);
                                System.out.println("Dir: " + dir);
                                //for (int i = iteradorImg; i < 21; i++) {
                                //nombreImg = "imagen" + "0";
                                //imagen = new File(pathImg + nombreImg);
                                //sizeFile = imagen.length();

                                solImg = true;
                                //iteradorImg += 1;
                                k.interestOps(SelectionKey.OP_WRITE);
                                //}                                

                            } else if (msj.equalsIgnoreCase("SALIR")) {
                                //Permite agregar un selectionkey a mi lista de eventos y ahora lo vamos a enviar 
                                //de regreso, como queremos que sea el selector el que ecriba agregamos el write a mi lista de eventos

                                k.interestOps(SelectionKey.OP_WRITE);
                                ch.close();
                                // k.cancel();
                            } else {
                                //Se envia el mensaje de eco si escibimos salir salimso de la conexion
                                System.out.println("Se llego al final de write en servidor");
                                EECO = "ECO->" + "";
                                k.interestOps(SelectionKey.OP_WRITE);
                            }//else
                        } catch (IOException io) {
                        }
                        continue;

                    } else if (k.isWritable()) { //Mi socketchanel esta listo para escribir algo y se envie
                        try {
                            //Vemos en que canal ocurrio
                            SocketChannel ch = (SocketChannel) k.channel();

                            System.out.println("Solicitud: " + solImg);
                            if (solImg) {
                                
                                
                                    //toma la imagen de entrada
                                    Path newPath = Paths.get("C://Users//LENOVO 720//Desktop//IPN Documents//6toSemestre//Redes//Nueva carpeta//2a_evaluacion_2021_1//imagen3.jpg");
                                    System.out.println(newPath);
                                    FileChannel inChannel = FileChannel.open(newPath);
                                    long size = inChannel.size();
                                    System.out.println("Size: " + size);
                                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                                    //buffer.clear();
                                    while (inChannel.read(buffer) > 0) {
                                        buffer.flip();
                                        ch.write(buffer);
                                        buffer.clear();

                                    }
                                    ch.close();
                                
                                solImg = false;
                            } else {
                                System.out.println("solicitud es null ):");
                            }

                            //Mandamos a un buffer el mensaje
                            //ByteBuffer bb = ByteBuffer.wrap(EECO.getBytes());
                            //Se escribe el mensaje
                            //ch.write(bb);
                            //System.out.println("Mensaje de " + EECO.length() + " bytes enviado: " + EECO);
                            //Borramos nuestro mensaje
                            //EECO = "";
                        } catch (IOException io) {
                        }
                        //Si ocurre un error esperamos a una siguiente lectura
                        k.interestOps(SelectionKey.OP_READ);
                        continue;
                    }//if
                }//while
            }//while
        } catch (Exception e) {
            e.printStackTrace();
        }//catch
    }//main
}
