package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.exceptions.AppException;
import ar.edu.unrn.seminario.modelo.Dueño;
import ar.edu.unrn.seminario.modelo.Rol;

public interface DueñoDao {
	void create(Dueño dueño) throws Exception;

	void update(Dueño dueño);

	void remove(String dni);

	void remove(Dueño dueño);

	Dueño find(String dni);

	List<Dueño> findAll() throws AppException;

	boolean exists(String dni) throws AppException;
}
