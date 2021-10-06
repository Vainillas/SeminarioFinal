package ar.edu.unrn.seminario.modelo;

public class Residuo {
	int cantidad;
	int valor;
	
	public Residuo(int kg) {
		this.cantidad=kg;
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

}
