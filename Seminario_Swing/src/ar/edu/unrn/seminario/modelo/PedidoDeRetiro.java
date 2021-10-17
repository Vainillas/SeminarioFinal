package ar.edu.unrn.seminario.modelo;

import java.util.ArrayList;


import ar.edu.unrn.seminario.Helper.ConditionHelper;
import ar.edu.unrn.seminario.exceptions.DataEmptyException;
import ar.edu.unrn.seminario.exceptions.DateNullException;
import ar.edu.unrn.seminario.exceptions.NotNullException;
import ar.edu.unrn.seminario.exceptions.StringNullException;


import java.sql.Date;

public class PedidoDeRetiro {
	
	private String observacion;
	private Boolean maquinaPesada;
	private ArrayList<Residuo> listResiduos;
	private Vivienda vivienda;
	private  Date fechaDelPedido;
	private Estado estado;
	private int codigo;
	
	public PedidoDeRetiro(String unaObservacion, Boolean requiereMaquinaPesada, ArrayList<Residuo> unaListaDeResiduos, Date unaFecha, Vivienda unaVivienda, int unCodigo)
	throws DataEmptyException,NotNullException, StringNullException, DateNullException{
		validarDatos(unaObservacion,unaListaDeResiduos,unaFecha,unaVivienda);
		this.observacion = unaObservacion;
		this.maquinaPesada = requiereMaquinaPesada;
		this.listResiduos = unaListaDeResiduos;
		this.vivienda = unaVivienda;
		this.fechaDelPedido = unaFecha;
	}
	private void validarDatos(String unaObservacion, ArrayList<Residuo> unaListaDeResiduos, Date unaFecha, Vivienda unaVivienda)
			throws NotNullException, DataEmptyException, StringNullException, DateNullException {
		if(ConditionHelper.IsNull(unaListaDeResiduos)) {throw new NotNullException("la lista de residuos esta vacia");}
		if(ConditionHelper.stringIsEmpty(unaObservacion)) {throw new StringNullException("observacion nula");}
		if(ConditionHelper.IsDateNull(unaFecha)) {throw new DateNullException("fecha nula");}
		if(ConditionHelper.stringIsNull(unaObservacion)){throw new DataEmptyException("observacion vacia");}
		if(ConditionHelper.IsNull(unaVivienda)) {throw new NotNullException("vivienda nula");}
		
	}
	public String obtenerEstado() {
		return this.estado.obtenerEstado();
	}

	public int getCodigo(){
		return this.codigo;
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

}
