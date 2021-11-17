package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.exceptions.AppException;
import ar.edu.unrn.seminario.exceptions.DataEmptyException;
import ar.edu.unrn.seminario.exceptions.IncorrectEmailException;
import ar.edu.unrn.seminario.exceptions.InsuficientPointsException;
import ar.edu.unrn.seminario.exceptions.NotNullException;
import ar.edu.unrn.seminario.exceptions.NotNumberException;
import ar.edu.unrn.seminario.exceptions.StringNullException;
import ar.edu.unrn.seminario.modelo.Beneficio;
import ar.edu.unrn.seminario.modelo.Campaña;
import ar.edu.unrn.seminario.modelo.Canje;
import ar.edu.unrn.seminario.modelo.Catalogo;
import ar.edu.unrn.seminario.modelo.Dueño;
import ar.edu.unrn.seminario.modelo.Rol;
import ar.edu.unrn.seminario.modelo.Usuario;

public class CanjeDAOJDBC implements CanjeDao {
	public void create(Canje canje) throws AppException{
		Connection conn;
		try { 
			conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
				.prepareStatement("INSERT INTO canjes(cod_beneficio, dni, fecha, cod_campaña) "
						+ "VALUES (?, ?,?,?)", Statement.RETURN_GENERATED_KEYS);

			statement.setInt(1, canje.getBeneficioCanjeado().getCodigo());
			statement.setString(2, canje.getDueñoCanjeador().getDni());
			statement.setDate(3, new java.sql.Date(canje.getFechaCanje().getDate())); 
			statement.setInt(4, canje.getCampaña().getCodigo());
			int cantidad = statement.executeUpdate();
			if (cantidad > 0) {
				System.out.println("Modificando " + cantidad + " registros");
			} else {
				System.out.println("Error al actualizar");
				// TODO: disparar Exception propia
			}
		} catch (SQLException e) {
			throw new AppException("Error al crear el canje: " + e.getMessage());
		} finally {
			ConnectionManager.disconnect();
			}
	}

	public void update(Canje canje) {
		
	}

	public void remove() {
		
	}

	public Canje find(int codigo) throws AppException, NotNullException{
		Canje canjeObjetivo = null;
		Beneficio beneficioCanjeado = null;
		
		Campaña campaña = null;
		Catalogo catalogo = null;
		ArrayList<Beneficio> listaBeneficios = new ArrayList<>();
		Beneficio beneficio = null;
		
		ArrayList<Canje>listaCanjesEfectuados = new ArrayList<>();
		Canje canje = null;
		Dueño dueño = null;
		Usuario user = null;
		Rol rol = null;
		
		ArrayList<Dueño>listaBeneficiarios = new ArrayList<>();
		
		
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM canje cje "
					+ "JOIN beneficios b ON (b.codigo = cje.cod_beneficio) "
					+ "JOIN propietarios p ON (p.dni = cje.dni) "
					+ "JOIN usuarios u ON (u.usuario = p.username) "
					+ "JOIN roles r ON (u.rol = r.codigo) "
					+ "JOIN campañas c ON (c.codigo = cje.cod_campaña) "
					+ "WHERE cje.codigo = ?");
			statement.setInt(1, codigo);
			ResultSet resultSetConsulta = statement.executeQuery();
			if(resultSetConsulta.next()) {
				
				//RECUPERAR CANJE SIN CAMPAÑA:
				beneficioCanjeado = new Beneficio(resultSetConsulta.getString("b.nombre_beneficio"),
						resultSetConsulta.getInt("b.costo"),
						resultSetConsulta.getInt("b.codigo"));
				rol = new Rol(resultSetConsulta.getInt("r.codigo"),
						resultSetConsulta.getString("r.nombre"));
				user = new Usuario(resultSetConsulta.getString("u.usuario"),
						resultSetConsulta.getString("u.contrasena"),
						resultSetConsulta.getString("u.email"),
						rol);
				dueño = new Dueño(resultSetConsulta.getString("p.nombre"),
						resultSetConsulta.getString("p.apellido"),
						resultSetConsulta.getString("p.dni"),
						resultSetConsulta.getString("p.correo_electronico"),
						user);
				canjeObjetivo = new Canje(beneficioCanjeado, dueño, resultSetConsulta.getDate("cje.fecha"),
						resultSetConsulta.getInt("cje.codigo"));
				
				
				
				//RECUPERAR CAMPAÑA:
				
				
				statement = conn.prepareStatement("SELECT * FROM campañas c "
						+ "JOIN catalogo ca ON (c.codigo = ca.cod_campaña) "
						+ "JOIN beneficio b ON (ca.cod_beneficio = b.codigo "
						+ "WHERE c.codigo = ?");
				statement.setInt(1, resultSetConsulta.getInt("c.codigo"));
				ResultSet resultSetConsulta2 = statement.executeQuery();
				while(resultSetConsulta2.next()) {
					beneficio = new Beneficio(resultSetConsulta2.getString("b.nombre_beneficio"),
							resultSetConsulta2.getInt("b.costo"),
							resultSetConsulta2.getInt("b.codigo"));
					listaBeneficios.add(beneficio);
				}
				catalogo = new Catalogo(listaBeneficios);
				campaña = new Campaña(resultSetConsulta.getString("c.nombre"),catalogo,
						resultSetConsulta.getString("c.estado"),
						resultSetConsulta.getInt("c.codigo"));
				
				//resultSetConsulta.close();
				statement = conn.prepareStatement("SELECT * FROM campañas c "
						+ "JOIN canjes ca ON (c.codigo = ca.cod) "
						+ "JOIN propietarios p ON (p.dni = ca.dni) "
						+ "JOIN beneficios b ON (b.codigo = ca.cod_beneficio) "
						+ "JOIN usuarios u ON (u.usuario = p.username) "
						+ "JOIN roles r ON (u.rol = r.codigo) "
						+ "WHERE c.codigo = ?");
				statement.setInt(1, resultSetConsulta.getInt("c.codigo"));
				ResultSet resultSetCanje = statement.executeQuery();
				while(resultSetCanje.next()) {
					rol = new Rol(resultSetCanje.getInt("r.codigo"),
							resultSetCanje.getString("r.nombre"));
					user = new Usuario(resultSetCanje.getString("u.usuario"),
							resultSetCanje.getString("u.contrasena"),
							resultSetCanje.getString("u.email"),
							rol);
					dueño = new Dueño(resultSetCanje.getString("p.nombre"),
							resultSetCanje.getString("p.apellido"),
							resultSetCanje.getString("p.dni"),
							resultSetCanje.getString("p.correo_electronico"),
							user);
					beneficio = new Beneficio(resultSetCanje.getString("b.nombre_beneficio"),
							resultSetCanje.getInt("b.costo"),
							resultSetCanje.getInt("b.codigo"));
					canje = new Canje(beneficio, dueño, campaña, new java.util.Date(resultSetCanje.getDate("ca.fecha").getTime()), resultSetCanje.getInt("ca.codigo"));
					listaCanjesEfectuados.add(canje);
				}
				campaña.setListaCanjesEfectuados(listaCanjesEfectuados);
				statement = conn.prepareStatement("SELECT DISTINCT p.*,c.codigo, u.*, r.* FROM campañas c "
						+ "JOIN canjes ca ON (c.codigo = ca.cod) "
						+ "JOIN propietarios p ON (p.dni = ca.dni) "
						+ "JOIN usuarios u ON (u.usuario = p.username) "
						+ "JOIN roles r ON (u.rol = r.codigo) "
						+ "WHERE c.codigo = ?");
				statement.setInt(1, resultSetConsulta.getInt("c.codigo"));
				ResultSet resultSetBeneficiarios = statement.executeQuery();
				while(resultSetBeneficiarios.next()) {
					rol = new Rol(resultSetBeneficiarios.getInt("r.codigo"),
							resultSetBeneficiarios.getString("r.nombre"));
					user = new Usuario(resultSetBeneficiarios.getString("u.usuario"),
							resultSetBeneficiarios.getString("u.contrasena"),
							resultSetBeneficiarios.getString("u.email"),
							rol);
					dueño = new Dueño(resultSetBeneficiarios.getString("p.nombre"),
							resultSetBeneficiarios.getString("p.apellido"),
							resultSetBeneficiarios.getString("p.dni"),
							resultSetBeneficiarios.getString("p.correo_electronico"),
							user);
					//Si no sale, hacer el listaBeneficiarios.contains() para agregar el dueño una sola vez
					listaBeneficiarios.add(dueño);
				}
				campaña.setListaBeneficiarios(listaBeneficiarios);
				
				
				//Setear campaña al canje
				canjeObjetivo.setCampaña(campaña);
			}
		} catch (SQLException | DataEmptyException | StringNullException | IncorrectEmailException | NotNumberException | NotNullException | InsuficientPointsException e) {
			throw new AppException("Error al buscar canje: " + e.getMessage());
		} finally {
		ConnectionManager.disconnect();
		}
		return canjeObjetivo;
	}

	public List<Canje> findAll() throws AppException, NotNullException{
		/*Nota: 
		*Inicializar las listas al momento de usarlas, porque a diferencia del find,
		*se hacen muchas iteraciones al reconstruir cada canje, por lo que se sigue
		*agregando en listas que nunca se borran.
		*/
		ArrayList<Canje>listaCanjes = new ArrayList<>();
		Canje canjeObjetivo = null;
		Beneficio beneficioCanjeado = null;
		
		Campaña campaña = null;
		Catalogo catalogo = null;

		Beneficio beneficio = null;
		
		
		Canje canje = null;
		Dueño dueño = null;
		Usuario user = null;
		Rol rol = null;
		
		
		
		
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM canjes cje "
					+ "JOIN beneficios b ON (b.codigo = cje.cod_beneficio) "
					+ "JOIN propietarios p ON (p.dni = cje.dni) "
					+ "JOIN usuarios u ON (u.usuario = p.username) "
					+ "JOIN roles r ON (u.rol = r.codigo) "
					+ "JOIN campañas c ON (c.codigo = cje.cod_campaña) ");
			ResultSet resultSetConsulta = statement.executeQuery();
			while(resultSetConsulta.next()) {
				
				//RECUPERAR CANJE SIN CAMPAÑA:
				beneficioCanjeado = new Beneficio(resultSetConsulta.getString("b.nombre_beneficio"),
						resultSetConsulta.getInt("b.costo"),
						resultSetConsulta.getInt("b.codigo"));
				rol = new Rol(resultSetConsulta.getInt("r.codigo"),
						resultSetConsulta.getString("r.nombre"));
				user = new Usuario(resultSetConsulta.getString("u.usuario"),
						resultSetConsulta.getString("u.contrasena"),
						resultSetConsulta.getString("u.email"),
						rol);
				dueño = new Dueño(resultSetConsulta.getString("p.nombre"),
						resultSetConsulta.getString("p.apellido"),
						resultSetConsulta.getString("p.dni"),
						resultSetConsulta.getString("p.correo_electronico"),
						user);
				canjeObjetivo = new Canje(beneficioCanjeado, dueño, resultSetConsulta.getDate("cje.fecha"),
						resultSetConsulta.getInt("cje.codigo"));
				
				
				
				//RECUPERAR CAMPAÑA:
				
				
				statement = conn.prepareStatement("SELECT * FROM campañas c "
						+ "JOIN beneficios_campaña ca ON (c.codigo = ca.cod_campaña) "
						+ "JOIN beneficios b ON (ca.cod_beneficio = b.codigo) "
						+ "WHERE c.codigo = ?");
				statement.setInt(1, resultSetConsulta.getInt("c.codigo"));
				ResultSet resultSetConsulta2 = statement.executeQuery();
				ArrayList<Beneficio> listaBeneficios = new ArrayList<>();
				while(resultSetConsulta2.next()) {
					beneficio = new Beneficio(resultSetConsulta2.getString("b.nombre_beneficio"),
							resultSetConsulta2.getInt("b.costo"),
							resultSetConsulta2.getInt("b.codigo"));
					listaBeneficios.add(beneficio);
				}
				catalogo = new Catalogo(listaBeneficios);
				campaña = new Campaña(resultSetConsulta.getString("c.nombre"),catalogo,
						resultSetConsulta.getString("c.estado"),
						resultSetConsulta.getInt("c.codigo"));
				
				
				statement = conn.prepareStatement("SELECT * FROM campañas c "
						+ "JOIN canjes ca ON (c.codigo = ca.cod_campaña) "
						+ "JOIN propietarios p ON (p.dni = ca.dni) "
						+ "JOIN beneficios b ON (b.codigo = ca.cod_beneficio) "
						+ "JOIN usuarios u ON (u.usuario = p.username) "
						+ "JOIN roles r ON (u.rol = r.codigo) "
						+ "WHERE c.codigo = ?");
				statement.setInt(1, resultSetConsulta.getInt("c.codigo"));
				ResultSet resultSetCanje = statement.executeQuery();
				ArrayList<Canje>listaCanjesEfectuados = new ArrayList<>();
				while(resultSetCanje.next()) {
					rol = new Rol(resultSetCanje.getInt("r.codigo"),
							resultSetCanje.getString("r.nombre"));
					user = new Usuario(resultSetCanje.getString("u.usuario"),
							resultSetCanje.getString("u.contrasena"),
							resultSetCanje.getString("u.email"),
							rol);
					dueño = new Dueño(resultSetCanje.getString("p.nombre"),
							resultSetCanje.getString("p.apellido"),
							resultSetCanje.getString("p.dni"),
							resultSetCanje.getString("p.correo_electronico"),
							user);
					beneficio = new Beneficio(resultSetCanje.getString("b.nombre_beneficio"),
							resultSetCanje.getInt("b.costo"),
							resultSetCanje.getInt("b.codigo"));
					canje = new Canje(beneficio, dueño, campaña, new java.util.Date(resultSetCanje.getDate("ca.fecha").getTime()), resultSetCanje.getInt("ca.codigo"));
					listaCanjesEfectuados.add(canje);
				}
				campaña.setListaCanjesEfectuados(listaCanjesEfectuados);
				statement = conn.prepareStatement("SELECT DISTINCT p.*,c.codigo, u.*, r.* FROM campañas c "
						+ "JOIN canjes ca ON (c.codigo = ca.cod_campaña) "
						+ "JOIN propietarios p ON (p.dni = ca.dni) "
						+ "JOIN usuarios u ON (u.usuario = p.username) "
						+ "JOIN roles r ON (u.rol = r.codigo) "
						+ "WHERE c.codigo = ?");
				statement.setInt(1, resultSetConsulta.getInt("c.codigo"));
				ResultSet resultSetBeneficiarios = statement.executeQuery();
				ArrayList<Dueño>listaBeneficiarios = new ArrayList<>();
				while(resultSetBeneficiarios.next()) {
					rol = new Rol(resultSetBeneficiarios.getInt("r.codigo"),
							resultSetBeneficiarios.getString("r.nombre"));
					user = new Usuario(resultSetBeneficiarios.getString("u.usuario"),
							resultSetBeneficiarios.getString("u.contrasena"),
							resultSetBeneficiarios.getString("u.email"),
							rol);
					dueño = new Dueño(resultSetBeneficiarios.getString("p.nombre"),
							resultSetBeneficiarios.getString("p.apellido"),
							resultSetBeneficiarios.getString("p.dni"),
							resultSetBeneficiarios.getString("p.correo_electronico"),
							user);
					//Si no sale, hacer el listaBeneficiarios.contains() para agregar el dueño una sola vez
					listaBeneficiarios.add(dueño);
				}
				campaña.setListaBeneficiarios(listaBeneficiarios);
				
				//Setear campaña al canje
				canjeObjetivo.setCampaña(campaña);
				listaCanjes.add(canjeObjetivo);
			}
		} catch (SQLException | DataEmptyException | StringNullException | IncorrectEmailException | NotNumberException | NotNullException | InsuficientPointsException e) {
			throw new AppException("Error al buscar todos los canjes: " + e.getMessage());
		} finally {
		ConnectionManager.disconnect();
		}
		return listaCanjes;
	}
	public List<Canje> findByUser(Dueño d) throws AppException, NotNullException{ //Muy copypasteada, probar
		ArrayList<Canje>listaCanjes = new ArrayList<>();
		Canje canjeObjetivo = null;
		Beneficio beneficioCanjeado = null;
		
		Campaña campaña = null;
		Catalogo catalogo = null;

		Beneficio beneficio = null;
		

		Canje canje = null;
		Dueño dueño = null;
		Usuario user = null;
		Rol rol = null;
		

		
		
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM canjes cje "
					+ "JOIN beneficios b ON (b.codigo = cje.cod_beneficio) "
					+ "JOIN propietarios p ON (p.dni = cje.dni) "
					+ "JOIN usuarios u ON (u.usuario = p.username) "
					+ "JOIN roles r ON (u.rol = r.codigo) "
					+ "JOIN campañas c ON (c.codigo = cje.cod_campaña) "
					+ "WHERE p.dni = ?");
			statement.setString(1, d.getDni());
			ResultSet resultSetConsulta = statement.executeQuery();
			while(resultSetConsulta.next()) {
				
				//RECUPERAR CANJE SIN CAMPAÑA:
				beneficioCanjeado = new Beneficio(resultSetConsulta.getString("b.nombre_beneficio"),
						resultSetConsulta.getInt("b.costo"),
						resultSetConsulta.getInt("b.codigo"));
				rol = new Rol(resultSetConsulta.getInt("r.codigo"),
						resultSetConsulta.getString("r.nombre"));
				user = new Usuario(resultSetConsulta.getString("u.usuario"),
						resultSetConsulta.getString("u.contrasena"),
						resultSetConsulta.getString("u.email"),
						rol);
				dueño = d;
				canjeObjetivo = new Canje(beneficioCanjeado, d, resultSetConsulta.getDate("cje.fecha"),
						resultSetConsulta.getInt("cje.codigo"));
				
				
				
				//RECUPERAR CAMPAÑA:
				
				
				statement = conn.prepareStatement("SELECT * FROM campañas c "
						+ "JOIN beneficios_campaña ca ON (c.codigo = ca.cod_campaña) "
						+ "JOIN beneficios b ON (ca.cod_beneficio = b.codigo) "
						+ "WHERE c.codigo = ?");
				ArrayList<Beneficio> listaBeneficios = new ArrayList<>();
				statement.setInt(1, resultSetConsulta.getInt("c.codigo"));
				ResultSet resultSetConsulta2 = statement.executeQuery();
				//RECUPERO LOS BENEFICIOS DE LA CAMPAÑA
				while(resultSetConsulta2.next()) {
					beneficio = new Beneficio(resultSetConsulta2.getString("b.nombre_beneficio"),
							resultSetConsulta2.getInt("b.costo"),
							resultSetConsulta2.getInt("b.codigo"));
					listaBeneficios.add(beneficio);
				}
				//CREO LA CAMPAÑA CON SU LISTA DE BENEFICIOS (CATALOGO)
				catalogo = new Catalogo(listaBeneficios);
				campaña = new Campaña(resultSetConsulta.getString("c.nombre"),catalogo,
						resultSetConsulta.getString("c.estado"),
						resultSetConsulta.getInt("c.codigo"));
				
				
				//RECUPERO LA LISTA DE CANJES EFECTUADOS PARA ESA CAMPAÑA
				statement = conn.prepareStatement("SELECT * FROM campañas c "
						+ "JOIN canjes ca ON (c.codigo = ca.cod_campaña) "
						+ "JOIN propietarios p ON (p.dni = ca.dni) "
						+ "JOIN beneficios b ON (b.codigo = ca.cod_beneficio) "
						+ "JOIN usuarios u ON (u.usuario = p.username) "
						+ "JOIN roles r ON (u.rol = r.codigo) "
						+ "WHERE c.codigo = ?");
				statement.setInt(1, resultSetConsulta.getInt("c.codigo"));
				ResultSet resultSetCanje = statement.executeQuery();
				ArrayList<Canje>listaCanjesEfectuados = new ArrayList<>();
				while(resultSetCanje.next()) {
					rol = new Rol(resultSetCanje.getInt("r.codigo"),
							resultSetCanje.getString("r.nombre"));
					user = new Usuario(resultSetCanje.getString("u.usuario"),
							resultSetCanje.getString("u.contrasena"),
							resultSetCanje.getString("u.email"),
							rol);
					dueño = new Dueño(resultSetCanje.getString("p.nombre"),
							resultSetCanje.getString("p.apellido"),
							resultSetCanje.getString("p.dni"),
							resultSetCanje.getString("p.correo_electronico"),
							user);
					beneficio = new Beneficio(resultSetCanje.getString("b.nombre_beneficio"),
							resultSetCanje.getInt("b.costo"),
							resultSetCanje.getInt("b.codigo"));
					canje = new Canje(beneficio, dueño, campaña, new java.util.Date(resultSetCanje.getDate("ca.fecha").getTime()), resultSetCanje.getInt("ca.codigo"));
					listaCanjesEfectuados.add(canje);
				}
				campaña.setListaCanjesEfectuados(listaCanjesEfectuados);
				statement = conn.prepareStatement("SELECT DISTINCT p.*,c.codigo, u.*, r.* FROM campañas c "
						+ "JOIN canjes ca ON (c.codigo = ca.cod_campaña) "
						+ "JOIN propietarios p ON (p.dni = ca.dni) "
						+ "JOIN usuarios u ON (u.usuario = p.username) "
						+ "JOIN roles r ON (u.rol = r.codigo) "
						+ "WHERE c.codigo = ?");
				statement.setInt(1, resultSetConsulta.getInt("c.codigo"));
				ResultSet resultSetBeneficiarios = statement.executeQuery();
				ArrayList<Dueño>listaBeneficiarios = new ArrayList<>();
				while(resultSetBeneficiarios.next()) {
					rol = new Rol(resultSetBeneficiarios.getInt("r.codigo"),
							resultSetBeneficiarios.getString("r.nombre"));
					user = new Usuario(resultSetBeneficiarios.getString("u.usuario"),
							resultSetBeneficiarios.getString("u.contrasena"),
							resultSetBeneficiarios.getString("u.email"),
							rol);
					dueño = new Dueño(resultSetBeneficiarios.getString("p.nombre"),
							resultSetBeneficiarios.getString("p.apellido"),
							resultSetBeneficiarios.getString("p.dni"),
							resultSetBeneficiarios.getString("p.correo_electronico"),
							user);
					//Si no sale, hacer el listaBeneficiarios.contains() para agregar el dueño una sola vez
					listaBeneficiarios.add(dueño);
				}
				campaña.setListaBeneficiarios(listaBeneficiarios);
				
				//Setear campaña al canje
				canjeObjetivo.setCampaña(campaña);
				listaCanjes.add(canjeObjetivo);
			}
		} catch (SQLException | DataEmptyException | StringNullException | IncorrectEmailException | NotNumberException | NotNullException | InsuficientPointsException e) {
			throw new AppException("Error al buscar todos los canjes por dueño: " + e.getMessage());
		} finally {
		ConnectionManager.disconnect();
		}
		return listaCanjes;
	}


	public boolean exists(int codigo) throws AppException{
		return false;
	}
}
