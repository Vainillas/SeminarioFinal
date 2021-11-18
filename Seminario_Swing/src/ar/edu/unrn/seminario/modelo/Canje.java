package ar.edu.unrn.seminario.modelo;

import java.util.Date;

import ar.edu.unrn.seminario.Helper.DateHelper;
import ar.edu.unrn.seminario.exceptions.InsuficientPointsException;

public class Canje {
	private Date fechaCanje;
	private Beneficio beneficioCanjeado;
	private Dueño dueñoCanjeador;
	private Campaña campaña;//BORRAR ESTO 
	private int codigo;

	
	public Canje(Beneficio unBeneficio, Dueño unDueño, Campaña unaCampaña, Date fecha, int codigo) throws  InsuficientPointsException{
		fechaCanje = fecha;
		this.beneficioCanjeado = unBeneficio;
		this.dueñoCanjeador = unDueño;
		this.campaña = unaCampaña;
		this.codigo = codigo;
		
		
		//validarDatos(unDueño.getPuntaje(),Integer.parseInt(unBeneficio.getPuntajeConsumible()));
	}
	public Canje(Beneficio unBeneficio, Dueño unDueño, Campaña unaCampaña, int codigo) throws  InsuficientPointsException{
		//java.util.Date fechaActualUtil = DateHelper.getDate();
    	//java.sql.Date fechaActual = new java.sql.Date(fechaActualUtil.getTime()); //CAMBIAR  a DATE NORMAL
    	java.util.Date fechaActual = DateHelper.getDate();
		fechaCanje = fechaActual;
		this.beneficioCanjeado = unBeneficio;
		this.dueñoCanjeador = unDueño;
		this.campaña = unaCampaña;
		this.codigo = codigo;
		
		
		//validarDatos(unDueño.getPuntaje(),Integer.parseInt(unBeneficio.getPuntajeConsumible()));
	}
	public Canje(Beneficio unBeneficio, Dueño unDueño, Campaña unaCampaña) throws  InsuficientPointsException{
		java.util.Date fechaActualUtil = DateHelper.getDate();
    	java.sql.Date fechaActual = new java.sql.Date(fechaActualUtil.getTime());
		fechaCanje = fechaActual;
		this.beneficioCanjeado = unBeneficio;
		this.dueñoCanjeador = unDueño;
		this.campaña = unaCampaña;
		
		validarDatos(unDueño.getPuntaje(),unBeneficio.getPuntajeConsumible());
	}
	
	
	private void validarDatos(int puntajeDelDueño, int puntajeConsumible) throws InsuficientPointsException {

		if(puntajeDelDueño<=puntajeConsumible) {throw new InsuficientPointsException("no cuentas con la cantidad de puntos suficientes Para el Beneficio Asignado");}
		
		
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

	@Override
	public String toString() {
		return "Canje [fechaCanje=" + fechaCanje + ", beneficioCanjeado=" + beneficioCanjeado + ", dueñoCanjeador="
				+ dueñoCanjeador + ", campaña=" + campaña.getNombreCampaña() +" codigocampaña: "+ campaña.getCodigo() + ", codigo canje=" + codigo + "]";
	}
	
}
