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
import ar.edu.unrn.seminario.exceptions.NotRegisterException;
import ar.edu.unrn.seminario.exceptions.StringNullException;
import ar.edu.unrn.seminario.modelo.Direccion;
import ar.edu.unrn.seminario.modelo.Dueño;
import ar.edu.unrn.seminario.modelo.Rol;
import ar.edu.unrn.seminario.modelo.Usuario;
import ar.edu.unrn.seminario.modelo.Vivienda;

public class DueñoDAOJDBC implements DueñoDao {

	@Override
	public void create(Dueño d) throws Exception {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("INSERT INTO propietarios(nombre,apellido,dni,correo_electronico) "
							+ "VALUES (?, ?, ?, ?)");
			
			statement.setString(1, d.getNombre());
			statement.setString(2, d.getApellido());
			statement.setString(3, d.getDni());
			statement.setString(4, d.getCorreo());
			int cantidad = statement.executeUpdate();
			if (cantidad > 0) {
				System.out.println("Modificando " + cantidad + " registros");
			} else {
				System.out.println("Error al actualizar");
				// TODO: disparar Exception propia
			}
		} catch (SQLException e) {
			throw new SQLException("Error al procesar consulta: "+e.getMessage());
		} catch (Exception e) {
			throw new Exception("Error al insertar un dueño: "+e.getMessage());
		} finally {
			ConnectionManager.disconnect();
		}


	}

	@Override
	public void update(Dueño dueño) {
		// TODO Auto-generated method stub

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
	public Dueño find(String dni) {
		Dueño dueño = null;
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM propietarios p "+"WHERE p.dni = ?");
			statement.setString(1,dni);
			ResultSet resultSetDueño = statement.executeQuery();
			if(resultSetDueño.next()) {
				dueño= new Dueño(resultSetDueño.getString("nombre"),
						resultSetDueño.getString("apellido"),
						resultSetDueño.getString("dni"),
						resultSetDueño.getString("correo_electronico"));
			}
		} catch (SQLException e) {
			System.out.println("Error al procesar consulta");
		// TODO: disparar Exception propia
		// throw new AppException(e, e.getSQLState(), e.getMessage());
		} catch (Exception e) {
		// TODO: disparar Exception propia
		// throw new AppException(e, e.getCause().getMessage(), e.getMessage());
		} finally {
		ConnectionManager.disconnect();
		}
		return dueño;
	}

	@Override
	public List<Dueño> findAll() throws AppException {
		List<Dueño> dueños = new ArrayList<Dueño>();
		try {
			Connection conn = ConnectionManager.getConnection();
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(
					"SELECT * from propietarios");

			while (rs.next()) {
				Dueño dueño = new Dueño(rs.getString("nombre"),
						rs.getString("apellido"),
						rs.getString("dni"),
						rs.getString("correo electronico"));
				dueños.add(dueño);
			}
		} catch (SQLException e) {
			System.out.println("Error de mySql\n" + e.toString());
			// TODO: disparar Exception propia
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			// TODO: disparar Exception propia
		} catch (DataEmptyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StringNullException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IncorrectEmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			throw new AppException("error al procesar la consulta");
		}finally {
			ConnectionManager.disconnect();
		}
		return exists;
		
	}

}
