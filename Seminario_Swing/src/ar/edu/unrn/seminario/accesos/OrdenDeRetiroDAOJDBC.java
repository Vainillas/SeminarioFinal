package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.exceptions.AppException;
import ar.edu.unrn.seminario.exceptions.DataEmptyException;
import ar.edu.unrn.seminario.exceptions.DateNullException;
import ar.edu.unrn.seminario.exceptions.IncorrectEmailException;
import ar.edu.unrn.seminario.exceptions.NotNullException;
import ar.edu.unrn.seminario.exceptions.NotNumberException;
import ar.edu.unrn.seminario.exceptions.StringNullException;
import ar.edu.unrn.seminario.modelo.Direccion;
import ar.edu.unrn.seminario.modelo.Dueño;
import ar.edu.unrn.seminario.modelo.Estado;
import ar.edu.unrn.seminario.modelo.OrdenDeRetiro;
import ar.edu.unrn.seminario.modelo.PedidoDeRetiro;
import ar.edu.unrn.seminario.modelo.Recolector;
import ar.edu.unrn.seminario.modelo.Residuo;
import ar.edu.unrn.seminario.modelo.Rol;
import ar.edu.unrn.seminario.modelo.TipoResiduo;
import ar.edu.unrn.seminario.modelo.Usuario;
import ar.edu.unrn.seminario.modelo.Visita;
import ar.edu.unrn.seminario.modelo.Vivienda;

public class OrdenDeRetiroDAOJDBC implements OrdenDeRetiroDao{ 


		public void create(OrdenDeRetiro o) throws AppException{
			try {
	            Connection conn = ConnectionManager.getConnection();
	            PreparedStatement statement = conn.prepareStatement
	                    ("INSERT INTO ordenes(codigoPedido, dniRecolector, fecha, estado) "
	                            + "VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
	            
	            statement.setInt(1, o.getPedidoAsociado().getCodigo());
	            if(o.getRecolector()!=null) {
	            	statement.setString(2, o.getRecolector().getDni());
	            }
	            else {
	            	statement.setNull(2, Types.VARCHAR);
	            }
	            statement.setDate(3, o.getFechaOrden());
	            statement.setString(4, o.getEstado().obtenerEstado());
	            
	            int cantidad = statement.executeUpdate();
	            if (cantidad > 0) {
	                System.out.println("Insertando " + cantidad + " registros");
	            } else {
	                System.out.println("Error al actualizar");
	            }
	        } catch (SQLException e) {
	            throw new AppException("Error al crear una Orden: " + e.getMessage());
	        } finally {
	            ConnectionManager.disconnect();
	        }
		}

		public void update(OrdenDeRetiro orden) throws AppException {
			try {
				Connection conn = ConnectionManager.getConnection();
	            PreparedStatement statement = conn.prepareStatement("UPDATE ordenes SET codigoPedido = ?, dniRecolector = ?, fecha = ?, estado = ?"
	            		+ "WHERE codigoOrden = ?");
	            statement.setInt(1, orden.getPedidoAsociado().getCodigo());
	            statement.setString(2, orden.getRecolector().getDni());
	            statement.setDate(3, orden.getFechaOrden());
	            statement.setString(4, orden.getEstado().obtenerEstado());
	            statement.setInt(5, orden.getCodigo());
	            statement.executeUpdate();
			}catch (SQLException e) {
	            throw new AppException("Error de SQL al actualizar orden de retiro: " + e.getMessage());
	        }  finally {
	            ConnectionManager.disconnect();
	        }
		}

		public void remove(int id) {
			
		}

		public void remove(OrdenDeRetiro orden) {
			
		}

		public OrdenDeRetiro find(int id) throws AppException { // ARREGLAR y AGREGAR VISITAs
			OrdenDeRetiro orden = null;
	        PedidoDeRetiro pedido = null;
	        
	        Vivienda vivienda = null;

			Dueño dueño = null;
			Usuario usuario = null;
			Rol rol = null;
			Direccion direccion = null;
			
			
			Recolector recolector = null;
			
			Estado estado = null;
			
			ArrayList<Visita> listaVisitas = new ArrayList<Visita>(); 
            Visita visita = null;

            TipoResiduo tipoResiduoPedido = null;
			Residuo residuoPedido = null;
			
			TipoResiduo tipoResiduoVisita = null;
			Residuo residuoVisita = null;
            
			
			try {
				Connection conn = ConnectionManager.getConnection();
				PreparedStatement statement = conn.prepareStatement("SELECT * FROM ordenes o "
						+ "JOIN pedidos p ON p.codigo = o.codigoPedido "
						+ "JOIN viviendas v ON v.codigo = p.codigo_vivienda "
						+ "JOIN propietarios pr ON v.dni = pr.dni "
						+ "JOIN dirección d ON v.calle = d.calle AND v.altura = d.altura "
						+ "JOIN usuarios u ON pr.username = u.usuario "
						+ "JOIN roles r ON u.rol = r.codigo "
						+ "JOIN recolectores re ON re.dni = o.dniRecolector WHERE codigoOrden = ?");
				statement.setInt(1, id);
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
						tipoResiduoPedido = new TipoResiduo(resultSetResiduo.getInt("r.puntaje"), resultSetResiduo.getString("r.nombre"));
						residuoPedido = new Residuo(tipoResiduoPedido, resultSetResiduo.getInt("rp.cantidad"));
						listaResiduos.add(residuoPedido);

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
					recolector = new Recolector(resultSetConsulta.getString("re.nombre"),
							resultSetConsulta.getString("re.apellido"),
							resultSetConsulta.getString("re.dni"),
							resultSetConsulta.getString("re.email"),
							resultSetConsulta.getString("re.telefono"));
					
					PreparedStatement statement8 = conn.prepareStatement("SELECT * FROM visitas v WHERE v.codigoOrden = ?");
					statement8.setInt(1, resultSetConsulta.getInt("codigoOrden"));
					ResultSet resultSetVisita = statement8.executeQuery();
					while(resultSetVisita.next()) {
						ArrayList<Residuo> listaResiduosVisita = new ArrayList<Residuo>(); 
						Connection conn2 = ConnectionManager.getConnection();
						PreparedStatement statement2 = conn2.prepareStatement("SELECT * FROM residuos_visita rv "
								+ "JOIN residuos r ON rv.nombre_residuo = r.nombre WHERE rv.codigo_visita = ?");
						statement2.setInt(1, resultSetVisita.getInt("codigo"));
						ResultSet resultSetResiduosVisita = statement2.executeQuery();

						while(resultSetResiduosVisita.next()){
								tipoResiduoVisita = new TipoResiduo(resultSetResiduosVisita.getInt("r.puntaje"), resultSetResiduosVisita.getString("r.nombre"));
								
								residuoVisita = new Residuo(tipoResiduoVisita, resultSetResiduosVisita.getInt("rv.cantidad"));
								
								listaResiduosVisita.add(residuoVisita);  
							}
						visita = new Visita(resultSetVisita.getString("v.observacion"), listaResiduosVisita,resultSetVisita.getInt("v.codigoOrden"),resultSetVisita.getInt("v.codigo"));
						System.out.println("\nVisita toString en OrdenDeRetiroDAOJDBC (Comprobación Residuos Duplicados): "+ visita.toString()); 
						listaVisitas.add(visita);
						}
					estado = new Estado(resultSetConsulta.getString("estado"));
					int codigoOrden = resultSetConsulta.getInt("codigoOrden");
					orden = new OrdenDeRetiro(pedido, recolector, resultSetConsulta.getDate("fecha"), estado, listaVisitas);
					orden.setCodigo(codigoOrden);
					}
					
					
					
	        } catch (SQLException | DataEmptyException | StringNullException | NotNumberException | NotNullException | DateNullException | IncorrectEmailException e) {
	            throw new AppException("Error al encontrar una orden de retiro : " +e.getMessage());
	        }  finally {
	            ConnectionManager.disconnect();
	        }
	        return orden;
		}

		public List<OrdenDeRetiro> findAll() throws AppException{ // AGREGAR LAS VISITAS
			List<OrdenDeRetiro> listaOrdenes = new ArrayList<>();
			OrdenDeRetiro orden = null;
	        PedidoDeRetiro pedido = null;
	        
	        Vivienda vivienda = null;

			Dueño dueño = null;
			Usuario usuario = null;
			Rol rol = null;
			Direccion direccion = null;
			
			
			Recolector recolector = null;
			
			Estado estado = null;
			
			ArrayList<Visita> listaVisitas = new ArrayList<Visita>(); 
            Visita visita = null;

            TipoResiduo tipoResiduoPedido = null;
			Residuo residuoPedido = null;
			
			TipoResiduo tipoResiduoVisita = null;
			Residuo residuoVisita = null;
            
			
			try {
				Connection conn = ConnectionManager.getConnection();
				PreparedStatement statement = conn.prepareStatement("SELECT * FROM ordenes o "
						+ "JOIN pedidos p ON p.codigo = o.codigoPedido "
						+ "JOIN viviendas v ON v.codigo = p.codigo_vivienda "
						+ "JOIN propietarios pr ON v.dni = pr.dni "
						+ "JOIN dirección d ON v.calle = d.calle AND v.altura = d.altura "
						+ "JOIN usuarios u ON pr.username = u.usuario "
						+ "JOIN roles r ON u.rol = r.codigo "
						+ "JOIN recolectores re ON re.dni = o.dniRecolector");
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
						tipoResiduoPedido = new TipoResiduo(resultSetResiduo.getInt("r.puntaje"), resultSetResiduo.getString("r.nombre"));
						residuoPedido = new Residuo(tipoResiduoPedido, resultSetResiduo.getInt("rp.cantidad"));
						listaResiduos.add(residuoPedido);

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
					recolector = new Recolector(resultSetConsulta.getString("re.nombre"),
							resultSetConsulta.getString("re.apellido"),
							resultSetConsulta.getString("re.dni"),
							resultSetConsulta.getString("re.email"),
							resultSetConsulta.getString("re.telefono"));
					
					PreparedStatement statement8 = conn.prepareStatement("SELECT * FROM visitas v WHERE v.codigoOrden = ?");
					statement8.setInt(1, resultSetConsulta.getInt("codigoOrden"));
					ResultSet resultSetVisita = statement8.executeQuery();
					while(resultSetVisita.next()) {
						ArrayList<Residuo> listaResiduosVisita = new ArrayList<Residuo>(); 
						Connection conn2 = ConnectionManager.getConnection();
						PreparedStatement statement2 = conn2.prepareStatement("SELECT * FROM residuos_visita rv "
								+ "JOIN residuos r ON rv.nombre_residuo = r.nombre WHERE rv.codigo_visita = ?");
						statement2.setInt(1, resultSetVisita.getInt("codigo"));
						ResultSet resultSetResiduosVisita = statement2.executeQuery();

						while(resultSetResiduosVisita.next()){
								tipoResiduoVisita = new TipoResiduo(resultSetResiduosVisita.getInt("r.puntaje"), resultSetResiduosVisita.getString("r.nombre"));
								
								residuoVisita = new Residuo(tipoResiduoVisita, resultSetResiduosVisita.getInt("rv.cantidad"));
								
								listaResiduosVisita.add(residuoVisita);  
							}
						visita = new Visita(resultSetVisita.getString("v.observacion"), listaResiduosVisita,resultSetVisita.getInt("v.codigoOrden"),resultSetVisita.getInt("v.codigo"));
						listaVisitas.add(visita);
						}
					estado = new Estado(resultSetConsulta.getString("estado"));
					int codigoOrden = resultSetConsulta.getInt("codigoOrden");
					orden = new OrdenDeRetiro(pedido, recolector, resultSetConsulta.getDate("fecha"), estado, listaVisitas);
					orden.setCodigo(codigoOrden);
					listaOrdenes.add(orden);
					}
					
	        }catch (SQLException | DataEmptyException | StringNullException | IncorrectEmailException | NotNumberException | NotNullException | DateNullException e) {
	            throw new AppException("Error al encontrar todas las ordenes de retiro : " +e.getMessage());
	        }  finally {
	            ConnectionManager.disconnect();
	        }
	        return listaOrdenes;
		}

		public boolean exists(int id) throws AppException{
			return false;
		}
	
}
