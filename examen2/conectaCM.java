/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
// C:\Users\LENOVO 720\Desktop\IPN Documents\6toSemestre\Redes\RMI\examen2Redes\Clientes
/**
 *
 * @author LENOVO 720
 */
public class conectaCM {

    public static void main(String[] args) {
        CEcoTCPNB cl = new CEcoTCPNB();
        boolean onetap = true;
        if (onetap) {
            cl.Cliente();
            onetap = false;
        }
    }
}
