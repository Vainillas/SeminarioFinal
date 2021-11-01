package ar.edu.unrn.seminario.accesos;

import java.sql.SQLException;
import java.util.List;

import ar.edu.unrn.seminario.exceptions.AppException;
import ar.edu.unrn.seminario.exceptions.DataEmptyException;
import ar.edu.unrn.seminario.exceptions.IncorrectEmailException;
import ar.edu.unrn.seminario.exceptions.NotNumberException;
import ar.edu.unrn.seminario.exceptions.StringNullException;
import ar.edu.unrn.seminario.modelo.Vivienda;

public interface ViviendaDao {
	void create(Vivienda v) throws AppException;

	void update(Vivienda v);

	void remove(Vivienda v);

	Vivienda find(Integer codigo) throws AppException;

	Vivienda find(String calle, String altura) throws AppException;

	
	List<Vivienda> findAll() throws AppException;

	List<Vivienda> findByUser(String usuario) throws AppException;

}
