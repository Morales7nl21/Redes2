
/**
 *
 * @author LENOVO 720
 */
import java.nio.channels.*;
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;
import java.util.Iterator;
import javax.swing.JOptionPane;

public class CEcoTCPNB {

    SocketChannel clG;
    tableroMemoramaM tm;
    String puerto = "";
    String[] parts = null;
    String jugador = "";
    boolean jugando = false;

    public CEcoTCPNB() {

        Cliente();
    }

    public void Cliente() {
        try {

            String dir = "127.0.0.1", nameImg = "imagen";
            int pto = 9000;
            ByteBuffer b1 = null, b2 = null;
            InetSocketAddress dst = new InetSocketAddress(dir, pto);
            String eleccionJuego = "";
            SocketChannel cl = SocketChannel.open();
            clG = cl;
            boolean pedirPuerto = true;
            boolean pedirarchivos = true, verificararchivos = false;
            int correctosArchivos = 0;
            boolean solicitudEspera = false, verificarJugando = false, banderaSeleccion = true;
            boolean verificarPuerto = false, flagDir = true, verificarEspera = false, solicitudSingle = false, solicitudMulti = false, verificarMulti = false;
            int contC = 0;

            //lo hacemos no bloqueante
            cl.configureBlocking(false);
            Selector sel = Selector.open();
            cl.register(sel, SelectionKey.OP_CONNECT);
            cl.connect(dst);
            while (true) {
                sel.select();
                Iterator<SelectionKey> it = sel.selectedKeys().iterator();
                while (it.hasNext()) {
                    SelectionKey k = (SelectionKey) it.next();

                    it.remove();
                    if (k.isConnectable()) {

                        SocketChannel ch = (SocketChannel) k.channel();

                        if (ch.isConnectionPending()) {
                            System.out.println("Estableciendo conexion con el servidor... espere..");

                            try {
                                ch.finishConnect();
                            } catch (Exception e) {
                            }//catch

                            System.out.println("Conexion establecida...\n");
                        }//if
                        //una vez establecida la conexion se agrega al selector mi canal y se le pone lo de leer y escirbir
                        ch.register(sel, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                        continue;
                    }//if
                    if (k.isReadable()) {
                        SocketChannel ch = (SocketChannel) k.channel();

                        if (verificarPuerto) {
                            b1 = ByteBuffer.allocate(2000);
                            b1.clear();
                            int n = ch.read(b1);
                            b1.flip();
                            puerto = new String(b1.array(), 0, n);
                            System.out.println("Mi puerto es: " + puerto);
                            verificarPuerto = false;
                        }
                        if (verificararchivos && correctosArchivos < 21 && verificarPuerto == false) {
                            if (flagDir) {
                                File directorio = new File("C:\\Users\\LENOVO 720\\Desktop\\IPN Documents\\6toSemestre\\Redes\\RMI\\examen2Redes\\Clientes\\" + puerto);
                                directorio.mkdir();
                                flagDir = false;
                            }
                            System.out.println("C:\\Users\\LENOVO 720\\Desktop\\IPN Documents\\6toSemestre\\Redes\\RMI\\examen2Redes\\Clientes\\" + puerto);
                            nameImg = "imagen" + String.valueOf(correctosArchivos) + ".jpg";
                            Path path = Paths.get("C:\\Users\\LENOVO 720\\Desktop\\IPN Documents\\6toSemestre\\Redes\\RMI\\examen2Redes\\Clientes\\" + puerto + "\\" + nameImg);
                            FileChannel fileChannel = FileChannel.open(path, EnumSet.of(
                                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE));

                            //Recivimos el tama?o de la imagen
                            ByteBuffer tempB = ByteBuffer.allocate(5);
                            tempB.clear();
                            int n = ch.read(tempB);
                            //JOptionPane.showMessageDialog(null, "Tama?o antes de codificar: " + String.valueOf(n) );
                            tempB.flip();
                            String sizeI = new String(tempB.array(), 0, n);
                            ByteBuffer buffer = ByteBuffer.allocate(1024);
                            buffer.clear();
                            int auxTamImg = 0;
                            while (ch.read(buffer) > 0 || auxTamImg < Integer.valueOf(sizeI)) {
                                auxTamImg++;
                                buffer.flip();
                                fileChannel.write(buffer);
                                //buffer.clear();
                                buffer.compact();
                            }
                            fileChannel.close();
                            System.out.println("Recivido de manera exitosa");
                            if (correctosArchivos == 20) {
                                pedirarchivos = false;
                                solicitudEspera = true;
                            }
                            correctosArchivos++;
                        }
                        if (verificarEspera && banderaSeleccion) {
                            int cont = 0;
                            if(cont == 0){
                                eleccionJuego = JOptionPane.showInputDialog(null, "Escribe (sp) si vas a jugar solo, de ser que quieras jugar con alguien escirbe (mp)");
                                cont ++;
                            }
                            if (eleccionJuego.equals("sp")) {
                                System.out.println("Cliente: ha elegido jugar " + eleccionJuego);
                                ByteBuffer b = ByteBuffer.allocate(2000);
                                b.clear();
                                int n = ch.read(b);
                                b1.flip();
                                String solS = new String(b.array(), 0, n);
                                System.out.println("Cliente -> Aceptado Servidor: " + solS);
                                //JOptionPane.showMessageDialog(null, solS);
                                banderaSeleccion = false;
                                tm = new tableroMemoramaM("C:/Users/LENOVO 720/Desktop/IPN Documents/6toSemestre/Redes/RMI/examen2Redes/Clientes/" + puerto, this, 0);

                                tm.setVisible(true);
                                //wait(10000);
                                //tm.destapar(5); Destapamos un carta

                            } else if (eleccionJuego.equals("mp")) {
                                banderaSeleccion = false;
                                System.out.println("Cliente: ha elegido jugar " + eleccionJuego);
                                ByteBuffer b = ByteBuffer.allocate(2000);
                                b.clear();
                                int n = ch.read(b);
                                b1.flip();
                                String solS = new String(b.array(), 0, n);
                                System.out.println("Cliente Espera -> Servidor: " + solS);
                                solicitudMulti = true;
                                //JOptionPane.showMessageDialog(null, solS);

                            }
                            verificarEspera = false;
                        }
                        if (verificarMulti) {
                            ByteBuffer b = ByteBuffer.allocate(2000);
                            b.clear();
                            int n = ch.read(b);
                            b1.flip();
                            String solS = new String(b.array(), 0, n);
                            if (solS.indexOf("Acceptado") != -1) {
                                parts = solS.split(" ");
                            }
                            System.out.println("Cliente Multijugador -> Servidor: " + solS);
                            if (parts == null) {
                            } else {
                                if (parts[1].equals("P1")) {
                                    JOptionPane.showMessageDialog(null, "Serás el jugador 1, puerto: " + puerto);
                                    jugador = "P1";

                                    generarTablero();
                                } else if (parts[1].equals("P2")) {
                                    JOptionPane.showMessageDialog(null, "Serás el jugador 2, puerto:  " + puerto);
                                    jugador = "P2";                                    
                                    generarTablero();
                                }
                                parts = null;
                            }
                            verificarMulti = false;
                            jugando = true;
                        } else if (verificarJugando) {
                            ByteBuffer b = ByteBuffer.allocate(20000);
                            b.clear();
                            b1.flip();
                            int n = ch.read(b);
                            String solS = "";
                            solS = new String(b.array(), 0, n);
                            System.out.println("Cliente-> Recibiendo jugada: " + solS);
                            parts = solS.split(" ");
                            System.out.println("Destapando carta: " + parts[1]);
                            destapaCarta(Integer.parseInt(parts[1]));
                            parts = null;
//                            if (jugador.equals("P2")) {
//                               // if (solS.indexOf("P2") != -1) {
//                                    parts = solS.split(" ");
//                               // }
//                                                                                           
//                            }
//                            if (jugador.equals("P1")) {
//                               // if (solS.indexOf("P1") != -1) {
//                                    parts = solS.split(" ");
//                                //}
//                                destapaCarta(Integer.parseInt(parts[1]));                                                                
//                            }
                            verificarJugando = false;

                        }

                        k.interestOps(SelectionKey.OP_WRITE);
                        continue;
                        //aqui hacemos operacion de escribir
                        //AQUI LOS EVENTOS YA OCURRIERON
                    } else if (k.isWritable()) {
                        SocketChannel ch = (SocketChannel) k.channel();
                        if (pedirPuerto) {

                            System.out.println("Solicitando puerto: ");
                            pedirPuerto = false;
                            String pedir = "solPuerto";
                            byte[] envio = pedir.getBytes();
                            b2 = ByteBuffer.wrap(envio);
                            ch.write(b2);
                            verificarPuerto = true;
                            k.interestOps(SelectionKey.OP_READ);
                        }
                        if (pedirarchivos == true && verificarPuerto == false) {
                            //System.out.println("Pedir archivos: " + pedirarchivos);
                            String pedir = "solImagenes";

                            System.out.println("Cliente: solicitando imagenes: " + pedir + " numero de imagen: " + String.valueOf(contC++));
                            byte[] envio = pedir.getBytes();
                            b2 = ByteBuffer.wrap(envio);
                            ch.write(b2);
                            verificararchivos = true;
                            k.interestOps(SelectionKey.OP_READ);

                        }
                        if (solicitudEspera == true && pedirarchivos == false && pedirPuerto == false) {

                            System.out.println("Cliente: Han llegado todas las imagenes: ");
                            String pedir = "solEspera";
                            System.out.println("Cliente: solicitando espera");
                            byte[] envio = pedir.getBytes();
                            b2 = ByteBuffer.wrap(envio);
                            ch.write(b2);
                            solicitudEspera = false;
                            verificarEspera = true;
                            k.interestOps(SelectionKey.OP_READ);

                        }
                        if (solicitudMulti == true && solicitudEspera == false && pedirarchivos == false && pedirPuerto == false) {
                            String pedir = "solMult";
                            //System.out.println("Cliente: solicitando multijuego");
                            byte[] envio = pedir.getBytes();
                            b2 = ByteBuffer.wrap(envio);
                            ch.write(b2);
                            solicitudMulti = false;
                            verificarMulti = true;
                            k.interestOps(SelectionKey.OP_READ);

                        }
                        if (jugando && solicitudMulti == false && solicitudEspera == false && pedirarchivos == false && pedirPuerto == false) {
                            //String pedir = "solJug";
                            //System.out.println("Cliente: solicitando multijuego");
                            //byte[] envio = pedir.getBytes();
                            //b2 = ByteBuffer.wrap(envio);
                            //ch.write(b2);
                            jugando = false;
                            verificarJugando = true;
                            k.interestOps(SelectionKey.OP_READ);

                        }
                        //if                                                
                        continue;
                    } //if
                }//while   
            }//while
        } catch (Exception e) {
            e.printStackTrace();
        }//catch

    }//cliente

    public void mandaBoton(int btn) throws IOException {
        //JOptionPane.showMessageDialog(null, "Boton recibido" + String.valueOf(btn));

        ByteBuffer btf = null;
        System.out.println("Jugador: " + jugador + " \tBoton presionado: " + btn);
        String tiempo = "Jugada: " + jugador + " " + btn;
        byte[] envio = tiempo.getBytes();
        btf = ByteBuffer.wrap(envio);
        clG.write(btf);
        jugando = true;

    }

    public void generarTablero() {

        tm = new tableroMemoramaM("C:/Users/LENOVO 720/Desktop/IPN Documents/6toSemestre/Redes/RMI/examen2Redes/Clientes/" + puerto, this, 1);
        tm.setVisible(true);
    }

    public void destapaCarta(int num) {
        tm.destapar(num);
    }

    void mandaTiempo(String cadena) throws IOException {
        ByteBuffer btf = null;

        String tiempo = "Seg: " + cadena;
        System.out.println(tiempo);
        byte[] envio = tiempo.getBytes();
        btf = ByteBuffer.wrap(envio);
        clG.write(btf);
        //clG.close();
        System.exit(0);
        //JOptionPane.showMessageDialog(null, "Boton recibido" + String.valueOf(seg));
    }
}
