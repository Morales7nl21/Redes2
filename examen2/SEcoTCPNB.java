
/**
 *
 * @author LENOVO 720
 */
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.swing.JOptionPane;

public class SEcoTCPNB {

    public static void main(String[] args) {

        try {
            //Variables necesarias para imagenes
            Map<String, Integer> map = new HashMap<String, Integer>();
            ArrayList<Integer> arl = new ArrayList<Integer>();

            String nombreImg = "";
            String jugada = "";
            boolean solImg = false, solEsp = false, solPuerto = false, solMult = false, salirC = false, turnoP1 = false, turnoP2 = false, bandSeg = false, bandJug = false;
            float tiempoFinal = 0;
            int iteradorImg = 0;
            boolean jugada1 = true, jugada2 = false;
            String EECO = "";
            int pto = 9000;
            ServerSocketChannel s = ServerSocketChannel.open();
            s.configureBlocking(false);
            s.socket().bind(new InetSocketAddress(pto));
            System.out.println("Esperando clientes...");
            Selector sel = Selector.open();
            //Se liga el server socket al sel
            s.register(sel, SelectionKey.OP_ACCEPT);
            SocketChannel player1 = null, player2 = null;
            int contM = 0;
            String[] parts = null;
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
                        System.out.println("Cliente conectado desde" + cl.socket().getInetAddress() + ":" + cl.socket().getPort()); //Tomo el puert
                        System.out.println("Se procedera a enviarle todas las fotos! ");
                        //Se hace el socket no bloqueante para que entren mas clientes
                        cl.configureBlocking(false);
                        iteradorImg = 0;
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
                            if (msj.indexOf("Seg:") != -1) {
                                parts = msj.split(" ");
                                bandSeg = true;

                            }
                            if (msj.indexOf("Jugada:") != -1) {
                                parts = msj.split(" ");
                                bandJug=true;
                            }
                            if (msj.equals("solPuerto")) {
                                solPuerto = true;
                                k.interestOps(SelectionKey.OP_WRITE);
                                msj = "";
                            }
                            if (msj.equals("solImagenes")) {

                                solImg = true;

                                k.interestOps(SelectionKey.OP_WRITE);
                                msj = "";

                            } else if (msj.equals("solEspera")) {
                                solEsp = true;
                                System.out.println("Se ha solicitado espera, para saber si jugará solo o contra alguien mas");
                                k.interestOps(SelectionKey.OP_WRITE);
                                msj = "";
                            } else if (msj.equals("solMult")) {
                                System.out.println("Servidor -> cliente ha solicitado juego multijugador:  " + ch.socket().getPort());

                                if (contM == 0) {
                                    System.out.println("Servidor -> Jugador 1  " + ch.socket().getPort());
                                    player1 = ch;

                                } else {
                                    System.out.println("Servidor -> Jugadir 2  " + ch.socket().getPort());
                                    player2 = ch;

                                    contM = 0;
                                    solMult = true;
                                }
                                contM++;
                                msj = "";

                                k.interestOps(SelectionKey.OP_WRITE);

                            }
                            if (parts == null) {
                            } else {

                                if (bandSeg) {
                                    if (parts[0].equals("Seg:")) {
                                        System.out.println(" Tiempo del cliente: " + parts[2] + " segundos: " + parts[1]);
                                        //JOptionPane.showMessageDialog(null, ((Object)parts[1]).getClass().getSimpleName());

                                        if (parts[1] != null && !parts[1].equals("")) {
                                            float f = Float.parseFloat(parts[1]);
                                            map.put(parts[2], (int) f);
                                            arl.add((int) f);
                                            Collections.sort(arl);
                                            //int tops = 1;
                                            if (!arl.isEmpty()) {
                                                arl.forEach((count) -> {
                                                    map.entrySet().stream().filter((entry) -> (Objects.equals(entry.getValue(), count))).forEachOrdered((entry) -> {                                                        
                                                        System.out.println("\n\tTiempo: " + count + "\t Hecho por: " + entry.getKey());                                                        
                                                    });
                                                });
                                            }

                                            msj = "";
                                        }
                                        parts = null;
                                        bandSeg = false;
                                    }

                                }
                                if (bandJug) {
                                    if (parts[1].equals("P1")) {
                                        
                                        turnoP2 = true;
                                        System.out.println("\nServidor recibió: " + parts[0] + parts[1] + parts[2]);
                                        jugada = parts[2];
                                        msj = "";
                                    } else if (parts[1].equals("P2")) {
                                        
                                        turnoP1 = true;
                                        System.out.println("\nServidor recibió: " + parts[0] + parts[1] + parts[2]);
                                        jugada = parts[2];
                                        msj = "";

                                    }
                                    msj = "";
                                }
                                msj = "";
                                parts=null;
                                // System.out.println(parts[0] + parts[1] + parts[2]);

                            }
//                            if(msj.equals("solJug")){
//                                turnoP1=true;
//                                turnoP2=true;
//                             msj = "";       
//                            }
                            k.interestOps(SelectionKey.OP_WRITE);

                        } catch (IOException io) {
                        }
                        continue;
                    } else if (k.isWritable()) { //Mi socketchanel esta listo para escribir algo y se envie
                        SocketChannel ch = (SocketChannel) k.channel();

                        try {
                            //System.out.println("Solicitud: " + solImg);
                            if (solPuerto) {
                                String puerto = String.valueOf(ch.socket().getPort());
                                byte[] envio = puerto.getBytes();
                                ByteBuffer b2 = ByteBuffer.wrap(envio);
                                ch.write(b2);
                                solPuerto = false;
                                k.interestOps(SelectionKey.OP_READ);
                            }
                            if (solImg == true && solPuerto == false) {
                                if (iteradorImg < 21) {
                                    nombreImg = "imagen" + String.valueOf(iteradorImg);
                                    Path newPath = Paths.get("C://Users//LENOVO 720//Desktop//IPN Documents//6toSemestre//Redes//Nueva carpeta//2a_evaluacion_2021_1//" + nombreImg + ".jpg");
                                    System.out.println(newPath);
                                    FileChannel inChannel = FileChannel.open(newPath);

                                    //Envio del tam de imagen
                                    long espera = inChannel.size();
                                    //JOptionPane.showMessageDialog(null, "Servidor mandando imagen de tam: " + espera);
                                    byte[] envio = String.valueOf(espera).getBytes();
                                    ByteBuffer b2 = ByteBuffer.wrap(envio);
                                    ch.write(b2);

                                    //Envio de la imagen
                                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                                    buffer.clear();
                                    int c = 0;
                                    while (inChannel.read(buffer) > 0 || c < inChannel.size()) {
                                        c++;
                                        buffer.flip();
                                        ch.write(buffer);
                                        //buffer.clear();
                                        buffer.compact();

                                    }
                                    inChannel.close();
                                    if (iteradorImg == 20) {
                                        solImg = false;
                                    }
                                    ++iteradorImg;
                                }
                            }
                            if (solEsp && solImg == false && solPuerto == false) {
                                String espera = "aceptado";
                                System.out.println("SEcoTCPNB.main() -> " + espera);
                                byte[] envio = espera.getBytes();
                                ByteBuffer b2 = ByteBuffer.wrap(envio);
                                ch.write(b2);
                                solEsp = false;
                                //k.interestOps(SelectionKey.OP_READ);
                            }

                            if (solMult && solEsp == false && solImg == false && solPuerto == false) {
                                String espera1 = "Acceptado P1";
                                ByteBuffer p1B = null, p2B = null;

                                byte[] envio1 = espera1.getBytes();
                                p1B = ByteBuffer.wrap(envio1);

                                String espera2 = "Acceptado P2";

                                byte[] envio2 = espera2.getBytes();
                                p2B = ByteBuffer.wrap(envio2);

                                System.out.println( "P1 port: " + player1.socket().getPort() + " P2 port: " + player2.socket().getPort());
                                player1.write(p1B);
                                player2.write(p2B);
                                //ch.write(b2);
                                solMult = false;

                                //k.interestOps(SelectionKey.OP_READ);
                            }
                            if (turnoP1) {
                                ByteBuffer p1B = null;
                                //String moves[] = jugada.split(" ");
                                System.out.println("Servidor enviando -> " + ("P1 " + jugada));
                                byte[] envio1 = ("P2 " + jugada).getBytes();                                
                                p1B = ByteBuffer.wrap(envio1);
                                player1.write(p1B);
                                jugada = "";
                                turnoP1 = false;
                            }
                            if (turnoP2) {
                                ByteBuffer p1B = null;
                                System.out.println("Servidor enviando -> " + ("P2 " + jugada));
                                byte[] envio1 = ("P1 " + jugada).getBytes();
                                p1B = ByteBuffer.wrap(envio1);
                                player2.write(p1B);
                                jugada = "";
                                turnoP2 = false;
                            }

                        } catch (IOException io) {
                        }
                        //Si ocurre un error esperamos a una siguiente lectura
                        k.interestOps(SelectionKey.OP_READ);
                        //ch.close();
                        continue;
                    }//if
                }//while
            }//while
        } catch (Exception e) {
            e.printStackTrace();
        }//catch
    }//main

}
