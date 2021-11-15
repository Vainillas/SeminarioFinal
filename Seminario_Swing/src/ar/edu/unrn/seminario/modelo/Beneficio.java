package ar.edu.unrn.seminario.modelo;

import ar.edu.unrn.seminario.Helper.ConditionHelper;
import ar.edu.unrn.seminario.exceptions.DataEmptyException;
import ar.edu.unrn.seminario.exceptions.NotNullException;
import ar.edu.unrn.seminario.exceptions.NotNumberException;

public class Beneficio {
	private String descripcion;
	private int puntajeConsumible;
	private int codigo;

	public Beneficio(String descripcion, String puntajeConsumible)throws NotNullException, DataEmptyException, NotNumberException {
		validarDatos(descripcion, puntajeConsumible);
		this.descripcion = descripcion;
		this.puntajeConsumible = Integer.parseInt(puntajeConsumible);
	}
	
	public Beneficio(String descripcion, int puntajeConsumible, int codigoBeneficio){
		this.descripcion = descripcion;
		this.puntajeConsumible = puntajeConsumible;
		this.codigo = codigoBeneficio;
	}
	
	public void validarDatos(String descripcion, String puntajeConsumible) throws NotNullException, DataEmptyException, NotNumberException {
		if(ConditionHelper.stringIsEmpty(puntajeConsumible)) {throw new DataEmptyException("Puntaje vacio");}
		if(ConditionHelper.stringIsEmpty(descripcion)) {throw new NotNullException("Descripcion vacia");}

		if(ConditionHelper.stringIsNull(puntajeConsumible)) {throw new NotNullException("puntaje nulo");}
		if(ConditionHelper.stringIsNull(puntajeConsumible)) {throw new NotNullException("Descripcion nula");}
		
		if(ConditionHelper.IsNotNumber(puntajeConsumible)) {throw new NotNumberException("El puntaje no es numerico");}
		
		
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

	@Override
	public String toString() {
		return "Beneficio [descripcion=" + descripcion + ", puntajeConsumible=" + puntajeConsumible + ", codigo="
				+ codigo + "]";
	}

}
