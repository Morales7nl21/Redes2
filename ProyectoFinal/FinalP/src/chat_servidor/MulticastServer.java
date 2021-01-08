package chat_servidor;


import chat_interfaces.opcionesMulticast;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MulticastServer extends Thread implements opcionesMulticast {

    public List<String> conectados = new ArrayList<String>();
    public String conect = "";
    String inicio = "";
    public void deleteUser(String msj) {
        conectados.remove(msj.split(">")[1].trim());
        conect = "<conectados>,";
        for (int y = 0; y < conectados.size(); y++) {
            conect += conectados.get(y) + ",";
        }

        conect.replace(" ", "");
    }

    private void addUser(String msj) {
        String[] x = msj.split(">");
        conectados.add(x[1].trim());

        conect = "<conectados>,";

        for (int y = 0; y < conectados.size(); y++) {
            conect += conectados.get(y) + ",";
        }
        for (Object con : conectados) {
            System.out.println("MulticastServer.addUser(): " + con);
        }

        conect = conect.replace(" ", "");
    }

    public void run() {
        try {
            NetworkInterface ni = NetworkInterface.getByName(INTERFACE_NAME);
            InetSocketAddress dir = new InetSocketAddress(PORT);
            DatagramChannel s = DatagramChannel.open(StandardProtocolFamily.INET);

            s.setOption(StandardSocketOptions.SO_REUSEADDR, true);
            s.setOption(StandardSocketOptions.IP_MULTICAST_IF, ni);

            InetAddress group = InetAddress.getByName(HHOST);
            s.join(group, ni);
            s.configureBlocking(false);
            s.socket().bind(dir);
            Selector sel = Selector.open();
            s.register(sel, SelectionKey.OP_READ | SelectionKey.OP_WRITE);

            System.out.println("Servidor listo.. Esperando clientes...");

            while (true) {
                
                sel.select();
                Iterator<SelectionKey> it = sel.selectedKeys().iterator();
                while (it.hasNext()) {
                    SelectionKey k = (SelectionKey) it.next();
                    it.remove();
                    if (k.isReadable()) {
                        //System.out.println("Is Readable");
                        DatagramChannel ch = (DatagramChannel) k.channel();
//                            Se creea un buffer como intermediario
                        ByteBuffer b = ByteBuffer.allocate(BUFFER_LENGHT);
                        b.clear();
                        SocketAddress emisor = ch.receive(b);
                        b.flip();
                        InetSocketAddress d = (InetSocketAddress) emisor;

                        inicio = new String(b.array(), "UTF-8");
                        if (!(inicio.equals(""))) {

                            System.out.println("Datagrama recibido desde " + d.getAddress() + ":" + d.getPort());

                            if (inicio.contains("<inicio>")) {
                                System.out.println("<inicio>" + inicio);
                                System.out.println("Add User");
                                addUser(inicio);
                            } else if (inicio.contains("<fin>")) {
                                deleteUser(inicio);
                            } else if (!inicio.contains("<conectados>")) {
                                System.out.println("mensaje:" + inicio);
                                //inicio = "";
                            }

                        }
                        k.interestOps(SelectionKey.OP_WRITE);
                        continue;
                    } else if (k.isWritable()) { //Si es de escritura
                        //System.out.println("Is Writable");
                        SocketAddress remote = new InetSocketAddress(HHOST, PORT2);
                        DatagramChannel ch = (DatagramChannel) k.channel();//Lo envío
                        ByteBuffer b = ByteBuffer.allocate(BUFFER_LENGHT);                        
                        ByteBuffer b2 = ByteBuffer.allocate(BUFFER_LENGHT);                        
                        b.clear();
                        b2.clear();
                        //b.flip();
                        byte[] envio = inicio.getBytes();
                        b = ByteBuffer.wrap(envio);
                       
                       // b = Charset.forName("UTF-8").encode(inicio);//no va inicio
                        //String mensajeR = new String(b.array(), "UTF-8");
                         if (!(inicio.equals(""))) {
                             //System.out.println("MENSAJEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE WRIT");
                             System.out.println("Mensaje devuelta: " + inicio);
                       
                           // if (true)) {
                                int sended = ch.send(b, remote);
                                System.out.println(sended);
                                // mensajeR = new String(b.array(), "UTF-8");
                                System.out.println("Enviado: " + inicio);
                                inicio = "";
                            //}else{
                                //int sended = ch.send(b, remote); 2
                                //System.out.println(sended); 2
                                // mensajeR = new String(b.array(), "UTF-8");
                                //System.out.println("Enviado: " + mensajeR);
                           // }
                         
                        
                        }
                         b2.clear();
                         b2 = Charset.forName("UTF-8").encode(conect);
                         String hola = new String(b2.array(), "UTF-8");
                         System.out.println("Conect: " + hola);
                         int sended2 = ch.send(b2, remote);
                       // DatagramChannel ch = (DatagramChannel) k.channel();//Lo envío
                       /*
                        b2 = Charset.forName("UTF-8").encode(conect); 
                        String conectado = new String(b2.array(), "UTF-8");
                        System.out.println("Conect: " + conectado);
                        int sended = ch.send(b2, remote);
                        System.out.println(sended);
                        if (sended > 0) {
                            System.out.println("==========================================================");
                        }
                        conectado = new String(b.array(), "UTF-8");
                        System.out.println("Enviado: " + conectado); 
                        
                        k.interestOps(SelectionKey.OP_READ);
                        */
                       k.interestOps(SelectionKey.OP_READ);
                       continue;
                    }

                }//while
            }//while
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(2);
        }//catch
    }//main

    public static void main(String args[]) {

        try {
            MulticastServer mc2 = new MulticastServer();
            mc2.start();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }//main
}//class
