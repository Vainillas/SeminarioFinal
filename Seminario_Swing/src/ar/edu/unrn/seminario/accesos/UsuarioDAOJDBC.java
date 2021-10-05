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
import ar.edu.unrn.seminario.exceptions.NotCorrectPasswordException;
import ar.edu.unrn.seminario.exceptions.NotNullException;
import ar.edu.unrn.seminario.exceptions.NotRegisterException;
import ar.edu.unrn.seminario.exceptions.StringNullException;
import ar.edu.unrn.seminario.modelo.Rol;
import ar.edu.unrn.seminario.modelo.Usuario;
import ar.edu.unrn.seminario.modelo.UsuarioIngreso;

public class UsuarioDAOJDBC implements UsuarioDao {

	@Override
	public void create(Usuario usuario) {
		try {

			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("INSERT INTO usuarios(usuario, password, email, activo,rol) "
							+ "VALUES (?, ?, ?, ?, ?)");
			statement.setString(1, usuario.getUsuario());
			statement.setString(2, usuario.getContrasena());
			statement.setString(3, usuario.getEmail());
			statement.setBoolean(4, usuario.isActivo());
			statement.setInt(5, usuario.getRol().getCodigo());
			int cantidad = statement.executeUpdate();
			if (cantidad > 0) {
				// System.out.println("Modificando " + cantidad + " registros");
			} else {
				System.out.println("Error al actualizar");
				// TODO: disparar Exception propia
			}
		} catch (SQLException e) {
			System.out.println("Error al procesar consulta");
			System.out.println(e.getMessage());
			// TODO: disparar Exception propia
		} catch (Exception e) {
			System.out.println("Error al insertar un usuario");
			// TODO: disparar Exception propia
		} finally {
			ConnectionManager.disconnect();
		}
	}
	public boolean exists(String username) throws NotRegisterException,AppException {//Exists

		boolean exists = false;
		
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn.prepareStatement(
					"SELECT u.usuario " + " FROM usuarios as u" + " WHERE u.usuario = ?");
			statement.setString(1, username);
			ResultSet rs = statement.executeQuery();
			
			if (rs.next()) {
				if(rs.getString("usuario").equals(username)) {
					exists = true;
				}		
			}
			if(!exists) {//si no existe el usuario se genera una excepcion
				throw new NotRegisterException("usuario no registrado");//como hacerlo una excepcion sin if?esta bien igual?
			}
		
		} catch (SQLException e ) {
			throw new AppException("error al procesar la consulta");
		}finally {
			ConnectionManager.disconnect();
		}
		return exists;
		
	}
	
	public boolean validateData(UsuarioIngreso user) throws NotRegisterException,AppException,NotCorrectPasswordException {//Exists
		boolean exists = false;
		if(this.exists(user.getUser())) {
			try {
				Connection conn = ConnectionManager.getConnection();
				PreparedStatement statement = conn.prepareStatement("SELECT u.usuario, u.password " + " FROM usuarios as u" + " WHERE u.usuario = ?");
				statement.setString(1,user.getUser());
				/*problema: la linea de arriba nose porque pero creo que genera algo y ese algo
				 *  no me deja tirar la excepcion NotRegisterException que esta abajo.
				 no comprendo el porque */
				
				//lo pude resolver con el if(exists) de arriba
				
				ResultSet rs = statement.executeQuery();
				if (rs.next()) {
						if(rs.getString("password").equals(user.getPassword())) {
							exists = true;
							}
							else {
								throw new NotCorrectPasswordException("contraseña incorrecta");
							}
				}
			} catch (SQLException e ) {
				System.out.println(e.getMessage());
				throw new AppException("error al procesar la consulta");
			}finally {
				ConnectionManager.disconnect();
			}
		}
	return exists;
		
	}
	@Override
	public void update(Usuario usuario) {
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
	public void remove(Usuario rol) {
		// TODO Auto-generated method stub

	}

	@Override
	public Usuario find(String username) {
		Usuario usuario = null;
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn.prepareStatement(
					"SELECT u.usuario,  u.contrasena, u.email, r.codigo as codigo_rol, r.nombre as nombre_rol "
							+ " FROM usuarios u JOIN roles r ON (u.rol = r.codigo) " + " WHERE u.usuario = ?");

			statement.setString(1, username);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				Rol rol = new Rol(rs.getInt("codigo_rol"), rs.getString("nombre_rol"));
				usuario = new Usuario(rs.getString("usuario"), rs.getString("contrasena"),
						rs.getString("email"), rol);
			}

		} catch (SQLException e) {
			
			// TODO: disparar Exception propia
			// throw new AppException(e, e.getSQLState(), e.getMessage());
		} catch (Exception e) {
			// TODO: disparar Exception propia
			// throw new AppException(e, e.getCause().getMessage(), e.getMessage());
		} finally {
			ConnectionManager.disconnect();
		}

		return usuario;
	}
	
	
	@Override
	public List<Usuario> findAll() throws AppException {
		List<Usuario> usuarios = new ArrayList<Usuario>();
		try {
			Connection conn = ConnectionManager.getConnection();
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(
					"SELECT u.usuario,  u.password, u.email, r.codigo as codigo_rol, r.nombre as nombre_rol  "
							+ "FROM usuarios u JOIN roles r ON (u.rol = r.codigo) ");

			while (rs.next()) {

				Rol rol = new Rol(rs.getInt("codigo_rol"), rs.getString("nombre_rol"));
				Usuario usuario = new Usuario(rs.getString("usuario"), rs.getString("password"),
					rs.getString("email"), rol);

				usuarios.add(usuario);
			}
		} catch (SQLException   | NotNullException   | DataEmptyException  | StringNullException | IncorrectEmailException e1) {
			System.out.println(e1.getMessage());
			throw new AppException("error de aplicacion");
		
		}finally {
			ConnectionManager.disconnect();
		}

		return usuarios;
	}

}
