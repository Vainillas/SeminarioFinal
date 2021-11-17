package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.exceptions.AppException;
import ar.edu.unrn.seminario.exceptions.NotNullException;
import ar.edu.unrn.seminario.modelo.Canje;
import ar.edu.unrn.seminario.modelo.Dueño;

public interface CanjeDao {
	void create(Canje canje) throws AppException;

	void update(Canje canje);

	void remove();

	Canje find(int codigo) throws AppException, NotNullException;

	List<Canje> findAll() throws AppException, NotNullException;

	boolean exists(int codigo) throws AppException;

	List<Canje> findByUser(Dueño dueño)throws AppException, NotNullException;
}
