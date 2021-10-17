package ar.edu.unrn.seminario.modelo;

import ar.edu.unrn.seminario.Helper.ConditionHelper;
import ar.edu.unrn.seminario.exceptions.KilogramEmptyException;
import ar.edu.unrn.seminario.exceptions.NotNumberException;

public class Residuo {
	private Integer valor;
	private String nombre;
	
	public Residuo(Integer valor, String nombre) throws KilogramEmptyException, NotNumberException {
		validarDatos(nombre);
		this.valor=valor;
		this.nombre = nombre;
		
	}
	
	private void validarDatos(String nombre) throws KilogramEmptyException, NotNumberException {
		if(ConditionHelper.stringIsNull(nombre)) {throw new KilogramEmptyException("nombre nulo");}
		if(ConditionHelper.stringIsEmpty(nombre)) {throw new KilogramEmptyException("nombre vacio");}
		

		
		
	}
	public String getNombre() {
		return nombre;
	}
	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	public String toString() {
		return "nombre:" + this.nombre+
				"valor: "+ this.valor;
	}
	

}
