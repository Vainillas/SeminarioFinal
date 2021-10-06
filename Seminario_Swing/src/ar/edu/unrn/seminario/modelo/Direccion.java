package ar.edu.unrn.seminario.modelo;

import ar.edu.unrn.seminario.Helper.ConditionHelper;
import ar.edu.unrn.seminario.exceptions.DataEmptyException;

import ar.edu.unrn.seminario.exceptions.StringNullException;
import ar.edu.unrn.seminario.exceptions.NotNumberException;

public class Direccion {
	private String calle;
	private String longitud;
	private String latitud;
	private String altura;
	private String codPostal;
	private String barrio;
	
	
	public Direccion(String calle, String altura, String codPostal, String longitud, String latitud, String barrio) throws DataEmptyException, StringNullException, NotNumberException {
		super();
		validarDatos(calle, altura, codPostal,longitud,latitud,barrio);
		this.calle = calle;
		this.longitud = longitud;
		this.latitud = latitud;
		this.altura = altura;
		this.codPostal = codPostal;
		this.barrio = barrio;
	}
	
	public void validarDatos(String calle, String altura, String codigoPostal, String longitud, String latitud, String barrio) throws StringNullException, DataEmptyException, NotNumberException {
		//if(ExceptionsHelper.IsNull(calle)) {throw new NotNullException("Calle nula");}
		
		if(ConditionHelper.stringIsNull(altura)) {throw new StringNullException("Altura nula");}
		if(ConditionHelper.stringIsNull(codigoPostal)) {throw new StringNullException("Codigo Postal nulo");}
		if(ConditionHelper.stringIsNull(latitud)) {throw new StringNullException("Latitud nula");}
		if(ConditionHelper.stringIsNull(longitud)) {throw new StringNullException("Longitud nula");}
		if(ConditionHelper.stringIsNull(barrio)) {throw new StringNullException("Barrio nulo");}

		
		
		if(ConditionHelper.stringIsEmpty(calle)) {throw new DataEmptyException("Campo de calle vacío.");}
		if(ConditionHelper.stringIsEmpty(altura)) {throw new DataEmptyException("Campo de altura vacío.");}
		if(ConditionHelper.stringIsEmpty(codigoPostal)) {throw new DataEmptyException("Campo de código postal vacío.");}
		if(ConditionHelper.stringIsEmpty(latitud)) {throw new DataEmptyException("Campo de latitud vacío.");}
		if(ConditionHelper.stringIsEmpty(longitud)) {throw new DataEmptyException("Campo de longitud vacío.");}
		if(ConditionHelper.stringIsEmpty(barrio)) {throw new DataEmptyException("Campo de barrio vacío.");}
		
		if(ConditionHelper.IsNotNumber(altura)) {throw new NotNumberException("La altura debe ser un número.");}
        if(ConditionHelper.IsNotNumber(codigoPostal)) {throw new NotNumberException("El código postal debe ser un número.");}
	}
	
	
	public String getBarrio() {
		return barrio;
	}

	public void setBarrio(String barrio) {
		this.barrio = barrio;
	}
	
	@Override
	public String toString() {
		return  calle + " " + altura + " /CP: " + codPostal;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getLongitud() {
		return longitud;
	}

	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}

	public String getLatitud() {
		return latitud;
	}

	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}

	public String getAltura() {
		return altura;
	}

	public void setAltura(String altura) {
		this.altura = altura;
	}

	public String getCodPostal() {
		return codPostal;
	}

	public void setCodPostal(String codPostal) {
		this.codPostal = codPostal;
	}
	
	
}
