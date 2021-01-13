package principal;

import java.util.ArrayList;

public class Sala {
	public boolean TipoSala;
	public String NombreP;
	public String Resumen;
	public ArrayList<String> Generos;
	
	public Sala(boolean tS, String nP, String rP, ArrayList<String> gens) {
		TipoSala = tS;
		NombreP = nP;
		Resumen = rP;
		Generos = gens;
	}
	
}
