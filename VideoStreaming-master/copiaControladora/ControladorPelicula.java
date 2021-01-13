package copiaControladora;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class ControladorPelicula {
	
	public ControladorPelicula(String Nombre,String Hora,String Genero,String Resumen) {
		
	}
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView Imagen;

    @FXML
    private Hyperlink TextResumen;

    @FXML
    private Button Entrar;


    @FXML
    private Label LabelHora;

    @FXML
    private Label LabelNombre;

    @FXML
    private Label LabelGenero;
    
    
    @FXML
    void showResumen(ActionEvent event) {
    	JOptionPane.showMessageDialog(null, "Esta historia trata a cerca de ...",
				"Resumen",JOptionPane.INFORMATION_MESSAGE); 
    }

    @FXML
    void unirseASala(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert Imagen != null : "fx:id=\"Imagen\" was not injected: check your FXML file 'Peliculas.fxml'.";
        assert LabelHora != null : "fx:id=\"LabelHora\" was not injected: check your FXML file 'Peliculas.fxml'.";
        assert LabelNombre != null : "fx:id=\"LabelNombre\" was not injected: check your FXML file 'Peliculas.fxml'.";
        assert LabelGenero != null : "fx:id=\"LabelGenero\" was not injected: check your FXML file 'Peliculas.fxml'.";
        assert TextResumen != null : "fx:id=\"TextResumen\" was not injected: check your FXML file 'Peliculas.fxml'.";
        assert Entrar != null : "fx:id=\"Entrar\" was not injected: check your FXML file 'Peliculas.fxml'.";

    }
}

