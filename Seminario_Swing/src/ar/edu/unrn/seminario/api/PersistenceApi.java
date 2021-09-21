package ar.edu.unrn.seminario.api;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ar.edu.unrn.seminario.accesos.DireccionDAOJDBC;
import ar.edu.unrn.seminario.accesos.DireccionDao;
import ar.edu.unrn.seminario.accesos.DueñoDAOJDBC;
import ar.edu.unrn.seminario.accesos.DueñoDao;
import ar.edu.unrn.seminario.accesos.RolDAOJDBC;
import ar.edu.unrn.seminario.accesos.RolDao;
import ar.edu.unrn.seminario.accesos.UsuarioDAOJDBC;
import ar.edu.unrn.seminario.accesos.UsuarioDao;
import ar.edu.unrn.seminario.accesos.ViviendaDAOJDBC;
import ar.edu.unrn.seminario.accesos.ViviendaDao;
import ar.edu.unrn.seminario.dto.DireccionDTO;
import ar.edu.unrn.seminario.dto.DueñoDTO;
import ar.edu.unrn.seminario.dto.RolDTO;
import ar.edu.unrn.seminario.dto.UsuarioDTO;
import ar.edu.unrn.seminario.dto.ViviendaDTO;
import ar.edu.unrn.seminario.exceptions.DataEmptyException;
import ar.edu.unrn.seminario.exceptions.IncorrectEmailException;
import ar.edu.unrn.seminario.exceptions.NotNullException;
import ar.edu.unrn.seminario.exceptions.NotNumberException;
import ar.edu.unrn.seminario.modelo.Direccion;
import ar.edu.unrn.seminario.modelo.Dueño;
import ar.edu.unrn.seminario.modelo.Rol;
import ar.edu.unrn.seminario.modelo.Usuario;
import ar.edu.unrn.seminario.modelo.Vivienda;

public class PersistenceApi implements IApi {

	private RolDao rolDao;
	private UsuarioDao usuarioDao;
	private ViviendaDao viviendaDao;
	private DueñoDao dueñoDao;
	private DireccionDao direccionDao;

	public PersistenceApi() {
		rolDao = new RolDAOJDBC();
		usuarioDao = new UsuarioDAOJDBC();
		viviendaDao = new ViviendaDAOJDBC();
		dueñoDao = new DueñoDAOJDBC();
		direccionDao = new DireccionDAOJDBC();
	}

	@Override
	public void registrarUsuario(String username, String password, String email, String nombre, Integer codigoRol) {
		Rol rol = rolDao.find(codigoRol);
		Usuario usuario = new Usuario(username, password, nombre, email, rol);
		this.usuarioDao.create(usuario);
	}

	@Override
	public List<UsuarioDTO> obtenerUsuarios() {
		List<UsuarioDTO> dtos = new ArrayList<>();
		List<Usuario> usuarios = usuarioDao.findAll();
		for (Usuario u : usuarios) {
			dtos.add(new UsuarioDTO(u.getUsuario(), u.getContrasena(), u.getNombre(), u.getEmail(),
					u.getRol().getNombre(), u.isActivo(), u.obtenerEstado()));
		}
		return dtos;
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
		List<Rol> roles = rolDao.findAll();
		List<RolDTO> rolesDTO = new ArrayList<>(0);
		for (Rol rol : roles) {
			rolesDTO.add(new RolDTO(rol.getCodigo(), rol.getNombre(), rol.isActivo()));
		}
		return rolesDTO;
	}

	@Override
	public List<RolDTO> obtenerRolesActivos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void guardarRol(Integer codigo, String descripción, boolean estado) {
		// TODO Auto-generated method stub

	}

	@Override
	public RolDTO obtenerRolPorCodigo(Integer codigo) {
		Rol rol = rolDao.find(codigo);
		RolDTO rolDTO = new RolDTO(rol.getCodigo(), rol.getNombre(), rol.isActivo());
		return rolDTO;
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
	public void activarUsuario(String username) {
		// TODO Auto-generated method stub

	}

	@Override
	public void desactivarUsuario(String username) {
		// TODO Auto-generated method stub

	}

	@Override
	public void agregarVivienda(String nombre, String apellido, String dni, String correo, String calle, String altura,
			String codigoPostal, String latitud, String longitud, String barrio)
			throws DataEmptyException, NotNullException, IncorrectEmailException, NotNumberException {
		Dueño dueño = new Dueño(nombre,apellido,dni,correo);
		Direccion direccion = new Direccion(calle, altura,codigoPostal,latitud,longitud,barrio);
		Vivienda vivienda = new Vivienda(direccion,dueño);
		this.viviendaDao.create(vivienda);
	}
	

	@Override
	public List<ViviendaDTO> obtenerViviendas() {
		List<ViviendaDTO> dtos = new ArrayList<>();
		List<Vivienda> viviendas = viviendaDao.findAll();
		for (Vivienda v : viviendas) {
			dtos.add(new ViviendaDTO(v.getDireccion(),v.getDueño(),v.getID()));
		}
		return dtos;
	}
	public List<ViviendaDTO> obtenerViviendasOrdenadas(){
		List<ViviendaDTO>vDTO = this.obtenerViviendas();
		vDTO= vDTO.stream().sorted((v1,v2)->{
				if(v1.getID()>v2.getID())
					return 1;
				else
					return -1;
			}).collect(Collectors.toList());
		return vDTO;
	}
		
	@Override
    public void agregarDueño(String nombre, String apellido, String dni, String correo) {
        Dueño dueño = null;
		try {
			dueño = new Dueño(nombre, apellido, dni, correo);
		} catch (DataEmptyException | NotNullException | IncorrectEmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        this.dueñoDao.create(dueño);
    }


    public List<DueñoDTO> obtenerDueños() {
        List<DueñoDTO> dtos = new ArrayList<>();
        List<Dueño> dueños = dueñoDao.findAll();
        for (Dueño d : dueños) {
            try {
				dtos.add(new DueñoDTO(d.getNombre(), d.getApellido(), d.getDni(), d.getCorreo()));
			} catch (DataEmptyException | NotNullException | IncorrectEmailException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        return dtos;
    }
    public void agregarDireccion(String calle, String altura, String codPostal, String latitud, String longitud, String barrio) {
        //Rol rol = rolDao.find(codigoRol);
        Direccion direccion = null;
		try {
			direccion = new Direccion(calle, altura, codPostal,latitud, longitud, barrio);
		} catch (DataEmptyException | NotNullException | NotNumberException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        this.direccionDao.create(direccion);
    }


    public List<DireccionDTO> obtenerDireccion() {
        List<DireccionDTO> dtos = new ArrayList<>();
        List<Direccion> direcciones = direccionDao.findAll();
        for (Direccion d : direcciones) {
            dtos.add(new DireccionDTO(d.getCalle(), d.getAltura(), d.getCodPostal(), d.getLatitud(), d.getLongitud(),d.getBarrio()));
        }
        return dtos;
    }

}
