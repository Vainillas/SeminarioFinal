package ar.edu.unrn.seminario.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import ar.edu.unrn.seminario.modelo.Rol;
import ar.edu.unrn.seminario.modelo.Usuario;
import ar.edu.unrn.seminario.modelo.Vivienda;
//eliminar abstract 
public abstract class MemoryApi implements IApi {

	/*private Map<Integer, Rol> roles = new HashMap<>();
	private Set<Usuario> usuarios = new HashSet<>();
	private ArrayList<Vivienda> viviendas= new ArrayList<Vivienda>();
	
	public MemoryApi() throws DataEmptyException, NotNullException, IncorrectEmailException, NotNumberException {
		// datos iniciales
		this.roles.put(1, new Rol(1, "ADMIN"));
		this.roles.put(2, new Rol(2, "COMUNIDAD"));
		this.roles.put(3, new Rol(3, "GOBIERNO"));
		inicializarUsuarios();
	}
	private void inicializarUsuarios() {
		registrarUsuario("mcambarieri", "1234", "mcambarieri@unrn.edu.ar", "Mauro", 3);
		registrarUsuario("ldifabio", "1234", "ldifabio@unrn.edu.ar", "Lucas", 2);
		registrarUsuario("admin", "1234", "admin@unrn.edu.ar", "Admin", 1);
	}

	public void agregarVivienda(String nombre, String apellido, String dni, String correo,String calle, String altura, String codigoPostal, String latitud, String longitud, String barrio) throws DataEmptyException, NotNullException, IncorrectEmailException, NotNumberException{//construir objetos aca dentro
		Dueño unDueño = new Dueño(nombre, apellido, dni, correo);
		Direccion unaDireccion = new Direccion(calle,altura,codigoPostal,latitud,longitud,barrio);
		this.viviendas.add(new Vivienda(unaDireccion,unDueño));
	}
	
	@Override
	public void registrarUsuario(String username, String password, String email, String nombre, Integer rol) {
		Rol role = this.roles.get(rol);
		Usuario usuario = new Usuario(username, password, nombre, email, role);
		this.usuarios.add(usuario);
	}

	@Override
	public List<UsuarioDTO> obtenerUsuarios() {
		List<UsuarioDTO> dtos = new ArrayList<>();
		for (Usuario u : this.usuarios) {
			dtos.add(new UsuarioDTO(u.getUsuario(), u.getContrasena(), u.getNombre(), u.getEmail(),
					u.getRol().getNombre(), u.isActivo(), u.obtenerEstado()));
		}
		return dtos;
	}
	
	@Override
	public List<ViviendaDTO> obtenerViviendas() {
		List<ViviendaDTO> dtos = new ArrayList<>();
		for(Vivienda u: this.viviendas){
			dtos.add(new ViviendaDTO(u.getDireccion(), u.getDueño()));
		}
		return  dtos;
	}

	@Override
	public UsuarioDTO obtenerUsuario(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void eliminarUsuario(String username) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<RolDTO> obtenerRoles() {
		List<RolDTO> dtos = new ArrayList<>();
		for (Rol r : this.roles.values()) {
			dtos.add(new RolDTO(r.getCodigo(), r.getNombre()));
		}
		return dtos;
	}

	@Override
	public List<RolDTO> obtenerRolesActivos() {
		List<RolDTO> dtos = new ArrayList<>();
		for (Rol r : this.roles.values()) {
			if (r.isActivo())
				dtos.add(new RolDTO(r.getCodigo(), r.getNombre()));
		}
		return dtos;
	}

	@Override
	public void guardarRol(Integer codigo, String descripcion, boolean estado) {
		// TODO Auto-generated method stub
		Rol rol = new Rol(codigo, descripcion);
		this.roles.put(codigo, rol);
	}

	@Override
	public RolDTO obtenerRolPorCodigo(Integer codigo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void activarRol(Integer codigo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void desactivarRol(Integer codigo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void activarUsuario(String usuario) throws StateException {
		for (Usuario u : usuarios) {
			if (u.getUsuario().equals(usuario))
				u.activar();
			//enviar mail
			//..
		}

	}

	@Override
	public void desactivarUsuario(String usuario) throws StateException {
		for (Usuario u : usuarios) {
			if (u.getUsuario().equals(usuario))
				u.desactivar();
			}
	}*/
}