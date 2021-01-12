package com.copiaControladora;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.scene.Node;///cuiddado
import CopiaInicio.CopiaConexionBD;
import CopiaInicio.CopiaPagIngreso;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


/* Se guarda  la contraseña y los usuarios para que sean accedidos desde cualquier parte del codigo
 *
 */
class datos{
static String usuario, contraseña;

public static  String getUsuario() {
	return usuario;
}

public  static void setUsuario(String usuario1) {
	usuario = usuario1;
}

public  static String getContraseña() {
	return contraseña;
}

public static  void setContraseña(String contraseña1) {
	contraseña = contraseña1;
}
  
  
  

}


/*Creamos un hilo para que al momento de conectar con la base de datos el programe, no espere
nesesariamente la conexion, osea que no se quede trabado*/
class ProcesoBD extends Thread{
	public void run() {
		 try {
			 datos datos= new datos();
			 
			 CopiaConexionBD activaconexion= new CopiaConexionBD(datos.getUsuario(),datos.getContraseña());	 
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}


/* ???  Al parecer esto hace que nuestra base de datos */

public class CompiaControladorLog implements Initializable {
	 static Stage ventana;
	@FXML
	public TextField Usuario;

	@FXML
	public PasswordField Contraseña;

	@FXML
	private Button Entrar;
	
    @FXML
    private Button Cerrar;

    @FXML
    private Button Cambia;
    

/* Cierra sesion de la base de datos*/
   
    @FXML
    void CerrarSesion(ActionEvent event) {
    	CopiaConexionBD.desconectar();

    }

   /*Es una manera que encontre para que la contraseña y el usuario se puedan mantener guardados
    si ejecutaramos directo aqui el comando se quedaria trabado por lo tanto debemos crear un hilo 
     independiente para que la base de datos opere sin interruciones.
    */
    
	@FXML
	void ConectaBD(ActionEvent event) throws IOException, InterruptedException{
//guarda usuaario y contraseña para pasar de clase a clase
		datos.setUsuario(Usuario.getText());    
		datos.setContraseña(Contraseña.getText());
// comienza el hilo		
        ProcesoBD  activa= new ProcesoBD();
        activa.start();
	}
	
	
/* Esta funcion por medio del evento mouse event cuando pasemos por un 
   boton este cambie de color por un momento
 */
	   @FXML
	  void Ilumina(MouseEvent event) {
		   if (event.getSource() == Cambia)
			   Cambia.setBackground(
						new Background(new BackgroundFill(Color.rgb(31, 31, 31), CornerRadii.EMPTY, Insets.EMPTY)));
			else if (event.getSource() == Entrar)
				 Entrar.setBackground(
						new Background(new BackgroundFill(Color.rgb(31, 31, 31), CornerRadii.EMPTY, Insets.EMPTY)));
			else if (event.getSource() == Cerrar)
				Cerrar.setBackground(
						new Background(new BackgroundFill(Color.rgb(31, 31, 31), CornerRadii.EMPTY, Insets.EMPTY)));
	   }
    
/* Funcion que nos permite pasar de ventana   cuando se presiona el boton cambia*/
    @FXML
    public void Ventana(ActionEvent event) throws IOException {
    	CopiaPagIngreso.primaryStage.close();
    	Parent root=new FXMLLoader().load(getClass().getResource("/CopiaTablas.fxml"));
	    Scene scene= new Scene(root);
	     ventana= (Stage) ((Node)event.getSource( ) ).getScene().getWindow();
	     ventana.setScene(scene);
	     ventana.show();
   
    }


    
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

}
