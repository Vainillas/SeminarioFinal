package ar.edu.unrn.seminario.modelo;

import java.util.ArrayList;

public class OrdenDeRetiro {
	private PedidoDeRetiro pedidoAsociado;
	private Recolector recolector;
	private Vivienda vivienda;
	private Estado estado;
	private ArrayList<Visita>visitas;
	private int codigo;
	
	public OrdenDeRetiro(PedidoDeRetiro p) {
		pedidoAsociado=p;
		recolector=null;
		vivienda=p.getVivienda();
		estado=new Estado();
		visitas=new ArrayList<>();
	}
	public OrdenDeRetiro(PedidoDeRetiro p, Recolector r) {
		this(p);
		recolector=r;
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
	public Vivienda getVivienda() {
		return vivienda;
	}
	public Estado getEstado() {
		return estado;
	}
	public int getCodigo() {
		return codigo;
	}
	
	
}
