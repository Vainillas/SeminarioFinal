package ar.edu.unrn.seminario.modelo;

import java.sql.Date;

public class Canje {
	private Date fechaCanje;
	private Beneficio beneficioCanjeado;
	private Dueño dueñoCanjeador;
	
	public Canje(Beneficio unBeneficio, Dueño unDueño){
		//Date = fechaActual;
		this.beneficioCanjeado = unBeneficio;
		this.dueñoCanjeador = unDueño;
	}

	public Date getFechaCanje() {
		return fechaCanje;
	}

	public void setFechaCanje(Date fechaCanje) {
		this.fechaCanje = fechaCanje;
	}

	public Beneficio getBeneficioCanjeado() {
		return beneficioCanjeado;
	}

	public void setBeneficioCanjeado(Beneficio beneficioCanjeado) {
		this.beneficioCanjeado = beneficioCanjeado;
	}

	public Dueño getDueñoCanjeador() {
		return dueñoCanjeador;
	}

	public void setDueñoCanjeador(Dueño dueñoCanjeador) {
		this.dueñoCanjeador = dueñoCanjeador;
	}
	
	
}
