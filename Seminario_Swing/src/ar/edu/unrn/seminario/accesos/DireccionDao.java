package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.exceptions.AppException;
import ar.edu.unrn.seminario.modelo.Direccion;

public interface DireccionDao {
	void create(Direccion direccion) throws AppException;

	void update(Direccion direccion);

	void remove(String calle, Integer altura);

	void remove(Direccion direccion);

	Direccion find(String calle, Integer altura) throws AppException;

	List<Direccion> findAll() throws AppException;

	boolean exists(String calle, String altura) throws AppException;
}
