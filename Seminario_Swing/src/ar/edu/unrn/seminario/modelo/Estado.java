package ar.edu.unrn.seminario.modelo;

public class Estado {
	private String estado;
	public Estado(String estado) {
		this.estado = estado;

	}
	
	public boolean Pendiente() {
		boolean validacion = false;
		if(!estado.equals("pendiente")) {
			this.estado = "pendiente" ;
			validacion = true;
		}
		return validacion;
	}
	
	public boolean EnEjecucion() {
		boolean validacion = false;
		if(!estado.equals("en ejecucion")) {
			this.estado = "en ejecucion";
			validacion = true;
		}
		return validacion;
	}
	
	public boolean Concretado() {
		boolean validacion = false;
		if(!estado.equals("concretado") && estado.equals("en ejecucion")) {
			this.estado = "concretado";
			validacion = true;
		}
		return validacion;
	}
	
	public String obtenerEstado() { 
		return this.estado;
	}
	
	public boolean cancelar() {
		boolean validacion = false;
		if(!estado.equals("cancelado")) {
			validacion = true;
			this.estado = "en ejecucion";
		}
		return validacion;
	}
	
	
	
	
}
