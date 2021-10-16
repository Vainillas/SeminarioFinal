package ar.edu.unrn.seminario.modelo;

import ar.edu.unrn.seminario.Helper.ConditionHelper;
import ar.edu.unrn.seminario.exceptions.KilogramEmptyException;
import ar.edu.unrn.seminario.exceptions.NotNumberException;

public class Residuo {
	private Integer cantidad;
	private Integer valor;
	private String nombre;
	
	public Residuo(String kg, String nombre) throws KilogramEmptyException, NotNumberException {
		validarDatos(kg, nombre);
		
		this.cantidad=Integer.parseInt(kg);
		
		this.nombre = nombre;
		
	}
	
	private void validarDatos(String kg, String nombre) throws KilogramEmptyException, NotNumberException {
		if(ConditionHelper.stringIsEmpty(kg)) {throw new KilogramEmptyException("el kg seleccionado debe ser distinto de 0");}
		if(ConditionHelper.IsNotNumber(kg)) {throw new NotNumberException("debe introducir un numero");}
		if(ConditionHelper.stringIsNull(kg)) {throw new NotNumberException("debe introducir un numero");}
		
		
		if(ConditionHelper.stringIsNull(nombre)) {throw new KilogramEmptyException("nombre nulo");}
		if(ConditionHelper.stringIsEmpty(nombre)) {throw new KilogramEmptyException("nombre vacio");}
		

		
		
	}
	public String getNombre() {
		return nombre;
	}
	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	public String toString() {
		return "nombre:" + this.nombre+
				"cantidad: "+ this.cantidad;
	}
	

}
