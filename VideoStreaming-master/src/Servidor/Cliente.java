package src.Servidor;

import src.Servidor.Ventana;
import java.io.*;
import java.net.*;

import javax.swing.text.StyledEditorKit;

public class Cliente extends Thread {

    public static final String MCAST_ADDR = "230.1.1.1";
    public static final int MCAST_PORT = 4000;
    public static final int DGRAM_BUF_LEN = 2048;
    static Ventana v; 
    
    
    public Cliente(Ventana vent){
    	v=vent;
    }
    
    public  void run() {
   
        InetAddress group = null;
        try {
            group = InetAddress.getByName(MCAST_ADDR);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.exit(1);
        }

        boolean salta = true;

        try {
            
        	//Cada cliente enviara paquetes al servidor los cual tiene que aceptarlos
            MulticastSocket socket = new MulticastSocket(MCAST_PORT);
            socket.joinGroup(group);
            DatagramPacket contacto = new DatagramPacket(("<inicio>" + v.getNombre()).getBytes(), ("<inicio>" + v.getNombre()).length(), group, MCAST_PORT);
            socket.send(contacto);
            
            //Continuamente esta ejecutandose dentro del hilo
            while (salta) {
            
                if (v.getStatus() == 0) {     //Lectura
                    
                	//Solo espera 100 ms a que se reciva un paquete de un cliente, si se pasa manda una excepcion
                	socket.setSoTimeout(100);
                    try {
                        byte[] buf = new byte[DGRAM_BUF_LEN];
                        DatagramPacket recv = new DatagramPacket(buf, buf.length);
                        socket.receive(recv);
                        byte[] data = recv.getData();
                        String mensaje = new String(data);
                        System.out.println("Datos recibidos: " + mensaje);//5 imprime el arreglo datos rec:
                      
                        v.setNewMessage(mensaje);
                    } catch (Exception e) {
                    }
                } else if (v.getStatus() == 1) {   //Escritura
                    String mensaje = "";
                    
                    //Si es que se sale
                    if(v.getSalida() == 1){
                        mensaje = "<salida>" + v.getNombre();
                    }else{
                    	
                        if(v.getActiveTab() != 0){//Significa que esta en el chat privado
                            mensaje = "C<msj><privado><" + v.getNombre() + "><" + v.getContactosChat(v.getActiveTab()) + ">" + v.getActiveMessage();
                        }else if(v.getActiveTab() == 0){ //Significa que esta en el chat general
                            mensaje = "C<msj><" + v.getNombre() + ">" + v.getActiveMessage();
                        }
                    
                    }
                    
                    //Se envia el paquete ya sea con las configuracion de mensaje privado o general
                    DatagramPacket packet = new DatagramPacket(mensaje.getBytes(), mensaje.length(), group, MCAST_PORT);
                    System.out.println("Enviando: " + mensaje + "  con un TTL= " + socket.getTimeToLive());
                    socket.send(packet);
                  
                    //Se cambia el modo escritura a lectura, osea que se pasa al chat general
                    v.setStatus(0);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(2);
        }

    }//run

  
}//class
