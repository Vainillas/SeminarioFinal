package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.exceptions.AppException;
import ar.edu.unrn.seminario.exceptions.NotNullException;
import ar.edu.unrn.seminario.modelo.Beneficio;

public interface BeneficioDao {
	void create(Beneficio beneficio) throws AppException;

	void update(Beneficio beneficio);

	void remove();

	Beneficio find(int codigo) throws AppException, NotNullException;

	List<Beneficio> findAll() throws AppException, NotNullException;

	boolean exists(int codigo) throws AppException;
}

