//Comentario prueba
package paquete;

import javax.swing.JFileChooser;
import java.net.*;
import java.io.*;
import javax.swing.JOptionPane;

public class CArchTCPB {
    public static void main(String []args){
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Escriba la direccion del servidor: ");
            String host = br.readLine();
            System.out.print("\n\nEscriba el puerto: ");
            int pto = Integer.parseInt(br.readLine());
            Socket cl = new Socket(host,pto);
            //boolean t = cl.getTcpNoDelay();
            int tamB = Integer.parseInt(JOptionPane.showInputDialog("Ingresa el tamaño para el buffer:"));
            int Nagle = JOptionPane.showConfirmDialog(null,"¿Deseas activar el algoritmo de Nagle?"," ",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
            if(Nagle == 0){
                cl.setTcpNoDelay(true);
            }
            else{
                cl.setTcpNoDelay(false);
            }
            JFileChooser jf = new JFileChooser();
            jf.setMultiSelectionEnabled(true);
            int r = jf.showOpenDialog(null);
            if(r == JFileChooser.APPROVE_OPTION){
                File []f = jf.getSelectedFiles();
                DataOutputStream dos = new DataOutputStream(cl.getOutputStream());
                dos.writeInt(tamB);//enviamos tamaño de buffer
                dos.flush();
                dos.writeInt(f.length);//enviamos numero de archivos
                dos.flush();
                dos.writeInt(Nagle);//enviamos estado de activacion del algoritmo Nagle
                dos.flush();
                //DataInputStream dis;
                for(int i=0; i<f.length;i++){
                    String archivo = f[i].getAbsolutePath();//ruta
                    String nombre = f[i].getName();//nombre
                    long tam = f[i].length();//tamaño
                    DataInputStream dis = new DataInputStream(new FileInputStream(archivo));
                    dos.writeUTF(nombre);//enviamos nombre
                    dos.flush();
                    dos.writeLong(tam);//enviamos tamaño
                    dos.flush();
                    byte[]b = new byte[(int)tam];
                    long enviados = 0;
                    int porcentaje,n;
                    while(enviados < tam){
                        n = dis.read(b);
                        dos.write(b,0,n);
                        dos.flush();
                        enviados += n;
                        porcentaje = (int)(enviados*100/tam);
                        System.out.print("Enviado "+porcentaje+"%\r");
                    }
                    dis.close();
                    System.out.println("\n\nArchivo Enviado");
                }
                //dis.close();
                dos.close();
                cl.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
