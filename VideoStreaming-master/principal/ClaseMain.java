package principal;


import javax.swing.JFrame;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClaseMain extends Application{
	public static Stage primaryStage;
	
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage=primaryStage;
		Parent root=new FXMLLoader().load(getClass().getResource("/PaginaPrincipal.fxml"));
	    Scene scene= new Scene(root);
	    primaryStage.setScene(scene);
	    primaryStage.show();
	     
	}
	public static void main(String[] args) throws Exception
	{
		launch(args);
		
	}


}

