package ar.edu.unrn.seminario.api;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import ar.edu.unrn.seminario.Helper.DateHelper;
import ar.edu.unrn.seminario.accesos.DireccionDAOJDBC;
import ar.edu.unrn.seminario.accesos.DireccionDao;
import ar.edu.unrn.seminario.accesos.DueñoDAOJDBC;
import ar.edu.unrn.seminario.accesos.DueñoDao;
import ar.edu.unrn.seminario.accesos.OrdenDeRetiroDAOJDBC;
import ar.edu.unrn.seminario.accesos.OrdenDeRetiroDao;
import ar.edu.unrn.seminario.accesos.PedidoDeRetiroDao;
import ar.edu.unrn.seminario.accesos.RecolectorDAOJDBC;
import ar.edu.unrn.seminario.accesos.RecolectorDao;
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
import ar.edu.unrn.seminario.modelo.OrdenDeRetiro;
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
	private OrdenDeRetiroDao ordenDeRetiroDao;
	private RecolectorDao recolectorDao;
	

	private Usuario userOnline;

	

	public PersistenceApi() {
		rolDao = new RolDAOJDBC();
		usuarioDao = new UsuarioDAOJDBC();
		viviendaDao = new ViviendaDAOJDBC();
		dueñoDao = new DueñoDAOJDBC();
		direccionDao = new DireccionDAOJDBC();
		pedidoDeRetiroDao = new PedidoDeRetiroDAOJDBC();
		tipoResiduoDao = new TipoResiduoDAOJDBC();
		ordenDeRetiroDao = new OrdenDeRetiroDAOJDBC();
		recolectorDao = new RecolectorDAOJDBC();
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

	public void activarUsuario(String username) throws AppException {
		this.usuarioDao.activate(username);

	}

	public void desactivarUsuario(String username) {
		// TODO Auto-generated method stub

	}
	
	public void usuarioActivo(String username) throws AppException {
		userOnline = usuarioDao.find(username);
		//usuarioDao.activate(username);
	}
	
	public String obtenerRolUsuarioActivo() {
		return userOnline.getRol().getNombre();
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


	//public void agregarVivienda(String nombre, String apellido, String dni, String correo, String calle, String altura,
	
	public void registrarVivienda(/*String nombre, String apellido, String dni, String correo, */String calle, String altura,
			String codigoPostal, String latitud, String longitud, String barrio)
			throws Exception {
		//Dueño dueño = new Dueño(nombre,apellido,dni,correo);
		Dueño dueño = dueñoDao.find(this.userOnline.getUsuario());
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
	
	public List<ViviendaDTO> obtenerViviendasOrdenadasPorCodigo() throws AppException{
		List<ViviendaDTO>vDTO = this.obtenerViviendas();
		vDTO= vDTO.stream()
				.sorted((v1,v2)->v1.getID()-v2.getID())
				.collect(Collectors.toList());
		return vDTO;
	}
	
	public List<ViviendaDTO> obtenerViviendasOrdenadasPorNombreYApellido() throws AppException{
		List<ViviendaDTO>vDTO = this.obtenerViviendas();
		vDTO= vDTO.stream()
				.sorted((v1,v2)->{
					
				String var1 = (v1.getDueño().getNombre()+ " " +  v1.getDueño().getApellido());
				String var2 = (v2.getDueño().getNombre()+ " " + v2.getDueño().getApellido());
				if(var1.compareToIgnoreCase(var2)>0) 
					return 1;
				
				else 
					return -1;
				})
				
				.collect(Collectors.toList());
		return vDTO;
	}
	
	public List<ViviendaDTO> obtenerViviendasOrdenadasPorCodigoPostal() throws AppException{
		List<ViviendaDTO>vDTO = this.obtenerViviendas();
		vDTO= vDTO.stream()
				.sorted((v1,v2)->{
					

				if(v1.getDireccion().getCodPostal().compareToIgnoreCase(v2.getDireccion().getCodPostal())>0) 
					return 1;
				
				else 
					return -1;
				})
				
				.collect(Collectors.toList());
		return vDTO;
	}
	
	public List<ViviendaDTO> obtenerViviendasOrdenadasPorBarrio() throws AppException{
		List<ViviendaDTO>vDTO = this.obtenerViviendas();
		vDTO= vDTO.stream()
				.sorted((v1,v2)->{
				if(v1.getDireccion().getBarrio().compareToIgnoreCase(v2.getDireccion().getBarrio())>0) 
					return 1;
				
				else 
					return -1;
				})
				.collect(Collectors.toList());
		return vDTO;
	}
	
	public List<ViviendaDTO> obtenerViviendasOrdenadasPorAltura() throws AppException{
		List<ViviendaDTO>vDTO = this.obtenerViviendas();
		vDTO= vDTO.stream()
				.sorted((v1,v2)->{
				if(v1.getDireccion().getAltura().compareToIgnoreCase(v2.getDireccion().getAltura())>0) 
					return 1;
				
				else 
					return -1;
				})
				.collect(Collectors.toList());
		return vDTO;
	}
	
	
	public List<ViviendaDTO> obtenerViviendasOrdenadasPorCalle() throws AppException{
		List<ViviendaDTO>vDTO = this.obtenerViviendas();
		vDTO= vDTO.stream()
				.sorted((v1,v2)->{
				if(v1.getDireccion().getCalle().compareToIgnoreCase(v2.getDireccion().getCalle())>0) 
					return 1;
				
				else 
					return -1;
				})
				.collect(Collectors.toList());
		return vDTO;
	}
	
	public List<ViviendaDTO> obtenerViviendasPorLatitudYLongitud() throws AppException{
		List<ViviendaDTO>vDTO = this.obtenerViviendas();
		vDTO= vDTO.stream()
				.sorted((v1,v2)->{
					String latLong1 = (v1.getDireccion().getLatitud() +" " +  v1.getDireccion().getLongitud());
					String latLong2 = (v2.getDireccion().getLatitud() +" " + v2.getDireccion().getLongitud());
					
				if(latLong1.compareToIgnoreCase(latLong2)>0) 
					return 1;
				else 
					return -1;
				})
				.collect(Collectors.toList());
		return vDTO;
	}
	
	public List<UsuarioDTO> obtenerUsuariosOrdenadosPorNombre()throws AppException{
		List<UsuarioDTO> usuario = this.obtenerUsuarios();
		usuario = usuario.stream().sorted((v1,v2)->{
		if(v1.getUsername().compareToIgnoreCase(v2.getUsername()) > 0) {

			return 1;
		}
			
		else {
			return -1;}
		
		})
		.collect(Collectors.toList());
		
		
		
		return usuario;
	}
	public List<UsuarioDTO> obtenerUsuariosOrdenadosPorCorreo()throws AppException{
		List<UsuarioDTO> usuario = this.obtenerUsuarios();
		 usuario = usuario.stream().sorted((v1,v2)->{
		if(v1.getEmail().compareToIgnoreCase(v2.getEmail()) >= 0) {
			
			return 1;}
		else {
			return -1;
		}
		})
		.collect(Collectors.toList());
	return usuario;

   

	}
	
	public List<UsuarioDTO> obtenerUsuariosOrdenadosPorRol()throws AppException{
		List<UsuarioDTO> usuario = this.obtenerUsuarios();
		 usuario = usuario.stream().sorted((v1,v2)->{
		if(v1.getRol().compareToIgnoreCase(v2.getRol())>=0)
			return 1;
		else
			return -1;
		})
		.collect(Collectors.toList());
	return usuario;
	}
	
	public static class filtradoUsuarioRol implements Comparator<UsuarioDTO>{
		public int compare(UsuarioDTO v1, UsuarioDTO v2) {
			if(v1.getEstado().compareToIgnoreCase(v2.getEstado())>=0)
				return 1;
			else
				return -1;
		}
	}
	public static class filtradoUsuarioNombre implements Comparator<UsuarioDTO>{
		public int compare(UsuarioDTO v1, UsuarioDTO v2) {
			if(v1.getUsername().compareToIgnoreCase(v2.getUsername()) > 0) {

				return 1;
			}

			else {
				return -1;}

		}
	}
	public static class filtradoUsuarioCorreo implements Comparator<UsuarioDTO>{
		public int compare(UsuarioDTO v1, UsuarioDTO v2) {
			if(v1.getEmail().compareToIgnoreCase(v2.getEmail()) >= 0) {
				
				return 1;}
			else {
				return -1;
			}
		}
	}
	public List<UsuarioDTO> obtenerUsuariosOrdenados(Comparator<UsuarioDTO> comparador)throws AppException{
		List<UsuarioDTO> usuario = this.obtenerUsuarios();
		usuario = usuario.stream().sorted((v1,v2)->comparador.compare(v1, v2))
		.collect(Collectors.toList());
		return usuario;
	}
	public List<UsuarioDTO> obtenerUsuariosOrdenadosPorEstado() throws AppException{
		List<UsuarioDTO> usuario = this.obtenerUsuarios();
		 usuario = usuario.stream().sorted((v1,v2)->{
		if(v1.getEstado().compareToIgnoreCase(v2.getEstado())>=0)
			return 1;
		else
			return -1;
		})
		.collect(Collectors.toList());
	return usuario;
	}
	
    public void registrarDueño(String nombre, String apellido, String dni, String correo, String username) throws Exception   {
        Dueño dueño = null;
		dueño = new Dueño(nombre, apellido, dni, correo, username);
        this.dueñoDao.create(dueño);
    }
	
	public DueñoDTO obtenerDueño(String dni) throws AppException {
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

    
	public boolean existeDueño(String dni) throws AppException {
		return dueñoDao.exists(dni);
		
	}

 
    
    

    
    public void registrarDireccion(String calle, String altura, String codPostal, String latitud, String longitud, String barrio) throws AppException, DataEmptyException, StringNullException, NotNumberException {
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
    
	public void generarPedidoDeRetiro(boolean cargaPesada, ArrayList<String> residuosSeleccionados, ArrayList<String> residuosSeleccionadosKg, String observacion, ArrayList<String> domicilioSeleccionado) 
		throws AppException, DataEmptyException, NotNullException, StringNullException, 
		DateNullException, NumberFormatException, KilogramEmptyException, NotNumberException {
    	
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
    	
    	//System.out.println(listResiduos.get(0).getTipo());
    	PedidoDeRetiro nuevoPedido = new PedidoDeRetiro(observacion, cargaPesada, listResiduos, fechaActual, unaVivienda, 1);
    	
		this.pedidoDeRetiroDao.create(nuevoPedido);
	
    }
	
	public List<PedidoDeRetiroDTO> obtenerPedidosDeRetiro() throws AppException, Exception {
		List<PedidoDeRetiroDTO> pedidosDto = new ArrayList<>();
		
        List<PedidoDeRetiro> pedidos = pedidoDeRetiroDao.findAll();
        for (PedidoDeRetiro d : pedidos) {
            pedidosDto.add(new PedidoDeRetiroDTO(d.getObservacion(), d.getMaquinaPesada(), d.getListResiduos(),d.getFechaDelPedido(), d.getVivienda(), d.getCodigo() ));
        }
        return pedidosDto;
	}
    
    
    public void generarOrdenDeRetiro(Integer codigoPedidoSeleccionado, String dniRecolector) throws AppException{
    	
    	java.util.Date fechaActualUtil = DateHelper.getDate();
    	java.sql.Date fechaActual = new java.sql.Date(fechaActualUtil.getTime());
    	
    	Recolector recolector = recolectorDao.find(dniRecolector);
    	PedidoDeRetiro unPedido = pedidoDeRetiroDao.find(codigoPedidoSeleccionado);
    	OrdenDeRetiro nuevaOrden = new OrdenDeRetiro(unPedido, recolector , fechaActual );
    	
    	ordenDeRetiroDao.create(nuevaOrden);
    }

    
   

    public void generarOrdenDeRetiro(Integer codigoPedidoSeleccionado) throws AppException{

    	
    	java.util.Date fechaActualUtil = DateHelper.getDate();
    	java.sql.Date fechaActual = new java.sql.Date(fechaActualUtil.getTime());
    	
    	Recolector recolector = null;
    	PedidoDeRetiro unPedido = pedidoDeRetiroDao.find(codigoPedidoSeleccionado);
    	OrdenDeRetiro nuevaOrden = new OrdenDeRetiro(unPedido, recolector , fechaActual );
    	
    	ordenDeRetiroDao.create(nuevaOrden);
    }
    
    
    
    
    
	public void registrarPersonal(String nombre, String apellido, String dni, String correoElectronico, String telefono)
			throws DataEmptyException, StringNullException, IncorrectEmailException, NotNumberException, AppException {
		Recolector p = new Recolector(nombre, apellido, dni, correoElectronico, telefono);
		recolectorDao.create(p);
	}
	

	//public List<RecolectorDTO> obtenerRecolector() throws DataEmptyException, StringNullException, IncorrectEmailException, AppException {
		/*RecolectorDTO recolectoresDto = null;
		
        List<Recolector> recolectores = recolectorDao.find();
        
        return recolectoresDto;*/
	//}
	
	

	public List<DireccionDTO> obtenerDireccionesDeDueño() throws AppException {
		
		// TODO Esbozo de método generado automáticamente
		return null;
	}

	
	@Override
	public List<RecolectorDTO> obtenerRecolectores() 
			throws DataEmptyException, StringNullException, IncorrectEmailException, AppException {
		 List<Recolector> recolectores = recolectorDao.findAll();
		 List<RecolectorDTO> recolectoresDTO = new ArrayList<>();
	        for (Recolector r : recolectores) {
	            recolectoresDTO.add(new RecolectorDTO(r.getNombre(), r.getApellido(), r.getDni(), r.getTelefono(), r.getEmail()));
	        }
	        return recolectoresDTO;
	}
	
	







}
