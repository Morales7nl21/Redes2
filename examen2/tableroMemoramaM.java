
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.temporal.Temporal;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author LENOVO 720
 */
public class tableroMemoramaM extends JFrame implements ActionListener {

    private int WIDTH_CARD = 80, HEIGHT_CARD = 80;
    JButton arreglo[] = new JButton[40];
    Carta cartas[] = new Carta[40];
    ImageIcon trasera;
    String path = "";
    String pruebaFondo = "";
    //Primer par a voltear pa comparar
    Carta tmp;
    int par = 0;
    public tableroMemoramaM(String path) {
        this.pruebaFondo = path + "/imagen0.jpg";

        //JOptionPane.showMessageDialog(null,"prueba fondo: " + pruebaFondo);
        this.path = path;
        tmp = new Carta(0, pruebaFondo, 0);
        iniciarTablero();
    }

    public tableroMemoramaM() {
        //this.path=path;
        iniciarTablero();
    }

    private final void iniciarTablero() {
        this.setSize(415, 680);
        this.setTitle("Memorama");
        this.setLayout(null);//Poner componentes donde queramos
        this.setLocationRelativeTo(null);//Pa que se centre
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        int contador = 0;
        //Creamos interfaz gráfica
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 8; j++) {
                trasera = new ImageIcon(pruebaFondo);
                //JOptionPane.showMessageDialog(null, pruebaFondo);                
                JButton btn = new JButton("", new ImageIcon(trasera.getImage().getScaledInstance(WIDTH_CARD, HEIGHT_CARD, Image.SCALE_SMOOTH))); //Al iniciar el juego por defecto tendran ese fondo
                btn.setBounds((i) * WIDTH_CARD, (j) * HEIGHT_CARD, WIDTH_CARD, HEIGHT_CARD);
                btn.setName(contador + "");//Al sumar unas comillas se transforma en cadena
                btn.addActionListener(this);
                arreglo[contador] = btn;
                contador++;
                this.add(btn);

            }
        }
        revolver(1);
    }

    private void revolver(int control) {

        int c = 0;
        
        
        for (int i = 1; i <= 20; i++) {
            Carta carta1 = new Carta(i, path + "/imagen" + i + ".jpg", c);
            Carta carta2 = new Carta(i, path + "/imagen" + i + ".jpg", c + 1);
            cartas[c] = carta1;
            c++;
            cartas[c] = carta2;
            c++;
        }
        if (control == 1) {
            int llenar = 0;
            Carta cartaTemporal[] = new Carta[40];
            //Cuando manejemos arreglos de objetos es necesario llenarlo con elementos inicializarlo
            for (int i = 0; i < cartaTemporal.length; i++) {
                cartaTemporal[i] = new Carta(0,path+"/imagen0.jpg",-1);
            }
            while (llenar<=39) {
                //Numero entre 0 y 39 que son posiciones de arreglo
                int aleatorio = ((int) (Math.random() * 40));
                if (buscarNum(aleatorio, cartaTemporal)) {
                    cartaTemporal[llenar] = cartas[aleatorio]; //mandamos un al azar
                    cartaTemporal[llenar].btn = arreglo[llenar]; //Conectamos los botones con el arreglo
                    llenar++;
                }
            }
            cartas=cartaTemporal;//asigamos a lo que ya desordenamos

        }
    }
    
    private boolean buscarNum(int aleatorio, Carta[] cartaTemporal) {
        int con=0;
        for(Carta c1 : cartaTemporal){
            if(aleatorio == c1.posicion){
                con++;
            }
        }
        return (con<1);
    }
    
    @Override
    public void actionPerformed(ActionEvent arg0) {
        for (int i = 0; i < arreglo.length; i++) {
            if(cartas[i].btn == arg0.getSource() && cartas[i].revelado == false){
                cartas[i].btn.setIcon(cartas[i].img);
                if(par==0){
                    cartas[i].revelado=true;
                    tmp = cartas[i];
                    par=1;
                }else{
                    par=0;
                    if(cartas[i].valor ==tmp.valor){
                        cartas[i].revelado=true;
                        boolean bandera = true;
                        for(Carta elemento : cartas){
                            if(elemento.revelado==false){
                                bandera=false;
                                break;
                            }
                        }
                        if(bandera){
                            JOptionPane.showMessageDialog(this, "has ganado");
                        }
                    }else{
                        try {
                            cartas[i].btn.update(cartas[i].btn.getGraphics());
                            Thread.sleep(500);
                            tapar(i);
                        } catch (InterruptedException e) {
                            System.err.println(e);
                        }
                    
                    }
                }
            }
        }

    }
    private void tapar(int a) {
        
       cartas[a].btn.setIcon(new ImageIcon(trasera.getImage().getScaledInstance(WIDTH_CARD, HEIGHT_CARD, Image.SCALE_SMOOTH)));
       cartas[Integer.valueOf(tmp.btn.getName())].revelado=false;
       cartas[Integer.valueOf(tmp.btn.getName())].btn.setIcon(new ImageIcon(trasera.getImage().getScaledInstance(WIDTH_CARD, HEIGHT_CARD, Image.SCALE_SMOOTH)));
    }
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new tableroMemoramaM().setVisible(true);
        });
        //new tableroMemoramaM().setVisible(true);
    }

    

}
