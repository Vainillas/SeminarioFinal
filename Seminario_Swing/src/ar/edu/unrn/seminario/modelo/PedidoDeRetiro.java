package ar.edu.unrn.seminario.modelo;

import java.util.ArrayList;
import java.util.Date;

public class PedidoDeRetiro {
	
	private String observacion;
	private Boolean maquinaPesada;
	private ArrayList<Residuo> listResiduos;
	private Vivienda vivienda;
	private Date fechaDelPedido;
	
	public PedidoDeRetiro(String unaObservacion, Boolean requiereMaquinaPesada, ArrayList<Residuo> unaListaDeResiduos){
		this.observacion = unaObservacion;
		this.maquinaPesada = requiereMaquinaPesada;
		this.listResiduos = unaListaDeResiduos;
		//this.vivienda = unaVivienda;
	}
	
	public PedidoDeRetiro(Boolean requiereMaquinaPesada, ArrayList<Residuo> unaListaDeResiduos){
		this.observacion = null;
		this.maquinaPesada = requiereMaquinaPesada;
		this.listResiduos = unaListaDeResiduos;
		//this.vivienda = unaVivienda;
	}
	
	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Boolean getMaquinaPesada() {
		return maquinaPesada;
	}

	public void setMaquinaPesada(Boolean maquinaPesada) {
		this.maquinaPesada = maquinaPesada;
	}

	public ArrayList<Residuo> getListResiduos() {
		return listResiduos;
	}

	public void setListResiduos(ArrayList<Residuo> listResiduos) {
		this.listResiduos = listResiduos;
	}

	public Vivienda getVivienda() {
		return vivienda;
	}

	public void setVivienda(Vivienda vivienda) {
		this.vivienda = vivienda;
	}
	
}
