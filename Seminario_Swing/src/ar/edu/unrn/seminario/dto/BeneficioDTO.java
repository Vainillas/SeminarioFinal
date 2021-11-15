package ar.edu.unrn.seminario.dto;

import ar.edu.unrn.seminario.Helper.ConditionHelper;
import ar.edu.unrn.seminario.exceptions.DataEmptyException;
import ar.edu.unrn.seminario.exceptions.NotNullException;
import ar.edu.unrn.seminario.exceptions.NotNumberException;

public class BeneficioDTO {
	private String descripcion;
	private int puntajeConsumible;
	private int codigo;

	public BeneficioDTO(String descripcion, int puntajeConsumible) {

		this.descripcion = descripcion;
		this.puntajeConsumible = puntajeConsumible;
	}
	
	public BeneficioDTO(String descripcion, int puntajeConsumible, int codigoBeneficio){
		this.descripcion = descripcion;
		this.puntajeConsumible = puntajeConsumible;
		this.codigo = codigoBeneficio;
	}
	

	
	public String getDescripcion() {
		return this.descripcion;
	}
	
	public int getPuntajeConsumible() {
		return this.puntajeConsumible;
	}
	
	public void setPuntajeConsumible(int nuevoPuntaje) {
		this.puntajeConsumible = nuevoPuntaje;
	}
	
	public void setDescripcion(String nuevaDescripcion) {
		this.descripcion = nuevaDescripcion;
	}
	
	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

}
