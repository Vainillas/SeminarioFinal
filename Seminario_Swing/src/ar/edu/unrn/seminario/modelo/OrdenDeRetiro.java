package ar.edu.unrn.seminario.modelo;

import java.sql.Date;
import java.util.ArrayList;


	
public class OrdenDeRetiro {
	private PedidoDeRetiro pedidoAsociado;
	private Recolector recolector;
	private Date fechaOrden;
	

	private Estado estado;
	private ArrayList<Visita>visitas;
	
	private int codigo;
	
	public OrdenDeRetiro(PedidoDeRetiro p, Recolector r, Date fechaActual) {
		pedidoAsociado = p;
		recolector = r;
		fechaOrden = fechaActual;
		estado=new Estado();
		visitas=new ArrayList<>();
	}
	
	public OrdenDeRetiro(PedidoDeRetiro p, Date fechaActual) {
		pedidoAsociado = p;
		recolector = null;
		fechaOrden = fechaActual;
		estado=new Estado();
		visitas=new ArrayList<>();
	}
	
	
	public Date getFechaOrden() {
		return fechaOrden;
	}

	public void setFechaOrden(Date fechaOrden) {
		this.fechaOrden = fechaOrden;
	}
	
	public Recolector getRecolector() {
		return recolector;
	}
	
	public void setRecolector(Recolector recolector) {
		this.recolector = recolector;
	}
	
	public ArrayList<Visita> getVisitas() {
		return visitas;
	}
	
	public void setVisitas(ArrayList<Visita> visitas) {
		this.visitas = visitas;
	}
	
	public PedidoDeRetiro getPedidoAsociado() {
		return pedidoAsociado;
	}
	
	
	public Estado getEstado() {
		return estado;
	}
	
	public int getCodigo() {
		return codigo;
	}
	

}
