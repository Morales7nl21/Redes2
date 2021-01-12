package com.copiaControladora;

import java.io.File;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import CopiaInicio.CopiaConexionBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class CopiaControladorRenovaciones implements Initializable {
	
	static List<String> archivos;
	@FXML
	private Button Renueva;
	
	@FXML
    private TableView<CopiaTablaModelo> TablaUsuarios;

    @FXML
    private TableColumn<CopiaTablaModelo, String> Boleta;

    @FXML
    private TableColumn<CopiaTablaModelo,String> Nombre;

    @FXML
    private TableColumn<CopiaTablaModelo, String> Correo;

    @FXML
    private TableColumn<CopiaTablaModelo, String> Casillero;

    @FXML
    private TextField DatosAcep;

    @FXML
    private Button BuscaAcep;


    @FXML
    void BuscaUsuarios(ActionEvent event) {
    	String boleta=DatosAcep.getText();
    	
    	ObservableList<CopiaTablaModelo> datos = FXCollections.observableArrayList();
		try {
			java.sql.Statement pst = CopiaConexionBD.obtenConexion().createStatement();

			ResultSet rs = pst.executeQuery("SELECT * FROM Renovaciones WHERE Boleta=" + boleta);

			while (rs.next()) {
				datos.add(new CopiaTablaModelo(rs.getString("Boleta"), rs.getString("Nombre"), rs.getString("Casillero"),
						rs.getString("Correo")));

			}

		} catch (Exception e) {

		}
           TablaUsuarios.setItems(datos);

    }

    ObservableList<CopiaTablaModelo> getDatos() {
		ObservableList<CopiaTablaModelo> datos = FXCollections.observableArrayList();
		try {

			java.sql.Statement pst = CopiaConexionBD.obtenConexion().createStatement();
			ResultSet rs = pst.executeQuery("SELECT * FROM Renovaciones");

			while (rs.next()) {
				datos.add(new CopiaTablaModelo(rs.getString("Boleta"), rs.getString("Nombre"), rs.getString("Casillero"),
						rs.getString("Correo")));

			}

		} catch (Exception e) {

		}

		return datos;

	}
    
    @FXML
    void RenuevaUsuarios(ActionEvent event) throws SQLException {
     	java.sql.Statement Aceptados;
    		java.sql.Statement Elimina;
    		String boleta = TablaUsuarios.getSelectionModel().getSelectedItem().Boleta;
        	Aceptados = CopiaConexionBD.obtenConexion().createStatement();
    		Aceptados.executeUpdate("INSERT INTO Aceptados(Boleta, Nombre,Renovacion,CasillerosDisponibles,Correo) SELECT Renovaciones.Boleta,Renovaciones.Nombre,Renovaciones.Renovacion,Renovaciones.Casillero,Renovaciones.Correo FROM Renovaciones WHERE Renovaciones.Boleta="+boleta);
    		Elimina = CopiaConexionBD.obtenConexion().createStatement();
    		Elimina.executeUpdate("DELETE FROM Renovaciones WHERE boleta="+boleta);
    		TablaUsuarios.setItems(getDatos());

    }
    
    @FXML
    void EnviaCorreo(ActionEvent event) throws SQLException {
    	java.sql.Statement consulta= CopiaConexionBD.obtenConexion().createStatement();
    	ResultSet CorreoConsulta = consulta.executeQuery("SELECT Correo FROM Renovaciones");
    	FileChooser Fc = new FileChooser();
    	Hilo proceso;
    	String Correo;
		// Fc.setInitialDirectory(new File("ruta inicial"));
    	
		Fc.getExtensionFilters().addAll(new ExtensionFilter("Word Files", archivos));
		File archivo = Fc.showOpenDialog(null);
		

		if (archivo != null) {
			
			System.out.println("Seleccionado" + archivo.getAbsolutePath() + "\n" + archivo.getName());
              while(CorreoConsulta.next()) {
            	Correo=CorreoConsulta.getString("Correo");
            	proceso = new Hilo(Correo, archivo);
      			proceso.start();	
			}
 
          	JOptionPane.showMessageDialog(null, "Se ha enviado el Correo");
		}

    }


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		Boleta.setCellValueFactory(new PropertyValueFactory("Boleta"));
		Nombre.setCellValueFactory(new PropertyValueFactory("Nombre"));
		Casillero.setCellValueFactory(new PropertyValueFactory("Casillero"));
		Correo.setCellValueFactory(new PropertyValueFactory("Correo"));

		archivos = new ArrayList<String>();
		archivos.add("*.doc");
		archivos.add("*.docx");
		archivos.add("*.DOC");
		archivos.add("*.DOCX");

		
	
		TablaUsuarios.setItems(getDatos());

		
	}

}
