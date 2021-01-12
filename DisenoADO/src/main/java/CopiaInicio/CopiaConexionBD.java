package CopiaInicio;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;




//conexion con la base de datos
public class CopiaConexionBD {
	public static java.sql.Connection conectar;
	 static Stage ventana;
	                                   //"com.mysql.cj.jdbc.Driver"
    private static final String driver="com.mysql.cj.jdbc.Driver";//utiliza el driver para acceder a la base
    private static final String url ="jdbc:mysql://db4free.net:3306/usuariosayon";//direccion de la base de datos
    private static final String user="ayonx1999";// usuario del host
    private static final String password="emalacm1pt";//contraseña del host

 
   //constructor que permite conectar a la base de datos  
	public CopiaConexionBD(String usuario, String contraseña) {
	
		//null significa que la base de datos siempre incicia desconectada
		conectar=null;
	
		
	//casos en donde la base de datos puede estar funcional o no 	
			try {
				Class.forName(driver);
				
				conectar=DriverManager.getConnection(url, usuario,contraseña);
				if(conectar!=null) {
					JOptionPane.showMessageDialog(null,"Conexion establecida",JOptionPane.ICON_PROPERTY,JOptionPane.INFORMATION_MESSAGE);
				
				}			
			} catch (Exception e) {
					JOptionPane.showMessageDialog(null,"Usuario o contraseña incorrectos",JOptionPane.ICON_PROPERTY,JOptionPane.ERROR_MESSAGE);
			}
			
			
	}
	
	public CopiaConexionBD(int i) {
		try {
			Class.forName(driver);
			conectar=DriverManager.getConnection(url, user, password);
			
		} catch (Exception e ) {
		}
		
	}
	
	
	//con este nos retorna la conexion
	public static java.sql.Connection obtenConexion() {
		return conectar;
	}
	
	//metodo para desconetar de la base
	public static void desconectar(){
		conectar=null;
		if(conectar==null)
			JOptionPane.showMessageDialog(null,"Conexion terminada",JOptionPane.ICON_PROPERTY,JOptionPane.INFORMATION_MESSAGE);
			
	}
	

}
