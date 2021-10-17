package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.exceptions.AppException;
import ar.edu.unrn.seminario.modelo.OrdenDeRetiro;


public interface OrdenDeRetiroDao {

		void create(OrdenDeRetiro orden) throws AppException;

		void update(OrdenDeRetiro pedido);

		void remove(int id);

		void remove(OrdenDeRetiro pedido);

		OrdenDeRetiro find(int id) throws AppException;

		List<OrdenDeRetiro> findAll() throws AppException;

		boolean exists(int id) throws AppException;
	
}
