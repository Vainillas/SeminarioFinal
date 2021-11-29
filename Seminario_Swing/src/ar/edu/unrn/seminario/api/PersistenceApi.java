package ar.edu.unrn.seminario.api;

import java.sql.Date;
import java.util.ArrayList;



import java.util.Comparator;
import java.util.List;
import ar.edu.unrn.seminario.Helper.DateHelper;
import ar.edu.unrn.seminario.accesos.BeneficioDAOJDBC;
import ar.edu.unrn.seminario.accesos.BeneficioDao;
import ar.edu.unrn.seminario.accesos.CanjeDAOJDBC;
import ar.edu.unrn.seminario.accesos.CanjeDao;

import ar.edu.unrn.seminario.accesos.CampañaDAOJDBC;
import ar.edu.unrn.seminario.accesos.CampañaDao;

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
import ar.edu.unrn.seminario.dto.BeneficioDTO;
import ar.edu.unrn.seminario.dto.CampañaDTO;
import ar.edu.unrn.seminario.dto.CanjeDTO;
import ar.edu.unrn.seminario.dto.DireccionDTO;
import ar.edu.unrn.seminario.dto.DueñoDTO;
import ar.edu.unrn.seminario.dto.OrdenDeRetiroDTO;
import ar.edu.unrn.seminario.dto.PedidoDeRetiroDTO;
import ar.edu.unrn.seminario.dto.RecolectorDTO;
import ar.edu.unrn.seminario.dto.ResiduoDTO;
import ar.edu.unrn.seminario.dto.RolDTO;
import ar.edu.unrn.seminario.dto.UsuarioDTO;
import ar.edu.unrn.seminario.dto.ViviendaDTO;
import ar.edu.unrn.seminario.exceptions.AppException;
import ar.edu.unrn.seminario.exceptions.DataEmptyException;
import ar.edu.unrn.seminario.exceptions.DateNullException;
import ar.edu.unrn.seminario.exceptions.IncorrectEmailException;
import ar.edu.unrn.seminario.exceptions.InsuficientPointsException;
import ar.edu.unrn.seminario.exceptions.KilogramEmptyException;
import ar.edu.unrn.seminario.exceptions.NotCorrectPasswordException;
import ar.edu.unrn.seminario.exceptions.NotNullException;
import ar.edu.unrn.seminario.exceptions.StringNullException;
import ar.edu.unrn.seminario.exceptions.NotNumberException;
import ar.edu.unrn.seminario.exceptions.NotRegisterException;
import ar.edu.unrn.seminario.modelo.Beneficio;
import ar.edu.unrn.seminario.modelo.Campaña;
import ar.edu.unrn.seminario.modelo.Canje;
import ar.edu.unrn.seminario.modelo.Catalogo;
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
	private BeneficioDao beneficioDao;
	private CanjeDao canjeDao;
	private CampañaDao campañaDao;
	private Usuario userOnline;
	private final String enEjecucion = "en ejecucion";
	private final String concretado = "concretado";
	private final String pendiente = "pendiente";
	private final String cancelado = "cancelado";

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
		beneficioDao = new BeneficioDAOJDBC();
		canjeDao = new CanjeDAOJDBC();
		campañaDao = new CampañaDAOJDBC();
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
					u.getRol(), u.isActivo(), u.obtenerEstado()));
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

	public void desactivarUsuario(String username) throws AppException {
		this.usuarioDao.deactivate(username);
	}
	
	public void usuarioActivo(String username) throws AppException {
		userOnline = usuarioDao.find(username);
		//usuarioDao.activate(username);
	}
	public boolean existeDueñoRegistrado() throws AppException{
		boolean existe = false;
		if(dueñoDao.existsByUser(this.userOnline.getUsuario()))
			existe = true;
		return existe;
		
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
			String codigoPostal, String latitud, String longitud, String barrio) throws AppException, DataEmptyException, StringNullException, NotNumberException {
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
	
    public void registrarDueño(String nombre, String apellido, String dni, String correo, String username) throws Exception   {
    	Usuario usuario = null;
    	usuario = usuarioDao.find(username);
        Dueño dueño = null;
		dueño = new Dueño(nombre, apellido, dni, correo, usuario);
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
					dueño.getUser(),
					dueño.getPuntaje());
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
					dueño.getUser(),
					dueño.getPuntaje());
		}
		return dueñodto;
	}
	
    public List<DueñoDTO> obtenerDueños() throws AppException, NotNumberException {
        List<DueñoDTO> dtos = new ArrayList<DueñoDTO>();
        List<Dueño> dueños = dueñoDao.findAll();
        for (Dueño d : dueños) {
            dtos.add(new DueñoDTO(d.getNombre(), d.getApellido(), d.getDni(), d.getCorreo(), d.getUser(),d.getPuntaje()));
        }
        return dtos;
    }
    
	public boolean existeDueño(String dni) throws AppException {
		return dueñoDao.exists(dni);
	}


    public void registrarVisita(ArrayList<String> residuosIngresados, ArrayList<String> residuosIngresadosKg, String observacion, Integer codOrden) throws AppException, NotNullException{
    	System.out.println(residuosIngresados.toString());
    	System.out.println(residuosIngresadosKg.toString());
    	
    	Visita visita = null; //Inicializo visita en null
    	ArrayList<TipoResiduo> listaTipos = new ArrayList<TipoResiduo>(); //Inicializo la lista de tipo de residuos

    	
    	for(int i=0;i<residuosIngresados.size();i++){  
    		//Por cada tipo de residuo ingresado creo una instancia y la agrego a la lista
    		TipoResiduo t = tipoResiduoDao.find(residuosIngresados.get(i));
    		listaTipos.add(t);
    	}
    	//Creo una lista de residuos
    	ArrayList<Residuo> listResiduos = new ArrayList<Residuo>();
    	//Instancio los residuos con el tipo de residuo que instancié antes y le meto la cantidad
    	for(int i=0;i<residuosIngresadosKg.size();i++){
    		
    		Residuo r = new Residuo(listaTipos.get(i), Integer.parseInt(residuosIngresadosKg.get(i)));
    		listResiduos.add(r);
    	
    	}
    	
    	//INICIO DE COMPROBACIÓN PARA EL CAPPEO//
    	OrdenDeRetiro ordenComprobacion = this.ordenDeRetiroDao.find(codOrden);
    	visita = new Visita(observacion, listResiduos, codOrden); //Creo la visita
    	if(comprobarExcedenteResiduos(ordenComprobacion, visita)) {
    		throw new AppException("Se ingresaron mas residuos de lo debido.");
    		
    	}

    	
    	
    	
    	
    	
    	//Si pasa el cappeo, creo la visita
    	if(ordenComprobacion.getEstado().enEjecucion() || ordenComprobacion.getEstado().pendiente()) {

        	this.visitaDao.create(visita);
        	

        	OrdenDeRetiro ordenCorrespondiente = this.ordenDeRetiroDao.find(codOrden);
        	if(ordenCorrespondiente.getVisitas().size() > 0 && !ordenCorrespondiente.getEstado().obtenerEstado().equals(enEjecucion)) {
        		actualizarEstadoOrden(codOrden, new Estado(enEjecucion));
        	}
        	
        	if(!comprobarCantidadResiduos(codOrden)) {
        		actualizarEstadoOrden(codOrden, new Estado(concretado));
        		OrdenDeRetiro orden = ordenDeRetiroDao.find(codOrden);
        		int puntaje = calcularPuntaje(orden);
        		sumarPuntos(orden.getPedidoAsociado().getVivienda().getDueño(), puntaje);
        	}
    	}
    	else
    		throw new AppException("La orden a agregar una visita ya está concretada o cancelada");
    	
    	
    	
    }
    public boolean comprobarExcedenteResiduos(OrdenDeRetiro ordenAComprobar, Visita visitaNueva) {
    	boolean excedido = false;
    	ArrayList<Visita> visitas= ordenAComprobar.getVisitas();
    	visitas.add(visitaNueva);
    	//Lista de residuos del pedido de retiro
    	ArrayList<Residuo> listaResiduos = ordenAComprobar.getPedidoAsociado().getListResiduos();
    	
    	
    	ArrayList<Residuo> listaSumaVisitas = new ArrayList<Residuo>();
    	for(int j=0; j<listaResiduos.size(); j++) {
    		listaSumaVisitas.add(new Residuo(listaResiduos.get(j).getTipo(), 0));
    	}
    	for(Visita visita: visitas){
    		for(Residuo residuo: visita.getResiduosExtraidos()){
    			for(int c = 0; c<listaSumaVisitas.size(); c++ ) {
    				if(listaSumaVisitas.get(c).getTipo().equals(residuo.getTipo())) {
    					listaSumaVisitas.set(c, new Residuo(listaSumaVisitas.get(c).getTipo(), listaSumaVisitas.get(c).getCantidadKg() + residuo.getCantidadKg()));
    				}
    			}
    		}
    		
    	}
    	int i;
    	for(i=0;i<listaResiduos.size();i++) {
    		
    		if(listaResiduos.get(i).getCantidadKg() < listaSumaVisitas.get(i).getCantidadKg()) {// quizas cambiar a != en otro momento
    			//Si alguna cantidad de un residuo especifico en la lista de residuos del pedido
    			//es mayor o igual a la cantidad de ese mismo residuo en el total de la lista de visitas
    			// el resultado pasa a ser verdadero
    			
    			excedido = true;
    		}
    	}
    	
    	
    	return excedido;
    }
    
    public Boolean comprobarCantidadResiduos(int codOrden) throws AppException {
    	
    	OrdenDeRetiro ordenCorrespondiente = this.ordenDeRetiroDao.find(codOrden);
    	
    	//Lista de los residuos del pedido de retiro asociado a la orden
    	ArrayList<Residuo> listaResiduos = ordenCorrespondiente.getPedidoAsociado().getListResiduos();
    	//System.out.println("Lista Residuos de comprobarCantidadResiduos: "+ listaResiduos);
    	//Lista de las visitas asociadas a la orden
    	ArrayList<Visita> listaVisitas = ordenCorrespondiente.getVisitas();
    	//System.out.println("Lista Visitas de comprobarCantidadResiduos: "+ listaVisitas);
    	//Lista del total de los kilogramos de cada residuo de todas las visitas
    	ArrayList<Residuo> listaSumaVisitas = new ArrayList<Residuo>();
    	for(int j=0; j<listaResiduos.size(); j++) {
    		listaSumaVisitas.add(new Residuo(listaResiduos.get(j).getTipo(), 0));
    	}
    	//System.out.println("Lista SumaVisitas de comprobarCantidadResiduos después del primer for: "+ listaSumaVisitas);
    	
    	for(Visita visita: listaVisitas){
    		for(Residuo residuo: visita.getResiduosExtraidos()){
    			//A listaSumaVisitas se le setea en la posición i, el valor que tenía previamente
    			//mas la cantidad de kilogramos del residuo de la visita actual
    			//System.out.println("Información del residuo a agregar: " + residuo.toString());
    			for(int c = 0; c<listaSumaVisitas.size(); c++ ) {
    				//Implementación con listaSumaVisitas con Residuos en vez de Integer
    				//System.out.println("Información del residuo "+ c + " en listaSuma visitas: " + listaSumaVisitas.get(c).toString());
    				if(listaSumaVisitas.get(c).getTipo().equals(residuo.getTipo())) {
    					//System.out.println("Información del tipoResiduo de listaSumaVisitas "+ c + " después de entrar al if: " +listaSumaVisitas.get(c).getTipo());
    					//System.out.println("Información del tipoResiduo del residuo "+ c + " después de entrar al if: " +residuo.getTipo());
    					listaSumaVisitas.set(c, new Residuo(listaSumaVisitas.get(c).getTipo(), listaSumaVisitas.get(c).getCantidadKg() + residuo.getCantidadKg()));
    				}
    			}
    		}
 
    	}
    	//System.out.println("El tamaño de la lista de Residuos en comprobarCantidadResiduos es : " + listaResiduos.size());
    	//System.out.println("El Tamaño de la Lista de Visitas en comprobarCantidadResiduos es de : " + listaVisitas.size());
    	//System.out.println("Lista SumaVisitas de comprobarCantidadResiduos después del segundo for: "+ listaSumaVisitas);
    	
    	
    	Boolean rtado = false;
    	int i;
    	
    	for(i=0;i<listaResiduos.size();i++) {
    		//System.out.println("Residuo Numero "+ i +"de la listaResiduos en sección Boolean de comprobarCantidadResiduos: "+ listaResiduos.get(i));
    		//System.out.println("Residuo Numero "+ i +"de la listaSumaVisitas en sección Boolean de comprobarCantidadResiduos: "+ listaSumaVisitas.get(i));
    		if(listaResiduos.get(i).getCantidadKg() > listaSumaVisitas.get(i).getCantidadKg()) {// quizas cambiar a != en otro momento
    			//Si alguna cantidad de un residuo especifico en la lista de residuos del pedido
    			//es mayor o igual a la cantidad de ese mismo residuo en el total de la lista de visitas
    			// el resultado pasa a ser verdadero
    			
    			rtado = true;
    		}
    	}
    	//System.out.println("El valor del booleano en comprobarCantidadResiduos es de: "+ rtado);
    	return rtado;  
    }
    public List<ResiduoDTO> devolverResiduosRestantes(int codOrden) throws AppException{
    	OrdenDeRetiro orden = this.ordenDeRetiroDao.find(codOrden);
    	ArrayList<Visita> visitas= orden.getVisitas();
    	ArrayList<Residuo> listaResiduos = orden.getPedidoAsociado().getListResiduos();
    	ArrayList<Residuo> listaSumaVisitas = new ArrayList<Residuo>();
    	ArrayList<ResiduoDTO> listaResiduosRestantesDTO = new ArrayList<ResiduoDTO>();
    	for(int j=0; j<listaResiduos.size(); j++) {
    		listaSumaVisitas.add(new Residuo(listaResiduos.get(j).getTipo(), 0));
    	}
    	for(Visita visita: visitas){
    		for(Residuo residuo: visita.getResiduosExtraidos()){
    			for(int c = 0; c<listaSumaVisitas.size(); c++ ) {
    				if(listaSumaVisitas.get(c).getTipo().equals(residuo.getTipo())) {
    					listaSumaVisitas.set(c, new Residuo(listaSumaVisitas.get(c).getTipo(), listaSumaVisitas.get(c).getCantidadKg() + residuo.getCantidadKg()));
    				}
    			}
    		}
    	}
    	
    	int i;
    	for(i=0;i<listaResiduos.size();i++) {
    		int cantidad = listaResiduos.get(i).getCantidadKg() - listaSumaVisitas.get(i).getCantidadKg();
    		ResiduoDTO residuoDTO = null;
    		if(cantidad >=0) {
    			residuoDTO = new ResiduoDTO(listaSumaVisitas.get(i).getTipo(), cantidad);
    		}
    		else
    			residuoDTO = new ResiduoDTO(listaSumaVisitas.get(i).getTipo(), 0);
    		listaResiduosRestantesDTO.add(residuoDTO);
    	}
    	return listaResiduosRestantesDTO;
    }
    
    public void actualizarEstadoOrden(int codOrden, Estado estado) throws AppException{
    	OrdenDeRetiro orden = this.ordenDeRetiroDao.find(codOrden);
    	boolean valido = false;
    	if(estado.cancelado()) {
    		valido = orden.cancelar();
    	}
    	if(estado.concretado()) {
    		valido = orden.concretar();
    	}
    	if(estado.enEjecucion()) {
    		valido = orden.ejecutar();
    	}
    	if(estado.pendiente()) {
    		valido = orden.setPendiente();
    	}
    	if(valido)
    		ordenDeRetiroDao.update(orden);
    	else {
    		throw new AppException("No se pudo actualizar la orden al estado \""+estado.obtenerEstado()+"\"");
    	}
    }
    public void concretarOrdenDeRetiro(int codOrden) throws AppException{
    	OrdenDeRetiro orden = ordenDeRetiroDao.find(codOrden);
    	actualizarEstadoOrden(codOrden, new Estado(concretado));
    	int puntaje = calcularPuntaje(orden);
		sumarPuntos(orden.getPedidoAsociado().getVivienda().getDueño(), puntaje);
    }
    public void cancelarOrdenDeRetiro(int codOrden) throws AppException{
    	actualizarEstadoOrden(codOrden, new Estado(cancelado));
    }
    
    public void registrarDireccion(String calle, String altura, String codPostal, String latitud, String longitud, String barrio) 
    		throws AppException, DataEmptyException, StringNullException, NotNumberException {
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
    
    public ArrayList<String> obtenerNombresResiduos() throws AppException{ 
    	ArrayList<String> nombresResiduos = new ArrayList<>();
		
		List<TipoResiduo> tiposResiduos = tipoResiduoDao.findAll();  
		
		for (TipoResiduo t : tiposResiduos) {
			nombresResiduos.add(t.getNombre());
		}
		return nombresResiduos;
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
	
	public List<PedidoDeRetiroDTO> obtenerPedidosDeRetiro() 
			throws AppException, IncorrectEmailException, DataEmptyException, NotNullException, StringNullException, DateNullException {
		List<PedidoDeRetiroDTO> pedidosDto = new ArrayList<>();
		
        List<PedidoDeRetiro> pedidos = pedidoDeRetiroDao.findAll();
        for (PedidoDeRetiro d : pedidos) {
            pedidosDto.add(new PedidoDeRetiroDTO(d.getObservacion(), d.getMaquinaPesada(), d.getListResiduos(),d.getFechaDelPedido(), d.getVivienda(), d.getCodigo() ));
        }
        return pedidosDto;
	}
	
	public List<PedidoDeRetiroDTO> obtenerPedidosDeRetiroDeUsuario() throws AppException, IncorrectEmailException, DataEmptyException, NotNullException, StringNullException, DateNullException {
		
		List<PedidoDeRetiroDTO> pedidosDto = new ArrayList<>();
		
        List<PedidoDeRetiro> pedidos = pedidoDeRetiroDao.findByUser(this.getUserOnline().getUsuario());
        for (PedidoDeRetiro d : pedidos) {
            pedidosDto.add(new PedidoDeRetiroDTO(d.getObservacion(), d.getMaquinaPesada(), d.getListResiduos(),d.getFechaDelPedido(), d.getVivienda(), d.getCodigo() ));
        }
        return pedidosDto;
	}
	
	public List<PedidoDeRetiroDTO>obtenerPedidosDeRetiroSinOrden()throws AppException, Exception{
		List<PedidoDeRetiroDTO> pedidosDto = new ArrayList<>();
		
        List<PedidoDeRetiro> pedidos = pedidoDeRetiroDao.findNoOrder();
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<UsuarioDTO> obtenerUsuarios(Predicate  predicate) throws AppException {
		return Filtro.filtrar(this.obtenerUsuarios(), predicate);	
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	
	public  List<ViviendaDTO> obtenerViviendas(Predicate predicate) throws AppException{
		return Filtro.filtrar(this.obtenerViviendas(), predicate);
		
		
	}
	@Override
	public <T>List<ViviendaDTO> obtenerViviendas(Comparator<T> comparator) throws AppException {
		 
		return Filtro.filtrar(this.obtenerViviendas(), comparator);
	}
	
	@Override
	public void registrarDueño(String nombre, String apellido, String dni)
			throws DataEmptyException, StringNullException, IncorrectEmailException, NotNumberException, AppException {
		Dueño dueño = new Dueño(nombre, apellido, dni, this.userOnline.getEmail(),this.userOnline);
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

	public  List<PedidoDeRetiroDTO> obtenerPedidosDeRetiro(Predicate  predicate) 
			throws AppException, IncorrectEmailException, DataEmptyException, NotNullException, StringNullException, DateNullException {
		return Filtro.filtrar(this.obtenerPedidosDeRetiro(), predicate);
		
	}
	public List<PedidoDeRetiroDTO> obtenerPedidosDeRetiro(Comparator<PedidoDeRetiroDTO> comparator) throws AppException, IncorrectEmailException, DataEmptyException, NotNullException, StringNullException, DateNullException{
		return Filtro.filtrar(this.obtenerPedidosDeRetiro(), comparator);
	}

	@Override
	public List<DueñoDTO> obtenerDueños(Predicate<DueñoDTO> predicate) throws AppException, NotNumberException {
		return Filtro.filtrar(this.obtenerDueños(), predicate);
	}
	
	public List<DueñoDTO> obtenerDueños(Comparator<DueñoDTO> comparator) throws AppException, NotNumberException {
		return Filtro.filtrar(this.obtenerDueños(), comparator);
		
		
	}


	public void agregarBeneficio(String descripcion, String puntajeConsumible) throws NotNullException, DataEmptyException, NotNumberException, AppException {
		
		Beneficio beneficio = new Beneficio(descripcion, puntajeConsumible);
		
		beneficioDao.create(beneficio);
		
	}
	
	public List<OrdenDeRetiroDTO> obtenerOrdenesDeRetiro(Comparator<OrdenDeRetiroDTO> comparator) throws AppException{
		return Filtro.filtrar(this.obtenerOrdenesDeRetiro(), comparator);
	}
	
	public List<BeneficioDTO> obtenerBeneficios() throws AppException, NotNullException, DataEmptyException, NotNumberException{
		List<BeneficioDTO> beneficiosDto = new ArrayList<>();
    	List<Beneficio> beneficios = beneficioDao.findAll();
    	for (Beneficio b : beneficios) {
    		beneficiosDto.add(new BeneficioDTO(b.getDescripcion(),b.getPuntajeConsumible(), b.getCodigo()));
    	} 
    	return beneficiosDto;
	}
	public BeneficioDTO obtenerBeneficioPorCodigo(int codigo) throws AppException, NotNullException{
		BeneficioDTO beneficiosDto = null;
    	Beneficio beneficio = beneficioDao.find(codigo);
    	beneficiosDto = new BeneficioDTO(beneficio.getDescripcion(),beneficio.getPuntajeConsumible(), beneficio.getCodigo());
    	return beneficiosDto;
	}


	
	public void generarCanje(int codBeneficio, int codCampaña) throws AppException, NotNullException, InsuficientPointsException {
		
		Beneficio beneficio = this.beneficioDao.find(codBeneficio);
		
		Campaña campaña = this.campañaDao.find(codCampaña);
		
		Dueño dueño = this.dueñoDao.findByUser(this.userOnline.getUsuario());
		
		Canje canje = new Canje(beneficio, dueño, campaña);
		
		canjeDao.create(canje);
		int puntajeNuevo = dueño.getPuntaje() - canje.getBeneficioCanjeado().getPuntajeConsumible();
		dueño.setPuntaje(puntajeNuevo);
		dueñoDao.update(dueño);
		
	}
	
	public List<CanjeDTO> obtenerCanjes() throws AppException, NotNullException, DataEmptyException, NotNumberException{
		List<CanjeDTO> canjesDto = new ArrayList<>();
    	List<Canje> canjes = canjeDao.findAll();
    	for (Canje c : canjes) {
    		canjesDto.add(new CanjeDTO(c.getBeneficioCanjeado(),c.getDueñoCanjeador(), c.getCampaña(),(Date) c.getFechaCanje(),c.getCodigo()));
    	} 
    	return canjesDto;
	}
	public List<CanjeDTO> obtenerCanjesPorUsuario() throws AppException, NotNullException, DataEmptyException, NotNumberException{
		String username = this.userOnline.getUsuario();
		Dueño dueño = dueñoDao.findByUser(username);
		List<CanjeDTO> canjesDto = new ArrayList<>();
    	List<Canje> canjes = canjeDao.findByUser(dueño);
    	for (Canje c : canjes) {
    		canjesDto.add(new CanjeDTO(c.getBeneficioCanjeado(),c.getDueñoCanjeador(), c.getCampaña(),(Date) c.getFechaCanje(),c.getCodigo()));
    	} 
    	return canjesDto;
	}
	public int calcularPuntaje(OrdenDeRetiro unaOrden){
		int sumaPuntos = 0;
		for(Visita v : unaOrden.getVisitas()) {
			for(Residuo r: v.getResiduosExtraidos()){
				sumaPuntos = sumaPuntos + r.getCantidadKg() * r.getTipo().getValor(); 
			}
		}
		
		return sumaPuntos;
	}
	
	public void sumarPuntos(Dueño dueño, int puntaje) throws AppException {  
		
		dueño.sumarPuntos(puntaje);
		dueñoDao.update(dueño);
	}
	public void generarCampaña(List<Integer> listaBeneficios, String unNombre) throws AppException, NotNullException {
		
		ArrayList<Beneficio> listaDeBeneficios = new ArrayList<Beneficio>();
		for(Integer i: listaBeneficios) {
			listaDeBeneficios.add(this.beneficioDao.find(i));
		}
		
		Catalogo catalogo = new Catalogo(listaDeBeneficios);
		
		Campaña campaña = new Campaña(unNombre, catalogo, "Activa");
		
		this.campañaDao.create(campaña);
		
	}
	public List<CampañaDTO> obtenerCampañas() throws AppException, NotNullException, DataEmptyException, NotNumberException{
		List<CampañaDTO> campañasDto = new ArrayList<>();
    	List<Campaña> campañas = campañaDao.findAll();


    	for (Campaña c : campañas) {
    		campañasDto.add(new CampañaDTO(c.getNombreCampaña(), c.getCatalogo(),c.getEstado(), c.getCodigo()));
    	} 
    	return campañasDto;
	}
	
	public CampañaDTO obtenerCampañaPorCodigo(int codigo) throws AppException, NotNullException, DataEmptyException, NotNumberException{
		CampañaDTO campañaDto = null;
    	Campaña campaña = campañaDao.find(codigo);
    		campañaDto = new CampañaDTO(campaña.getNombreCampaña(), campaña.getCatalogo(),campaña.getEstado(), campaña.getCodigo());
    	return campañaDto;
	}
	
	public List<CampañaDTO> obtenerCampañas(Predicate<CampañaDTO> predicado) throws AppException, NotNullException, DataEmptyException, NotNumberException{
		return Filtro.filtrar(this.obtenerCampañas(), predicado);
		
	}
	public List<BeneficioDTO> obtenerBeneficios(Predicate<BeneficioDTO> predicado) throws AppException, NotNullException, DataEmptyException, NotNumberException{
		return Filtro.filtrar(this.obtenerBeneficios(), predicado);
		
	}

	@Override
	public List<CanjeDTO> obtenerCanjes(Predicate<CanjeDTO> predicate) throws AppException, NotNullException, DataEmptyException, NotNumberException {
		return Filtro.filtrar(this.obtenerCanjes(),predicate);
	}

	public List<ViviendaDTO> obtenerViviendasDeUsuario(Comparator<ViviendaDTO> comparator) throws AppException {
		return Filtro.filtrar(this.obtenerViviendasDeUsuario(), comparator);
		
	}

	@Override
	public List<ViviendaDTO> obtenerViviendasDeUsuario(Predicate<ViviendaDTO> predicate) throws AppException {
		return Filtro.filtrar(this.obtenerViviendasDeUsuario(), predicate);
	}

	@Override
	public List<PedidoDeRetiroDTO> obtenerPedidosDeRetiroDeUsuario(Comparator<PedidoDeRetiroDTO> comparator) throws AppException, IncorrectEmailException, DataEmptyException, NotNullException, StringNullException, DateNullException {
		return Filtro.filtrar(this.obtenerPedidosDeRetiroDeUsuario(), comparator);
	}

	@Override
	public List<PedidoDeRetiroDTO> obtenerPedidosDeRetiroDeUsuario(Predicate<PedidoDeRetiroDTO> predicate) throws AppException, IncorrectEmailException, DataEmptyException, NotNullException, StringNullException, DateNullException {
		return Filtro.filtrar(this.obtenerPedidosDeRetiroDeUsuario(), predicate);
	}

	@Override
	public void registrarVisita(ArrayList<String> residuosIngresados, ArrayList<String> residuosIngresadosKg,
			String observacion, Integer codOrden, Integer dia, Integer mes, Integer año)
			throws AppException, NotNullException {
	}



}
