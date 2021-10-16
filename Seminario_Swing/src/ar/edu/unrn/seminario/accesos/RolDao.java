package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.exceptions.AppException;
import ar.edu.unrn.seminario.modelo.Rol;

public interface RolDao {
	void create(Rol rol);

	void update(Rol rol);

	void remove(Long id);

	void remove(Rol rol);

	Rol find(Integer codigo) throws AppException;

	List<Rol> findAll() throws AppException;

}
