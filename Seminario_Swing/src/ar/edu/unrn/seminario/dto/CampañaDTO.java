package ar.edu.unrn.seminario.dto;

import java.util.ArrayList;

import ar.edu.unrn.seminario.modelo.Canje;
import ar.edu.unrn.seminario.modelo.Catalogo;
import ar.edu.unrn.seminario.modelo.Dueño;

public class CampañaDTO {
	private String nombreCampaña;
	private ArrayList<Dueño> listaBeneficiarios = null;
	private ArrayList<Canje> listaCanjesEfectuados = null;
	private Catalogo catalogo;
	private String estado; //Esto no es un string
	private int codigo;
	
	public CampañaDTO(String nombre, Catalogo unCatalogo, String estado ){ //Checkear estado;
		nombreCampaña = nombre;
		catalogo = unCatalogo;
		this.estado=estado;
	}
	public CampañaDTO(String nombre, Catalogo unCatalogo, String estado, int codigo){ //Checkear estado;
		nombreCampaña = nombre;
		catalogo = unCatalogo;
		this.estado = estado;
		this.codigo = codigo;
	}
	public String getNombreCampaña() {
		return nombreCampaña;
	}
	public void setNombreCampaña(String nombreCampaña) {
		this.nombreCampaña = nombreCampaña;
	}
	public ArrayList<Dueño> getListaBeneficiarios() {
		return listaBeneficiarios;
	}
	public void setListaBeneficiarios(ArrayList<Dueño> listaBeneficiarios) {
		this.listaBeneficiarios = listaBeneficiarios;
	}
	public ArrayList<Canje> getListaCanjesEfectuados() {
		return listaCanjesEfectuados;
	}
	public void setListaCanjesEfectuados(ArrayList<Canje> listaCanjesEfectuados) {
		this.listaCanjesEfectuados = listaCanjesEfectuados;
	}
	public Catalogo getCatalogo() {
		return catalogo;
	}
	public void setCatalogo(Catalogo catalogo) {
		this.catalogo = catalogo;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	
}