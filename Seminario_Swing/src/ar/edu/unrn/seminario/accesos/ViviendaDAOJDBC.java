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
import ar.edu.unrn.seminario.modelo.Direccion;
import ar.edu.unrn.seminario.modelo.Dueño;
import ar.edu.unrn.seminario.modelo.Vivienda;

public class ViviendaDAOJDBC implements ViviendaDao {

	@Override
	public void create(Vivienda v) throws AppException {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("INSERT INTO viviendas(calle,altura,dni) "
							+ "VALUES (?, ?, ?)");
			
			statement.setString(1, v.getDireccion().getCalle());
			statement.setString(2, v.getDireccion().getAltura());
			statement.setString(3, v.getDueño().getDni());
			int cantidad = statement.executeUpdate();
			if (cantidad > 0) {
				System.out.println("Modificando " + cantidad + " registros");
			} else {
				throw new AppException("error al procesar consulta");
			}
		} catch (SQLException  e) {
			throw new AppException("error en la consulta");
		}finally {
			ConnectionManager.disconnect();
		}

	}

	@Override
	public void update(Vivienda v) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(Vivienda v) {
		// TODO Auto-generated method stub

	}

	@Override
	public Vivienda find(Integer codigo) throws AppException {
		Vivienda vivienda=null;
		Direccion direccion = null;
		Dueño dueño = null;
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM viviendas v "+"WHERE v.codigo = ?");
			statement.setInt(1,codigo);
			ResultSet resultSetViviendas = statement.executeQuery();
			if(resultSetViviendas.next()) {
				PreparedStatement statement2 = connection.prepareStatement("SELECT * FROM propietarios p WHERE p.dni = ?");
				statement2.setString(1, resultSetViviendas.getString("dni"));
				ResultSet resultSetConstructor = statement2.executeQuery();
				if(resultSetConstructor.next()) {
					dueño = new Dueño(resultSetConstructor.getString("nombre"),
							resultSetConstructor.getString("apellido"),
							resultSetConstructor.getString("dni"),
							resultSetConstructor.getString("correo_electronico"),
							resultSetConstructor.getString("username")
							);
				}
				statement2 = connection.prepareStatement("SELECT * FROM dirección d WHERE d.calle= ? AND d.altura= ?");
				statement2.setString(1,resultSetViviendas.getString("calle"));
				statement2.setInt(2, resultSetViviendas.getInt("altura"));
				resultSetConstructor=statement2.executeQuery();
				if(resultSetConstructor.next()) {
					direccion = new Direccion(resultSetConstructor.getString("calle"), 
							Integer.toString(resultSetConstructor.getInt("altura")), 
							Integer.toString(resultSetConstructor.getInt("codigo_postal")), 
							resultSetConstructor.getString("longitud"),
							resultSetConstructor.getString("latitud"),
							resultSetConstructor.getString("barrio"));
				}
				vivienda= new Vivienda(direccion, dueño,resultSetViviendas.getInt("codigo"));
			}
		} catch (SQLException | DataEmptyException | StringNullException | NotNumberException | IncorrectEmailException e) {
			throw new AppException("Error al registrar una vivienda: "+e.getMessage());
		 
		} finally {
			
			ConnectionManager.disconnect();
		}
		return vivienda;
	}
	
	@Override
	public Vivienda find(String calle, String altura) throws AppException {
		Vivienda vivienda=null;
		Direccion direccion = null;
		Dueño dueño = null;
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM viviendas v "+"WHERE v.calle = ?");
			statement.setString(1,calle);
			ResultSet resultSetViviendas = statement.executeQuery();
			if(resultSetViviendas.next()) {
				PreparedStatement statement2 = connection.prepareStatement("SELECT * FROM propietarios p WHERE p.dni = ?");
				statement2.setString(1, resultSetViviendas.getString("dni"));
				ResultSet resultSetConstructor = statement2.executeQuery();
				if(resultSetConstructor.next()) {
					dueño = new Dueño(resultSetConstructor.getString("nombre"),
							resultSetConstructor.getString("apellido"),
							resultSetConstructor.getString("dni"),
							resultSetConstructor.getString("correo_electronico"),
							resultSetConstructor.getString("username")
							);
				}
				statement2 = connection.prepareStatement("SELECT * FROM dirección d WHERE d.calle= ? AND d.altura= ?");
				statement2.setString(1,resultSetViviendas.getString("calle"));
				statement2.setInt(2, resultSetViviendas.getInt("altura"));
				resultSetConstructor=statement2.executeQuery();
				if(resultSetConstructor.next()) {
					direccion = new Direccion(resultSetConstructor.getString("calle"), 
							Integer.toString(resultSetConstructor.getInt("altura")), 
							Integer.toString(resultSetConstructor.getInt("codigo_postal")), 
							resultSetConstructor.getString("longitud"),
							resultSetConstructor.getString("latitud"),
							resultSetConstructor.getString("barrio"));
				}
				vivienda= new Vivienda(direccion, dueño,resultSetViviendas.getInt("codigo"));
			}
		} catch (SQLException | DataEmptyException | StringNullException | IncorrectEmailException | NotNumberException e) {

			throw new AppException("Error al buscar una vivienda: ");
		 
		} finally {
		ConnectionManager.disconnect();
		}
		return vivienda;
	}
	
	
	@Override
	public List<Vivienda> findAll() throws AppException {
		List<Vivienda>viviendas = new ArrayList<>();
		Vivienda vivienda=null;
		Direccion direccion = null;
		Dueño dueño = null;
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM viviendas v");
			ResultSet resultSetViviendas = statement.executeQuery();
			

			while(resultSetViviendas.next()) {
				PreparedStatement statement2 = conn.prepareStatement("SELECT * FROM propietarios p WHERE p.dni = ?");
				statement2.setString(1, resultSetViviendas.getString("dni"));
				ResultSet resultSetConstructor = statement2.executeQuery();
				

				

				if(resultSetConstructor.next()) {


					dueño = new Dueño(resultSetConstructor.getString("nombre"),
							resultSetConstructor.getString("apellido"),
							
							resultSetConstructor.getString("dni"),
							resultSetConstructor.getString("correo_electronico"),
							resultSetConstructor.getString("username")
							);
				}
				
				statement2 = conn.prepareStatement("SELECT * FROM dirección d WHERE d.calle= ? AND d.altura= ?");
				statement2.setString(1,resultSetViviendas.getString("calle"));
				statement2.setInt(2, resultSetViviendas.getInt("altura"));
				resultSetConstructor=statement2.executeQuery();
				if(resultSetConstructor.next()) {
					
					direccion = new Direccion(resultSetConstructor.getString("calle"), 
							Integer.toString(resultSetConstructor.getInt("altura")), 
							Integer.toString(resultSetConstructor.getInt("codigo_postal")), 
							resultSetConstructor.getString("longitud"),
							resultSetConstructor.getString("latitud"),
							resultSetConstructor.getString("barrio"));
				}
				vivienda= new Vivienda(direccion, dueño,resultSetViviendas.getInt("codigo"));
				viviendas.add(vivienda);
				
			}
		} catch (Exception  e) {
			throw new AppException("Error al procesar la consulta: " + e.getMessage());
			
		}finally {
		ConnectionManager.disconnect();
		}
		return viviendas;
	}
	public List<Vivienda> findByUser(String username) throws AppException {
		List<Vivienda>viviendas = new ArrayList<>();
		Vivienda vivienda=null;
		Direccion direccion = null;
		Dueño dueño = null;
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn.prepareStatement("SELECT v.dni, v.calle, v.altura, v.codigo FROM viviendas v JOIN propietarios p ON p.dni=v.dni WHERE p.username = ?");
			statement.setString(1, username);
			ResultSet resultSetViviendas = statement.executeQuery();
			

			while(resultSetViviendas.next()) {
				PreparedStatement statement2 = conn.prepareStatement("SELECT * FROM propietarios p WHERE p.dni = ?");
				statement2.setString(1, resultSetViviendas.getString("dni"));
				ResultSet resultSetConstructor = statement2.executeQuery();
				

				

				if(resultSetConstructor.next()) {


					dueño = new Dueño(resultSetConstructor.getString("nombre"),
							resultSetConstructor.getString("apellido"),
							
							resultSetConstructor.getString("dni"),
							resultSetConstructor.getString("correo_electronico"),
							resultSetConstructor.getString("username")
							);
				}
				
				statement2 = conn.prepareStatement("SELECT * FROM dirección d WHERE d.calle= ? AND d.altura= ?");
				statement2.setString(1,resultSetViviendas.getString("calle"));
				statement2.setInt(2, resultSetViviendas.getInt("altura"));
				resultSetConstructor=statement2.executeQuery();
				if(resultSetConstructor.next()) {
					
					direccion = new Direccion(resultSetConstructor.getString("calle"), 
							Integer.toString(resultSetConstructor.getInt("altura")), 
							Integer.toString(resultSetConstructor.getInt("codigo_postal")), 
							resultSetConstructor.getString("longitud"),
							resultSetConstructor.getString("latitud"),
							resultSetConstructor.getString("barrio"));
				}
				vivienda= new Vivienda(direccion, dueño,resultSetViviendas.getInt("codigo"));
				viviendas.add(vivienda);
				
			}
		} catch (Exception  e) {
			throw new AppException("Error al procesar la consulta: " + e.getMessage());
			
		}finally {
		ConnectionManager.disconnect();
		}
		return viviendas;
	}

}
