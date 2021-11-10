package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.exceptions.AppException;
import ar.edu.unrn.seminario.exceptions.NotNullException;
import ar.edu.unrn.seminario.modelo.Campaña;


public interface CampañaDao {
	void create(Campaña campaña) throws AppException;

	void update(Campaña campaña) throws AppException;

	void remove();

	Campaña find(int codigo) throws AppException, NotNullException;

	List<Campaña> findAll() throws AppException, NotNullException;

	boolean exists(int codigo) throws AppException;
}
