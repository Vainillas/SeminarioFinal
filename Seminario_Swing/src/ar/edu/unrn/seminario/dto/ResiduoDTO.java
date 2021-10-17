package ar.edu.unrn.seminario.dto;

public class ResiduoDTO {
	private Integer valor;
	private String nombre;
	public ResiduoDTO(Integer valor, String nombre) {
		this.valor=valor;
		this.nombre = nombre;
		
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
