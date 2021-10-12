package ar.edu.unrn.seminario.modelo;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

public class Visita {
	private String observacion;
	private ArrayList<Residuo>residuosExtraidos;
	private Date fecha;
	
	public Visita(String observacion) {
		this.observacion=observacion;
		residuosExtraidos = new ArrayList<>();
		fecha = new Date(Calendar.getInstance().getTime().getTime());
	}
	public Visita(String observacion, Date fecha) {
		this(observacion);
		this.fecha=fecha;
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
	public void añadirResiduo(Residuo residuo) {
		residuosExtraidos.add(residuo);
	}
}
