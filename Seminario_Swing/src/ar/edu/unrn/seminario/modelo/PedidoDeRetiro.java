package ar.edu.unrn.seminario.modelo;

import java.util.ArrayList;
import java.sql.Date;

public class PedidoDeRetiro {
	
	private String observacion;
	private Boolean maquinaPesada;
	private ArrayList<Residuo> listResiduos;
	private Vivienda vivienda;
	private  Date fechaDelPedido;
	
	
	public PedidoDeRetiro(String unaObservacion, Boolean requiereMaquinaPesada, ArrayList<Residuo> unaListaDeResiduos, Date unaFecha, Vivienda unaVivienda){
		this.observacion = unaObservacion;
		this.maquinaPesada = requiereMaquinaPesada;
		this.listResiduos = unaListaDeResiduos;
		this.vivienda = unaVivienda;
		this.fechaDelPedido = unaFecha;
	}
	
	public PedidoDeRetiro(Boolean requiereMaquinaPesada, ArrayList<Residuo> unaListaDeResiduos,  Date unaFecha, Vivienda unaVivienda){
		this.observacion = null;
		this.maquinaPesada = requiereMaquinaPesada;
		this.listResiduos = unaListaDeResiduos;
		this.fechaDelPedido = unaFecha;
		this.vivienda = unaVivienda;
	}
	
	public Date getFechaDelPedido() {
		return fechaDelPedido;
	}

	public void setFechaDelPedido(Date fechaDelPedido) {
		this.fechaDelPedido = fechaDelPedido;
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

	public int getPlastico() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getVidrio() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getMetal() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getCarton() {
		// TODO Auto-generated method stub
		return 0;
	}
}
