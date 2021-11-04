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
import ar.edu.unrn.seminario.exceptions.DateNullException;
import ar.edu.unrn.seminario.exceptions.IncorrectEmailException;
import ar.edu.unrn.seminario.exceptions.KilogramEmptyException;
import ar.edu.unrn.seminario.exceptions.NotNullException;
import ar.edu.unrn.seminario.exceptions.NotNumberException;
import ar.edu.unrn.seminario.exceptions.StringNullException;
import ar.edu.unrn.seminario.modelo.Direccion;
import ar.edu.unrn.seminario.modelo.Dueño;
import ar.edu.unrn.seminario.modelo.PedidoDeRetiro;
import ar.edu.unrn.seminario.modelo.Residuo;
import ar.edu.unrn.seminario.modelo.Rol;
import ar.edu.unrn.seminario.modelo.TipoResiduo;
import ar.edu.unrn.seminario.modelo.Usuario;
import ar.edu.unrn.seminario.modelo.TipoResiduo;
import ar.edu.unrn.seminario.modelo.Vivienda;


public class PedidoDeRetiroDAOJDBC implements PedidoDeRetiroDao{

		public void create(PedidoDeRetiro p) throws AppException{
			try {
	            Connection conn = ConnectionManager.getConnection();
	            PreparedStatement statement = conn.prepareStatement
	                    ("INSERT INTO pedidos(codigo_vivienda,observacion,carga,fecha) "
	                            + "VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
	            statement.setInt(1, p.getVivienda().getID());
	            statement.setString(2, p.getObservacion());
	            
	            if(p.getMaquinaPesada()) { 
	            	statement.setInt(3, 1);
	            }else {
	            	statement.setInt(3, 0);
	            }
	            statement.setDate(4, p.getFechaDelPedido());
	            statement.executeUpdate();   
	            
	            
	             
	            ResultSet clave = statement.getGeneratedKeys();
	            clave.next();
	            int codigoPedido = clave.getInt(1);
	            clave.close();
	            
	            Connection conn2 = ConnectionManager.getConnection();
	            PreparedStatement statement2 = conn2.prepareStatement
	                    ("INSERT INTO residuos_pedido(codigo_pedido , nombre_residuo , cantidad) "
	                            + "VALUES (?, ?, ?)");
	            
	            for(int i=0; i < p.getListResiduos().size() ; i++) {
		            statement2.setInt(1, codigoPedido);
		            statement2.setString(2, p.getListResiduos().get(i).getTipo().getNombre()); 
		            System.out.println(p.getListResiduos().get(i).getTipo().getNombre());
		            statement2.setString(2, p.getListResiduos().get(i).getTipo().getNombre());
		            statement2.setInt(3, p.getListResiduos().get(i).getCantidadKg());
		            statement2.executeUpdate();
	            }
	            
	        } catch (SQLException e) {
	            throw new AppException("Error al registrar un pedido: "  + e.getMessage());
	        }  finally {
	            ConnectionManager.disconnect();
	        }

		}

		public void update(PedidoDeRetiro pedido) {
			
		}

		public void remove(String dni) {
		
		}
		
		public void remove(PedidoDeRetiro pedido) {
			
		}

		public PedidoDeRetiro find(int codigo) throws AppException {
			PedidoDeRetiro pedido = null;
			Vivienda vivienda = null;

			Dueño dueño = null;
			Usuario usuario = null;
			Rol rol = null;
			
			Direccion direccion = null;

			TipoResiduo tipoResiduo = null;
			Residuo residuo = null;
			

			try {
				Connection conn = ConnectionManager.getConnection();
				PreparedStatement statement = conn.prepareStatement("SELECT * FROM pedidos p JOIN viviendas v ON v.codigo = p.codigo_vivienda "
						+ "JOIN propietarios pr ON v.dni = pr.dni "
						+ "JOIN dirección d ON v.calle = d.calle AND v.altura = d.altura "
						+ "JOIN usuarios u ON pr.username = u.usuario "
						+ "JOIN roles r ON u.rol = r.codigo "+"WHERE p.codigo = ?");
				statement.setInt(1,codigo);
				ResultSet resultSetConsulta = statement.executeQuery();
				if(resultSetConsulta.next()) {
					rol = new Rol(resultSetConsulta.getInt("r.codigo"), resultSetConsulta.getString("r.nombre"));
					usuario = new Usuario(resultSetConsulta.getString("u.usuario"),
							resultSetConsulta.getString("u.contrasena"),
							resultSetConsulta.getString("u.email"),
							rol);
					dueño = new Dueño(resultSetConsulta.getString("pr.nombre") , resultSetConsulta.getString("pr.apellido") , resultSetConsulta.getString("pr.dni"), resultSetConsulta.getString("pr.correo_electronico"), usuario);
					direccion = new Direccion(resultSetConsulta.getString("d.calle"), Integer.toString(resultSetConsulta.getInt("d.altura")), Integer.toString(resultSetConsulta.getInt("d.codigo_postal")), resultSetConsulta.getString("d.longitud"), resultSetConsulta.getString("d.latitud"), resultSetConsulta.getString("d.barrio"));
					vivienda = new Vivienda(direccion, dueño, resultSetConsulta.getInt("v.codigo"));

					ArrayList<Residuo>listaResiduos = new ArrayList<>();
					PreparedStatement statement5 = conn.prepareStatement("SELECT * FROM residuos_pedido rp "
							+ "JOIN residuos r ON r.nombre = rp.nombre_residuo WHERE rp.codigo_pedido = ?");
					statement5.setInt(1, resultSetConsulta.getInt("p.codigo"));
					ResultSet resultSetResiduo = statement5.executeQuery();
					while(resultSetResiduo.next()) {
						tipoResiduo = new TipoResiduo(resultSetResiduo.getInt("r.puntaje"), resultSetResiduo.getString("r.nombre"));
						residuo = new Residuo(tipoResiduo, resultSetResiduo.getInt("rp.cantidad"));
						listaResiduos.add(residuo);

					}


					Boolean maq = false;
					if(resultSetConsulta.getInt("p.carga") == 1) {
						maq = true;
					}
					pedido = new PedidoDeRetiro(resultSetConsulta.getString("p.observacion"),
							maq,
							listaResiduos,
							resultSetConsulta.getDate("p.fecha"),
							vivienda, resultSetConsulta.getInt("p.codigo"));
				}
				
			} catch (SQLException | DataEmptyException | NotNullException | StringNullException | DateNullException | NotNumberException | IncorrectEmailException  e) {
				throw new AppException("error al encontrar Pedido"  + e.getMessage());


			}  finally {
				ConnectionManager.disconnect();
			}
			return pedido;

		}

		public List<PedidoDeRetiro> findAll() throws AppException, IncorrectEmailException{

			List<PedidoDeRetiro> pedidos = new ArrayList<>();
			PedidoDeRetiro pedido = null;
			Vivienda vivienda = null;

			Dueño dueño = null;
			Usuario usuario = null;
			Rol rol = null;

			Direccion direccion = null;

			TipoResiduo tipoResiduo = null;
			Residuo residuo = null;
			try {
				Connection conn = ConnectionManager.getConnection();
				PreparedStatement statement = conn.prepareStatement("SELECT DISTINCT * FROM pedidos p "
						+ "JOIN viviendas v ON p.codigo_vivienda = v.codigo "
						+ "JOIN propietarios pr ON v.dni = pr.dni "
						+ "JOIN dirección d ON v.calle = d.calle AND v.altura = d.altura "
						+ "JOIN usuarios u ON pr.username = u.usuario "
						+ "JOIN roles r ON u.rol = r.codigo");
				ResultSet resultSetConsulta = statement.executeQuery();
				while(resultSetConsulta.next()) {
					rol = new Rol(resultSetConsulta.getInt("r.codigo"), resultSetConsulta.getString("r.nombre"));
					usuario = new Usuario(resultSetConsulta.getString("u.usuario"),
							resultSetConsulta.getString("u.contrasena"),
							resultSetConsulta.getString("u.email"),
							rol);
					dueño = new Dueño(resultSetConsulta.getString("pr.nombre") , resultSetConsulta.getString("pr.apellido") , resultSetConsulta.getString("pr.dni"), resultSetConsulta.getString("pr.correo_electronico"), usuario);
					direccion = new Direccion(resultSetConsulta.getString("d.calle"), Integer.toString(resultSetConsulta.getInt("d.altura")), Integer.toString(resultSetConsulta.getInt("d.codigo_postal")), resultSetConsulta.getString("d.longitud"), resultSetConsulta.getString("d.latitud"), resultSetConsulta.getString("d.barrio"));
					vivienda = new Vivienda(direccion, dueño, resultSetConsulta.getInt("v.codigo"));

					ArrayList<Residuo>listaResiduos = new ArrayList<>();
					PreparedStatement statement5 = conn.prepareStatement("SELECT * FROM residuos_pedido rp "
							+ "JOIN residuos r ON r.nombre = rp.nombre_residuo WHERE rp.codigo_pedido = ?");
					statement5.setInt(1, resultSetConsulta.getInt("p.codigo"));
					ResultSet resultSetResiduo = statement5.executeQuery();
					while(resultSetResiduo.next()) {
						tipoResiduo = new TipoResiduo(resultSetResiduo.getInt("r.puntaje"), resultSetResiduo.getString("r.nombre"));
						residuo = new Residuo(tipoResiduo, resultSetResiduo.getInt("rp.cantidad"));
						listaResiduos.add(residuo);
					}


					Boolean maq = false;
					if(resultSetConsulta.getInt("p.carga") == 1) {
						maq = true;
					}
					pedido = new PedidoDeRetiro(resultSetConsulta.getString("p.observacion"),
							maq,
							listaResiduos,
							resultSetConsulta.getDate("p.fecha"),
							vivienda, resultSetConsulta.getInt("p.codigo"));
					pedidos.add(pedido);
				}
			} catch (SQLException | DataEmptyException | NotNullException | StringNullException | DateNullException  | NotNumberException e   ) {

				throw new AppException("Error al encontrar todos los Pedidos: "+ e.getMessage());
			} finally {
				ConnectionManager.disconnect();
			}
			return pedidos;
		}
	       
		public List<PedidoDeRetiro> findByUser(String username) throws AppException, IncorrectEmailException{
			List<PedidoDeRetiro> pedidos = new ArrayList<>();
			PedidoDeRetiro pedido = null;
			Vivienda vivienda = null;

			Dueño dueño = null;
			Usuario usuario = null;
			Rol rol = null;

			Direccion direccion = null;

			TipoResiduo tipoResiduo = null;
			Residuo residuo = null;

			try {
				Connection conn = ConnectionManager.getConnection();
				PreparedStatement statement = conn.prepareStatement("SELECT * FROM pedidos p JOIN viviendas v ON v.codigo = p.codigo_vivienda "
						+ "JOIN propietarios pr ON v.dni = pr.dni "
						+ "JOIN dirección d ON v.calle = d.calle AND v.altura = d.altura "
						+ "JOIN usuarios u ON pr.username = u.usuario "
						+ "JOIN roles r ON u.rol = r.codigo WHERE pr.username = ?");
				statement.setString(1, username);
				ResultSet resultSetConsulta = statement.executeQuery();

				while(resultSetConsulta.next()) {
					rol = new Rol(resultSetConsulta.getInt("r.codigo"), resultSetConsulta.getString("r.nombre"));
					usuario = new Usuario(resultSetConsulta.getString("u.usuario"),
							resultSetConsulta.getString("u.contrasena"),
							resultSetConsulta.getString("u.email"),
							rol);
					dueño = new Dueño(resultSetConsulta.getString("pr.nombre") , resultSetConsulta.getString("pr.apellido") , resultSetConsulta.getString("pr.dni"), resultSetConsulta.getString("pr.correo_electronico"), usuario);
					direccion = new Direccion(resultSetConsulta.getString("d.calle"), Integer.toString(resultSetConsulta.getInt("d.altura")), Integer.toString(resultSetConsulta.getInt("d.codigo_postal")), resultSetConsulta.getString("d.longitud"), resultSetConsulta.getString("d.latitud"), resultSetConsulta.getString("d.barrio"));
					vivienda = new Vivienda(direccion, dueño, resultSetConsulta.getInt("v.codigo"));

					ArrayList<Residuo>listaResiduos = new ArrayList<>();
					PreparedStatement statement5 = conn.prepareStatement("SELECT * FROM residuos_pedido rp "
							+ "JOIN residuos r ON r.nombre = rp.nombre_residuo WHERE rp.codigo_pedido = ?");
					statement5.setInt(1, resultSetConsulta.getInt("p.codigo"));
					ResultSet resultSetResiduo = statement5.executeQuery();
					while(resultSetResiduo.next()) {
						tipoResiduo = new TipoResiduo(resultSetResiduo.getInt("r.puntaje"), resultSetResiduo.getString("r.nombre"));
						residuo = new Residuo(tipoResiduo, resultSetResiduo.getInt("rp.cantidad"));
						listaResiduos.add(residuo);

					}


					Boolean maq = false;
					if(resultSetConsulta.getInt("p.carga") == 1) {
						maq = true;
					}
					pedido = new PedidoDeRetiro(resultSetConsulta.getString("p.observacion"),
							maq,
							listaResiduos,
							resultSetConsulta.getDate("p.fecha"),
							vivienda, resultSetConsulta.getInt("p.codigo"));
					pedidos.add(pedido);
				}
			} catch (SQLException | DataEmptyException | NotNullException | StringNullException | DateNullException  | NotNumberException e   ) {

				throw new AppException("Error al encontrar todos los Pedidos: "+ e.getMessage());
			} finally {
				ConnectionManager.disconnect();
			}
			return pedidos;
		}
		@Override
		public boolean exists(String dni) throws AppException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public List<PedidoDeRetiro> findNoOrder() throws AppException, IncorrectEmailException {
			// TODO Auto-generated method stub
			return null;
		}

		
	
}

