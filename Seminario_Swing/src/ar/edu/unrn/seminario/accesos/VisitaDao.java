package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.exceptions.AppException;
import ar.edu.unrn.seminario.exceptions.IncorrectEmailException;
import ar.edu.unrn.seminario.modelo.Visita;

public interface VisitaDao {

		void create(Visita visita) throws AppException;

		void update(Visita visita);

		void remove();

		void remove(Visita visita);

		Visita find() throws AppException;

		List<Visita> findAll() throws AppException, IncorrectEmailException;

		boolean exists() throws AppException;
	
}