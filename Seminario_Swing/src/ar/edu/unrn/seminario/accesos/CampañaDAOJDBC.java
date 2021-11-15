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

public class CampañaDAOJDBC implements CampañaDao{

	public void create(Campaña campaña) throws AppException{

		Connection conn;
		try { 
			conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn  
				.prepareStatement("INSERT INTO campañas(nombre,estado) "
						+ "VALUES (?, ?)" ,Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, campaña.getNombreCampaña());
			statement.setString(2, campaña.getEstado());
			statement.executeUpdate();
			ResultSet clave = statement.getGeneratedKeys();
			clave.next();
			int codigoCampaña = clave.getInt(1);
			clave.close();
			for(Beneficio b : campaña.getCatalogo().getListaBeneficios()) {
				PreparedStatement statement2 = conn.prepareStatement
	                    ("INSERT INTO beneficios_campaña(cod_beneficio, cod_campaña) "
	                    		+ "VALUES(?, ?)");
				statement2.setInt(1, b.getCodigo());
				statement2.setInt(2, codigoCampaña);
				statement2.executeUpdate();
			}
			
			} catch (SQLException e) {
				throw new AppException("Error al crear la campaña " + e.getMessage());
			} finally {
			ConnectionManager.disconnect();
			}
	}

	public void update(Campaña campaña) throws AppException {
		try {
			Connection conn = ConnectionManager.getConnection();
            PreparedStatement statement = conn.prepareStatement("UPDATE campañas SET nombre = ?, estado = ? "
            		+ "WHERE codigo = ?");
            statement.setString(1, campaña.getNombreCampaña());
            statement.setString(2, campaña.getEstado());
            statement.setInt(3, campaña.getCodigo());
            statement.executeUpdate();
		}catch (SQLException e) {
            throw new AppException("Error de SQL al actualizar campaña: " + e.getMessage());
        }  finally {
            ConnectionManager.disconnect();
        }
	}

	public void remove() {
		
	}

	public Campaña find(int codigo) throws AppException{
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
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM campañas c "
					+ "WHERE c.codigo = ?");
			statement.setInt(1, codigo);
			ResultSet resultSetConsulta = statement.executeQuery();
			if(resultSetConsulta.next()) {
				statement = conn.prepareStatement("SELECT * FROM campañas c "
						+ "JOIN beneficios_campaña ca ON (c.codigo = ca.cod_campaña) "
						+ "JOIN beneficios b ON (ca.cod_beneficio = b.codigo) "
						+ "WHERE c.codigo = ?");
				statement.setInt(1, codigo);
				ResultSet resultSetListaBeneficios = statement.executeQuery();
				while(resultSetListaBeneficios.next()) {
					beneficio = new Beneficio(resultSetListaBeneficios.getString("b.nombre_beneficio"),
							String.valueOf(resultSetListaBeneficios.getInt("b.costo")),
							resultSetListaBeneficios.getInt("b.codigo"));
					listaBeneficios.add(beneficio);
				}
				catalogo = new Catalogo(listaBeneficios);
				campaña = new Campaña(resultSetConsulta.getString("c.nombre"),catalogo,
						resultSetConsulta.getString("c.estado"),
						resultSetConsulta.getInt("c.codigo"));

				//resultSetConsulta.close();
				statement = conn.prepareStatement("SELECT * FROM campañas c "
						+ "JOIN canjes ca ON (c.codigo = ca.cod_campaña) "
						+ "JOIN propietarios p ON (p.dni = ca.dni) "
						+ "JOIN beneficios b ON (b.codigo = ca.cod_beneficio) "
						+ "JOIN usuarios u ON (u.usuario = p.username) "
						+ "JOIN roles r ON (u.rol = r.codigo) "
						+ "WHERE c.codigo = ?");
				statement.setInt(1, codigo);
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
							String.valueOf(resultSetCanje.getInt("b.costo")),
							resultSetCanje.getInt("b.codigo"));
					canje = new Canje(beneficio, dueño, campaña, resultSetCanje.getDate("ca.fecha"), resultSetCanje.getInt("ca.codigo"));
					listaCanjesEfectuados.add(canje);
				}
				campaña.setListaCanjesEfectuados(listaCanjesEfectuados);
				statement = conn.prepareStatement("SELECT DISTINCT p.*,c.codigo, u.*, r.* FROM campañas c "
						+ "JOIN canjes ca ON (c.codigo = ca.cod_campaña) "
						+ "JOIN propietarios p ON (p.dni = ca.dni) "
						+ "JOIN usuarios u ON (u.usuario = p.username) "
						+ "JOIN roles r ON (u.rol = r.codigo) "
						+ "WHERE c.codigo = ?");
				statement.setInt(1, codigo);
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
			}
			} catch (SQLException | DataEmptyException | StringNullException | IncorrectEmailException | NotNumberException | NotNullException | InsuficientPointsException e) {
				throw new AppException("Error al buscar campaña: " + e.getMessage());
			} finally {
				ConnectionManager.disconnect();
			}
		return campaña;
	}

	public List<Campaña> findAll() throws AppException, NotNullException{
		ArrayList<Campaña> listaCampañas = new ArrayList<>();
		Campaña campaña = null;
		Catalogo catalogo = null;
		Beneficio beneficio = null;
		
		Canje canje = null;
		Dueño dueño = null;
		Usuario user = null;
		Rol rol = null;
		

		
		
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM campañas c");
			ResultSet resultSetConsulta = statement.executeQuery();
			while(resultSetConsulta.next()) {
				statement = conn.prepareStatement("SELECT * FROM campañas c "
						+ "JOIN beneficios_campaña ca ON (c.codigo = ca.cod_campaña) "
						+ "JOIN beneficios b ON (ca.cod_beneficio = b.codigo) "
						+ "WHERE c.codigo = ?");
				statement.setInt(1, resultSetConsulta.getInt("c.codigo"));
				ResultSet resultSetListaBeneficios = statement.executeQuery();
				ArrayList<Beneficio> listaBeneficios = new ArrayList<>();
				while(resultSetListaBeneficios.next()) {
					beneficio = new Beneficio(resultSetListaBeneficios.getString("b.nombre_beneficio"),
							String.valueOf(resultSetListaBeneficios.getInt("b.costo")),
							resultSetListaBeneficios.getInt("b.codigo"));
					listaBeneficios.add(beneficio);
				}
				
				catalogo = new Catalogo(listaBeneficios);
				campaña = new Campaña(resultSetConsulta.getString("c.nombre"),catalogo,
						resultSetConsulta.getString("c.estado"),
						resultSetConsulta.getInt("c.codigo"));

				//resultSetConsulta.close();
				PreparedStatement statement2 = conn.prepareStatement("SELECT * FROM campañas c "
						+ "JOIN canjes ca ON (c.codigo = ca.cod_campaña) "
						+ "JOIN propietarios p ON (p.dni = ca.dni) "
						+ "JOIN beneficios b ON (b.codigo = ca.cod_beneficio) "
						+ "JOIN usuarios u ON (u.usuario = p.username) "
						+ "JOIN roles r ON (u.rol = r.codigo) "
						+ "WHERE c.codigo = ?");
				statement2.setInt(1, resultSetConsulta.getInt("c.codigo"));
				ResultSet resultSetCanje = statement2.executeQuery();
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
							String.valueOf(resultSetCanje.getInt("b.costo")),
							resultSetCanje.getInt("b.codigo"));
					canje = new Canje(beneficio, dueño, campaña, resultSetCanje.getDate("ca.fecha"), resultSetCanje.getInt("ca.codigo"));
					listaCanjesEfectuados.add(canje);
				}
				campaña.setListaCanjesEfectuados(listaCanjesEfectuados);
				PreparedStatement statement3 = conn.prepareStatement("SELECT DISTINCT p.*,c.codigo, u.*, r.* FROM campañas c "
						+ "JOIN canjes ca ON (c.codigo = ca.cod_campaña) "
						+ "JOIN propietarios p ON (p.dni = ca.dni) "
						+ "JOIN usuarios u ON (u.usuario = p.username) "
						+ "JOIN roles r ON (u.rol = r.codigo) "
						+ "WHERE c.codigo = ?");
				statement3.setInt(1, resultSetConsulta.getInt("c.codigo"));
				ResultSet resultSetBeneficiarios = statement3.executeQuery();
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
				listaCampañas.add(campaña);
			}
		} catch (SQLException | DataEmptyException | StringNullException | IncorrectEmailException | NotNumberException | NotNullException | InsuficientPointsException e) {
			throw new AppException("Error al obtener todas las campañas: " + e.getMessage());
		} finally {
		ConnectionManager.disconnect();
		}
		return listaCampañas;
	}

	public boolean exists(int codigo) throws AppException{
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM campañas c WHERE c.codigo = ?");
			ResultSet resultSetConsulta = statement.executeQuery();
			if(resultSetConsulta.next()) {
				return true;
			}
			else
				return false;
		} catch (SQLException e) {
			throw new AppException("Error al verificar existencia: " + e.getMessage());
		} finally {
		ConnectionManager.disconnect();
		}
	}
}
