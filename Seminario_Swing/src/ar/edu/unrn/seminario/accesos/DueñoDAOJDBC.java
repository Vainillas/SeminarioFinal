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
import ar.edu.unrn.seminario.modelo.Dueño;
import ar.edu.unrn.seminario.modelo.Rol;
import ar.edu.unrn.seminario.modelo.Usuario;

public class DueñoDAOJDBC implements DueñoDao {

	@Override
	public void create(Dueño d) throws AppException {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("INSERT INTO propietarios(nombre,apellido,dni,correo_electronico,username, puntaje) "
							+ "VALUES (?, ?, ?, ?, ?, ?)");
			
			statement.setString(1, d.getNombre());
			statement.setString(2, d.getApellido());
			statement.setString(3, d.getDni());
			statement.setString(4, d.getCorreo());
			statement.setString(5, d.getUser().getUsuario());
			statement.setInt(6, d.getPuntaje());
			int cantidad = statement.executeUpdate();
			if (cantidad > 0) {
				System.out.println("Modificando " + cantidad + " registros");
			} else {
				System.out.println("Error al actualizar");
				// TODO: disparar Exception propia
			}
		} catch (SQLException e) {
			throw new AppException("Error al insertar dueño: " + e.getMessage());
		}  finally {
			ConnectionManager.disconnect();
		}


	}

	@Override
	public void update(Dueño dueño) throws AppException {
		try {
			Connection conn = ConnectionManager.getConnection();
            PreparedStatement statement = conn.prepareStatement("UPDATE propietarios SET nombre = ?, apellido = ?, dni = ?, correo_electronico = ?, username = ?, puntaje = ? "
            		+ "WHERE dni = ?");
            statement.setString(1, dueño.getNombre());
            statement.setString(2, dueño.getApellido());
            statement.setString(3, dueño.getDni());
            statement.setString(4, dueño.getCorreo());
            statement.setString(5, dueño.getUser().getUsuario());
            statement.setInt(6, dueño.getPuntaje());
            statement.setString(7, dueño.getDni());
            statement.executeUpdate();
		}catch (SQLException e) {
            throw new AppException("Error de SQL al actualizar el dueño: " + e.getMessage());
        }  finally {
            ConnectionManager.disconnect();
        }
	}

	@Override
	public void remove(String dni) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(Dueño dueño) {
		// TODO Auto-generated method stub

	}

	@Override
	public Dueño find(String dni) throws AppException {
		Dueño dueño = null;
		Usuario usuario = null;
		Rol rol = null;
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM propietarios p JOIN usuarios u ON p.username = u.usuario "
					+ "JOIN roles r ON u.rol = r.codigo WHERE p.dni = ? ");
			statement.setString(1,dni);
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
						usuario, resultSetConsulta.getInt("p.puntaje"));
			}
		} catch (SQLException | DataEmptyException | StringNullException | IncorrectEmailException | NotNumberException | NotNullException e) {
			throw new AppException("Error al procesar consulta: " + e.getMessage());
		} finally {
		ConnectionManager.disconnect();
		}
		return dueño;
	}
	
	public Dueño findByUser(String username) throws AppException {
		Dueño dueño = null;
		Usuario usuario = null;
		Rol rol = null;
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM propietarios p JOIN usuarios u ON p.username = u.usuario "
					+ "JOIN roles r ON u.rol = r.codigo WHERE p.username = ? ");
			statement.setString(1,username);
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
						usuario, resultSetConsulta.getInt("p.puntaje"));
			
			}
		} catch (SQLException | DataEmptyException | StringNullException | IncorrectEmailException | NotNumberException | NotNullException e) {
			throw new AppException("Error al procesar consulta: " + e.getMessage());
		} finally {
		ConnectionManager.disconnect();
		}
		return dueño;
	}

	@Override
	public List<Dueño> findAll() throws AppException, NotNumberException {
		List<Dueño> dueños = new ArrayList<Dueño>();
		Dueño dueño = null;
		Usuario usuario = null;
		Rol rol = null;
		try {
			Connection conn = ConnectionManager.getConnection();
			Statement statement = conn.createStatement();
			ResultSet resultSetConsulta = statement.executeQuery(
					"SELECT * FROM propietarios p JOIN usuarios u ON p.username = u.usuario "
							+ "JOIN roles r ON u.rol = r.codigo");
			while (resultSetConsulta.next()) {
				rol = new Rol(resultSetConsulta.getInt("r.codigo"), resultSetConsulta.getString("r.nombre"));
				usuario = new Usuario(resultSetConsulta.getString("u.usuario"),
						resultSetConsulta.getString("u.contrasena"),
						resultSetConsulta.getString("u.email"),
						rol);
				dueño= new Dueño(resultSetConsulta.getString("p.nombre"),
						resultSetConsulta.getString("p.apellido"),
						resultSetConsulta.getString("p.dni"),
						resultSetConsulta.getString("p.correo_electronico"),
						usuario, resultSetConsulta.getInt("p.puntaje"));
				dueños.add(dueño);
			}
		} catch (SQLException | DataEmptyException | StringNullException | IncorrectEmailException | NotNullException e) {
			throw new AppException("Error al encontrar Dueños" + e.getMessage());
		} finally {
			ConnectionManager.disconnect();
		}
		return dueños;
	}

	@Override
	public boolean exists(String dni) throws AppException {
		boolean exists = false;

		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn.prepareStatement(
					"SELECT u.dni " + " FROM propietarios as u" + " WHERE u.dni = ?");
			statement.setString(1, dni);
			ResultSet rs = statement.executeQuery();

			if (rs.next()) {
				if(rs.getString("dni").equals(dni)) {
					exists = true;
				}		
			}
		} catch (SQLException | AppException e ) {
			throw new AppException("Error al verificar existencia: ");
		}finally {
			ConnectionManager.disconnect();
		}
		return exists;

	}

}
