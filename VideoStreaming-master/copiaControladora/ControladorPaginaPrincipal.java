package copiaControladora;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import principal.ClaseMain;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;

public class ControladorPaginaPrincipal {
	static Stage pagP;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
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
    private Button IngresaPrivado;

    @FXML
    private Button CrearSala;

    @FXML
    void BuscarP(ActionEvent event) {
    	System.out.println("Boton Buscar Pelicula");
    }

    @FXML
    void crearSala(ActionEvent event) throws IOException {
    	System.out.println("Boton crear sala");
    	ClaseMain.primaryStage.close();
    	Parent root=new FXMLLoader().load(getClass().getResource("/CrearSala.fxml"));//7
	    Scene scene= new Scene(root);
    	pagP = (Stage) ((Node)event.getSource( ) ).getScene().getWindow();
    	pagP.setScene(scene);
    	pagP.show();
    }

    @FXML
    void getCodigo(ActionEvent event) {
    	String IdP = IdPrivado.getText();
    	System.out.println(IdP);
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
   
}
