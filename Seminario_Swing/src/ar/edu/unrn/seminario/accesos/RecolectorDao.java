package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.exceptions.AppException;
import ar.edu.unrn.seminario.modelo.Recolector;

public interface RecolectorDao {
	void create(Recolector recolector) throws AppException;

	void update(Recolector recolector);

	void remove(String dni);

	void remove(Recolector recolector);

	Recolector find(String dni) throws AppException;

	List<Recolector> findAll() throws AppException;

	boolean exists(String dni) throws AppException;
}
