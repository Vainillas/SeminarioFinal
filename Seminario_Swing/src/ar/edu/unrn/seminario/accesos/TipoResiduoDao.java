package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.exceptions.AppException;
import ar.edu.unrn.seminario.modelo.TipoResiduo;

public interface TipoResiduoDao {
	void create(TipoResiduo r) throws AppException;
	void update(TipoResiduo r);
	void remove(TipoResiduo r);
	void remove(String nombre);
	TipoResiduo find(String nombre)throws AppException;
	List<TipoResiduo> findAll() throws AppException;
}
