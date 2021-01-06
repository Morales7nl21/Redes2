package paquete;

import java.net.*;
import java.io.*;

public class SArchTCPB {
    
    public static void main(String []args){
        //boolean flagDir=true;
        boolean flagEnvio=false;
        
        ArrayList<String> nombresArchivos = new ArrayList<String>(); //Almacenara los nombres de los archivos que recibio para guardalros ahi

        try{
            //Creamos el socket
            ServerSocket s = new ServerSocket(7000);
            
            //Iniciamos ciclo infinito del Servidor
            for(;;){
                //Esperamos una conexion
                Socket cl = s.accept();
                System.out.println("Conexion establecida desde: "+cl.getInetAddress()+" ; "+cl.getPort());
               
                DataInputStream dis = new DataInputStream(cl.getInputStream());
                int tamB = dis.readInt();
                int nArch = dis.readInt();
                byte []b = new byte[tamB];
                for(int i=0;i<nArch;i++){                    
                    String nombre = dis.readUTF();
                    nombresArchivos.add(nombre);
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
                    flagEnvio=true;
                    dos.close();
                    
                    
                }
                
                dis.close();
                cl.close();
                
                
                if(flagEnvio){
                    //se procedera a enviar todo al otro vato
                    try{
                        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                        System.out.print("Escriba la direccion del servidor: ");
                        String host = br.readLine();
                        //System.out.print("\n\nEscriba el puerto: ");
                        //int pto = Integer.parseInt(br.readLine());
                        Socket cl2 = new Socket(host,7005);
                        Iterator<String> archivo = nombresArchivos.iterator();
                        
                        while(archivo.hasNext()){
                            //Mediante archivo se comprobará el archivo en la carpeta para re enviarlo
                            String elemento = archivo.next();
                            
                        }           

                    
                    }catch(Exception e){

                    }    
                
                
                }
                
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
