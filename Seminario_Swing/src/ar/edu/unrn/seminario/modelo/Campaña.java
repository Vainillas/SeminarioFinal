package ar.edu.unrn.seminario.modelo;

import java.util.ArrayList;

public class Campaña {
	private String nombreCampaña;
	private ArrayList<Dueño> listaBeneficiarios;
	private ArrayList<Canje> listaCanjesEfectuados;
	private Catalogo catalogo;
	private Estado estado;
	
	public Campaña(String nombre, Catalogo unCatalogo){
		nombreCampaña = nombre;
		catalogo = unCatalogo;
	}

	public int getCodigo() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
