package ar.edu.unrn.seminario.api;

import java.sql.SQLException;




import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.stream.Collectors;

import ar.edu.unrn.seminario.Helper.DateHelper;
import ar.edu.unrn.seminario.accesos.ConnectionManager;
import ar.edu.unrn.seminario.accesos.DireccionDAOJDBC;
import ar.edu.unrn.seminario.accesos.DireccionDao;
import ar.edu.unrn.seminario.accesos.DueñoDAOJDBC;
import ar.edu.unrn.seminario.accesos.DueñoDao;
import ar.edu.unrn.seminario.accesos.PedidoDeRetiroDao;
import ar.edu.unrn.seminario.accesos.PedidoDeRetiroDAOJDBC;
import ar.edu.unrn.seminario.accesos.RolDAOJDBC;
import ar.edu.unrn.seminario.accesos.RolDao;
import ar.edu.unrn.seminario.accesos.TipoResiduoDAOJDBC;
import ar.edu.unrn.seminario.accesos.TipoResiduoDao;
import ar.edu.unrn.seminario.accesos.UsuarioDAOJDBC;
import ar.edu.unrn.seminario.accesos.UsuarioDao;
import ar.edu.unrn.seminario.accesos.ViviendaDAOJDBC;
import ar.edu.unrn.seminario.accesos.ViviendaDao;
import ar.edu.unrn.seminario.dto.DireccionDTO;
import ar.edu.unrn.seminario.dto.DueñoDTO;
import ar.edu.unrn.seminario.dto.PedidoDeRetiroDTO;
import ar.edu.unrn.seminario.dto.RecolectorDTO;
import ar.edu.unrn.seminario.dto.RolDTO;
import ar.edu.unrn.seminario.dto.UsuarioDTO;
import ar.edu.unrn.seminario.dto.ViviendaDTO;
import ar.edu.unrn.seminario.exceptions.AppException;
import ar.edu.unrn.seminario.exceptions.DataEmptyException;
import ar.edu.unrn.seminario.exceptions.DateNullException;
import ar.edu.unrn.seminario.exceptions.IncorrectEmailException;
import ar.edu.unrn.seminario.exceptions.KilogramEmptyException;
import ar.edu.unrn.seminario.exceptions.NotCorrectPasswordException;
import ar.edu.unrn.seminario.exceptions.NotNullException;
import ar.edu.unrn.seminario.exceptions.StringNullException;
import ar.edu.unrn.seminario.exceptions.NotNumberException;
import ar.edu.unrn.seminario.exceptions.NotRegisterException;
import ar.edu.unrn.seminario.modelo.Direccion;
import ar.edu.unrn.seminario.modelo.Dueño;
import ar.edu.unrn.seminario.modelo.PedidoDeRetiro;
import ar.edu.unrn.seminario.modelo.Recolector;
import ar.edu.unrn.seminario.modelo.Residuo;
import ar.edu.unrn.seminario.modelo.TipoResiduo;
import ar.edu.unrn.seminario.modelo.Rol;
import ar.edu.unrn.seminario.modelo.Usuario;
import ar.edu.unrn.seminario.modelo.UsuarioIngreso;
import ar.edu.unrn.seminario.modelo.Vivienda;


public class PersistenceApi implements IApi {

	private RolDao rolDao;
	private UsuarioDao usuarioDao;
	private ViviendaDao viviendaDao;
	private DueñoDao dueñoDao;
	private DireccionDao direccionDao;
	private PedidoDeRetiroDao pedidoDeRetiroDao;
	private TipoResiduoDao tipoResiduoDao;
	
	

	private Usuario userOnline;

	

	public PersistenceApi() {
		rolDao = new RolDAOJDBC();
		usuarioDao = new UsuarioDAOJDBC();
		viviendaDao = new ViviendaDAOJDBC();
		dueñoDao = new DueñoDAOJDBC();
		direccionDao = new DireccionDAOJDBC();
		pedidoDeRetiroDao = new PedidoDeRetiroDAOJDBC();
		tipoResiduoDao = new TipoResiduoDAOJDBC();
	}

	public void registrarUsuario(String username, String password, String email, Integer codigoRol) 
		throws NotNullException, IncorrectEmailException, DataEmptyException, StringNullException, AppException {
		Rol rol = rolDao.find(codigoRol);
		Usuario usuario = new Usuario(username, password, email, rol);
		this.usuarioDao.create(usuario);
	}

	@Override
	public List<UsuarioDTO> obtenerUsuarios() throws AppException {
		List<UsuarioDTO> dtos = new ArrayList<>();
		List<Usuario> usuarios = usuarioDao.findAll();
		
		for (Usuario u : usuarios) {
			dtos.add(new UsuarioDTO(u.getUsuario(), u.getContrasena(), u.getEmail(),
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
	public List<RolDTO> obtenerRoles() throws AppException {
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
	public RolDTO obtenerRolPorCodigo(Integer codigo) throws AppException {
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

	public void activarUsuario(String username) {
		// TODO Auto-generated method stub

	}

	public void desactivarUsuario(String username) {
		// TODO Auto-generated method stub

	}

	public void agregarVivienda(String nombre, String apellido, String dni, String correo, String calle, String altura,
			String codigoPostal, String latitud, String longitud, String barrio)
			throws Exception {
		Dueño dueño = new Dueño(nombre,apellido,dni,correo);
		Direccion direccion = new Direccion(calle, altura,codigoPostal,latitud,longitud,barrio);
		Vivienda vivienda = new Vivienda(direccion,dueño);
		this.viviendaDao.create(vivienda);
	}
	

	@Override
	public List<ViviendaDTO> obtenerViviendas() throws AppException {
		List<ViviendaDTO> dtos = new ArrayList<>();
		
		List<Vivienda> viviendas = viviendaDao.findAll();
		
		for (Vivienda v : viviendas) {
			dtos.add(new ViviendaDTO(v.getDireccion(),v.getDueño(),v.getID()));
		}
		return dtos;
	}
	
	public List<ViviendaDTO> obtenerViviendasOrdenadas() throws AppException{
		List<ViviendaDTO>vDTO = this.obtenerViviendas();
		vDTO= vDTO.stream()
				.sorted((v1,v2)->v1.getID()-v2.getID())
				.collect(Collectors.toList());
		return vDTO;
	}
		
	@Override
    public void agregarDueño(String nombre, String apellido, String dni, String correo) throws Exception   {
        Dueño dueño = null;
		dueño = new Dueño(nombre, apellido, dni, correo);
        this.dueñoDao.create(dueño);
    }
	
	public DueñoDTO obtenerDueño(String dni) {
		Dueño dueño = dueñoDao.find(dni);
		DueñoDTO dueñodto = null;
		if(dueño!=null) {
			dueñodto = new DueñoDTO(dueño.getNombre(),
					dueño.getApellido(),
					dueño.getDni(),
					dueño.getCorreo());
		}
		return dueñodto;
	}


    public List<DueñoDTO> obtenerDueños() throws AppException, NotNumberException {
        List<DueñoDTO> dtos = new ArrayList<DueñoDTO>();
        List<Dueño> dueños = dueñoDao.findAll();
        for (Dueño d : dueños) {
            dtos.add(new DueñoDTO(d.getNombre(), d.getApellido(), d.getDni(), d.getCorreo()));
        }
        return dtos;
    }
    
    public void agregarDireccion(String calle, String altura, String codPostal, String latitud, String longitud, String barrio) throws Exception {
        //Rol rol = rolDao.find(codigoRol);
        Direccion direccion = null;
		direccion = new Direccion(calle, altura, codPostal,latitud, longitud, barrio);
        this.direccionDao.create(direccion);
    }
    
    public DireccionDTO obtenerDireccion(String calle, int altura) throws AppException {
    	DireccionDTO d = null;
    	Direccion direccion = direccionDao.find(calle, altura);
    	if(direccion!=null) {
    		d=new DireccionDTO(direccion.getCalle(),
    				direccion.getAltura(),
    				direccion.getCodPostal(),
    				direccion.getLongitud(),
    				direccion.getLatitud(),
    				direccion.getBarrio());
    		}
    	return d;
    	}
    
    public List<DireccionDTO> obtenerDirecciones() throws AppException {
        List<DireccionDTO> dtos = new ArrayList<>();
        List<Direccion> direcciones = direccionDao.findAll();
        for (Direccion d : direcciones) {
            dtos.add(new DireccionDTO(d.getCalle(), d.getAltura(), d.getCodPostal(), d.getLatitud(), d.getLongitud(),d.getBarrio()));
        }
        return dtos;
    }

    @Override
	public void generarPedidoDeRetiro(boolean cargaPesada, ArrayList<String> residuosSeleccionados, ArrayList<String> residuosSeleccionadosKg, String observacion, ArrayList<String> domicilioSeleccionado) 
		throws AppException, DataEmptyException, NotNullException, StringNullException, 
		DateNullException, NumberFormatException, KilogramEmptyException, NotNumberException {
    	
    	//hay que solucionar esto
    	//no me parece correcto que se cree una excepcion en persistence pero 
    	//si no esta ningnuna de estas, en algun caso va a provocar un error

    	
    	if(domicilioSeleccionado == null) {
    		throw new NotNullException("No selecciono ningun domicilio");
    	}
    	 if(residuosSeleccionados.size() != residuosSeleccionadosKg.size()) {
    		 throw new NotNullException("Indique el campo kg");
         }
    	
    	if(residuosSeleccionados.size() == 0) {
    		throw new NotNullException("No selecciono ningun residuo");
    	}
    	if(residuosSeleccionadosKg.size() == 0) {
    		throw new NotNullException("Por favor, indique el kg");
    	}
    	
    	ArrayList<TipoResiduo> listaTipos = new ArrayList<TipoResiduo>();
    	
    	for(int i=0;i<residuosSeleccionados.size();i++){
    		TipoResiduo t = tipoResiduoDao.find(residuosSeleccionados.get(i));
    		listaTipos.add(t);
    	}
    	
    	ArrayList<Residuo> listResiduos = new ArrayList<Residuo>();
    	
    	for(int i=0;i<residuosSeleccionadosKg.size();i++){
    		Residuo r = new Residuo(listaTipos.get(i), Integer.parseInt(residuosSeleccionadosKg.get(i)));
    		listResiduos.add(r);
    		
    	}


    	java.util.Date fechaActualUtil = DateHelper.getDate();
    	java.sql.Date fechaActual = new java.sql.Date(fechaActualUtil.getTime());
    	
    	
    	
    	//domicilioSeleccionado.get(1) tiene la calle
    	// domiciltioSeleccionado.get(2) tiene la altura
    	Vivienda unaVivienda = viviendaDao.find(domicilioSeleccionado.get(1), domicilioSeleccionado.get(2));
    	
    	PedidoDeRetiro nuevoPedido = new PedidoDeRetiro(observacion, cargaPesada, listResiduos, fechaActual, unaVivienda);
    	
		this.pedidoDeRetiroDao.create(nuevoPedido);
	
    }

	@Override
	public void agregarPersonal(String nombre, String apellido, String dni, String correoElectronico, String telefono)
			throws DataEmptyException, StringNullException, IncorrectEmailException, NotNumberException {
		Recolector p = new Recolector(nombre, apellido, dni, correoElectronico, telefono);
		
	}


	public boolean existeUsuario(String usuario) throws NotRegisterException, AppException {
		return usuarioDao.exists(usuario);
	}
	
	public boolean validarUsuario(String usuario, String password) throws NotRegisterException,AppException, NotCorrectPasswordException, DataEmptyException, StringNullException, IncorrectEmailException {
		UsuarioIngreso user = new UsuarioIngreso(usuario,password);
		if(usuarioDao.validateData(user)){
			this.userOnline = usuarioDao.find(usuario); 
		}
		return usuarioDao.validateData(user);
		
	}
	
	public Usuario getUserOnline(){
		return this.userOnline;
	}

	public boolean existeDueño(String dni) throws AppException {
		return dueñoDao.exists(dni);
		
	}

	public List<DireccionDTO> obtenerDireccionesDeDueño() throws AppException {
		
		// TODO Esbozo de método generado automáticamente
		return null;
	}

	@Override
	public void usuarioActivo(String username) throws AppException {
		userOnline = usuarioDao.find(username);
		//usuarioDao.activate(username);
	}
	
	public String obtenerRolUsuarioActivo() {
		return userOnline.getRol().getNombre();
	}
	
	@Override
	public List<RecolectorDTO> obtenerRecolectores() {
		// TODO Esbozo de método generado automáticamente
		return null;
	}
	
	public List<PedidoDeRetiroDTO> obtenerPedidosDeRetiro() throws AppException, Exception {
		List<PedidoDeRetiroDTO> pedidosDto = new ArrayList<>();
		
        List<PedidoDeRetiro> pedidos = pedidoDeRetiroDao.findAll();
        for (PedidoDeRetiro d : pedidos) {
            pedidosDto.add(new PedidoDeRetiroDTO(d.getObservacion(), d.getMaquinaPesada(), d.getListResiduos(),d.getFechaDelPedido(), d.getVivienda() ));
        }
        return pedidosDto;
	}





}
