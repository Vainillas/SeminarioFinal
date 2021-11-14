package ar.edu.unrn.seminario.modelo;

import java.sql.Date;

import ar.edu.unrn.seminario.Helper.DateHelper;
import ar.edu.unrn.seminario.exceptions.InsuficientPointsException;

public class Canje {
	private Date fechaCanje;
	private Beneficio beneficioCanjeado;
	private Dueño dueñoCanjeador;
	private Campaña campaña;
	private int codigo;

	
	public Canje(Beneficio unBeneficio, Dueño unDueño, Campaña unaCampaña) throws  InsuficientPointsException{
		java.util.Date fechaActualUtil = DateHelper.getDate();
    	java.sql.Date fechaActual = new java.sql.Date(fechaActualUtil.getTime());
		fechaCanje = fechaActual;
		this.beneficioCanjeado = unBeneficio;
		this.dueñoCanjeador = unDueño;
		this.campaña = unaCampaña;
		
		
		validarDatos(unDueño.getPuntaje(),Integer.parseInt(unBeneficio.getPuntajeConsumible()));
	}
	
	private void validarDatos(int puntajeDelDueño, int puntajeConsumible) throws InsuficientPointsException {
		if(puntajeDelDueño<puntajeConsumible) {throw new InsuficientPointsException("no cuentas con la cantidad de puntos suficientes Para el Beneficio Asignado");}
		
		
	}
	public Canje(Beneficio unBeneficio, Dueño unDueño, Date fecha){
		fechaCanje = fecha;
		this.beneficioCanjeado = unBeneficio;
		this.dueñoCanjeador = unDueño;
	}
	
	public Canje(Beneficio unBeneficio, Dueño unDueño, Date fecha, int codigo){
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
