package ar.edu.unrn.seminario.api;

import java.util.ArrayList;
import java.util.Collections;
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
import ar.edu.unrn.seminario.accesos.VisitaDAOJDBC;
import ar.edu.unrn.seminario.accesos.VisitaDao;
import ar.edu.unrn.seminario.accesos.ViviendaDAOJDBC;
import ar.edu.unrn.seminario.accesos.ViviendaDao;
import ar.edu.unrn.seminario.dto.DireccionDTO;
import ar.edu.unrn.seminario.dto.DueñoDTO;
import ar.edu.unrn.seminario.dto.OrdenDeRetiroDTO;
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
import ar.edu.unrn.seminario.modelo.Estado;
import ar.edu.unrn.seminario.modelo.OrdenDeRetiro;
import ar.edu.unrn.seminario.modelo.PedidoDeRetiro;
import ar.edu.unrn.seminario.modelo.Recolector;
import ar.edu.unrn.seminario.modelo.Residuo;
import ar.edu.unrn.seminario.modelo.TipoResiduo;
import ar.edu.unrn.seminario.modelo.Rol;
import ar.edu.unrn.seminario.modelo.Usuario;
import ar.edu.unrn.seminario.modelo.UsuarioIngreso;
import ar.edu.unrn.seminario.modelo.Visita;
import ar.edu.unrn.seminario.modelo.Vivienda;
import ar.edu.unrn.seminario.utilities.Filtro;
import ar.edu.unrn.seminario.utilities.OrderingPredicate;
import ar.edu.unrn.seminario.utilities.Predicate;



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

	private VisitaDao visitaDao;

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
		visitaDao = new VisitaDAOJDBC();
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
	
	public void registrarVivienda(String calle, String altura,
			String codigoPostal, String latitud, String longitud, String barrio)
			throws Exception {
		Dueño dueño = dueñoDao.findByUser(this.userOnline.getUsuario());
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
	public List<ViviendaDTO> obtenerViviendasDeUsuario() throws AppException {
		List<ViviendaDTO> dtos = new ArrayList<>();
		
		List<Vivienda> viviendas = viviendaDao.findByUser(this.getUserOnline().getUsuario());
		
		for (Vivienda v : viviendas) {
			dtos.add(new ViviendaDTO(v.getDireccion(),v.getDueño(),v.getID()));
		}
		return dtos;
	}
	
	
	public static class filtradoUsuarioRol implements Comparator<UsuarioDTO>{
		public int compare(UsuarioDTO v1, UsuarioDTO v2) {
			if(v1.getRol().compareToIgnoreCase(v2.getRol())>=0)
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
					dueño.getCorreo(),
					dueño.getUsername());
		}
		return dueñodto;
	}
	
	public DueñoDTO obtenerDueñoActivo() throws AppException {
		String username = this.userOnline.getUsuario();
		
		Dueño dueño = dueñoDao.findByUser(username);
		DueñoDTO dueñodto = null;
		if(dueño!=null) {
			dueñodto = new DueñoDTO(dueño.getNombre(),
					dueño.getApellido(),
					dueño.getDni(),
					dueño.getCorreo(),
					dueño.getUsername());
		}
		return dueñodto;
	}
	
    public List<DueñoDTO> obtenerDueños() throws AppException, NotNumberException {
        List<DueñoDTO> dtos = new ArrayList<DueñoDTO>();
        List<Dueño> dueños = dueñoDao.findAll();
        for (Dueño d : dueños) {
            dtos.add(new DueñoDTO(d.getNombre(), d.getApellido(), d.getDni(), d.getCorreo(), d.getUsername()));
        }
        return dtos;
    }
    
	public boolean existeDueño(String dni) throws AppException {
		return dueñoDao.exists(dni);
	}

 
    public void registrarVisita(ArrayList<String> residuosIngresados, ArrayList<String> residuosIngresadosKg, String observacion, int codOrden) throws AppException{
    	Visita visita = null;
    	
    	ArrayList<TipoResiduo> listaTipos = new ArrayList<TipoResiduo>();
    	
    	for(int i=0;i<residuosIngresados.size();i++){  
    		
    		TipoResiduo t = tipoResiduoDao.find(residuosIngresados.get(i));
    		listaTipos.add(t);
    		
    	}

    	ArrayList<Residuo> listResiduos = new ArrayList<Residuo>();
    	
    	for(int i=0;i<residuosIngresadosKg.size();i++){
    		Residuo r = new Residuo(listaTipos.get(i), Integer.parseInt(residuosIngresadosKg.get(i)));
    		listResiduos.add(r);
    	}
    	
    	visita = new Visita(observacion, listResiduos, codOrden);
    	
    	this.visitaDao.create(visita);
    	
    	if(this.ordenDeRetiroDao.find(codOrden).getVisitas().size() > 0 && !this.ordenDeRetiroDao.find(codOrden).getEstado().obtenerEstado().equals("en ejecucion")) {
    		System.out.println("ok entre, y ahora");
    		actualizarEstadoOrden(codOrden, "en ejecucion");
    	}
    	
    	if(!comprobarCantidadResiduos(codOrden)) {
    		actualizarEstadoOrden(codOrden, "concretado");
    	}
    	
    }
    
    public Boolean comprobarCantidadResiduos(int codOrden) throws AppException {
    	//Lista de los residuos del pedido de retiro asociado a la orden
    	ArrayList<Residuo> listaResiduos = this.ordenDeRetiroDao.find(codOrden).getPedidoAsociado().getListResiduos();
    	//Lista de las visitas asociadas a la orden
    	ArrayList<Visita> listaVisitas = this.ordenDeRetiroDao.find(codOrden).getVisitas();
    	//Lista del total de los kilogramos de cada residuo de todas las visitas
    	ArrayList<Integer> listaSumaVisitas = new ArrayList<Integer>();
    	
    
    	for(int j=0; j<listaResiduos.size(); j++) {
    		listaSumaVisitas.add(0);
    	}
    	
    	for(Visita visita: listaVisitas){
    		
        	int i = 0;
        	
    		for(Residuo residuo: visita.getResiduosExtraidos()){
    			
    			listaSumaVisitas.set(i, listaSumaVisitas.get(i) + residuo.getCantidadKg());
    			
    			i++;
    		}
 
    	}
    	System.out.println("El tamaño de la lista de Residuos es : " + listaResiduos.size());
    	System.out.println("El Tamaño de la Lista de Visitas es de : " + listaVisitas.size());
    	Boolean rtado = false;
    	int i;
    	
    	for(i=0;i<listaResiduos.size();i++) {
    		if(listaResiduos.get(i).getCantidadKg() >= listaSumaVisitas.get(i)) {// quizas cambiar a != en otro momento
    			rtado = true;
    		}
    	}
    	
    	return rtado;  
    }
    
    public void actualizarEstadoOrden(int codOrden, String estado) throws AppException{
    	OrdenDeRetiro orden = this.ordenDeRetiroDao.find(codOrden);
    	Estado nuevoEstado = new Estado(estado);
    	orden.setEstado(nuevoEstado);
    	ordenDeRetiroDao.update(orden);
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
	public PedidoDeRetiroDTO obtenerPedidoDeRetiro(int codigo) throws DataEmptyException, NotNullException, StringNullException, DateNullException, AppException {
		PedidoDeRetiroDTO pedidoDTO = null;
		PedidoDeRetiro pedido= pedidoDeRetiroDao.find(codigo);
		if(pedido!=null) {
			pedidoDTO = new PedidoDeRetiroDTO(pedido.getObservacion(),
					pedido.getMaquinaPesada(),
					pedido.getListResiduos(),
					pedido.getFechaDelPedido(),
					pedido.getVivienda(),
					pedido.getCodigo());
		}
		return pedidoDTO;
	}
	
	public List<PedidoDeRetiroDTO> obtenerPedidosDeRetiro() throws AppException, Exception {
		List<PedidoDeRetiroDTO> pedidosDto = new ArrayList<>();
		
        List<PedidoDeRetiro> pedidos = pedidoDeRetiroDao.findAll();
        for (PedidoDeRetiro d : pedidos) {
            pedidosDto.add(new PedidoDeRetiroDTO(d.getObservacion(), d.getMaquinaPesada(), d.getListResiduos(),d.getFechaDelPedido(), d.getVivienda(), d.getCodigo() ));
        }
        return pedidosDto;
	}
	public List<PedidoDeRetiroDTO> obtenerPedidosDeRetiroDeUsuario() throws AppException, Exception {
		
		List<PedidoDeRetiroDTO> pedidosDto = new ArrayList<>();
		
        List<PedidoDeRetiro> pedidos = pedidoDeRetiroDao.findByUser(this.getUserOnline().getUsuario());
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
    
    public List<OrdenDeRetiroDTO> obtenerOrdenesDeRetiro() throws AppException{
    	List<OrdenDeRetiroDTO> ordenesDto = new ArrayList<>();
    	List<OrdenDeRetiro> ordenes = ordenDeRetiroDao.findAll();
    	for (OrdenDeRetiro o : ordenes) {
    		ordenesDto.add(new OrdenDeRetiroDTO(o.getPedidoAsociado(),
    				o.getRecolector(),
    				o.getFechaOrden(),
    				o.getEstado(), 
    				o.getVisitas(),
    				o.getCodigo()));
    	} 
    	return ordenesDto;
    }
    
    public OrdenDeRetiroDTO obtenerOrdenDeRetiro(int codigo) throws AppException {
    	OrdenDeRetiroDTO o = null;
    	OrdenDeRetiro orden = ordenDeRetiroDao.find(codigo);
    	if(orden!=null) {
    		o = new OrdenDeRetiroDTO(orden.getPedidoAsociado(),
    				orden.getRecolector(),
    				orden.getFechaOrden(),
    				orden.getEstado(),
    				orden.getVisitas(),
    				orden.getCodigo());
    	}
    	return o;
    }
    
    public void actualizarOrdenDeRetiro(OrdenDeRetiro orden) throws AppException {
    	ordenDeRetiroDao.update(orden);
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

	@Override
	public List<UsuarioDTO> obtenerUsuarios(Predicate predicate) throws AppException {
		return Filtro.filtrar(this.obtenerUsuarios(), predicate);	
	}
	
	public List<ViviendaDTO> obtenerViviendas(Predicate predicate) throws AppException{
		return Filtro.filtrar(this.obtenerViviendas(), predicate);
		
		
	}
	@Override
	public List<ViviendaDTO> obtenerViviendas(Comparator comparator) throws AppException {
		 
		return Filtro.filtrar(this.obtenerViviendas(), comparator);
	}
	
	@Override
	public void registrarDueño(String nombre, String apellido, String dni)
			throws DataEmptyException, StringNullException, IncorrectEmailException, NotNumberException, AppException {
		Dueño dueño = new Dueño(nombre, apellido, dni, this.userOnline.getEmail(),this.userOnline.getUsuario());
		
        this.dueñoDao.create(dueño);
        
		
	}

	@Override
	public List<OrdenDeRetiroDTO> obtenerOrdenesDeRetiro(Predicate predicado) throws AppException {
		return Filtro.filtrar(this.obtenerOrdenesDeRetiro(), predicado);
		
	}

	@Override
	public boolean existeDireccion(String calle, String altura) throws AppException {
		return direccionDao.exists(calle, altura);
	}

	@Override
	public boolean existeVivienda(String dni, String calle, String altura) throws AppException {
		return viviendaDao.exists(dni,calle,altura);
	}

	public List<UsuarioDTO> obtenerUsuarios(Comparator comparator ) throws AppException{
		return Filtro.filtrar(this.obtenerUsuarios(), comparator);
		
	}

	public List<PedidoDeRetiroDTO> obtenerPedidosDeRetiro(Predicate predicate) throws AppException, Exception {
		return Filtro.filtrar(this.obtenerPedidosDeRetiro(), predicate);
		
	}
	public List<PedidoDeRetiroDTO> obtenerPedidosDeRetiro(Comparator<PedidoDeRetiroDTO> comparator) throws AppException, Exception{
		return Filtro.filtrar(this.obtenerPedidosDeRetiro(), comparator);
	}








}
