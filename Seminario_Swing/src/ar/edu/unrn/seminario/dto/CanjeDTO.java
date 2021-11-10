package ar.edu.unrn.seminario.dto;

import java.sql.Date;

import ar.edu.unrn.seminario.modelo.Beneficio;
import ar.edu.unrn.seminario.modelo.Campaña;
import ar.edu.unrn.seminario.modelo.Dueño;

public class CanjeDTO {
	private Date fechaCanje;
	private Beneficio beneficioCanjeado;
	private Dueño dueñoCanjeador;
	private Campaña campaña;
	
	public CanjeDTO(Beneficio unBeneficio, Dueño unDueño, Campaña unaCampaña){
		//Date = fechaActual;
		this.beneficioCanjeado = unBeneficio;
		this.dueñoCanjeador = unDueño;
		this.campaña = unaCampaña;
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
	
	public Campaña getCampaña() {
		return this.campaña;
	}
}
