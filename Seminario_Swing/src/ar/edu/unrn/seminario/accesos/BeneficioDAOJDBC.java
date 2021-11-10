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
import ar.edu.unrn.seminario.exceptions.NotNullException;
import ar.edu.unrn.seminario.exceptions.NotNumberException;
import ar.edu.unrn.seminario.exceptions.StringNullException;
import ar.edu.unrn.seminario.modelo.Beneficio;
import ar.edu.unrn.seminario.modelo.Direccion;

public class BeneficioDAOJDBC implements BeneficioDao {
	public void create(Beneficio b) throws AppException{
		Connection conn;
		try { 
			conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
				.prepareStatement("INSERT INTO beneficios(nombre_beneficio,costo) "
						+ "VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);

			statement.setString(1, b.getDescripcion());
			statement.setInt(2, Integer.parseInt(b.getPuntajeConsumible()));
			int cantidad = statement.executeUpdate();
			if (cantidad > 0) {
				System.out.println("Modificando " + cantidad + " registros");
			} else {
				System.out.println("Error al actualizar");
			}
		} catch (SQLException e) {
			throw new AppException("Error al crear el Beneficio: " + e.getMessage());
		} finally {
			ConnectionManager.disconnect();
			}
	}

	public void update(Beneficio beneficio) {
		
	}

	public void remove() {
		 
	}

	public Beneficio find(int codigo) throws AppException, NotNullException{
		Beneficio beneficio = null;
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM beneficios b "+"WHERE d.codigo = ?");
			statement.setInt(1, codigo);
			ResultSet resultSetConsulta = statement.executeQuery();
			if(resultSetConsulta.next()) {
				beneficio= new Beneficio(resultSetConsulta.getString("nombre_beneficio"),  
						String.valueOf(resultSetConsulta.getInt("costo")),
						resultSetConsulta.getInt("codigo"));
			}
		} catch (SQLException e) {
			throw new AppException("Error al encontrar un beneficio: " + e.getMessage());
		} finally {
		ConnectionManager.disconnect();
		}
		return beneficio;
	}

	public List<Beneficio> findAll() throws AppException, NotNullException{
		List<Beneficio> beneficios = new ArrayList<Beneficio>();
		try {
			Connection conn = ConnectionManager.getConnection();
			Statement statement = conn.createStatement();
			ResultSet ResultSetBeneficios = statement.executeQuery(
					"SELECT * from beneficios");

			while (ResultSetBeneficios.next()) {
				Beneficio beneficio = new Beneficio(ResultSetBeneficios.getString("nombre_beneficio"),String.valueOf(ResultSetBeneficios.getString("costo")),
						ResultSetBeneficios.getInt("codigo"));
				beneficios.add(beneficio);
			}
		} catch (SQLException e) {
			throw new AppException("Error al encontrar los beneficios: " + e.getMessage());
		} finally {
		ConnectionManager.disconnect();
		}
		return beneficios;
	}

	public boolean exists(int codigo) throws AppException{
		return false;
	}
}
