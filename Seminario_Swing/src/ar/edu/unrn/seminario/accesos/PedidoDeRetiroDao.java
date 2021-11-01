package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.exceptions.AppException;
import ar.edu.unrn.seminario.exceptions.IncorrectEmailException;
import ar.edu.unrn.seminario.modelo.PedidoDeRetiro;

public interface PedidoDeRetiroDao {

		void create(PedidoDeRetiro pedido) throws AppException;

		void update(PedidoDeRetiro pedido);

		void remove(String dni);

		void remove(PedidoDeRetiro pedido);

		PedidoDeRetiro find(int id) throws AppException;

		List<PedidoDeRetiro> findAll() throws AppException, IncorrectEmailException;

		boolean exists(String dni) throws AppException;

		List<PedidoDeRetiro> findByUser(String usuario) throws AppException, IncorrectEmailException;
	
}
