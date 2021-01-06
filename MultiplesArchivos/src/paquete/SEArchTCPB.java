/*
SERVIDOR DE ECO
Recibe archivos y los manda a servidor final
*/
package paquete;

import java.net.*;
import java.io.*;

public class SEArchTCPB {
    
    public static void main(String []args){
        try{
            //Creamos el socket
            ServerSocket s = new ServerSocket(7000);
            //Iniciamos ciclo infinito del Servidor
            for(;;){
                //Esperamos una conexion
                Socket cl = s.accept();
                System.out.println("Conexion establecida desde: "+cl.getInetAddress()+" ; "+cl.getPort());
                DataInputStream dis = new DataInputStream(cl.getInputStream());
                int tamB = dis.readInt();   //recibe tamaño del buffer
                int nArch = dis.readInt();  //recibe numero de archivos
                byte []b = new byte[tamB];
                for(int i=0;i<nArch;i++){
                    String nombre = dis.readUTF();
                    System.out.println("Recibimos el archivo: "+nombre);
                    long tam = dis.readLong();
                    DataOutputStream dos = new DataOutputStream(new FileOutputStream(nombre));
                    long recibidos = 0;
                    int n, porcentaje;
                    long faltante;
                    //Ciclo para recibir por partes el archivo
                    while(recibidos < tam){
                        faltante = tam - recibidos;
                        if(faltante < tamB){
                            byte []bf = new byte[(int)faltante];
                            n = dis.read(bf);
                            dos.write(bf,0,n);
                            dos.flush();
                        }
                        else{
                            n = dis.read(b);
                            dos.write(b,0,n);
                            dos.flush();
                        }
                        recibidos += n;
                        porcentaje = (int)(recibidos*100/tam);
                        System.out.print("Recibido "+porcentaje+"%\r");
                    }
                    System.out.println("\n\nArchivo Recibido.");
                    dos.close();
                }
                
                dis.close();
                cl.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}
