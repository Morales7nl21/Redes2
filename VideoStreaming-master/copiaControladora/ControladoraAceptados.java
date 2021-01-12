<<<<<<< HEAD
package copiaControladora;

=======
//Pagina Principal
package copiaControladora;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
>>>>>>> 2a77276120471e631d100f842c006cd77c553942
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
<<<<<<< HEAD
=======
import javafx.stage.Stage;
>>>>>>> 2a77276120471e631d100f842c006cd77c553942

public class ControladoraAceptados {

    @FXML
<<<<<<< HEAD
=======
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
>>>>>>> 2a77276120471e631d100f842c006cd77c553942
    private TextField Datos;

    @FXML
    private ComboBox<?> Genero;

    @FXML
    private Button Ingresa;

    @FXML
    private DialogPane MuestraDisponibles;

    @FXML
    private TextField IdPrivado;

    @FXML
<<<<<<< HEAD
    private Button CrearSala;

    @FXML
    void IngresaDatos(ActionEvent event) {

    }

    @FXML
    void RecibeDatos(ActionEvent event) {

    }

=======
    private Button IngresaPrivado;

    @FXML
    private Button CrearSala;

    @FXML
    void RecibeDatos(ActionEvent event) {
    	System.out.println("Boton Ingresa");
    }

    @FXML
    void getCodigo(ActionEvent event) {
    	String IdP = IdPrivado.getText();
    	System.out.println(IdP);
    }

    @FXML
    void otraInterfaz(ActionEvent event) {
    	System.out.println("Boton crear sala");
    	Sala s = new Sala();
    	//s.launch(null);
    }

    @FXML
    void initialize() {
        assert Datos != null : "fx:id=\"Datos\" was not injected: check your FXML file 'PaginaPrincipal.fxml'.";
        assert Genero != null : "fx:id=\"Genero\" was not injected: check your FXML file 'PaginaPrincipal.fxml'.";
        assert Ingresa != null : "fx:id=\"Ingresa\" was not injected: check your FXML file 'PaginaPrincipal.fxml'.";
        assert MuestraDisponibles != null : "fx:id=\"MuestraDisponibles\" was not injected: check your FXML file 'PaginaPrincipal.fxml'.";
        assert IdPrivado != null : "fx:id=\"IdPrivado\" was not injected: check your FXML file 'PaginaPrincipal.fxml'.";
        assert IngresaPrivado != null : "fx:id=\"IngresaPrivado\" was not injected: check your FXML file 'PaginaPrincipal.fxml'.";
        assert CrearSala != null : "fx:id=\"CrearSala\" was not injected: check your FXML file 'PaginaPrincipal.fxml'.";

    }
    
    class Sala extends Application{
    	public Stage primaryStage;
    	public void start(Stage primaryStage) throws Exception {
    		this.primaryStage=primaryStage;
    		Parent root=new FXMLLoader().load(getClass().getResource("/Peliculas.fxml"));//7
    	    Scene scene= new Scene(root);
    	    primaryStage.setScene(scene);
    	    primaryStage.show();
    	     
    	}
    	
    }
>>>>>>> 2a77276120471e631d100f842c006cd77c553942
}
