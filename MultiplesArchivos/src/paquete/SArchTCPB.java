package paquete;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

public class SArchTCPB {

    public static void main(String[] args) {
        //boolean flagDir=true;
        boolean flagEnvio = false; //sin usar, nunca llega
        int tamB, nagle, nArch;

        ArrayList<String> nombresArchivos = new ArrayList<>(); //Almacenara los nombres de los archivos que recibio para guardalros ahi

        try {
            //Creamos el socket
            ServerSocket s = new ServerSocket(4000);

            //Iniciamos ciclo infinito del Servidor
            for (;;) {
                try ( //Esperamos una conexion
                         Socket cl = s.accept()) {
                    System.out.println("Conexion establecida desde: " + cl.getInetAddress() + " ; " + cl.getPort());
                    try ( DataInputStream dis = new DataInputStream(cl.getInputStream())) {
                        tamB = dis.readInt();
                        System.out.println("BUFFER: " + tamB);
                        nArch = dis.readInt();
                        System.out.println("nARCH: " + nArch);
                        //nagle = dis.readInt();
                        //System.out.println("NAGLE: " + nagle);
                        byte[] b = new byte[tamB];
                        for (int i = 0; i < nArch; i++) {
                            String nombre = dis.readUTF();
                            nombresArchivos.add(nombre);
                            System.out.println("Recibimos el archivo: " + nombre);
                            long tam = dis.readLong();
                            try ( DataOutputStream dos = new DataOutputStream(new FileOutputStream(nombre))) {
                                long recibidos = 0;
                                int n, porcentaje;
                                long faltante;
                                //Ciclo para recibir por partes el archivo
                                while (recibidos < tam) {
                                    faltante = tam - recibidos;
                                    if (faltante < tamB) {
                                        byte[] bf = new byte[(int) faltante];
                                        n = dis.read(bf);
                                        dos.write(bf, 0, n);
                                        dos.flush();
                                    } else {
                                        n = dis.read(b);
                                        dos.write(b, 0, n);
                                        dos.flush();
                                    }
                                    recibidos += n;
                                    porcentaje = (int) (recibidos * 100 / tam);
                                    System.out.print("Recibido " + porcentaje + "%\r");
                                }
                                System.out.println("\n\nArchivo Recibido.");
                                //nagle = dis.readInt();
                                //System.out.println("NAGLE: " + nagle);                                
                            }

                        }
                        flagEnvio = true;
                        nagle = dis.readInt();
                        System.out.println("NAGLE: " + nagle);

                        if (flagEnvio) {
                            //se procedera a enviar todo al otro vato
                            try {
                                Socket cl2 = new Socket("192.168.100.77", 9000);
                                for (;;) {
                                    try (DataOutputStream dose = new DataOutputStream(cl2.getOutputStream())) {
                                        if (nagle == 0) {
                                            cl2.setTcpNoDelay(true);
                                        } else {
                                            cl2.setTcpNoDelay(false);
                                        }
                                        dose.writeInt(tamB);//enviamos tamaño de buffer
                                        dose.flush();
                                        dose.writeInt(nombresArchivos.size());//enviamos numero de archivos
                                        dose.flush();
                                        
                                        Iterator<String> archivo = nombresArchivos.iterator();
                                        while (archivo.hasNext()) {
                                            //Mediante archivo se comprobará el archivo en la carpeta para re enviarlo
                                            String elemento = archivo.next();
                                            File ficheroe = new File(elemento);
                                            String archivoe = ficheroe.getAbsolutePath();//ruta
                                            String nombree = ficheroe.getName();//nombre
                                            long tame = ficheroe.length();//tamaño
                                            System.out.println("paquete.SArchTCPB.main(): " + archivoe + " --- " + nombree + "---" + tame);
                                            try (DataInputStream dise = new DataInputStream(new FileInputStream(archivoe))) {
                                                dose.writeUTF(nombree);//enviamos nombre
                                                dose.flush();
                                                dose.writeLong(tame);//enviamos tamaño
                                                dose.flush();
                                                byte[] be = new byte[(int) tame];
                                                long enviados = 0;
                                                int porcentajee, ne;
                                                while (enviados < tame) {
                                                    ne = dise.read(be);
                                                    dose.write(be, 0, ne);
                                                    dose.flush();
                                                    enviados += ne;
                                                    porcentajee = (int) (enviados * 100 / tame);
                                                    System.out.print("Enviado " + porcentajee + "%\r");
                                                }
                                            } //enviamos nombre
                                            System.out.println("\n\nArchivo Enviado");
                                            System.out.println(elemento);
                                        }
                                        dose.writeInt(nagle);
                                        dose.flush();
                                    }
                                    cl2.close();
                                }
                            } catch (IOException e) {
                                System.err.println(e);
                            } finally {
                                flagEnvio = false;
                            }
                        } //End if flag

                    }
                }
            }
        } catch (IOException e) {
        }
    }
}
