package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.exceptions.AppException;
import ar.edu.unrn.seminario.exceptions.KilogramEmptyException;
import ar.edu.unrn.seminario.exceptions.NotNumberException;
import ar.edu.unrn.seminario.modelo.Residuo;

public class ResiduoDAOJDBC implements ResiduoDao {

	@Override
	public void create(Residuo r) throws AppException {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("INSERT INTO residuos(nombre, puntaje) "
							+ "VALUES (?, ?)");
			
			statement.setString(1, r.getNombre());
			statement.setInt(2, r.getValor());
			int cantidad = statement.executeUpdate();
		} catch (SQLException  e) {
			throw new AppException("Error en la creación: "+ e.getMessage());
		} catch (Exception e) {
			System.out.println("Error indeterminado: "+ e.getMessage());
		}finally {
			ConnectionManager.disconnect();
		}
	}

	@Override
	public void update(Residuo r) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(Residuo r) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(String nombre) {
		// TODO Auto-generated method stub

	}

	@Override
	public Residuo find(String nombre) throws AppException {
		Residuo residuo = null;
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM residuos r WHERE r.nombre = ?");
			statement.setString(1, nombre);
			ResultSet resultSetResiduo = statement.executeQuery();
			if(resultSetResiduo.next()) {
				residuo = new Residuo(resultSetResiduo.getInt("puntaje"),
						resultSetResiduo.getString("nombre"));
			}
		} catch (SQLException | KilogramEmptyException | NotNumberException e) {
			throw new AppException("Error en la búsqueda: "+ e.getMessage());
		}
		finally {
			ConnectionManager.disconnect();
		}
		return residuo;
	}

	@Override
	public List<Residuo> findAll() throws AppException {
		List<Residuo>listaResiduos = new ArrayList<>();
		Residuo residuo = null;
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM residuos");
			ResultSet resultSetResiduo = statement.executeQuery();
			while(resultSetResiduo.next()) {
				residuo = new Residuo(resultSetResiduo.getInt("puntaje"),
						resultSetResiduo.getString("nombre"));
				listaResiduos.add(residuo);
			}
		} catch (SQLException | KilogramEmptyException | NotNumberException e) {
			throw new AppException("Error en la búsqueda: "+ e.getMessage());
		}finally {
			ConnectionManager.disconnect();
		}
		return listaResiduos;
	}

}
