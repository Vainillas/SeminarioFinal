package ar.edu.unrn.seminario.modelo;

import java.util.ArrayList;

public class Catalogo {
	
	private ArrayList<Beneficio> listaBeneficios;
	
	public Catalogo(ArrayList<Beneficio> listaDeBeneficios){
		listaBeneficios = listaDeBeneficios;
	}

	public ArrayList<Beneficio> getListaBeneficios() {
		return listaBeneficios;
	}

	public void setListaBeneficios(ArrayList<Beneficio> listaBeneficios) {
		this.listaBeneficios = listaBeneficios;
	}
}
