package com.copiaControladora;


/*
  Es una clase que guarda el diseño de nuestra tabla
 */



public class CopiaTablaModelo {
String Boleta,Nombre,Correo,Registro,Semestre,Telefono,Fecha,Renovacion,CasillerosDisponibles,Envio,Casillero;



//Cuidado con el nombre de los get y set ya que el programa los regresa por defecto deben ser iguales a las variables

public String getFecha() {
	return Fecha;
}



public void setFecha(String fecha) {
	Fecha = fecha;
}



public CopiaTablaModelo(String boleta, String nombre, String registro, String correo, String semestre, String telefono,
		 String fechaLimite,String envio) {
	super();
	Boleta = boleta;
	Nombre = nombre;
	Correo = correo;
	Registro = registro;
	Semestre = semestre;
	Telefono = telefono;
	Fecha = fechaLimite;
	Envio = envio;
	
}



public CopiaTablaModelo(String boleta,String nombre, String renovacion,  String casillerosdisponibles) {
	// TODO Auto-generated constructor stub
	super();
	Boleta = boleta;
	Renovacion=renovacion;
	Nombre=nombre;
	CasillerosDisponibles=casillerosdisponibles;
	Correo= casillerosdisponibles;
	Casillero=renovacion;
	
	
}



public String getCasillero() {
	return Casillero;
}



public void setCasillero(String casillero) {
	Casillero = casillero;
}



public String getRenovacion() {
	return Renovacion;
}



public void setRenovacion(String renovacion) {
	Renovacion = renovacion;
}



public String getCasillerosDisponibles() {
	return CasillerosDisponibles;
}



public void setCasillerosDisponibles(String casillerosdisponibles) {
	CasillerosDisponibles = casillerosdisponibles;
}




public String getEnvio() {
	return Envio;
}


public void setEnvio(String envio) {
	Envio = envio;
}


public String getBoleta() {
	return Boleta;
}

public void setBoleta(String boleta) {
	Boleta = boleta;
}

public String getNombre() {
	return Nombre;
}

public void setNombre(String nombre) {
	Nombre = nombre;
}

public String getCorreo() {
	return Correo;
}

public void setCorreo(String correo) {
	Correo = correo;
}

public String getRegistro() {
	return Registro;
}

public void setRegistro(String registro) {
	Registro = registro;
}

public String getSemestre() {
	return Semestre;
}

public void setSemestre(String semestre) {
	Semestre = semestre;
}

public String getTelefono() {
	return Telefono;
}

public void setTelefono(String telefono) {
	Telefono = telefono;
}


	

}
