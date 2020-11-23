/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udp;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

public class SEcoUDPS {

    public static void main(String[] args) {
        try {
            DatagramSocket s = new DatagramSocket(2000);
            System.out.println("Servidor iniciado, esperando cliente");
            ArrayList<String> listMessages = new ArrayList<>();

            String msj = "", msjT = "", msjS = "";
            int count = 0, c = 0, tamC = 0, tam = 0;

            for (;;) {
                DatagramPacket p = new DatagramPacket(new byte[20], 20);
                while (!msj.equals("fin") && !(tam == -1)) {

                    s.receive(p);
                    if (c == 0) {
                        System.out.println("Cliente(s) conectado(s) ");
                        c = 1;
                    }
                    //System.out.println("contado cliente: " + c);
                    if (tamC == 0) {
                        msjT = new String(p.getData(), 0, p.getLength());
                        tam = Integer.parseInt(msjT);
                        tamC = 1;
                        //System.out.println("Tama?o paquete: " + tam);                        
                        if (tam > 20) {
                            int numero_paquetes = tam / 20;
                            float numero_paquetes_aux = (float) tam / 20;
                            if (numero_paquetes_aux > (float) numero_paquetes) {
                                numero_paquetes++;
                            }
                            //System.out.println("numero de paquetes: "+String.valueOf(numero_paquetes));                            
                            while (numero_paquetes > 0) {
                                DatagramPacket n = new DatagramPacket(new byte[20], 20);
                                s.receive(n);                                
                                msj = new String(n.getData(), 0, n.getLength());
                                count += 1;
                                
                                numero_paquetes--;
                                //System.out.println("contador de num de paquetes: " + String.valueOf(numero_paquetes));
                                //System.out.println(msj);
                                listMessages.add(msj);
                                DatagramPacket respuesta = new DatagramPacket(n.getData(), n.getLength(), p.getAddress(), p.getPort());
                                s.send(respuesta);
                                if (numero_paquetes == 0) {
                                    System.out.print("Cliente: ");
                                    for (int i = 0; i < count; i++) {
                                        System.out.print("" + listMessages.get(i));
                                    }
                                    tamC = 0;
                                    tam = 0;
                                    //c=0;
                                    count = 0;
                                    System.out.println("");
                                    listMessages.clear();
                                }                                
                            }
                        } else {
                            
                            DatagramPacket n = new DatagramPacket(new byte[20], 20);
                            s.receive(n);
                            msjS = new String(n.getData(), 0, n.getLength());
                            System.out.print("Cliente: " + msjS);
                            DatagramPacket respuesta = new DatagramPacket(n.getData(), n.getLength(), p.getAddress(), p.getPort());
                            s.send(respuesta);
                            //c=0;
                            tam=0;
                            tamC=0;
                            System.out.println("");                            
                        }
                    }
                }
                msj = "";
                c = 0;
            }//for
            //s.close();
        } catch (IOException e) {
        }//catch
    }//main
}
