package ar.edu.unrn.seminario.modelo;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

import ar.edu.unrn.seminario.Helper.ConditionHelper;
import ar.edu.unrn.seminario.exceptions.NotNullException;

public class Visita {
	private String observacion;
	private ArrayList<Residuo>residuosExtraidos;
	private Date fecha;
	private int codOrden;
	private int codigo;
	
	public Visita(String observacion, ArrayList<Residuo> listaResiduos, int codOrden)throws NotNullException {

		this.observacion=observacion;
		residuosExtraidos = listaResiduos;
		this.codOrden = codOrden;
		fecha = new Date(Calendar.getInstance().getTime().getTime());
	}
	
	public Visita(String observacion, ArrayList<Residuo> listaResiduos, int codOrden, int codigo)throws NotNullException {
		this.observacion=observacion;
		residuosExtraidos = listaResiduos;
		this.codOrden = codOrden;
		this.codigo=codigo;
		fecha = new Date(Calendar.getInstance().getTime().getTime());
	}
	
	public void ValidarDatos(String observacion,ArrayList<Residuo> listaResiduos ) throws NotNullException {
		if(ConditionHelper.stringIsEmpty(observacion)) { 
			throw new NotNullException("Descripcion vacia");}
		if(ConditionHelper.stringIsNull(observacion)) {
			throw new NotNullException("Descripcion vacia");}
		
		if(ConditionHelper.IsNull(listaResiduos)) {throw new NotNullException("lista de residuos nula");}
	}
	
	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	
	
	public String getObservacion() {
		return observacion;
	}
	
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	
	public Date getFecha() {
		return fecha;
	}
	
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	public ArrayList<Residuo> getResiduosExtraidos() {
		return residuosExtraidos;
	}
	
	public void añadirResiduo(Residuo tipoResiduo) {
		residuosExtraidos.add(tipoResiduo);
	}
	

	public int getCodOrden() {
		return codOrden; 
	}

	public void setCodOrden(int codOrden) {
		this.codOrden = codOrden;
	}

	@Override
	public String toString() {
		return "Visita [observacion=" + observacion + ", residuosExtraidos=" + residuosExtraidos + ", fecha=" + fecha
				+ ", codOrden=" + codOrden + ", codigo=" + codigo + "]";
	}

}
