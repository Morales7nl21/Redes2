package chat_cliente;

import chat_interfaces.opcionesMulticast;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.InetAddress;
import java.nio.charset.Charset;

public class MulticastClient extends Thread implements opcionesMulticast {

    private SocketAddress remote = null;    
    private icliente chat;

    public MulticastClient(icliente chat) {
        this.chat = chat;
        System.out.println("Cliente iniciado.");
    }
    public void MandaMsj(String msj) {
        try {
            try {
                remote = new InetSocketAddress(HHOST, PORT);
            } catch (Exception e) {
                System.out.println("Error en el remote");
                e.printStackTrace();
            }//catch           
            InetAddress group = InetAddress.getByName(HHOST); //Se resuelve la direccion multicast            
            NetworkInterface ni = NetworkInterface.getByName("wlan1");
            DatagramChannel cl = DatagramChannel.open(StandardProtocolFamily.INET);
            cl.setOption(StandardSocketOptions.SO_REUSEADDR, true);
            cl.setOption(StandardSocketOptions.IP_MULTICAST_IF, ni);
            cl.configureBlocking(false);
            Selector sel = Selector.open(); //se crea el selector, debido a que es una interfaz se hace mediante el método open
            cl.register(sel, SelectionKey.OP_READ | SelectionKey.OP_WRITE);//Se agrega canal de escritura y lectura
            cl.join(group, ni);//Nos conectamos al canal
            sel.select();//Como esta vacío puedo hacer una escritura
            Iterator<SelectionKey> it = sel.selectedKeys().iterator();

            while (it.hasNext()) {
                SelectionKey k = (SelectionKey) it.next();
                it.remove();
                if (k.isWritable()) { //Si es de escritura
                    DatagramChannel ch = (DatagramChannel) k.channel();
                    ByteBuffer b = ByteBuffer.allocate(BUFFER_LENGHT);
                    b.clear();
                    b = Charset.forName("UTF-8").encode(msj);
                    String msjC = new String(b.array(), "UTF-8");
                    System.out.println("Cliente enviando: " + msjC);
                    int sended = ch.send(b, remote);
                    System.out.println(sended);
                    k.interestOps(SelectionKey.OP_READ);
                    continue;
                }
                if (k.isReadable()) {
                    System.out.println("READ EN EL CLIENTE: ::::");
                    DatagramChannel ch = (DatagramChannel) k.channel();
                    ByteBuffer b = ByteBuffer.allocate(BUFFER_LENGHT);
                    b.clear();
                    SocketAddress emisor = ch.receive(b);
                    b.flip();
                    InetSocketAddress d = (InetSocketAddress) emisor;
                    String msjC = new String(b.array(), "UTF-8");
                    if (!msjC.isEmpty()) {
                        System.err.println(msjC);
                    }
                    System.out.println("Mensaje recibido del servidor: " + msjC);
                    k.interestOps(SelectionKey.OP_WRITE);
                    continue;
                }
            }//while
        } catch (IOException e) {
        }
    }

    public void run() {

        try {
            InetAddress group = InetAddress.getByName(HHOST); //se trata de resolver dir multicast 
            InetSocketAddress dir = new InetSocketAddress(PORT2);
            NetworkInterface ni = NetworkInterface.getByName(INTERFACE_NAME);
            DatagramChannel cl = DatagramChannel.open(StandardProtocolFamily.INET);
            cl.setOption(StandardSocketOptions.SO_REUSEADDR, true);
            cl.setOption(StandardSocketOptions.IP_MULTICAST_IF, ni);
            cl.configureBlocking(false);
            cl.socket().bind(dir);
            Selector sel = Selector.open();
            cl.register(sel, SelectionKey.OP_READ | SelectionKey.OP_WRITE);//Recibire read y write
            cl.join(group, ni);
            String mensajeStr;
            while (true) {
                ByteBuffer b = ByteBuffer.allocate(BUFFER_LENGHT);
                sel.select();
                Iterator<SelectionKey> it = sel.selectedKeys().iterator();
                while (it.hasNext()) {
                    SelectionKey k = (SelectionKey) it.next();
                    it.remove();
                    if (k.isReadable()) {
                        DatagramChannel ch = (DatagramChannel) k.channel();
                        b.clear();
                        SocketAddress emisor = ch.receive(b);
                        InetSocketAddress d = (InetSocketAddress) emisor;
                        mensajeStr = new String(b.array());
                        System.out.println("Recibido " + mensajeStr + "Desde la maquina del servidor: " + d.getHostName());
                        chat.Agregar_conversacion(mensajeStr);
                        continue;
                    }
                    if (k.isWritable()) {
                        Thread.sleep(100);
                    }
                }
            }
        } catch (IOException e) {
        } catch (InterruptedException ex) {
            Logger.getLogger(MulticastClient.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}//class

