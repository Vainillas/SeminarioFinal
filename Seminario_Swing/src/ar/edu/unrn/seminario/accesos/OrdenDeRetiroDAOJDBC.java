package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.exceptions.AppException;
import ar.edu.unrn.seminario.modelo.OrdenDeRetiro;
import ar.edu.unrn.seminario.modelo.PedidoDeRetiro;
import ar.edu.unrn.seminario.modelo.Recolector;
import ar.edu.unrn.seminario.modelo.TipoResiduo;
import ar.edu.unrn.seminario.modelo.Vivienda;

public class OrdenDeRetiroDAOJDBC implements OrdenDeRetiroDao{


		public void create(OrdenDeRetiro o) throws AppException{
			try {
	            Connection conn = ConnectionManager.getConnection();
	            PreparedStatement statement = conn.prepareStatement
	                    ("INSERT INTO ordenes(codigoPedido, dniRecolector, fecha, estado, codigoOrden) "
	                            + "VALUES (?, ?, ?, ?, ?, ?)");
	            
	            statement.setInt(1, o.getPedidoAsociado().getCodigo());
	            statement.setString(2, o.getRecolector().getDni());
	            statement.setDate(3, o.getFechaOrden());
	            statement.setString(4, o.getEstado().obtenerEstado());
	            statement.setInt(5, o.getCodigo()); 
	            
	            int cantidad = statement.executeUpdate();
	            if (cantidad > 0) {
	                System.out.println("Insertando " + cantidad + " registros");
	            } else {
	                System.out.println("Error al actualizar");
	                // TODO: disparar Exception propia
	            }
	        } catch (SQLException e) {
	            throw new AppException("Error al crear una Orden: ");
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

		public OrdenDeRetiro find(int id) throws AppException {
			OrdenDeRetiro orden = null;
	        PedidoDeRetiro pedido = null;
	        try {
	            Connection conn = ConnectionManager.getConnection();
	            PreparedStatement statement = conn.prepareStatement("SELECT * FROM ordenes o "+"WHERE po.codigoOrden = ?");
	            statement.setInt(1, id );
	            ResultSet resultSetOrden = statement.executeQuery();
	            if(resultSetOrden.next()) {
	                statement = conn.prepareStatement("SELECT * FROM pedido p" + 
	                            "WHERE p.codigo = ?");
	                statement.setInt(1, resultSetOrden.getInt("codigo"));
	                
	                ResultSet resultSetPedido = statement.executeQuery();
	                if(resultSetPedido.next()) {
	                    PedidoDeRetiroDao pedidoDao = new PedidoDeRetiroDAOJDBC();
	                    pedido = pedidoDao.find(resultSetPedido.getInt("codigo"));
	                }
	            }
	           
	            
	            
	            
	            
	            RecolectorDao recolectorDao = new RecolectorDAOJDBC();
	            Recolector recolector = recolectorDao.find(resultSetOrden.getString("dniRecolector"));
	            
	            Date fechaOrden = resultSetOrden.getDate("fecha");
	            
	            if(recolector != null) {
	            	orden = new OrdenDeRetiro(pedido, recolector, fechaOrden);
	            }else {
	            	orden = new OrdenDeRetiro(pedido, fechaOrden);
	            }
	        } catch (SQLException e) {
	            throw new AppException("error de la aplicacion");
	        }  finally {
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
