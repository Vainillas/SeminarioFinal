package ar.edu.unrn.seminario.modelo;


import ar.edu.unrn.seminario.Helper.ConditionHelper;
import ar.edu.unrn.seminario.exceptions.DataEmptyException;
import ar.edu.unrn.seminario.exceptions.NotNullException;

public class TipoResiduo {
	private Integer valor;
	private String nombre;
	
	public TipoResiduo(Integer valor, String nombre) throws NotNullException, DataEmptyException {
		validarDatos(nombre);
		this.valor=valor;
		this.nombre = nombre;
	}
	
	private void validarDatos(String nombre) throws NotNullException, DataEmptyException { //No deberia tirar KilogramEmpty
		if(ConditionHelper.stringIsNull(nombre)) {throw new NotNullException("nombre nulo");}
		if(ConditionHelper.stringIsEmpty(nombre)) {throw new DataEmptyException("nombre vacio");}
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
