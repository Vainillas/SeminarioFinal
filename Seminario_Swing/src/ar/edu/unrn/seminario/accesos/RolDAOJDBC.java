package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.modelo.Rol;
import ar.edu.unrn.seminario.modelo.Usuario;

public class RolDAOJDBC implements RolDao {

	@Override
	public void create(Rol rol) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Rol rol) {
		// TODO Auto-generated method stub

//		if (e instanceof SQLIntegrityConstraintViolationException) {
//	        // Duplicate entry
//	    } else {
//	        // Other SQL Exception
//	    }

	}

	@Override
	public void remove(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(Rol rol) {
		// TODO Auto-generated method stub

	}

	@Override
	public Rol find(Integer codigo) {
		Rol rol = null;
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("SELECT r.codigo, r.nombre " + " FROM roles r " + " WHERE r.codigo = ?");

			statement.setInt(1, codigo);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				rol = new Rol(rs.getInt("codigo"), rs.getString("nombre"));
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

		return rol;
	}

	@Override
	public List<Rol> findAll() {
		List<Rol> listado = new ArrayList<Rol>();
		Statement sentencia = null;
		ResultSet resultado = null;
		try {
			sentencia = ConnectionManager.getConnection().createStatement();
			resultado = sentencia.executeQuery("select r.nombre, r.codigo, r.activo from roles r ");

			while (resultado.next()) {
				Rol rol = new Rol();
				rol.setNombre(resultado.getString(1));
				rol.setCodigo(resultado.getInt(2));
				rol.setActivo(resultado.getBoolean(3));

				listado.add(rol);
			}
		} catch (SQLException e) {
			System.out.println("Error de mySql\n" + e.toString());
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.disconnect();
		}

		return listado;
	}

}
