/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udp;

/**
 *
 * @author LENOVO 720
 */
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JOptionPane;
import java.util.Arrays.*;
import java.util.List;

public class CEcoUDPS {

    public static void main(String[] args) {
        try {
            //datagra,socket sirve pa cliente y servidor
            DatagramSocket cl = new DatagramSocket();
            //Mensaje a enviar
            System.out.print("Cliente iniciado, escriba un mensaje de saludo:");
            //Se crea el  buffer para el dato a enviar
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String mensaje = br.readLine();
            byte[] b = mensaje.getBytes();

            if (b.length > 20) {
                //ArrayList<byte[]> list2 = ArrayUtil.splitArray(b, 20);
                ArrayList<byte[]> list = new ArrayList<byte[]>();
                
                
                int numero_paquetes = b.length / 20;
                float numero_paquetes_aux = (float) b.length / 20;

                System.out.println(b.length);
                System.out.println(numero_paquetes);
                System.out.println(b.length / 20);
                int bandera_paquete = 0;
                int k = 0;

                if (numero_paquetes_aux > (float) numero_paquetes) {
                    numero_paquetes++;
                    bandera_paquete = b.length % 20;
                    System.out.println(bandera_paquete);
                }
                if (bandera_paquete != 0) {
                    for (int i = 0; i < numero_paquetes; i++) {
                        if (i == numero_paquetes - 1) {
                            //byte[] arregloBaux = new byte[20];
                            //System.arraycopy(b, 0 + k, arregloBaux, 0, bandera_paquete);
                            //list.add(arregloBaux);
                            list.add((byte []) Arrays.copyOfRange(b, k, k+bandera_paquete));
                        } else {
                            byte[] arregloBaux = new byte[20];
                            System.arraycopy(b, 0 + k, arregloBaux, 0, 20);                            
                            list.add(arregloBaux);
                        }
                        k += 20;
                    }

                } else {
                    for (int i = 0; i < numero_paquetes; i++) {
                        byte[] arregloBaux = new byte[20];
                        System.arraycopy(b, 0 + k, arregloBaux, 0, 20);
                        list.add(arregloBaux);
                        k += 20;
                    }

                }

                //Prueba de coppiar arreglos
                //byte[] b2 = new byte[20];
                //System.arraycopy(b, 0, b2, 0, 20);
                //list.add(b2);
                System.out.println(Arrays.toString(list.get(0)));
                System.out.println(Arrays.toString(list.get(1)));
                System.out.println(Arrays.toString(list.get(2)));
                System.out.println(Arrays.toString(b));

            }

            //Devuelve el ASCI de cada char de la cadena 
            //System.out.println(Arrays.toString(b));
            //System.out.println(b.length);
            //String dst = "127.0.0.1";
            //int pto = 2000;
            // DatagramPacket p = new DatagramPacket(b,b.length,InetAddress.getByName(dst),pto);
            // cl.send(p);
            // cl.close();
        } catch (Exception e) {
            e.printStackTrace();
        }//catch
    }//main
}
