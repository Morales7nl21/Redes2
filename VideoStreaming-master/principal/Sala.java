package principal;

import java.util.ArrayList;
import java.util.Calendar;

import copiaControladora.ControladorPelicula;

public class Sala {
	public boolean TipoSala;
	public String NombreP;
	public String Resumen;
	public ArrayList<String> Generos;
	public Calendar HoraI;
	public ControladorPelicula cp;
	
	public Sala(boolean tS, String nP, String rP, ArrayList<String> gens) {
		TipoSala = tS;
		NombreP = nP;
		Resumen = rP;
		Generos = gens;
		HoraI = Calendar.getInstance();
		int hora =HoraI.get(Calendar.HOUR_OF_DAY);
		int minutos = HoraI.get(Calendar.MINUTE);
		String genero = "";
		for(int i=0;i<gens.size();i++) {
			if(i != gens.size()-1)
				genero+=gens.get(i)+",";
			else
				genero+=gens.get(i);
		}
		//cp = new ControladorPelicula(NombreP,hora+":"+minutos,genero,Resumen);
		
	}
	
}
