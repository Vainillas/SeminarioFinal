package ar.edu.unrn.seminario.dto;

import java.sql.Date;

import java.util.ArrayList;


import ar.edu.unrn.seminario.modelo.Estado;
import ar.edu.unrn.seminario.modelo.PedidoDeRetiro;
import ar.edu.unrn.seminario.modelo.Recolector;
import ar.edu.unrn.seminario.modelo.Visita;

public class OrdenDeRetiroDTO {
	private PedidoDeRetiro pedidoAsociado;
	private Recolector recolector;
	private Date fechaOrden;
	

	private Estado estado;
	private ArrayList<Visita>visitas;
	
	private int codigo;
	
	public OrdenDeRetiroDTO(PedidoDeRetiro p, Recolector r, Date fechaActual, Estado estado, ArrayList<Visita> visitas, int codigo) {
		//validarDatos(p, r, fechaActual);
		pedidoAsociado = p;
		recolector = r;
		fechaOrden = fechaActual;
		this.estado=estado;
		this.visitas=visitas;
		this.codigo=codigo;
	}
	
	public OrdenDeRetiroDTO(PedidoDeRetiro p, Recolector r, Date fechaActual, Estado estado, ArrayList<Visita> visitas) {
		//validarDatos(p, r, fechaActual);
		pedidoAsociado = p;
		recolector = r;
		fechaOrden = fechaActual;
		this.estado=estado;
		this.visitas=visitas;
	}
	
	public OrdenDeRetiroDTO(PedidoDeRetiro p, Recolector r, Date fechaActual) {
		pedidoAsociado = p;
		recolector = r;
		fechaOrden = fechaActual;
		estado=new Estado("pendiente");
		visitas=new ArrayList<>();
	}
	
	public OrdenDeRetiroDTO(PedidoDeRetiro p, Date fechaActual) {
		pedidoAsociado = p;
		recolector = null;
		fechaOrden = fechaActual;
		estado=new Estado("pendiente");
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
	
	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	public Estado getEstado() {
		return estado;
	}
	public void setCodigo(int codigo) {
		this.codigo=codigo;
	}
	public int getCodigo() {
		return codigo;
	}
	

}
