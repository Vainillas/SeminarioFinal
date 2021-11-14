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
	
	private void validarDatos(String nombre) throws NotNullException, DataEmptyException {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TipoResiduo other = (TipoResiduo) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		return true;
	}
	


}
