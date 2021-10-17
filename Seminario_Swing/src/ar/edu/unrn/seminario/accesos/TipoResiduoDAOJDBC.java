package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.exceptions.AppException;
import ar.edu.unrn.seminario.exceptions.DataEmptyException;
import ar.edu.unrn.seminario.exceptions.NotNullException;
import ar.edu.unrn.seminario.modelo.TipoResiduo;

public class TipoResiduoDAOJDBC implements TipoResiduoDao {

	@Override
	public void create(TipoResiduo r) throws AppException {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("INSERT INTO residuos(nombre, puntaje) "
							+ "VALUES (?, ?)");
			
			statement.setString(1, r.getNombre());
			statement.setInt(2, r.getValor());
			statement.executeUpdate();
		} catch (SQLException  e) {
			throw new AppException("Error en la creación: "+ e.getMessage());
		} catch (Exception e) {
			System.out.println("Error indeterminado: "+ e.getMessage());
		}finally {
			ConnectionManager.disconnect();
		}
	}

	@Override
	public void update(TipoResiduo r) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(TipoResiduo r) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(String nombre) {
		// TODO Auto-generated method stub

	}

	@Override
	public TipoResiduo find(String nombre) throws AppException {
		TipoResiduo tipoResiduo = null;
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM residuos r WHERE r.nombre = ?");
			statement.setString(1, nombre);
			ResultSet resultSetResiduo = statement.executeQuery();
			if(resultSetResiduo.next()) {
				tipoResiduo = new TipoResiduo(resultSetResiduo.getInt("puntaje"),
						resultSetResiduo.getString("nombre"));
			}
		} catch (SQLException | NotNullException | DataEmptyException e) {
			throw new AppException("Error en la búsqueda: "+ e.getMessage());
		}
		finally {
			ConnectionManager.disconnect();
		}
		return tipoResiduo;
	}

	@Override
	public List<TipoResiduo> findAll() throws AppException {
		List<TipoResiduo>listaResiduos = new ArrayList<>();
		TipoResiduo tipoResiduo = null;
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM residuos");
			ResultSet resultSetResiduo = statement.executeQuery();
			while(resultSetResiduo.next()) {
				tipoResiduo = new TipoResiduo(resultSetResiduo.getInt("puntaje"),
						resultSetResiduo.getString("nombre"));
				listaResiduos.add(tipoResiduo);
			}
		} catch (SQLException | NotNullException | DataEmptyException e) {
			throw new AppException("Error en la búsqueda: "+ e.getMessage());
		}finally {
			ConnectionManager.disconnect();
		}
		return listaResiduos;
	}

}
