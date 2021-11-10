package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import ar.edu.unrn.seminario.exceptions.AppException;
import ar.edu.unrn.seminario.exceptions.NotNullException;
import ar.edu.unrn.seminario.modelo.Canje;

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
			statement.setDate(3, canje.getFechaCanje());
			statement.setInt(4, canje.getCampaña().getCodigo());
			int cantidad = statement.executeUpdate();
			if (cantidad > 0) {
				System.out.println("Modificando " + cantidad + " registros");
			} else {
				System.out.println("Error al actualizar");
				// TODO: disparar Exception propia
			}
		} catch (SQLException e) {
			throw new AppException("Error al crear el Beneficio: " + e.getMessage());
		} finally {
			ConnectionManager.disconnect();
			}
	}

	public void update(Canje canje) {
		
	}

	public void remove() {
		
	}

	public Canje find(int codigo) throws AppException, NotNullException{
		return null;
	}

	public List<Canje> findAll() throws AppException, NotNullException{
		return null;
	}

	public boolean exists(int codigo) throws AppException{
		return false;
	}
}
