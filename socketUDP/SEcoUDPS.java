/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udp;

import java.net.*;
import java.util.ArrayList;

public class SEcoUDPS {

    public static void main(String[] args) {
        try {
            DatagramSocket s = new DatagramSocket(2000);
            System.out.println("Servidor iniciado, esperando cliente");
            //Lista para agrupar los clientes acivos y con base al cliente agrupar los mensajes de cada uno
            //ArrayList<InetAddress> listClients = new ArrayList<>();
            //ArrayList<String []> listMessages = new ArrayList<>();
            String msj = "";
            for (;;) {
                DatagramPacket p = new DatagramPacket(new byte[20], 20);
                while (!msj.equals("fin")) {
                   
                    s.receive(p);

                    //listClients.add(p.getAddress());
                    //System.out.println("Datagrama recibido desde"+p.getAddress()+":"+p.getPort());                
                    msj = new String(p.getData(), 0, p.getLength());

                    System.out.print(msj);
                    
                    DatagramPacket respuesta = new DatagramPacket(p.getData(), p.getLength(), p.getAddress(), p.getPort());
                    s.send(respuesta);
                
                }
                

            }//for
            //s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }//catch
    }//main
}
