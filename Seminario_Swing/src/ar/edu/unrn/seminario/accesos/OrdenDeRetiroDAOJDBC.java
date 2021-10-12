package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.exceptions.AppException;
import ar.edu.unrn.seminario.modelo.OrdenDeRetiro;
import ar.edu.unrn.seminario.modelo.PedidoDeRetiro;
import ar.edu.unrn.seminario.modelo.Recolector;
import ar.edu.unrn.seminario.modelo.Residuo;
import ar.edu.unrn.seminario.modelo.Residuo_Carton;
import ar.edu.unrn.seminario.modelo.Residuo_Metal;
import ar.edu.unrn.seminario.modelo.Residuo_Plastico;
import ar.edu.unrn.seminario.modelo.Residuo_Vidrio;
import ar.edu.unrn.seminario.modelo.Vivienda;

public class OrdenDeRetiroDAOJDBC implements OrdenDeRetiroDao{


		public void create(OrdenDeRetiro o) throws Exception{
			try {
	            Connection conn = ConnectionManager.getConnection();
	            PreparedStatement statement = conn.prepareStatement
	                    ("INSERT INTO ordenes(codigoPedido, dniRecolector, calle, altura, estado, codigoOrden) "
	                            + "VALUES (?, ?, ?, ?, ?, ?)");
	            /*statement.setString(1, o.getCodigoPedido());
	            statement.setInt(2, Integer.parseInt(o.getRecolector().getDni();
	            statement.setString(3, o.getVivienda().getCalle());
	            statement.setString(4, o.getVivienda().getAltura());
	            statement.setDate(5, o.getEstado());
	            statement.setInt(6, o.getCodigoOrden());*/
	            
	            int cantidad = statement.executeUpdate();
	            if (cantidad > 0) {
	                System.out.println("Insertando " + cantidad + " registros");
	            } else {
	                System.out.println("Error al actualizar");
	                // TODO: disparar Exception propia
	            }
	        } catch (SQLException e) {
	            throw new SQLException("Error al registrar un pedido: "+e.getMessage());
	        } catch (Exception e) {
	            throw new Exception("Error al registrar un pedido: "+e.getMessage());
	        } finally {
	            ConnectionManager.disconnect();
	        }
		}

		public void update(OrdenDeRetiro pedido) {
			
		}

		public void remove(int id) {
			
		}

		public void remove(OrdenDeRetiro pedido) {
			
		}

		public OrdenDeRetiro find(int id) {
			OrdenDeRetiro orden = null;
	        Vivienda vivienda = null;
	        try {
	            Connection conn = ConnectionManager.getConnection();
	            PreparedStatement statement = conn.prepareStatement("SELECT * FROM pedidos p "+"WHERE p.codigo = ?");
	            statement.setInt(1, id );
	            ResultSet resultSetOrden = statement.executeQuery();
	            if(resultSetOrden.next()) {
	                statement = conn.prepareStatement("SELECT codigo FROM vivienda v" + 
	                            "WHERE v.calle = ? AND v.altura = ?");
	                statement.setString(1, resultSetOrden.getString("calle"));
	                statement.setInt(2, resultSetOrden.getInt("altura"));
	                ResultSet resultSetVivienda = statement.executeQuery();
	                if(resultSetVivienda.next()) {
	                    ViviendaDao viviendaDao = new ViviendaDAOJDBC();
	                    vivienda = viviendaDao.find(resultSetVivienda.getInt("codigo"));
	                }
	            }
	           
	            Boolean estado = false;
	            if(resultSetOrden.getInt("carga") == 1) {
	            	estado = true;
	            }
	            PedidoDeRetiroDao pedidoDao = new PedidoDeRetiroDAOJDBC();
	            PedidoDeRetiro pedido = pedidoDao.find(resultSetOrden.getInt("codigoPedido"));
	            
	            ViviendaDao viviendaDao = new ViviendaDAOJDBC();
	            //vivienda = viviendaDao.find(resultSetOrden.getString("calle"), resultSetOrden.getString("altura"));
	            
	            //RecolectorDao recolectorDao = new RecolectorDAOJDBC();
	            //Recolector recolector = recolectorDao.find(resultSetOrden.getString("dniRecolector"));
	            
	            //if(recolector != null) {
	            	//orden = new OrdenDeRetiro(pedido, recolector);
	            //}else {
	            	orden = new OrdenDeRetiro(pedido);
	            //}
	            //orden = new OrdenDeRetiro(null);
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
	        return orden;
	      
		}

		public List<OrdenDeRetiro> findAll() throws AppException{
			return null;
		}

		public boolean exists(int id) throws AppException{
			return false;
		}
	
}
