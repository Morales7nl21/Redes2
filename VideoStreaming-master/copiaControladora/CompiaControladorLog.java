
package copiaControladora;
//Peliculas
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class CompiaControladorLog {

    @FXML
<<<<<<< HEAD
=======
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
>>>>>>> 2a77276120471e631d100f842c006cd77c553942
    private ImageView Imagen;

    @FXML
    private Button Entrar;

    @FXML
    void ConectaBD(ActionEvent event) {

    }

    @FXML
    void Ilumina(MouseEvent event) {

    }

    @FXML
    void initialize() {
        assert Imagen != null : "fx:id=\"Imagen\" was not injected: check your FXML file 'Peliculas.fxml'.";
        assert Entrar != null : "fx:id=\"Entrar\" was not injected: check your FXML file 'Peliculas.fxml'.";

    }
}
