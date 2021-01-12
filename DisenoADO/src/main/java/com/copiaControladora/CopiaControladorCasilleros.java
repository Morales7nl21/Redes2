package com.copiaControladora;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.mysql.cj.xdevapi.Statement;

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

public class CopiaControladorCasilleros  implements Initializable{
	
	@FXML
    private TableView<CopiaTablaModelo> TablaUsuarios;

    @FXML
    private TableColumn<CopiaTablaModelo, String> Boleta;

    @FXML
    private TableColumn<CopiaTablaModelo,String> Nombre;

    @FXML
    private TableColumn<CopiaTablaModelo, String> Renovacion;

    @FXML
    private TableColumn<CopiaTablaModelo, String> CasillerosDisponibles;
    
    @FXML
    private Button BuscaAcep;

    @FXML
    private TextField DatosAcep;
   
    @FXML
    private Button Renueva;



    ObservableList<CopiaTablaModelo> getDatos() {
		ObservableList<CopiaTablaModelo> datos = FXCollections.observableArrayList();
		try {

			java.sql.Statement pst = CopiaConexionBD.obtenConexion().createStatement();
			ResultSet rs = pst.executeQuery("SELECT * FROM Aceptados");

			while (rs.next()) {
				datos.add(new CopiaTablaModelo(rs.getString("Boleta"), rs.getString("Nombre"), rs.getString("Renovacion"),
						rs.getString("CasillerosDisponibles")));

			}

		} catch (Exception e) {

		}

		return datos;

	}
    
    @FXML
    void RenuevaIntegrantes(ActionEvent event) throws SQLException {
    	
    	java.sql.Statement Aceptados;
		java.sql.Statement Elimina;
    	Aceptados = CopiaConexionBD.obtenConexion().createStatement();
		Aceptados.executeUpdate("INSERT INTO Renovaciones(Boleta, Nombre,Renovacion,Casillero,Correo) SELECT Aceptados.Boleta,Aceptados.Nombre,Aceptados.Renovacion,Aceptados.CasillerosDisponibles,Aceptados.Correo FROM Aceptados");

		Elimina = CopiaConexionBD.obtenConexion().createStatement();
		Elimina.executeUpdate("DELETE FROM Aceptados");
		TablaUsuarios.setItems(getDatos());

    }
  
   

    @FXML
    void BuscaUsuarios(ActionEvent event) {
    	String boleta=DatosAcep.getText();
    	
    	ObservableList<CopiaTablaModelo> datos = FXCollections.observableArrayList();
		try {
			java.sql.Statement pst = CopiaConexionBD.obtenConexion().createStatement();

			ResultSet rs = pst.executeQuery("SELECT * FROM Aceptados WHERE Boleta=" + boleta);

			while (rs.next()) {
				datos.add(new CopiaTablaModelo(rs.getString("Boleta"), rs.getString("Nombre"), rs.getString("Renovacion"),
						rs.getString("CasillerosDisponibles")));

			}

		} catch (Exception e) {

		}
           TablaUsuarios.setItems(datos);

    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		Boleta.setCellValueFactory(new PropertyValueFactory("Boleta"));
		Nombre.setCellValueFactory(new PropertyValueFactory("Nombre"));
		Renovacion.setCellValueFactory(new PropertyValueFactory("Renovacion"));
		CasillerosDisponibles.setCellValueFactory(new PropertyValueFactory("CasillerosDisponibles"));
		
	
		TablaUsuarios.setItems(getDatos());

		
	}

}
