package ar.edu.unrn.seminario.modelo;

public class Residuo {
	private TipoResiduo tipo;
	private int cantidadKg;
	
	public Residuo(TipoResiduo tipo, int cantidadKg) {
		this.tipo = tipo;
		this.cantidadKg = cantidadKg;
	}

	public TipoResiduo getTipo() {
		return tipo;
	}

	public void setTipo(TipoResiduo tipo) {
		this.tipo = tipo;
	}

	public int getCantidadKg() {
		return cantidadKg;
	}

	public void setCantidadKg(int cantidadKg) {
		this.cantidadKg = cantidadKg;
	}
	
}
