package ar.edu.unrn.seminario.modelo;


public class Estado {
	private String estado;
	private final String enEjecucion = "en ejecucion";
	private final String concretado = "concretado";
	private final String pendiente = "pendiente";
	private final String cancelado = "cancelado";
	public Estado(String estado) {
		this.estado = estado;

	}
	
	public boolean setPendiente() {
		boolean validacion = false;
		if(!estado.equals(pendiente)) {
			this.estado = pendiente ;
			validacion = true;
		}
		return validacion;
	}
	
	public boolean ejecutar() {
		boolean validacion = false;
		if(estado.equals(pendiente)) {
			this.estado = enEjecucion;
			validacion = true;
		}
		return validacion;
	}
	
	public boolean concretar() {
		boolean validacion = false;
		if(estado.equals(enEjecucion)) {
			this.estado = concretado;
			validacion = true;
		}
		return validacion;
	}
	public boolean cancelar() {
		boolean validacion = false;
		if(estado.equals(pendiente)) {
			validacion = true;
			this.estado = cancelado;
		}
		return validacion;
	}
	public boolean cancelado() {
		boolean validacion = false;
		if(estado.equals(cancelado)) {
			validacion = true;
		}
		return validacion;
	}
	public boolean enEjecucion() {
		boolean validacion = false;
		if(estado.equals(enEjecucion)) {
			validacion = true;
		}
		return validacion;
	}
	public boolean pendiente() {
		boolean validacion = false;
		if(estado.equals(pendiente)) {
			validacion = true;
		}
		return validacion;
	}
	public boolean concretado() {
		boolean validacion = false;
		if(estado.equals(concretado)) {
			validacion = true;
		}
		return validacion;
	}
	
	public String obtenerEstado() { 
		return this.estado;
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
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
		Estado other = (Estado) obj;
		if (estado == null) {
			if (other.estado != null)
				return false;
		} else if (!estado.equals(other.estado))
			return false;
		return true;
	}
	
	
	
	
}
