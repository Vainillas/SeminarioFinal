package ar.edu.unrn.seminario.dto;

import ar.edu.unrn.seminario.exceptions.DataEmptyException;
import ar.edu.unrn.seminario.exceptions.NotNullException;
import ar.edu.unrn.seminario.exceptions.NotNumberException;

public class BeneficioDTO {
	private String descripcion;
	private String puntajeConsumible;
	public BeneficioDTO(String descripcion, String puntajeConsumible)throws NotNullException, DataEmptyException, NotNumberException {
		this.descripcion = descripcion;
		this.puntajeConsumible = puntajeConsumible;
	}
	public String getDescripcion() {
		return this.descripcion;
	}
	public String getPuntajeConsumible() {
		return this.puntajeConsumible;
	}
	public void setPuntajeConsumible(String nuevoPuntaje) {
		this.puntajeConsumible = nuevoPuntaje;
	}
	public void setDescripcion(String nuevaDescripcion) {
	this.descripcion = nuevaDescripcion;
	}
}
