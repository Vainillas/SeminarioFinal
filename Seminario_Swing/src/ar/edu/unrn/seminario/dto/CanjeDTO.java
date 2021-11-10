package ar.edu.unrn.seminario.dto;

import java.sql.Date;

import ar.edu.unrn.seminario.Helper.DateHelper;
import ar.edu.unrn.seminario.modelo.Beneficio;
import ar.edu.unrn.seminario.modelo.Campaña;
import ar.edu.unrn.seminario.modelo.Dueño;

public class CanjeDTO {
	private Date fechaCanje;
	private Beneficio beneficioCanjeado;
	private Dueño dueñoCanjeador;
	private Campaña campaña;
	private int codigo;

	
	public CanjeDTO(Beneficio unBeneficio, Dueño unDueño, Campaña unaCampaña){
		java.util.Date fechaActualUtil = DateHelper.getDate();
    	java.sql.Date fechaActual = new java.sql.Date(fechaActualUtil.getTime());
		fechaCanje = fechaActual;
		this.beneficioCanjeado = unBeneficio;
		this.dueñoCanjeador = unDueño;
		this.campaña = unaCampaña;
	}
	
	public CanjeDTO(Beneficio unBeneficio, Dueño unDueño, Date fecha){
		fechaCanje = fecha;
		this.beneficioCanjeado = unBeneficio;
		this.dueñoCanjeador = unDueño;
	}
	
	public CanjeDTO(Beneficio unBeneficio, Dueño unDueño, Date fecha, int codigo){
		fechaCanje = fecha;
		this.beneficioCanjeado = unBeneficio;
		this.dueñoCanjeador = unDueño;
		this.codigo = codigo;
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
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	
	public Campaña getCampaña() {
		return this.campaña;
	}

	public void setCampaña(Campaña campaña) {
		this.campaña = campaña;
	}
}
