package copiaControladora;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.input.MouseEvent;

public class CopiaLogin {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Hyperlink Resumen;

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
        assert Resumen != null : "fx:id=\"Resumen\" was not injected: check your FXML file 'CopiaLogin.fxml'.";
        assert Entrar != null : "fx:id=\"Entrar\" was not injected: check your FXML file 'CopiaLogin.fxml'.";

    }
}
