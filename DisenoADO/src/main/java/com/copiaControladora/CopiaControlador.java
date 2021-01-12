package com.copiaControladora;


import java.io.IOException;

import CopiaInicio.CopiaPagIngreso;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;





public class CopiaControlador{
	static boolean estado;
	@FXML
	private Button Usuario;

	@FXML
	private Button Asistencia;

	@FXML
	private Button UsuariosRech;

	@FXML
	private ToggleButton AsistenciaBoton;

	@FXML
	private Pane Cambia;

	@FXML
	private Text grande;

	@FXML
	private Text chico;

	@FXML
	private Button AsistenciaPasa;
   
	@FXML
    private Button Login;
	
    @FXML
    private BorderPane BorderPane;



  /*
 	Cuando presionamos el boton Usuario cambia nuestro pane 
 	y el texto adentro de el.
  */
	@FXML
	void UsuarioFuncion(ActionEvent event) throws IOException {
		grande.setText("Registros");
		chico.setText("Asignaciones/Renovaciones");
		Cambia.setBackground(
				new Background(new BackgroundFill(Color.rgb(110, 43, 220), CornerRadii.EMPTY, Insets.EMPTY)));

		// Ejecuta la escena con respecto al automata seleccionado
	    Parent root = new FXMLLoader().load(getClass().getResource("/Aceptados.fxml"));
		BorderPane.setCenter(root);
	}
	
	/*
 	Cuando presionamos el boton Rechazados cambia nuestro pane 
 	y el texto adentro de el.
  */

	@FXML
	void RechazadosFuncion(ActionEvent event) throws IOException {
		grande.setText("Renovaciones");
		chico.setText("Asignaciones/Registros");
		Cambia.setBackground(
				new Background(new BackgroundFill(Color.rgb(157, 104, 202), CornerRadii.EMPTY, Insets.EMPTY)));
		
		// Ejecuta la escena con respecto al automata seleccionado
	    Parent root = new FXMLLoader().load(getClass().getResource("/Rechazados.fxml"));
		BorderPane.setCenter(root);

	}

	/*
 	Cuando presionamos el boton Asistencia cambia nuestro pane 
 	y el texto adentro de el.
  */
	@FXML
	void AsistenciaFuncion(ActionEvent event) throws IOException {

		chico.setText("Registros/Renovaciones");
		grande.setText("Asignaciones");
		Cambia.setBackground(new Background(new BackgroundFill(Color.rgb(100, 50, 240), CornerRadii.EMPTY, Insets.EMPTY)));
	
		// Ejecuta la escena con respecto al automata seleccionado
	    Parent root = new FXMLLoader().load(getClass().getResource("/Ingresados.fxml"));
		BorderPane.setCenter(root);

	
	}

	
/*Al pasar por el boton por donde el cursor esta
   este cambia de color 
   	
 */
	@FXML
	void Mueve(MouseEvent event) {
		if (event.getSource() == Usuario)
			Usuario.setBackground(
					new Background(new BackgroundFill(Color.rgb(31, 31, 31), CornerRadii.EMPTY, Insets.EMPTY)));
		else if (event.getSource() == UsuariosRech)
			UsuariosRech.setBackground(
					new Background(new BackgroundFill(Color.rgb(31, 31, 31), CornerRadii.EMPTY, Insets.EMPTY)));
		else if (event.getSource() == Asistencia)
			Asistencia.setBackground(
					new Background(new BackgroundFill(Color.rgb(31, 31, 31), CornerRadii.EMPTY, Insets.EMPTY)));
		else if (event.getSource() == AsistenciaPasa)
			AsistenciaPasa.setBackground(
					new Background(new BackgroundFill(Color.rgb(31, 31, 31), CornerRadii.EMPTY, Insets.EMPTY)));
		else if (event.getSource() ==AsistenciaBoton)
			AsistenciaBoton.setBackground(
					new Background(new BackgroundFill(Color.rgb(31, 31, 31), CornerRadii.EMPTY, Insets.EMPTY)));
		else if (event.getSource() ==Login)
			Login.setBackground(
					new Background(new BackgroundFill(Color.rgb(31, 31, 31), CornerRadii.EMPTY, Insets.EMPTY)));
			
			

	}
	
	


	
	//Se supone que limpia todo lo que halla creado el usuario
	@FXML
    void Regresa(ActionEvent event) throws IOException {
		CompiaControladorLog.ventana.close();
		Parent root=new FXMLLoader().load(getClass().getResource("/CopiaLogin.fxml"));///7
	    Scene scene= new Scene(root);
	     Stage ventana= (Stage) ((Node)event.getSource( ) ).getScene().getWindow();
	     ventana.setScene(scene);
	     ventana.show();
		
    
	   
       
    }
	
	

}
