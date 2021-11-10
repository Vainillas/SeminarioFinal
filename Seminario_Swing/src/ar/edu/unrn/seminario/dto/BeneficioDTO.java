package ar.edu.unrn.seminario.dto;

import ar.edu.unrn.seminario.Helper.ConditionHelper;
import ar.edu.unrn.seminario.exceptions.DataEmptyException;
import ar.edu.unrn.seminario.exceptions.NotNullException;
import ar.edu.unrn.seminario.exceptions.NotNumberException;

public class BeneficioDTO {
	private String descripcion;
	private String puntajeConsumible;
	private int codigo;

	public BeneficioDTO(String descripcion, String puntajeConsumible) {
		
		this.descripcion = descripcion;
		this.puntajeConsumible = puntajeConsumible;
	}
	
	public BeneficioDTO(String descripcion, String puntajeConsumible, int codigoBeneficio){
		this.descripcion = descripcion;
		this.puntajeConsumible = puntajeConsumible;
		this.codigo = codigoBeneficio;
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
	
	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

}
