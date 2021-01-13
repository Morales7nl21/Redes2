package principal;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClaseMain extends Application{
	public static Stage primaryStage;
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage=primaryStage;
		Parent root=new FXMLLoader().load(getClass().getResource("/PaginaPrincipal.fxml"));//7
	    Scene scene= new Scene(root);
	    primaryStage.setScene(scene);
	    primaryStage.show();
	     
	}
	
	
	public static void main(String[] args) throws Exception
	{
		System.out.println(args.length); 
		launch(args);
		
	}


}

