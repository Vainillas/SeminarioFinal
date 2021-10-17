package ar.edu.unrn.seminario.modelo;

import ar.edu.unrn.seminario.Helper.ConditionHelper;
import ar.edu.unrn.seminario.exceptions.*;

public class Persona {
	protected String nombre;
	protected String apellido;
	protected String dni;
	public Persona (String nombre, String apellido, String dni)throws DataEmptyException, StringNullException, IncorrectEmailException, NotNumberException {
		validarDatos(nombre,apellido,dni);
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
	}
	private void validarDatos(String nombre,String apellido, String dni) throws StringNullException, IncorrectEmailException, DataEmptyException, NotNumberException {
		if(ConditionHelper.stringIsNull(nombre)) {throw new StringNullException("nombre nulo");}
		if(ConditionHelper.stringIsNull(apellido)) {throw new StringNullException("apellido nulo");}
		if(ConditionHelper.stringIsNull(dni)) {throw new StringNullException("dni nulo");}
		
		
		if(ConditionHelper.stringIsEmpty(nombre)) {throw new DataEmptyException("El campo de nombre está vacío");}
		if(ConditionHelper.stringIsEmpty(apellido)) {throw new DataEmptyException("El campo de apellido está vacío");}
		if(ConditionHelper.stringIsEmpty(dni)) {throw new DataEmptyException("El campo DNI está vacío");}
		
		if(ConditionHelper.IsNotNumber(dni)) {throw new NotNumberException("el dni debe ser numerico.");}
	}
	public String getNombre () {
		return this.nombre;
	}
	public String getApellido () {
		return this.apellido;
	}

	public String getDni() {
		return this.dni;
	}
	public void setDni(String dni) {
		this.dni=dni;
	}
	public void setNombre(String nombre) {
		this.nombre=nombre;
	}
	public void setApellido(String apellido) {
		this.apellido=apellido;
	}
	public String toString() {
		return "nombre: "+ this.nombre + "apellido: "+ this.apellido+
				"dni: "+ this.dni;
	}
}