package ar.edu.unrn.seminario.api;

import java.sql.SQLException;



import java.util.ArrayList;
import java.util.Comparator;

import java.util.List;

import ar.edu.unrn.seminario.dto.BeneficioDTO;
import ar.edu.unrn.seminario.dto.CampañaDTO;
import ar.edu.unrn.seminario.dto.CanjeDTO;
import ar.edu.unrn.seminario.dto.DireccionDTO;
import ar.edu.unrn.seminario.dto.DueñoDTO;
import ar.edu.unrn.seminario.dto.OrdenDeRetiroDTO;
import ar.edu.unrn.seminario.dto.PedidoDeRetiroDTO;
import ar.edu.unrn.seminario.dto.RolDTO;
import ar.edu.unrn.seminario.dto.UsuarioDTO;
import ar.edu.unrn.seminario.dto.ViviendaDTO;
import ar.edu.unrn.seminario.exceptions.StateException;
import ar.edu.unrn.seminario.exceptions.AppException;
import ar.edu.unrn.seminario.exceptions.DataEmptyException;
import ar.edu.unrn.seminario.exceptions.DateNullException;
import ar.edu.unrn.seminario.exceptions.IncorrectEmailException;
import ar.edu.unrn.seminario.exceptions.InsuficientPointsException;
import ar.edu.unrn.seminario.exceptions.KilogramEmptyException;
import ar.edu.unrn.seminario.exceptions.NotCorrectPasswordException;
import ar.edu.unrn.seminario.exceptions.NotNullException;
import ar.edu.unrn.seminario.exceptions.StringNullException;
import ar.edu.unrn.seminario.dto.RecolectorDTO;
import ar.edu.unrn.seminario.dto.ResiduoDTO;
import ar.edu.unrn.seminario.modelo.Dueño;
import ar.edu.unrn.seminario.modelo.OrdenDeRetiro;
import ar.edu.unrn.seminario.modelo.PedidoDeRetiro;
import ar.edu.unrn.seminario.modelo.Usuario;
import ar.edu.unrn.seminario.modelo.Visita;
import ar.edu.unrn.seminario.utilities.Predicate;
import ar.edu.unrn.seminario.exceptions.NotNumberException;
import ar.edu.unrn.seminario.exceptions.NotRegisterException;

public interface IApi {

	void registrarVivienda(String calle, String altura, String codigoPostal, String latitud, String longitud, String barrio) throws DataEmptyException, StringNullException, IncorrectEmailException, NotNumberException, SQLException,Exception;
	
	UsuarioDTO obtenerUsuario(String username);

	void eliminarUsuario(String username);
	
	void registrarPersonal(String nombre, String apellido, String dni, String correoElectronico, String telefono) 
			throws DataEmptyException,StringNullException,IncorrectEmailException, NotNumberException, AppException;
	
	
	void registrarUsuario(String usuario, String password, String email, Integer rol) 
			throws NotNullException, IncorrectEmailException, DataEmptyException, StringNullException, AppException;
	
	List<RolDTO> obtenerRoles() 
			throws AppException;

	
	
	List<RolDTO> obtenerRolesActivos();
	void guardarRol(Integer codigo, String descripción, boolean estado); // crear el objeto de dominio “Rol”

	RolDTO obtenerRolPorCodigo(Integer codigo) 
			throws AppException; // recuperar el rol almacenado

	public void activarRol(Integer codigo); // recuperar el objeto Rol, implementar el comportamiento de estado.

	public void desactivarRol(Integer codigo); // recuperar el objeto Rol, imp

	public List<UsuarioDTO> obtenerUsuarios() throws AppException;
	public <T>List<UsuarioDTO> obtenerUsuarios(Predicate <T> predicate) 
			throws AppException;
	
	public List<ViviendaDTO> obtenerViviendas() throws AppException;
	
	public <T> List<ViviendaDTO> obtenerViviendas(Comparator<T> comparator ) throws AppException;
	public <T>List<UsuarioDTO> obtenerUsuarios(Comparator <T> comparator ) 
			throws AppException;

	
	void activarUsuario(String username) 
			throws StateException, AppException; // recuperar el objeto Usuario, implementar el comportamiento de estado.

	void desactivarUsuario(String username) 
			throws StateException, AppException ; // recuperar el objeto Usuario, implementar el comportamiento de estado.
	boolean existeDueñoRegistrado() throws AppException;
	
	void registrarDueño(String nombre, String apellido, String dni, String correo, String username) 
			throws Exception;

	void registrarDireccion(String calle, String altura, String codPostal, String latitud, String longitud,
			String barrio) 
					throws Exception;

	DueñoDTO obtenerDueño(String dni) 
			throws AppException;

	List<DueñoDTO> obtenerDueños() 
			throws AppException, NotNumberException;
	
	DireccionDTO obtenerDireccion(String calle, int num) 
			throws AppException;

	void generarPedidoDeRetiro(boolean cargaPesada, ArrayList<String> residuosSeleccionados,ArrayList<String> residuosSeleccionadosKg, String observacion, ArrayList<String> domicilioSeleccionado) 
		throws AppException, DataEmptyException, NotNullException, StringNullException, DateNullException, NumberFormatException, KilogramEmptyException, NotNumberException ;

	boolean existeUsuario(String usuario) 
			throws NotRegisterException, AppException;
	
	boolean validarUsuario(String usuario, String password) 
			throws NotRegisterException, AppException, NotCorrectPasswordException, DataEmptyException, StringNullException, IncorrectEmailException ;
	
	boolean existeDueño(String dni) 
			throws AppException;
	
	List<DireccionDTO> obtenerDirecciones() 
			throws AppException;


	void usuarioActivo(String username) 
			throws AppException;


	public String obtenerRolUsuarioActivo();
	
	List<RecolectorDTO> obtenerRecolectores() 
			throws DataEmptyException, StringNullException, IncorrectEmailException, AppException;
	public PedidoDeRetiroDTO obtenerPedidoDeRetiro(int codigo) 
			throws DataEmptyException, NotNullException, StringNullException, DateNullException, AppException;
	
	List<PedidoDeRetiroDTO> obtenerPedidosDeRetiro() 
			throws DataEmptyException, NotNullException, StringNullException, DateNullException, AppException, Exception;
	List<PedidoDeRetiroDTO> obtenerPedidosDeRetiroDeUsuario() throws AppException, Exception;
	
	Usuario getUserOnline();
	 public void generarOrdenDeRetiro(Integer codigoPedidoSeleccionado, String dniRecolector) 
			 throws AppException;
	 
	 public void generarOrdenDeRetiro(Integer codigoPedidoSeleccionado) 
			 throws AppException;

	public List<OrdenDeRetiroDTO> obtenerOrdenesDeRetiro()
			throws AppException;

	void registrarDueño(String nombre, String apellido, String dni) 
			throws DataEmptyException, StringNullException, IncorrectEmailException, NotNumberException, AppException;
	
	void registrarVisita(ArrayList<String> residuosIngresados, ArrayList<String> residuosIngresadosKg, String observacion, int codOrden) 
			throws AppException, NotNullException;
	
	<T> List<OrdenDeRetiroDTO> obtenerOrdenesDeRetiro(Predicate<T> predicate) 
			throws AppException;
	

	List<ViviendaDTO> obtenerViviendas(Predicate predicate) 
			throws AppException;

	ArrayList<String> obtenerNombresResiduos() throws AppException;
	


	List<ViviendaDTO> obtenerViviendasDeUsuario() 
			throws AppException;

	boolean existeDireccion(String calle, String altura) 
			throws AppException;

	boolean existeVivienda(String dni, String calle, String altura) 
			throws AppException;

	DueñoDTO obtenerDueñoActivo() 
			throws AppException;

	<T> List<PedidoDeRetiroDTO> obtenerPedidosDeRetiro(Predicate <T> predicado) 
			throws AppException, IncorrectEmailException, DataEmptyException, NotNullException, StringNullException, DateNullException;

	List<PedidoDeRetiroDTO> obtenerPedidosDeRetiro(Comparator<PedidoDeRetiroDTO> comparator) 
			throws AppException, Exception;

	List<DueñoDTO> obtenerDueños(Predicate<DueñoDTO> predicate) 
			throws AppException, NotNumberException;

	List<DueñoDTO> obtenerDueños(Comparator<DueñoDTO> comparator) 
			throws AppException, NotNumberException;

	void agregarBeneficio(String descripcion, String puntajeConsumible) 
			throws NotNullException, DataEmptyException, NotNumberException, AppException;

	List<BeneficioDTO> obtenerBeneficios() throws AppException, NotNullException, DataEmptyException, NotNumberException;
	
	List<OrdenDeRetiroDTO> obtenerOrdenesDeRetiro(Comparator<OrdenDeRetiroDTO> comparator) throws AppException;

	List<PedidoDeRetiroDTO> obtenerPedidosDeRetiroSinOrden() throws AppException, Exception;
	
	void generarCampaña(List<Integer> codigo, String unNombre) throws AppException, NotNullException;
	
	
	void generarCanje(int codBeneficio, int codCampaña) throws AppException, NotNullException, InsuficientPointsException;
	
	List<CanjeDTO> obtenerCanjes() throws AppException, NotNullException, DataEmptyException, NotNumberException;
	List<CampañaDTO> obtenerCampañas() throws AppException, NotNullException, DataEmptyException, NotNumberException;
	CampañaDTO obtenerCampañaPorCodigo(int codigo) throws AppException, NotNullException, DataEmptyException, NotNumberException;
	int calcularPuntaje(PedidoDeRetiro unPedido);
	void sumarPuntos(Dueño dueño, int puntaje) throws AppException;
	List<ResiduoDTO> devolverResiduosRestantes(int codOrden) throws AppException;
	Boolean comprobarCantidadResiduos(int codOrden) throws AppException;
	boolean comprobarExcedenteResiduos(OrdenDeRetiro ordenAComprobar, Visita visitaNueva);
	
	List<CampañaDTO> obtenerCampañas(Predicate<CampañaDTO> predicado) throws AppException, NotNullException, DataEmptyException, NotNumberException;
	

	List<BeneficioDTO> obtenerBeneficios(Predicate<BeneficioDTO> predicado) throws AppException, NotNullException, DataEmptyException, NotNumberException;

	List<CanjeDTO> obtenerCanjes(Predicate<CanjeDTO> predicate) throws AppException, NotNullException, DataEmptyException, NotNumberException;

	List<ViviendaDTO> obtenerViviendasDeUsuario(Comparator<ViviendaDTO> comparator) throws AppException;

	List<ViviendaDTO> obtenerViviendasDeUsuario(Predicate<ViviendaDTO> predicate) throws AppException;
	
}
