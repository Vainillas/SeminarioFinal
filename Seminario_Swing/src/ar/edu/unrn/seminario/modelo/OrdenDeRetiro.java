package ar.edu.unrn.seminario.modelo;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.Helper.ConditionHelper;
import ar.edu.unrn.seminario.exceptions.NotNullException;


	
public class OrdenDeRetiro {
	private PedidoDeRetiro pedidoAsociado;
	private Recolector recolector;
	private Date fechaOrden;
	private Estado estado;
	private ArrayList<Visita>visitas;
	private int codigo;
	
	public OrdenDeRetiro(PedidoDeRetiro p, Recolector r, Date fechaActual, Estado estado, ArrayList<Visita> visitas) {
		//validarDatos(p, r, fechaActual);
		pedidoAsociado = p;
		recolector = r;
		fechaOrden = fechaActual;
		this.estado=estado;
		this.visitas=visitas;
	}
	public OrdenDeRetiro(PedidoDeRetiro p, Recolector r, Date fechaActual) {
		//validarDatos(p, r, fechaActual);
		pedidoAsociado = p;
		recolector = r;
		fechaOrden = fechaActual;
		estado=new Estado("pendiente");
		visitas=new ArrayList<>();
	}
	
	public OrdenDeRetiro(PedidoDeRetiro p, Date fechaActual) {
		//validarDatos(p, fechaActual);
		pedidoAsociado = p;
		recolector = null;
		fechaOrden = fechaActual;
		estado=new Estado("pendiente");
		visitas=new ArrayList<>();
	}
	
	private void validarDatos(PedidoDeRetiro pedido, Recolector recolector, Date fechaActual) throws NotNullException{
		if(ConditionHelper.IsNull(pedido)){throw new NotNullException("pedido NULO");}
		if(ConditionHelper.IsNull(recolector)) {throw new NotNullException("recolector NULO");}
		if(ConditionHelper.IsNull(fechaActual)) {throw new NotNullException("Fecha NULA");}
		
	}
	private void validarDatos(PedidoDeRetiro pedido, Date fechaActual) throws NotNullException{
		if(ConditionHelper.IsNull(pedido)){throw new NotNullException("pedido NULO");}
		if(ConditionHelper.IsNull(fechaActual)) {throw new NotNullException("Fecha NULA");}
		
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
