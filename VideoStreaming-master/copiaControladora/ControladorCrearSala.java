package copiaControladora;

import com.gluonhq.charm.glisten.control.Icon;
import com.jfoenix.controls.JFXCheckBox;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import principal.Sala;

public class ControladorCrearSala {
	
	public boolean TipoSala = false; //se inicializa en privada
	public String NombreP;
	public String Resumen;
	public ArrayList<String> Generos;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Pane Cambia;

    @FXML
    private Text grande;

    @FXML
    private Text chico;

    @FXML
    private ToggleButton publico;

    @FXML
    private Icon Icon;

    @FXML
    private ToggleButton Privado;

    @FXML
    private Icon Icon2;

    @FXML
    private TextField NombrePel;

    @FXML
    private TextArea Descripcion;

    @FXML
    private Button CrearSala;

    @FXML
    private Button SubirArchivo;

    @FXML
    private Label LabelAr;

    @FXML
    private JFXCheckBox Terror;

    @FXML
    private JFXCheckBox Romance;

    @FXML
    private JFXCheckBox Accion;

    @FXML
    private JFXCheckBox Comedia;

    @FXML
    private JFXCheckBox Tragedia;

    @FXML
    private JFXCheckBox Ficcion;

    @FXML
    void crearSala(ActionEvent event) {
    	//System.out.println("Finaliza creacion de sala");
    	NombreP = NombrePel.getText();
    	if(NombreP.equals("")) {
    		System.out.println("Nombre vacio");
    		JOptionPane.showMessageDialog(null, "Falta nombre de la pelicula",
    				"Nombre de Pelicula",JOptionPane.WARNING_MESSAGE);
    		//lanzar Exception
    	}
    	Resumen = Descripcion.getText();
    	if(Resumen.equals("")) {
    		Resumen = "Sin Informacion";
    	}
    	Generos = new ArrayList<>();
    	if(Terror.isSelected()) {
    		Generos.add("Terror");
    	}
    	if(Romance.isSelected()) {
    		Generos.add("Romance");
    	}
    	if(Accion.isSelected()) {
    		Generos.add("Accion");
    	}
    	if(Comedia.isSelected()) {
    		Generos.add("Comedia");
    	}
    	if(Tragedia.isSelected()) {
    		Generos.add("Tragedia");
    	}
    	if(Ficcion.isSelected()) {
    		Generos.add("Ficcion");
    	}
    	if(Generos.size() == 0){
    		Generos.add("Sin Genero");
    	}
    	System.out.println("Llega");
    	//Sala salaN = new Sala(TipoSala,NombreP,Resumen,Generos);
    }

    @FXML
    void selSalaPrivada(ActionEvent event) {
    	//System.out.println("Se selecciona sala Privada");
    	TipoSala = false;
    }

    @FXML
    void selSalaPublica(ActionEvent event) {
    	//System.out.println("Se selecciona sala Publica");
    	TipoSala = true;
    }

    @FXML
    void selecVideo(ActionEvent event) {
    	//System.out.println("Seleccionar Video a trasmitir");
    }

    @FXML
    void initialize() {
        assert Cambia != null : "fx:id=\"Cambia\" was not injected: check your FXML file 'CrearSala.fxml'.";
        assert grande != null : "fx:id=\"grande\" was not injected: check your FXML file 'CrearSala.fxml'.";
        assert chico != null : "fx:id=\"chico\" was not injected: check your FXML file 'CrearSala.fxml'.";
        assert publico != null : "fx:id=\"publico\" was not injected: check your FXML file 'CrearSala.fxml'.";
        assert Icon != null : "fx:id=\"Icon\" was not injected: check your FXML file 'CrearSala.fxml'.";
        assert Privado != null : "fx:id=\"Privado\" was not injected: check your FXML file 'CrearSala.fxml'.";
        assert Icon2 != null : "fx:id=\"Icon2\" was not injected: check your FXML file 'CrearSala.fxml'.";
        assert NombrePel != null : "fx:id=\"NombrePel\" was not injected: check your FXML file 'CrearSala.fxml'.";
        assert Descripcion != null : "fx:id=\"Descripcion\" was not injected: check your FXML file 'CrearSala.fxml'.";
        assert CrearSala != null : "fx:id=\"CrearSala\" was not injected: check your FXML file 'CrearSala.fxml'.";
        assert SubirArchivo != null : "fx:id=\"SubirArchivo\" was not injected: check your FXML file 'CrearSala.fxml'.";
        assert LabelAr != null : "fx:id=\"LabelAr\" was not injected: check your FXML file 'CrearSala.fxml'.";
        assert Terror != null : "fx:id=\"Terror\" was not injected: check your FXML file 'CrearSala.fxml'.";
        assert Romance != null : "fx:id=\"Romance\" was not injected: check your FXML file 'CrearSala.fxml'.";
        assert Accion != null : "fx:id=\"Accion\" was not injected: check your FXML file 'CrearSala.fxml'.";
        assert Comedia != null : "fx:id=\"Comedia\" was not injected: check your FXML file 'CrearSala.fxml'.";
        assert Tragedia != null : "fx:id=\"Tragedia\" was not injected: check your FXML file 'CrearSala.fxml'.";
        assert Ficcion != null : "fx:id=\"Ficcion\" was not injected: check your FXML file 'CrearSala.fxml'.";

    }
}
