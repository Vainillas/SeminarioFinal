package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.exceptions.AppException;
import ar.edu.unrn.seminario.exceptions.NotCorrectPasswordException;
import ar.edu.unrn.seminario.exceptions.NotRegisterException;
import ar.edu.unrn.seminario.modelo.Usuario;
import ar.edu.unrn.seminario.modelo.UsuarioIngreso;

public interface UsuarioDao {
	void create(Usuario Usuario) throws AppException;

	void update(Usuario Usuario);

	void remove(Long id);

	void remove(Usuario Usuario);

	Usuario find(String username) throws AppException;

	List<Usuario> findAll() throws AppException;

	boolean exists(String usuario) throws NotRegisterException, AppException;

	boolean validateData(UsuarioIngreso user) throws NotRegisterException, AppException,NotCorrectPasswordException;

}
