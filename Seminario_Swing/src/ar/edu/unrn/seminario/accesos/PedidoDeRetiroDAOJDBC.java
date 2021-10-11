package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.exceptions.AppException;
import ar.edu.unrn.seminario.modelo.PedidoDeRetiro;
import ar.edu.unrn.seminario.modelo.Residuo;
import ar.edu.unrn.seminario.modelo.Residuo_Carton;
import ar.edu.unrn.seminario.modelo.Residuo_Metal;
import ar.edu.unrn.seminario.modelo.Residuo_Plastico;
import ar.edu.unrn.seminario.modelo.Residuo_Vidrio;
import ar.edu.unrn.seminario.modelo.Vivienda;

public class PedidoDeRetiroDAOJDBC implements PedidoDeRetiroDao{

		public void create(PedidoDeRetiro p) throws Exception{
			try {
	            Connection conn = ConnectionManager.getConnection();
	            PreparedStatement statement = conn.prepareStatement
	                    ("INSERT INTO pedidos(calle,altura,observacion,carga,fecha,vidrio,plastico,metal,carton) "
	                            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
	            statement.setString(1, p.getVivienda().getDireccion().getCalle());
	            statement.setInt(2, Integer.parseInt(p.getVivienda().getDireccion().getAltura()));
	            statement.setString(3, p.getObservacion());
	            
	            if(p.getMaquinaPesada()) {
	            	statement.setInt(4, 1);
	            }else {
	            	statement.setInt(4, 0);
	            }
	            
	            statement.setDate(5, p.getFechaDelPedido());
	            statement.setInt(6, p.getVidrio());
	            statement.setInt(7, p.getPlastico());
	            statement.setInt(8, p.getMetal());
	            statement.setInt(9, p.getCarton());
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

		public void update(PedidoDeRetiro pedido) {
			
		}

		public void remove(String dni) {
		
		}
		
		public void remove(PedidoDeRetiro pedido) {
			
		}

		public PedidoDeRetiro find(int codigo) {
	        PedidoDeRetiro pedido = null;
	        Vivienda vivienda = null;
	        try {
	            Connection conn = ConnectionManager.getConnection();
	            PreparedStatement statement = conn.prepareStatement("SELECT * FROM pedidos p "+"WHERE p.codigo = ?");
	            statement.setInt(1,codigo);
	            ResultSet resultSetPedido = statement.executeQuery();
	            if(resultSetPedido.next()) {
	                statement = conn.prepareStatement("SELECT codigo FROM vivienda v" + 
	                            "WHERE v.calle = ? AND v.altura = ?");
	                statement.setString(1, resultSetPedido.getString("calle"));
	                statement.setInt(2, resultSetPedido.getInt("altura"));
	                ResultSet resultSetVivienda = statement.executeQuery();
	                if(resultSetVivienda.next()) {
	                    ViviendaDao viviendaDao = new ViviendaDAOJDBC();
	                    vivienda = viviendaDao.find(resultSetVivienda.getInt("codigo"));
	                }
	            }
	            ArrayList<Residuo>listaResiduos = new ArrayList<>();
	            Residuo vidrio = new Residuo_Vidrio(resultSetPedido.getInt("vidrio"));
	            Residuo metal = new Residuo_Metal(resultSetPedido.getInt("metal"));
	            Residuo carton = new Residuo_Carton(resultSetPedido.getInt("carton"));
	            Residuo plastico = new Residuo_Plastico(resultSetPedido.getInt("plastico"));
	            listaResiduos.add(vidrio);
	            listaResiduos.add(metal);
	            listaResiduos.add(carton);
	            listaResiduos.add(plastico);
	            Boolean maq = false;
	            if(resultSetPedido.getInt("carga") == 1) {
	            	maq = true;
	            }
	            pedido = new PedidoDeRetiro(resultSetPedido.getString("observacion"),
	                    maq,
	                    listaResiduos,
	                    resultSetPedido.getDate("fecha"),
	                    vivienda);
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
	        return pedido;
	        //return null;
	    }
			
	

		public List<PedidoDeRetiro> findAll() throws AppException{
			return null;
		}

		public boolean exists(String dni) throws AppException{
			return false;
		}

		
	
}

