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
import ar.edu.unrn.seminario.exceptions.NotNumberException;
import ar.edu.unrn.seminario.exceptions.StringNullException;
import ar.edu.unrn.seminario.modelo.Dueño;
import ar.edu.unrn.seminario.modelo.Recolector;

public class RecolectorDAOJDBC implements RecolectorDao {
	public void create(Recolector r) throws AppException{
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("INSERT INTO recolectores(nombre,apellido,dni,email,telefono)"
							+ "VALUES (?, ?, ?, ?, ?)");
			
			statement.setString(1, r.getNombre());
			statement.setString(2, r.getApellido());
			statement.setString(3, r.getDni());
			statement.setString(4, r.getEmail());
			statement.setString(5, r.getTelefono());
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new AppException("Error al insertar recolector: " + e.getMessage());
		}  finally {
			ConnectionManager.disconnect();
		}
	}

	public void update(Recolector recolector) {
		
	}

	public void remove(String dni) {
		
	}

	public void remove(Recolector recolector) {
		
	}

	public Recolector find(String dni) throws AppException {
		Recolector recolector = null;
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM recolectores r "+"WHERE r.dni = ?");
			statement.setString(1,dni);
			ResultSet resultSetRecolector = statement.executeQuery();
			if(resultSetRecolector.next()) {
				recolector= new Recolector(resultSetRecolector.getString("nombre"),
						resultSetRecolector.getString("apellido"),
						resultSetRecolector.getString("dni"),
						resultSetRecolector.getString("email"),
						resultSetRecolector.getString("telefono"));
			}
		} catch (SQLException | DataEmptyException | StringNullException | IncorrectEmailException | NotNumberException e) {
			throw new AppException("Error al procesar consulta: " + e.getMessage());
		} finally {
		ConnectionManager.disconnect();
		}
		return recolector;
	}


	public List<Recolector> findAll() throws AppException{
		List<Recolector> recolectores = new ArrayList<Recolector>();
		try {
			Connection conn = ConnectionManager.getConnection();
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(
					"SELECT * from recolectores");

			while (rs.next()) {
				Recolector recolector = new Recolector(rs.getString("nombre"),
						rs.getString("apellido"),
						rs.getString("dni"),
						rs.getString("email"),
						rs.getString("telefono"));
				recolectores.add(recolector);
			}
		} catch (SQLException | DataEmptyException | StringNullException | IncorrectEmailException | NotNumberException e) {
			throw new AppException("Error al encontrar todos los recolectores:" + e.toString());
		} finally {
			ConnectionManager.disconnect();
		}
		return recolectores;
	}

	public boolean exists(String dni) throws AppException{
		boolean exists = false;

		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn.prepareStatement(
					"SELECT r.dni " + " FROM recolectores r" + " WHERE r.dni = ?");
			statement.setString(1, dni);
			ResultSet rs = statement.executeQuery();

			if (rs.next()) {
				if(rs.getString("dni").equals(dni)) {
					exists = true;
				}		
			}
		} catch (SQLException | AppException e ) {
			throw new AppException("Error al verificar existencia: "+ e.getMessage());
		}finally {
			ConnectionManager.disconnect();
		}
		return exists;
	}
}
