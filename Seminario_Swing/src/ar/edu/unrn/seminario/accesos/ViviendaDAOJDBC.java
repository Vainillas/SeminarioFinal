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
import ar.edu.unrn.seminario.exceptions.NotNullException;
import ar.edu.unrn.seminario.exceptions.NotNumberException;
import ar.edu.unrn.seminario.exceptions.StringNullException;
import ar.edu.unrn.seminario.modelo.Direccion;
import ar.edu.unrn.seminario.modelo.Dueño;
import ar.edu.unrn.seminario.modelo.Rol;
import ar.edu.unrn.seminario.modelo.Usuario;
import ar.edu.unrn.seminario.modelo.Vivienda;

public class ViviendaDAOJDBC implements ViviendaDao {

	@Override
	public void create(Vivienda v) throws AppException {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("INSERT INTO viviendas(calle,altura,dni) "
							+ "VALUES (?, ?, ?)"); 
			
			statement.setString(1, v.getDireccion().getCalle());
			statement.setString(2, v.getDireccion().getAltura());
			statement.setString(3, v.getDueño().getDni());
			int cantidad = statement.executeUpdate();
			if (cantidad > 0) {
				System.out.println("Modificando " + cantidad + " registros");
			} else {
				throw new AppException("error al procesar consulta"); 
			}
		} catch (SQLException  e) {
			throw new AppException("error en la consulta");
		}finally {
			ConnectionManager.disconnect();
		}

	}

	@Override
	public void update(Vivienda v) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(Vivienda v) {
		// TODO Auto-generated method stub

	}

	@Override
	public Vivienda find(Integer codigo) throws AppException {
		Vivienda vivienda=null;
		Direccion direccion = null;
		Dueño dueño = null;
		Usuario usuario = null;
		Rol rol = null;
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM viviendas v "
					+ "JOIN propietarios p ON p.dni = v.dni "
					+ "JOIN usuarios u ON u.usuario = p.username "
					+ "JOIN dirección d ON v.calle = d.calle AND v.altura = d.altura "
					+ "JOIN roles r ON r.codigo = u.rol"+"WHERE v.codigo = ?");
			statement.setInt(1,codigo);
			ResultSet resultSetConsulta = statement.executeQuery();
			if(resultSetConsulta.next()) {
				rol = new Rol(resultSetConsulta.getInt("r.codigo"), resultSetConsulta.getString("r.nombre"));
				usuario = new Usuario(resultSetConsulta.getString("u.usuario"),
						resultSetConsulta.getString("u.contrasena"),
						resultSetConsulta.getString("u.email"),
						rol);
				dueño= new Dueño(resultSetConsulta.getString("p.nombre"),
						resultSetConsulta.getString("p.apellido"),
						resultSetConsulta.getString("p.dni"),
						resultSetConsulta.getString("p.correo_electronico"),
						usuario);
				direccion = new Direccion(resultSetConsulta.getString("d.calle"), 
						Integer.toString(resultSetConsulta.getInt("d.altura")), 
						Integer.toString(resultSetConsulta.getInt("d.codigo_postal")), 
						resultSetConsulta.getString("d.longitud"),
						resultSetConsulta.getString("d.latitud"),
						resultSetConsulta.getString("d.barrio"));
				}
				vivienda= new Vivienda(direccion, dueño,resultSetConsulta.getInt("v.codigo"));
		} catch (SQLException | DataEmptyException | StringNullException | NotNumberException | IncorrectEmailException | NotNullException e) {
			throw new AppException("Error al buscar una vivienda: "+e.getMessage());
		 
		} finally {
			
			ConnectionManager.disconnect();
		}
		return vivienda;
	}
	
	@Override
	public Vivienda find(String calle, String altura) throws AppException {
		Vivienda vivienda=null;
		Direccion direccion = null;
		Dueño dueño = null;
		Usuario usuario = null;
		Rol rol = null;
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM viviendas v "
					+ "JOIN propietarios p ON p.dni = v.dni "
					+ "JOIN dirección d ON d.calle = v.calle AND d.altura = v.altura "
					+ "JOIN usuarios u ON u.usuario = p.username "
					+ "JOIN roles r ON r.codigo = u.rol"+"WHERE v.calle = ? AND v.altura = ?");
			statement.setString(1,calle);
			statement.setInt(2, Integer.parseInt(altura));
			ResultSet resultSetConsulta = statement.executeQuery();
			if(resultSetConsulta.next()) {
				rol = new Rol(resultSetConsulta.getInt("r.codigo"), resultSetConsulta.getString("r.nombre"));
				usuario = new Usuario(resultSetConsulta.getString("u.usuario"),
						resultSetConsulta.getString("u.contrasena"),
						resultSetConsulta.getString("u.email"),
						rol);
				dueño= new Dueño(resultSetConsulta.getString("p.nombre"),
						resultSetConsulta.getString("p.apellido"),
						resultSetConsulta.getString("p.dni"),
						resultSetConsulta.getString("p.correo_electronico"),
						usuario);
				direccion = new Direccion(resultSetConsulta.getString("d.calle"), 
						Integer.toString(resultSetConsulta.getInt("d.altura")), 
						Integer.toString(resultSetConsulta.getInt("d.codigo_postal")), 
						resultSetConsulta.getString("d.longitud"),
						resultSetConsulta.getString("d.latitud"),
						resultSetConsulta.getString("d.barrio"));
				}
				vivienda= new Vivienda(direccion, dueño,resultSetConsulta.getInt("v.codigo"));
		} catch (SQLException | DataEmptyException | StringNullException | IncorrectEmailException | NotNumberException | NotNullException e) {

			throw new AppException("Error al buscar las viviendas: ");
		 
		} finally {
		ConnectionManager.disconnect();
		}
		return vivienda;
	}
	
	
	@Override
	public List<Vivienda> findAll() throws AppException {
		List<Vivienda>viviendas = new ArrayList<>();
		Vivienda vivienda=null;
		Direccion direccion = null;
		Dueño dueño = null;
		Usuario usuario = null;
		Rol rol = null;
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM viviendas v "
					+ "JOIN propietarios p ON p.dni = v.dni "
					+ "JOIN dirección d ON d.calle = v.calle AND d.altura = v.altura "
					+ "JOIN usuarios u ON u.usuario = p.username "
					+ "JOIN roles r ON r.codigo = u.rol");
			ResultSet resultSetConsulta = statement.executeQuery();			

			while(resultSetConsulta.next()) {
				rol = new Rol(resultSetConsulta.getInt("r.codigo"), resultSetConsulta.getString("r.nombre"));
				usuario = new Usuario(resultSetConsulta.getString("u.usuario"),
						resultSetConsulta.getString("u.contrasena"),
						resultSetConsulta.getString("u.email"),
						rol);
				dueño= new Dueño(resultSetConsulta.getString("p.nombre"),
						resultSetConsulta.getString("p.apellido"),
						resultSetConsulta.getString("p.dni"),
						resultSetConsulta.getString("p.correo_electronico"),
						usuario);
				direccion = new Direccion(resultSetConsulta.getString("d.calle"), 
						Integer.toString(resultSetConsulta.getInt("d.altura")), 
						Integer.toString(resultSetConsulta.getInt("d.codigo_postal")), 
						resultSetConsulta.getString("d.longitud"),
						resultSetConsulta.getString("d.latitud"),
						resultSetConsulta.getString("d.barrio"));
				vivienda= new Vivienda(direccion, dueño,resultSetConsulta.getInt("v.codigo"));
				viviendas.add(vivienda);

			}
		} catch (Exception  e) {
			throw new AppException("Error al procesar la consulta: " + e.getMessage());

		}finally {
			ConnectionManager.disconnect();
		}
		return viviendas;
	}
	public List<Vivienda> findByUser(String username) throws AppException {
		List<Vivienda>viviendas = new ArrayList<>();
		Vivienda vivienda=null;
		Direccion direccion = null;
		Dueño dueño = null;
		Usuario usuario = null;
		Rol rol = null;
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM viviendas v "
					+ "JOIN propietarios p ON p.dni = v.dni "
					+ "JOIN dirección d ON d.calle = v.calle AND d.altura = v.altura "
					+ "JOIN usuarios u ON u.usuario = p.username "
					+ "JOIN roles r ON r.codigo = u.rol "
					+ "WHERE p.username = ?");
			statement.setString(1, username);
			ResultSet resultSetConsulta = statement.executeQuery();			

			while(resultSetConsulta.next()) {
				rol = new Rol(resultSetConsulta.getInt("r.codigo"), resultSetConsulta.getString("r.nombre"));
				usuario = new Usuario(resultSetConsulta.getString("u.usuario"),
						resultSetConsulta.getString("u.contrasena"),
						resultSetConsulta.getString("u.email"),
						rol);
				dueño= new Dueño(resultSetConsulta.getString("p.nombre"),
						resultSetConsulta.getString("p.apellido"),
						resultSetConsulta.getString("p.dni"),
						resultSetConsulta.getString("p.correo_electronico"),
						usuario);
				direccion = new Direccion(resultSetConsulta.getString("d.calle"), 
						Integer.toString(resultSetConsulta.getInt("d.altura")), 
						Integer.toString(resultSetConsulta.getInt("d.codigo_postal")), 
						resultSetConsulta.getString("d.longitud"),
						resultSetConsulta.getString("d.latitud"),
						resultSetConsulta.getString("d.barrio"));
				vivienda= new Vivienda(direccion, dueño,resultSetConsulta.getInt("v.codigo"));
				viviendas.add(vivienda);

			}
		} catch (Exception  e) {
			throw new AppException("Error al procesar la consulta: " + e.getMessage());

		}finally {
			ConnectionManager.disconnect();
		}
		return viviendas;
	}

	@Override
	public boolean exists(String dni, String calle, String altura) throws AppException {
		boolean exists = false;

		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn.prepareStatement(
					"SELECT calle, altura, dni FROM viviendas WHERE calle = ? AND altura = ? AND dni = ?");
			statement.setString(1, calle);
			statement.setString(2, altura);
			statement.setString(3, dni);
			ResultSet rs = statement.executeQuery();

			if (rs.next()) {
				if(rs.getString("calle").equals(calle) && rs.getInt("altura")== Integer.parseInt((altura)) && rs.getString("dni").equals(dni)) {
					exists = true;
				}		
			}
		} catch (SQLException e ) {
			throw new AppException("Error al verificar la existencia de la vivienda");
		}finally {
			ConnectionManager.disconnect();
		}
		return exists;
	}

}
