package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import ar.edu.unrn.seminario.exceptions.AppException;
import ar.edu.unrn.seminario.exceptions.NotNullException;
import ar.edu.unrn.seminario.modelo.Campaña;

public class CampañaDAOJDBC {
	public void create(Campaña campaña) throws AppException{
		Connection conn;
		try { 
			conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
				.prepareStatement("INSERT INTO campañas(nombre_beneficio,costo) "
						+ "VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);

			//tatement.setString(1, b.getDescripcion());
			
			int cantidad = statement.executeUpdate();
			if (cantidad > 0) {
				System.out.println("Modificando " + cantidad + " registros");
			} else {
				System.out.println("Error al actualizar");
				// TODO: disparar Exception propia
			}
		} catch (SQLException e) {
			throw new AppException("Error al crear la Campaña " + e.getMessage());
		} finally {
			ConnectionManager.disconnect();
			}
	}

	public void update(Campaña campaña) {
		
	}

	public void remove() {
		
	}

	public Campaña find(int codigo) throws AppException, NotNullException{
		return null;
	}

	public List<Campaña> findAll() throws AppException, NotNullException{
		return null;
	}

	boolean exists(int codigo) throws AppException{
		return false;
	}
}
