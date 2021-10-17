package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.exceptions.AppException;
import ar.edu.unrn.seminario.modelo.Residuo;

public interface ResiduoDao {
	void create(Residuo r) throws AppException;
	void update(Residuo r);
	void remove(Residuo r);
	void remove(String nombre);
	Residuo find(String nombre)throws AppException;
	List<Residuo> findAll() throws AppException;
}
