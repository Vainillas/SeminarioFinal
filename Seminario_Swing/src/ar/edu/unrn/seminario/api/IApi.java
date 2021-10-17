package ar.edu.unrn.seminario.api;

import java.sql.SQLException;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;


import ar.edu.unrn.seminario.dto.DireccionDTO;
import ar.edu.unrn.seminario.dto.DueñoDTO;
import ar.edu.unrn.seminario.dto.PedidoDeRetiroDTO;
import ar.edu.unrn.seminario.dto.RolDTO;
import ar.edu.unrn.seminario.dto.UsuarioDTO;
import ar.edu.unrn.seminario.dto.ViviendaDTO;
import ar.edu.unrn.seminario.exceptions.StateException;
import ar.edu.unrn.seminario.exceptions.AppException;
import ar.edu.unrn.seminario.exceptions.DataEmptyException;
import ar.edu.unrn.seminario.exceptions.DateNullException;
import ar.edu.unrn.seminario.exceptions.IncorrectEmailException;
import ar.edu.unrn.seminario.exceptions.KilogramEmptyException;
import ar.edu.unrn.seminario.exceptions.NotCorrectPasswordException;
import ar.edu.unrn.seminario.exceptions.NotNullException;
import ar.edu.unrn.seminario.exceptions.StringNullException;
import ar.edu.unrn.seminario.dto.RecolectorDTO;
import ar.edu.unrn.seminario.modelo.PedidoDeRetiro;
import ar.edu.unrn.seminario.modelo.Rol;
import ar.edu.unrn.seminario.modelo.Usuario;
import ar.edu.unrn.seminario.exceptions.NotNumberException;
import ar.edu.unrn.seminario.exceptions.NotRegisterException;

public interface IApi {

	
	//void registrarVivienda(Direccion unaDireccion, String unDueño);
	
	void agregarVivienda(String nombre, String apellido, String dni, String correo,String calle, String altura, String codigoPostal, String latitud, String longitud, String barrio) throws DataEmptyException, StringNullException, IncorrectEmailException, NotNumberException, SQLException,Exception;
	
	UsuarioDTO obtenerUsuario(String username);

	void eliminarUsuario(String username);
	
	void agregarPersonal(String nombre, String apellido, String dni, String correoElectronico, String telefono) 
			throws DataEmptyException,StringNullException,IncorrectEmailException, NotNumberException;
	
	
	void registrarUsuario(String usuario, String password, String email, Integer rol) 
			throws NotNullException, IncorrectEmailException, DataEmptyException, StringNullException, AppException;
	
	List<RolDTO> obtenerRoles() 
			throws AppException;
	

	List<RolDTO> obtenerRolesActivos();

	void guardarRol(Integer codigo, String descripción, boolean estado); // crear el objeto de dominio “Rol”

	RolDTO obtenerRolPorCodigo(Integer codigo) throws AppException; // recuperar el rol almacenado

	void activarRol(Integer codigo); // recuperar el objeto Rol, implementar el comportamiento de estado.

	void desactivarRol(Integer codigo); // recuperar el objeto Rol, imp

	List<UsuarioDTO> obtenerUsuarios() throws AppException; // recuperar todos los usuarios
	
	List<ViviendaDTO> obtenerViviendas() throws Exception;
	
	void activarUsuario(String username) throws StateException; // recuperar el objeto Usuario, implementar el comportamiento de estado.

	void desactivarUsuario(String username) throws StateException ; // recuperar el objeto Usuario, implementar el comportamiento de estado.

	void agregarDueño(String nombre, String apellido, String dni, String correo) throws Exception;

	void agregarDireccion(String calle, String altura, String codPostal, String latitud, String longitud,
			String barrio) throws Exception;

	List<ViviendaDTO> obtenerViviendasOrdenadas() throws Exception;

	DueñoDTO obtenerDueño(String text);

	DireccionDTO obtenerDireccion(String text, int num) throws AppException;

	void generarPedidoDeRetiro(boolean cargaPesada, ArrayList<String> residuosSeleccionados,ArrayList<String> residuosSeleccionadosKg, String observacion, ArrayList<String> domicilioSeleccionado) 
		throws AppException, DataEmptyException, NotNullException, StringNullException, DateNullException, NumberFormatException, KilogramEmptyException, NotNumberException ;


	boolean existeUsuario(String usuario) throws NotRegisterException, AppException;
	
	boolean validarUsuario(String usuario, String password) throws NotRegisterException, AppException, NotCorrectPasswordException, DataEmptyException, StringNullException, IncorrectEmailException ;
	boolean existeDueño(String dni) throws AppException;
	List<DireccionDTO> obtenerDirecciones() throws AppException;

	void usuarioActivo(String text) throws AppException;

	List<DireccionDTO> obtenerDireccionesDeDueño() throws AppException;
	public String obtenerRolUsuarioActivo();
	List<RecolectorDTO> obtenerRecolectores();

	List<PedidoDeRetiroDTO> obtenerPedidosDeRetiro() throws DataEmptyException, NotNullException, StringNullException, DateNullException, AppException, Exception;

	Usuario getUserOnline();
}
