package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import ar.edu.unrn.seminario.modelo.TipoResiduo;
import ar.edu.unrn.seminario.modelo.Visita;
import ar.edu.unrn.seminario.modelo.Vivienda;

public class OrdenDeRetiroDAOJDBC implements OrdenDeRetiroDao{ 


		public void create(OrdenDeRetiro o) throws AppException{
			try {
	            Connection conn = ConnectionManager.getConnection();
	            PreparedStatement statement = conn.prepareStatement
	                    ("INSERT INTO ordenes(codigoPedido, dniRecolector, fecha, estado) "
	                            + "VALUES (?, ?, ?, ?)");
	            
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
				PreparedStatement statement = conn.prepareStatement("SELECT * from ordenes");
				ResultSet resultSetOrden = statement.executeQuery();
				if(resultSetOrden.next()) {
					statement = conn.prepareStatement("SELECT * FROM pedido p" + 
							"WHERE p.codigo = ?");
					statement.setInt(1, resultSetOrden.getInt("codigoPedido"));
					ResultSet resultSetPedido = statement.executeQuery();
					if(resultSetPedido.next()) {
						statement = conn.prepareStatement("SELECT * FROM viviendas v WHERE v.codigo = ?");
						statement.setInt(1, resultSetPedido.getInt("codigo_vivienda"));
						ResultSet resultSetVivienda = statement.executeQuery();
						if(resultSetVivienda.next()) {
							Connection conn2 = ConnectionManager.getConnection();
							PreparedStatement statement3 = conn2.prepareStatement("SELECT * FROM propietarios p WHERE p.dni = ?");
							statement3.setString(1, resultSetVivienda.getString("dni"));
							ResultSet resultSetDueño = statement3.executeQuery();

							if(resultSetDueño.next()) {

								Connection conn3 = ConnectionManager.getConnection();
								PreparedStatement statement4 = conn3.prepareStatement("SELECT * FROM dirección d WHERE d.calle = ? AND d.altura = ?");
								statement4.setString(1, resultSetVivienda.getString("calle"));
								statement4.setInt(2, resultSetVivienda.getInt("altura"));
								ResultSet resultSetDireccion = statement4.executeQuery();

								if(resultSetDireccion.next()) {
									dueño = new Dueño(resultSetDueño.getString("nombre") , resultSetDueño.getString("apellido") , resultSetDueño.getString("dni"), resultSetDueño.getString("correo_electronico"), resultSetDueño.getString("username"));
									direccion = new Direccion(resultSetDireccion.getString("calle"), Integer.toString(resultSetDireccion.getInt("altura")), Integer.toString(resultSetDireccion.getInt("codigo_postal")), resultSetDireccion.getString("longitud"), resultSetDireccion.getString("latitud"), resultSetDireccion.getString("barrio"));
									vivienda = new Vivienda(direccion, dueño, resultSetVivienda.getInt("codigo"));
								}
							}
						}
						ArrayList<Residuo>listaResiduos = new ArrayList<>();


						Connection conn4 = ConnectionManager.getConnection();

						PreparedStatement statement5 = conn4.prepareStatement("SELECT * FROM residuos_pedido p WHERE p.codigo_pedido = ?");
						statement5.setInt(1, resultSetPedido.getInt("codigo"));
						ResultSet resultSetResiduo = statement5.executeQuery();

						while(resultSetResiduo.next()) {

							PreparedStatement statement6 = conn4.prepareStatement("SELECT * FROM residuos r WHERE r.nombre = ?");
							statement6.setString(1, resultSetResiduo.getString("nombre_residuo"));
							ResultSet resultSetTipoResiduo = statement6.executeQuery();

							if(resultSetTipoResiduo.next()) {
								tipoResiduoPedido = new TipoResiduo(resultSetTipoResiduo.getInt("puntaje"), resultSetTipoResiduo.getString("nombre"));
								residuoPedido = new Residuo(tipoResiduoPedido, resultSetResiduo.getInt("cantidad"));
								listaResiduos.add(residuoPedido);
							}

						}


						Boolean maq = false;
						if(resultSetPedido.getInt("carga") == 1) {
							maq = true;
						}
						pedido = new PedidoDeRetiro(resultSetPedido.getString("observacion"),
								maq,
								listaResiduos,
								resultSetPedido.getDate("fecha"),
								vivienda, resultSetPedido.getInt("codigo"));
	            	}
					statement = conn.prepareStatement("SELECT * FROM recolectores r" + 
							"WHERE r.dni = ?");
					statement.setString(1, resultSetOrden.getString("dniRecolector"));
					ResultSet resultSetRecolector = statement.executeQuery();
					if(resultSetRecolector.next()) {
						recolector = new Recolector(resultSetRecolector.getString("nombre"),
								resultSetRecolector.getString("apellido"),
								resultSetRecolector.getString("dni"),
								resultSetRecolector.getString("email"),
								resultSetRecolector.getString("telefono"));
					}
					statement = conn.prepareStatement("SELECT * FROM visitas v WHERE v.codigoOrden = ?");
					statement.setInt(1, resultSetOrden.getInt("codigoOrden"));
					ResultSet resultSetVisita = statement.executeQuery();
					while(resultSetVisita.next()) {
						ArrayList<Residuo> listaResiduosVisita = new ArrayList<Residuo>(); 
						Connection conn2 = ConnectionManager.getConnection();
						PreparedStatement statement2 = conn2.prepareStatement("SELECT * FROM residuos_visita rv ");
						
						ResultSet resultSetResiduosVisita = statement2.executeQuery();
						Connection conn3 = ConnectionManager.getConnection();

						while(resultSetResiduosVisita.next()){

							PreparedStatement statement3 = conn3.prepareStatement("SELECT * FROM residuos r WHERE r.nombre = ?");
							statement3.setString(1, resultSetResiduosVisita.getString("nombre_residuo"));
							ResultSet resultSetTipoResiduo = statement3.executeQuery();

							if(resultSetTipoResiduo.next()) {
								tipoResiduoVisita = new TipoResiduo(resultSetTipoResiduo.getInt("puntaje"), resultSetTipoResiduo.getString("nombre"));
								residuoVisita = new Residuo(tipoResiduoVisita, resultSetResiduosVisita.getInt("cantidad"));
								listaResiduosVisita.add(residuoVisita);
							}
						}
						visita= new Visita(resultSetVisita.getString("observacion"), listaResiduosVisita,resultSetVisita.getInt("codigoOrden"),resultSetVisita.getInt("codigo"));
						listaVisitas.add(visita);
					}
					
					estado = new Estado(resultSetOrden.getString("estado"));
					int codigoOrden = resultSetOrden.getInt("codigoOrden");
					orden = new OrdenDeRetiro(pedido, recolector, resultSetOrden.getDate("fecha"), estado, listaVisitas);
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
				PreparedStatement statement = conn.prepareStatement("SELECT * from ordenes");
				ResultSet resultSetOrden = statement.executeQuery();
				
				while(resultSetOrden.next()) {
					
					statement = conn.prepareStatement("SELECT * FROM pedidos p WHERE p.codigo = ?");
					
					statement.setInt(1, resultSetOrden.getInt("codigoPedido"));
					ResultSet resultSetPedido = statement.executeQuery();
					
					if(resultSetPedido.next()) {
						statement = conn.prepareStatement("SELECT * FROM viviendas v WHERE v.codigo = ?");
						statement.setInt(1, resultSetPedido.getInt("codigo_vivienda"));
						ResultSet resultSetVivienda = statement.executeQuery();

						if(resultSetVivienda.next()) {
							Connection conn2 = ConnectionManager.getConnection();
							PreparedStatement statement3 = conn2.prepareStatement("SELECT * FROM propietarios p WHERE p.dni = ?");
							statement3.setString(1, resultSetVivienda.getString("dni"));
							ResultSet resultSetDueño = statement3.executeQuery();

							if(resultSetDueño.next()) {

								Connection conn3 = ConnectionManager.getConnection();
								PreparedStatement statement4 = conn3.prepareStatement("SELECT * FROM dirección d WHERE d.calle = ? AND d.altura = ?");
								statement4.setString(1, resultSetVivienda.getString("calle"));
								statement4.setInt(2, resultSetVivienda.getInt("altura"));
								ResultSet resultSetDireccion = statement4.executeQuery();
								
								if(resultSetDireccion.next()) {
									dueño = new Dueño(resultSetDueño.getString("nombre") , resultSetDueño.getString("apellido") , resultSetDueño.getString("dni"), resultSetDueño.getString("correo_electronico"), resultSetDueño.getString("username"));
									direccion = new Direccion(resultSetDireccion.getString("calle"), Integer.toString(resultSetDireccion.getInt("altura")), Integer.toString(resultSetDireccion.getInt("codigo_postal")), resultSetDireccion.getString("longitud"), resultSetDireccion.getString("latitud"), resultSetDireccion.getString("barrio"));
									vivienda = new Vivienda(direccion, dueño, resultSetVivienda.getInt("codigo"));

								}
							}
						}
						ArrayList<Residuo>listaResiduos = new ArrayList<>();


						Connection conn4 = ConnectionManager.getConnection();

						PreparedStatement statement5 = conn4.prepareStatement("SELECT * FROM residuos_pedido p WHERE p.codigo_pedido = ?");
						statement5.setInt(1, resultSetPedido.getInt("codigo"));
						ResultSet resultSetResiduo = statement5.executeQuery();

						while(resultSetResiduo.next()) {

							PreparedStatement statement6 = conn4.prepareStatement("SELECT * FROM residuos r WHERE r.nombre = ?");
							statement6.setString(1, resultSetResiduo.getString("nombre_residuo"));
							ResultSet resultSetTipoResiduo = statement6.executeQuery();

							if(resultSetTipoResiduo.next()) {
								tipoResiduoPedido = new TipoResiduo(resultSetTipoResiduo.getInt("puntaje"), resultSetTipoResiduo.getString("nombre"));
								residuoPedido = new Residuo(tipoResiduoPedido, resultSetResiduo.getInt("cantidad"));
								listaResiduos.add(residuoPedido);
							}

						}


						Boolean maq = false;
						if(resultSetPedido.getInt("carga") == 1) {
							maq = true;
						}
						pedido = new PedidoDeRetiro(resultSetPedido.getString("observacion"),
								maq,
								listaResiduos,
								resultSetPedido.getDate("fecha"),
								vivienda, resultSetPedido.getInt("codigo"));
	            	}
					statement = conn.prepareStatement("SELECT * FROM recolectores r WHERE r.dni = ?");
					statement.setString(1, resultSetOrden.getString("dniRecolector"));
					ResultSet resultSetRecolector = statement.executeQuery();
					if(resultSetRecolector.next()) {
						recolector = new Recolector(resultSetRecolector.getString("nombre"),
								resultSetRecolector.getString("apellido"),
								resultSetRecolector.getString("dni"),
								resultSetRecolector.getString("email"),
								resultSetRecolector.getString("telefono"));
					}
					statement = conn.prepareStatement("SELECT * FROM visitas v WHERE v.codigoOrden = ?");
					statement.setInt(1, resultSetOrden.getInt("codigoOrden"));
					ResultSet resultSetVisita = statement.executeQuery();
					while(resultSetVisita.next()) {
						ArrayList<Residuo> listaResiduosVisita = new ArrayList<Residuo>(); 
						Connection conn2 = ConnectionManager.getConnection();
						PreparedStatement statement2 = conn2.prepareStatement("SELECT * FROM residuos_visita rv ");
						
						ResultSet resultSetResiduosVisita = statement2.executeQuery();
						Connection conn3 = ConnectionManager.getConnection();

						while(resultSetResiduosVisita.next()){

							PreparedStatement statement3 = conn3.prepareStatement("SELECT * FROM residuos r WHERE r.nombre = ?");
							statement3.setString(1, resultSetResiduosVisita.getString("nombre_residuo"));
							ResultSet resultSetTipoResiduo = statement3.executeQuery();

							if(resultSetTipoResiduo.next()) {
								tipoResiduoVisita = new TipoResiduo(resultSetTipoResiduo.getInt("puntaje"), resultSetTipoResiduo.getString("nombre"));
								residuoVisita = new Residuo(tipoResiduoVisita, resultSetResiduosVisita.getInt("cantidad"));
								listaResiduosVisita.add(residuoVisita);
							}
						}
						visita= new Visita(resultSetVisita.getString("observacion"), listaResiduosVisita,resultSetVisita.getInt("codigoOrden"),resultSetVisita.getInt("codigo"));
						listaVisitas.add(visita);
					}
					
					estado = new Estado(resultSetOrden.getString("estado"));
					int codigoOrden = resultSetOrden.getInt("codigoOrden");
					orden = new OrdenDeRetiro(pedido, recolector, resultSetOrden.getDate("fecha"), estado, listaVisitas);
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
