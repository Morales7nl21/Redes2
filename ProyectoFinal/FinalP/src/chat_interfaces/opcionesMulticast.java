package chat_interfaces;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author LENOVO 720
 */
public interface opcionesMulticast {
    public static final String HHOST  = "230.1.1.1"; //dir clase D valida
    //Se necesitan 2 puertos uno que esuchce y otro que envie info
    public static final int PORT = 4000;//puerto multicast     
    public static final int PORT2 = 4004;//puerto multicast   
    
    public static final int BUFFER_LENGHT=1024; //tamaño del buffer
    public static final String INTERFACE_NAME = "wlan1";
}
