package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.exceptions.AppException;
import ar.edu.unrn.seminario.exceptions.DataEmptyException;
import ar.edu.unrn.seminario.exceptions.DateNullException;
import ar.edu.unrn.seminario.exceptions.KilogramEmptyException;
import ar.edu.unrn.seminario.exceptions.NotNullException;
import ar.edu.unrn.seminario.exceptions.NotNumberException;
import ar.edu.unrn.seminario.exceptions.StringNullException;
import ar.edu.unrn.seminario.modelo.Direccion;
import ar.edu.unrn.seminario.modelo.Dueño;
import ar.edu.unrn.seminario.modelo.PedidoDeRetiro;
import ar.edu.unrn.seminario.modelo.Residuo;

import ar.edu.unrn.seminario.modelo.Residuo;
import ar.edu.unrn.seminario.modelo.Vivienda;


public class PedidoDeRetiroDAOJDBC implements PedidoDeRetiroDao{

		public void create(PedidoDeRetiro p) throws AppException{
			try {
	            Connection conn = ConnectionManager.getConnection();
	            PreparedStatement statement = conn.prepareStatement
	                    ("INSERT INTO pedidos(calle,altura,observacion,carga,fecha) "
	                            + "VALUES (?, ?, ?, ?, ?)");
	            statement.setString(1, p.getVivienda().getDireccion().getCalle());
	            statement.setInt(2, Integer.parseInt(p.getVivienda().getDireccion().getAltura()));
	            statement.setString(3, p.getObservacion());
	            
	            if(p.getMaquinaPesada()) {
	            	statement.setInt(4, 1);
	            }else {
	            	statement.setInt(4, 0);
	            }
	            statement.setDate(5, p.getFechaDelPedido());
	            statement.executeUpdate();
	        } catch (SQLException e) {
	            throw new AppException("Error al registrar un pedido: ");
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
	            listaResiduos.add(new Residuo(resultSetPedido.getString("vidrio"),"Vidrio" ));
	            listaResiduos.add(new Residuo(resultSetPedido.getString("plastico"),"Plástico"));
	            listaResiduos.add(new Residuo(resultSetPedido.getString("carton"),"Carton"));
	            listaResiduos.add(new Residuo(resultSetPedido.getString("metal"),"Metal"));
	            
	            
	            Boolean maq = false;
	            if(resultSetPedido.getInt("carga") == 1) {
	            	maq = true;
	            }
	            pedido = new PedidoDeRetiro(resultSetPedido.getString("observacion"),
	                    maq,
	                    listaResiduos,
	                    resultSetPedido.getDate("fecha"),
	                    vivienda);
	        } catch (SQLException | DataEmptyException | NotNullException | StringNullException | DateNullException | KilogramEmptyException | NotNumberException e) {
	        	throw new AppException("error al procesar consulta");
	        	
	           
	        }  finally {
	            ConnectionManager.disconnect();
	        }
	        return pedido;

	        //return null;

	    }
			
		public List<PedidoDeRetiro> findAll() throws AppException{
			
			List<PedidoDeRetiro> pedidos = new ArrayList<>();
			PedidoDeRetiro pedido = null;
	        Vivienda vivienda = null;
	        try {
	        	Connection conn = ConnectionManager.getConnection();
	            PreparedStatement statement = conn.prepareStatement("SELECT * FROM pedidos p ");
	            ResultSet resultSetPedido = statement.executeQuery();
	            
	            while(resultSetPedido.next()) {


	            	PreparedStatement statement2 = conn.prepareStatement("SELECT codigo FROM viviendas v WHERE v.calle = ? AND v.altura = ?");

	            	statement2.setString(1, resultSetPedido.getString("calle"));

	            	statement2.setInt(2, resultSetPedido.getInt("altura"));

	            	ResultSet resultSetVivienda = statement2.executeQuery();
	            	System.out.print("ok4");
	            	if(resultSetVivienda.next()){
	            		System.out.print("ok5");
	            		ViviendaDao viviendaDao = new ViviendaDAOJDBC();
	            		System.out.print(resultSetVivienda.getInt("codigo"));
	            		vivienda = viviendaDao.find(resultSetVivienda.getInt("codigo"));
	            		
	            		/*DueñoDao dueñoDao = new DueñoDAOJDBC();
						Dueño dueño = dueñoDao.find(resultSetVivienda.getString("dni"));
						System.out.print(resultSetVivienda.getString("dni"));
						DireccionDao direccionDao = new DireccionDAOJDBC();
						Direccion direccion = direccionDao.find(resultSetVivienda.getString("calle"),resultSetVivienda.getInt("altura"));
	            		vivienda = new Vivienda(direccion, dueño);
	            		vivienda.setID(resultSetVivienda.getInt("codigo"));*/
	            	}
	            	
	            	
	            	
	            	ArrayList<Residuo>listaResiduos = new ArrayList<>();
	            	listaResiduos.add(new Residuo(resultSetPedido.getString("vidrio"),"Vidrio" ));
	 	            listaResiduos.add(new Residuo(resultSetPedido.getString("plastico"),"Plastico"));
	 	            listaResiduos.add(new Residuo(resultSetPedido.getString("carton"),"Carton"));
	 	            listaResiduos.add(new Residuo(resultSetPedido.getString("metal"),"Metal"));
	            	Boolean maq = false;
	            	if(resultSetPedido.getInt("carga") == 1) {
	            		maq = true;
	            	}
	            	pedido = new PedidoDeRetiro(resultSetPedido.getString("observacion"),
	            			maq,
	            			listaResiduos,
	            			resultSetPedido.getDate("fecha"),
	            			vivienda);

	            	pedidos.add(pedido);
	            }
	        } catch (SQLException | DataEmptyException | NotNullException | StringNullException | DateNullException | KilogramEmptyException | NotNumberException e   ) {

				throw new AppException("Error al registrar una vivienda: ");
	        } finally {
	            ConnectionManager.disconnect();
	        }
	        return pedidos;
		}

		public boolean exists(String dni) throws AppException{
			return false;
		}

		
	
}

