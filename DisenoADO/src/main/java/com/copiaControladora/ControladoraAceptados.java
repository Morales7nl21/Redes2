package com.copiaControladora;

import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import com.mysql.cj.xdevapi.Statement;

import CopiaInicio.CopiaConexionBD;
import CopiaInicio.Email;
import javafx.fxml.Initializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

class Hilo extends Thread {

	public static String correo;
	public static File archivo;
	

	public Hilo(String correo, File archivo) {
		this.correo = correo;
		this.archivo = archivo;

	}

	public void run() {
		Email.envia(correo, archivo, archivo.getName());

	}

}

public class ControladoraAceptados implements Initializable {

	static List<String> archivos;
	static List<String> CasillerosManual;
	static String[] ArregloDatos = new String[5];
	static LocalDate FechaActual = null;

	@FXML
	private DatePicker IngresaFecha;
	@FXML
	private Button Ingresa;
	@FXML
	private TextField Datos;

	@FXML
	private Button EnviaCorreo;

	@FXML
	private TableView<CopiaTablaModelo> TablaUsuarios;

	@FXML
	private Button Aceptar;

	@FXML
	private TableColumn<CopiaTablaModelo, String> Boleta;

	@FXML
	private TableColumn<CopiaTablaModelo, String> Nombre;

	@FXML
	private TableColumn<CopiaTablaModelo, String> Correo;

	@FXML
	private TableColumn<CopiaTablaModelo, String> Registro;

	@FXML
	private TableColumn<CopiaTablaModelo, String> Semestre;

	@FXML
	private TableColumn<CopiaTablaModelo, String> Telefono;
	@FXML
	private TableColumn<CopiaTablaModelo, String> Fecha;

	@FXML
	private TableColumn<CopiaTablaModelo, String> Envio;

	@FXML
	ObservableList<String> datosAlumnos = FXCollections.observableArrayList();

	@FXML
	private Button Tipos;
	@FXML
	private ComboBox<String> Alumnos;

	/*
	 * Cuando presionamos el boton ingresa llamamos a este evento por que mediante
	 * del codigo SQL ingresa todos los datos que tengamos en nuestro Jtexfield
	 * (boleta,Nombre,Fecha,correo)
	 * 
	 * Y al final ingresamos los nuevos datos en nuestra Tabla
	 */
	@FXML
	void RecibeDatos(ActionEvent event) throws Throwable {
		PreparedStatement pst11;
		try {

			LocalDate FechaRegistro = IngresaFecha.getValue();
			// LocalDate FechaLimite =
			// FechaRegistro.withDayOfMonth(IngresaFecha.getValue().getDayOfMonth() + 5);
			// System.out.println(FechaRegistro + " " + FechaLimite + " " + FechaActual);

			pst11 = CopiaConexionBD.obtenConexion().prepareStatement(
					"INSERT INTO `Registro`(`Boleta`, `Nombre`, `Registro`, `correo`, `Semestre`, `Telefono`, `Fecha`,`Envio`) VALUES (?,?,?,?,?,?,?,?)");
			pst11.setString(1, ArregloDatos[0]);
			pst11.setString(2, ArregloDatos[1]);
			pst11.setString(3, FechaRegistro.toString());
			pst11.setString(4, ArregloDatos[2]);
			pst11.setString(5, ArregloDatos[3]);
			pst11.setString(6, ArregloDatos[4]);
			pst11.setString(7, LocalDate.of(0000, 01, 01).toString());
			pst11.setInt(8, 0);
			pst11.executeUpdate();

			TablaUsuarios.setItems(getDatos());
			cambia("0");// verde
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Repitio Alumno");
			e.printStackTrace();
		}

	}

	/*
	 * Guarda todos los datos que se encuentren en nuestra base datos
	 */
	ObservableList<CopiaTablaModelo> getDatos() {
		ObservableList<CopiaTablaModelo> datos = FXCollections.observableArrayList();
		try {

			java.sql.Statement pst = CopiaConexionBD.obtenConexion().createStatement();
			ResultSet rs = pst.executeQuery("SELECT * FROM Registro order by Registro ASC");

			while (rs.next()) {
				datos.add(new CopiaTablaModelo(rs.getString("Boleta"), rs.getString("Nombre"), rs.getString("Registro"),
						rs.getString("Correo"), rs.getString("Semestre"), rs.getString("Telefono"),
						rs.getString("Fecha"), String.valueOf(rs.getInt("Envio"))));

			}

		} catch (Exception e) {

		}

		return datos;

	}

	/*
	 * Guarda todos los datos que se encuentren en nuestra base datos
	 */
	ObservableList<CopiaTablaModelo> Busqueda(String boleta) {
		ObservableList<CopiaTablaModelo> datos = FXCollections.observableArrayList();
		try {
			java.sql.Statement pst = CopiaConexionBD.obtenConexion().createStatement();

			ResultSet rs = pst.executeQuery("SELECT * FROM Registro WHERE Boleta=" + boleta);

			while (rs.next()) {
				datos.add(new CopiaTablaModelo(rs.getString("Boleta"), rs.getString("Nombre"), rs.getString("Registro"),
						rs.getString("Correo"), rs.getString("Semestre"), rs.getString("Telefono"),
						rs.getString("Fecha"), String.valueOf(rs.getInt("Envio"))));

			}

		} catch (Exception e) {

		}

		return datos;

	}

	@FXML
	void Invitacion(ActionEvent event) throws Throwable {
		String boleta = TablaUsuarios.getSelectionModel().getSelectedItem().Boleta;
		String prueba = String.valueOf(TablaUsuarios.getSelectionModel().getSelectedItem().Envio);
		String correo = TablaUsuarios.getSelectionModel().getSelectedItem().Correo;

		FileChooser Fc = new FileChooser();
		// Fc.setInitialDirectory(new File("ruta inicial"));
		Fc.getExtensionFilters().addAll(new ExtensionFilter("Word Files", archivos));
		File archivo = Fc.showOpenDialog(null);

		if (archivo != null) {
			System.out.println("Seleccionado" + archivo.getAbsolutePath() + "\n" + archivo.getName());
			Hilo proceso = new Hilo(correo, archivo);
			proceso.start();

		}

		if (prueba.equals("0")) {
			try {
				FechaActual = FechaActual.now();
				PreparedStatement consulta = CopiaConexionBD.obtenConexion()
						.prepareStatement("UPDATE Registro SET Envio= ?, Fecha= ? WHERE Boleta= ?");
				consulta.setInt(1, 1);
				consulta.setString(2, FechaActual.toString());
				consulta.setString(3, boleta);
				consulta.executeUpdate();

				cambia("1");// Azul

			} catch (Exception e) {

			}

		} else
			JOptionPane.showMessageDialog(null, "Ya envio correo a este alumno");

		TablaUsuarios.setItems(getDatos());

	}

	@FXML
	void IngresaDatos(ActionEvent event) {

		if (Alumnos.getSelectionModel().getSelectedItem().toString().equals("Busqueda"))
			TablaUsuarios.setItems(Busqueda(Datos.getText()));

		if (Alumnos.getSelectionModel().getSelectedItem().toString().equals("Boleta"))
			ArregloDatos[0] = Datos.getText();

		if (Alumnos.getSelectionModel().getSelectedItem().toString().equals("Nombre"))
			ArregloDatos[1] = Datos.getText();

		if (Alumnos.getSelectionModel().getSelectedItem().toString().equals("Correo"))
			ArregloDatos[2] = Datos.getText();

		if (Alumnos.getSelectionModel().getSelectedItem().toString().equals("Semestre"))
			ArregloDatos[3] = Datos.getText();

		if (Alumnos.getSelectionModel().getSelectedItem().toString().equals("Telefono"))
			ArregloDatos[4] = Datos.getText();

	}

	void cambia(String tipo) {

		Envio.setCellFactory(column -> new TableCell<CopiaTablaModelo, String>() {

			protected void updateItem(String item, boolean empty) {
				TableRow currentRow = getTableRow();
				super.updateItem(item, empty);
				if (item == null) {
					currentRow.setStyle("");
				} else if (item.equals("1")) {
					currentRow.setStyle("-fx-background-color: #1E90FF;");// azul
				} else if (item.equals("0") && (!item.equals("-1"))) {
					currentRow.setStyle("-fx-background-color: #1FFF54;");// verde
				} else if (item.equals("-1")) {
					currentRow.setStyle("-fx-background-color: #FF1F1F;");// rojo
				}

				// TablaUsuarios.setItems(getDatos());

			}
		});
	}

	// rEVISA QUE LOS DIAS NO PASEN A MAS DE 5 DESPUES DEL EVIO DEL CORREO
	void revisaLimite() {
		FechaActual = FechaActual.now();

		Fecha.setCellFactory(column -> new TableCell<CopiaTablaModelo, String>() {

			protected void updateItem(String item, boolean empty) {
				TableRow currentRow = getTableRow();

				super.updateItem(item, empty);
				if (item == null) {
					currentRow.setStyle("");
				} else if (item.equals(FechaActual.toString())) {

					try {

						PreparedStatement consulta = CopiaConexionBD.obtenConexion()
								.prepareStatement("UPDATE Registro SET Envio= ? WHERE Fecha= ?");
						consulta.setInt(1, -1);
						consulta.setString(2, item);
						consulta.executeUpdate();
						// TablaUsuarios.setItems(getDatos());
					} catch (Exception e) {

					}

				}

			}
		});

	}

	static String Casillero1, Casillero2;

	@FXML
	void RelacionaUsuario(ActionEvent event) {
		String boleta = TablaUsuarios.getSelectionModel().getSelectedItem().Boleta;
		String Nombre = TablaUsuarios.getSelectionModel().getSelectedItem().Nombre;
		LocalDate Renovacion = FechaActual.now();
		String Correo = TablaUsuarios.getSelectionModel().getSelectedItem().Correo;
		int eleccion = JOptionPane.showConfirmDialog(null,
				"¿Desea Asignacion Automatica de casillero o manual de casillero?");

		if (eleccion == 0) {

			PreparedStatement Aceptados;
			java.sql.Statement CasilleroDisponible;
			java.sql.Statement CasilleroAceptado;
			java.sql.Statement Elimina;

			ResultSet casillerosPrueba;
			ResultSet casillerosAceptado;
			try {

				CasilleroDisponible = CopiaConexionBD.obtenConexion().createStatement();
				CasilleroAceptado = CopiaConexionBD.obtenConexion().createStatement();

				casillerosAceptado = CasilleroAceptado.executeQuery("SELECT CasillerosDisponibles  FROM Aceptados");
				casillerosPrueba = CasilleroDisponible.executeQuery("SELECT * FROM CasillerosDisponibles");

				externo: while (casillerosPrueba.next()) {
					Casillero1 = casillerosPrueba.getString("Casilleros");

					while (casillerosAceptado.next()) {
						Casillero2 = casillerosAceptado.getString("CasillerosDisponibles");
						if (Casillero1.equals(Casillero2))
							break;
						else {
							Casillero1 = casillerosPrueba.getString("Casilleros");
							break externo;

						}

					}

				}

				Aceptados = CopiaConexionBD.obtenConexion().prepareStatement(
						"INSERT INTO `Aceptados`(`Nombre`, `Renovacion`, `Boleta`, `CasillerosDisponibles`, `Correo`) VALUES (?,?,?,?,?)");

				Aceptados.setString(1, Nombre);
				Aceptados.setString(2, Renovacion.toString());
				Aceptados.setString(3, boleta);
				Aceptados.setString(4, Casillero1);
				Aceptados.setString(5, Correo);
				Aceptados.executeUpdate();

				Elimina = CopiaConexionBD.obtenConexion().createStatement();
				Elimina.executeUpdate("DELETE FROM Registro WHERE Registro.Boleta=" + boleta);
				TablaUsuarios.setItems(getDatos());
				cambia("0");// verde
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, "Repitio Casillero");
				e.printStackTrace();
			}
		} else {
			CasillerosManual = new ArrayList<String>();
			PreparedStatement Aceptados;
			java.sql.Statement CasilleroDisponible;
			java.sql.Statement CasilleroAceptado;
			java.sql.Statement Elimina;

			ResultSet casillerosPrueba;
			ResultSet casillerosAceptado;
			try {

				CasilleroDisponible = CopiaConexionBD.obtenConexion().createStatement();
				CasilleroAceptado = CopiaConexionBD.obtenConexion().createStatement();

				casillerosAceptado = CasilleroAceptado.executeQuery("SELECT CasillerosDisponibles  FROM Aceptados");
				casillerosPrueba = CasilleroDisponible.executeQuery("SELECT * FROM CasillerosDisponibles");

				while (casillerosAceptado.next()) {
					CasillerosManual.add(casillerosAceptado.getString("CasillerosDisponibles"));
				}

				while (casillerosPrueba.next()) {
					if (CasillerosManual.contains(casillerosPrueba.getString("Casilleros")))
						CasillerosManual.remove(casillerosPrueba.getString("Casilleros"));
					else
						CasillerosManual.add(casillerosPrueba.getString("Casilleros"));
				}

			} catch (Exception e) {
				// TODO: handle exception
			}

			JComboBox jcd = new JComboBox(CasillerosManual.toArray());
			jcd.setEditable(true);

			
			
			// create a JOptionPane
			Object[] options = new Object[] {};
			JOptionPane jop = new JOptionPane("Casilleros Disponibles", JOptionPane.QUESTION_MESSAGE,
					JOptionPane.DEFAULT_OPTION, null, options, null);
			// add combos to JOptionPane
			jop.add(jcd);
			// create a JDialog and add JOptionPane to it
			JDialog diag = new JDialog();
			diag.getContentPane().add(jop);
			diag.pack();
			diag.setVisible(true);
			
			jcd.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// TODO Auto-generated method stub
					
					diag.setVisible(false);
					PreparedStatement Aceptados;
					java.sql.Statement Elimina;
					String Casilleromanual=jcd.getSelectedItem().toString();
					
					try {
						Aceptados = CopiaConexionBD.obtenConexion().prepareStatement(
								"INSERT INTO `Aceptados`(`Nombre`, `Renovacion`, `Boleta`, `CasillerosDisponibles`, `Correo`) VALUES (?,?,?,?,?)");
					
					
						Aceptados.setString(1, Nombre);
						Aceptados.setString(2, Renovacion.toString());
						Aceptados.setString(3, boleta);
						Aceptados.setString(4,Casilleromanual);
						Aceptados.setString(5, Correo);
						Aceptados.executeUpdate();

						Elimina = CopiaConexionBD.obtenConexion().createStatement();
						Elimina.executeUpdate("DELETE FROM Registro WHERE Registro.Boleta=" + boleta);
						TablaUsuarios.setItems(getDatos());
										
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					
					
				}
			});

		}
	}

	/*
	 * Nuestra interfaz initializable nos permite que los datos que nosotros
	 * ingresemos se muestre en una tabla al inicio del programa
	 */
	public void initialize(URL url, ResourceBundle resources) {
		// TODO Auto-generated method stub

		Boleta.setCellValueFactory(new PropertyValueFactory("Boleta"));
		Nombre.setCellValueFactory(new PropertyValueFactory("Nombre"));
		Registro.setCellValueFactory(new PropertyValueFactory("Registro"));
		Correo.setCellValueFactory(new PropertyValueFactory("Correo"));
		Semestre.setCellValueFactory(new PropertyValueFactory("Semestre"));
		Telefono.setCellValueFactory(new PropertyValueFactory("Telefono"));
		Fecha.setCellValueFactory(new PropertyValueFactory("Fecha"));
		Envio.setCellValueFactory(new PropertyValueFactory("Envio"));

		datosAlumnos.add("Busqueda");
		datosAlumnos.add("Boleta");
		datosAlumnos.add("Nombre");
		datosAlumnos.add("Correo");
		datosAlumnos.add("Semestre");
		datosAlumnos.add("Telefono");

		Alumnos.setItems(datosAlumnos);
		// Mueste en la tabla lo que se encontro en la base de datos

		archivos = new ArrayList<String>();
		archivos.add("*.doc");
		archivos.add("*.docx");
		archivos.add("*.DOC");
		archivos.add("*.DOCX");

		TablaUsuarios.setItems(getDatos());
		cambia("1");// Azul
		// revisaLimite();
		cambia("0");// verde

	}

}
