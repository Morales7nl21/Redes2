
import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author LENOVO 720
 */
class Carta {
 
    //valor -> saber si hay un par
    int posicion=0, valor =0;  
   
    boolean revelado = false;
    ImageIcon img,imgscaled;
    Icon icon;
    JButton btn;
    public Carta(int valor, String nombre, int posicion){
        this.valor=valor; 
        this.posicion=posicion;
        img=new ImageIcon(nombre);//Cargamos una imagen
        try {            
            img = new ImageIcon(img.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    
}
