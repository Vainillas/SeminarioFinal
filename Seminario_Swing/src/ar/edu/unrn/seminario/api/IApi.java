package ar.edu.unrn.seminario.api;

import java.util.List;


import ar.edu.unrn.seminario.dto.DireccionDTO;
import ar.edu.unrn.seminario.dto.RolDTO;
import ar.edu.unrn.seminario.dto.UsuarioDTO;
import ar.edu.unrn.seminario.dto.ViviendaDTO;
import ar.edu.unrn.seminario.exceptions.StateException;
import ar.edu.unrn.seminario.exceptions.DataEmptyException;
import ar.edu.unrn.seminario.exceptions.IncorrectEmailException;
import ar.edu.unrn.seminario.exceptions.NotNullException;
import ar.edu.unrn.seminario.exceptions.NotNumberException;
import ar.edu.unrn.seminario.modelo.Direccion;
import ar.edu.unrn.seminario.modelo.Dueño;

public interface IApi {

	void registrarUsuario(String username, String password, String email, String nombre, Integer rol);
	
	//void registrarVivienda(Direccion unaDireccion, String unDueño);
	
	void agregarVivienda(String nombre, String apellido, String dni, String correo,String calle, String altura, String codigoPostal, String latitud, String longitud, String barrio) throws DataEmptyException, NotNullException, IncorrectEmailException, NotNumberException;
	
	UsuarioDTO obtenerUsuario(String username);

	void eliminarUsuario(String username);

	List<RolDTO> obtenerRoles();

	List<RolDTO> obtenerRolesActivos();

	void guardarRol(Integer codigo, String descripción, boolean estado); // crear el objeto de dominio “Rol”

	RolDTO obtenerRolPorCodigo(Integer codigo); // recuperar el rol almacenado

	void activarRol(Integer codigo); // recuperar el objeto Rol, implementar el comportamiento de estado.

	void desactivarRol(Integer codigo); // recuperar el objeto Rol, imp

	List<UsuarioDTO> obtenerUsuarios(); // recuperar todos los usuarios
	
	List<ViviendaDTO> obtenerViviendas();
	
	void activarUsuario(String username) throws StateException; // recuperar el objeto Usuario, implementar el comportamiento de estado.

	void desactivarUsuario(String username) throws StateException ; // recuperar el objeto Usuario, implementar el comportamiento de estado.

	void agregarDueño(String nombre, String apellido, String dni, String correo);

	void agregarDireccion(String calle, String altura, String codPostal, String latitud, String longitud,
			String barrio);
}
