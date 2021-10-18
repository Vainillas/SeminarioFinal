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
import ar.edu.unrn.seminario.exceptions.StringNullException;
import ar.edu.unrn.seminario.exceptions.NotNumberException;
import ar.edu.unrn.seminario.modelo.Direccion;
import ar.edu.unrn.seminario.modelo.Dueño;

public class DireccionDAOJDBC implements DireccionDao {

	@Override
	public void create(Direccion d) throws AppException  {
		

			Connection conn;
			try {
				conn = ConnectionManager.getConnection();
				PreparedStatement statement = conn
					.prepareStatement("INSERT INTO dirección(calle,altura,barrio,codigo_postal,latitud,longitud) "
							+ "VALUES (?, ?, ?, ?, ?, ?)");
	
				statement.setString(1, d.getCalle());
				statement.setInt(2, Integer.parseInt(d.getAltura()));
				statement.setString(3, d.getBarrio());
				statement.setInt(4, Integer.parseInt(d.getCodPostal()));
				statement.setString(5, d.getLatitud());
				statement.setString(6, d.getLongitud());
				int cantidad = statement.executeUpdate();
				if (cantidad > 0) {
					System.out.println("Modificando " + cantidad + " registros");
				} else {
					System.out.println("Error al actualizar");
					// TODO: disparar Exception propia
				}
			} catch (SQLException e) {
				throw new AppException("Error al crear la direccion: " + e.getMessage());
			} finally {
				ConnectionManager.disconnect();
				}
			}

	
	@Override
	public void update(Direccion direccion) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(String calle, Integer altura) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(Direccion direccion) {
		// TODO Auto-generated method stub

	}

	public Direccion find(String calle, Integer altura) throws AppException {
		Direccion direccion = null;
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM dirección d"+"WHERE d.calle = ? AND d.altura = ?");
			statement.setString(1,calle);
			statement.setInt(2, altura);
			ResultSet resultSetDireccion = statement.executeQuery();
			if(resultSetDireccion.next()) {
				direccion= new Direccion(resultSetDireccion.getString("calle"),
						resultSetDireccion.getString("longitud"),
						resultSetDireccion.getString("latitud"),
						Integer.toString(resultSetDireccion.getInt("altura")),
						Integer.toString(resultSetDireccion.getInt("codigo_postal")),
						resultSetDireccion.getString("barrio"));
			}
		} catch (SQLException | DataEmptyException | StringNullException | NotNumberException e) {
			throw new AppException("Error al encontrar la direccion: " + e.getMessage());
		} finally {
		ConnectionManager.disconnect();
		}
		return direccion;
	}

	public List<Direccion> findDireccionesDueño(Dueño d) throws AppException { 
		//Para hacer esto se necesitan hacer JOINS con la tabla de Viviendas y la de Dueño creo.
		List<Direccion> direcciones = new ArrayList<Direccion>();
		try {
			Connection conn = ConnectionManager.getConnection();
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(
					"SELECT * from dirección");

			while (rs.next()) {
				Direccion direccion = new Direccion(rs.getString("calle"),
						Integer.toString(rs.getInt("altura")),
						Integer.toString(rs.getInt("codigo_postal")),
						rs.getString("longitud"),
						rs.getString("latitud"),
						rs.getString("barrio"));
				direcciones.add(direccion);
			}
		} catch (SQLException | DataEmptyException | StringNullException | NotNumberException e) {
			throw new AppException("Error al encontrar las direcciones relacionadas al dueño: " + e.getMessage());
		} finally {
		ConnectionManager.disconnect();
		}
		return direcciones;
	}

	public List<Direccion> findAll() throws AppException {
		List<Direccion> direcciones = new ArrayList<Direccion>();
		try {
			Connection conn = ConnectionManager.getConnection();
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(
					"SELECT * from dirección");

			while (rs.next()) {
				Direccion direccion = new Direccion(rs.getString("calle"),
						Integer.toString(rs.getInt("altura")),
						Integer.toString(rs.getInt("codigo_postal")),
						rs.getString("longitud"),
						rs.getString("latitud"),
						rs.getString("barrio"));
				direcciones.add(direccion);
			}
		} catch (SQLException | DataEmptyException | StringNullException | NotNumberException e) {
			throw new AppException("Error al encontrar las direcciones: " + e.getMessage());
		} finally {
		ConnectionManager.disconnect();
		}
		return direcciones;
	}
}
