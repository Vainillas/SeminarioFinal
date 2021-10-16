package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.exceptions.AppException;
import ar.edu.unrn.seminario.exceptions.DateNullException;
import ar.edu.unrn.seminario.modelo.Direccion;
import ar.edu.unrn.seminario.modelo.Dueño;
import ar.edu.unrn.seminario.modelo.PedidoDeRetiro;
import ar.edu.unrn.seminario.modelo.Residuo;
import ar.edu.unrn.seminario.modelo.Residuo_Carton;
import ar.edu.unrn.seminario.modelo.Residuo_Metal;
import ar.edu.unrn.seminario.modelo.Residuo_Plastico;
import ar.edu.unrn.seminario.modelo.Residuo_Vidrio;
import ar.edu.unrn.seminario.modelo.Vivienda;


public class PedidoDeRetiroDAOJDBC implements PedidoDeRetiroDao{

		public void create(PedidoDeRetiro p) throws AppException{
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
	        	
	            throw new AppException("Error al registrar un pedido: "+e.getMessage());
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
			
		public List<PedidoDeRetiro> findAll() throws Exception{
			
			List<PedidoDeRetiro> pedidos = new ArrayList<>();
			PedidoDeRetiro pedido = null;
	        Vivienda vivienda = null;
	        try {
	        	Connection conn = ConnectionManager.getConnection();
	            PreparedStatement statement = conn.prepareStatement("SELECT p.* FROM pedidos p ");
	            ResultSet resultSetPedido = statement.executeQuery();
	            //conn.close();
	            
	            while(resultSetPedido.next()) {
	            	conn = ConnectionManager.getConnection();
	            	//PreparedStatement statement2 = conn.prepareStatement("SELECT v.codigo FROM viviendas v WHERE v.calle = ? AND v.altura = ?");
	            	PreparedStatement statement2 = conn.prepareStatement("SELECT v.codigo, v.dni FROM viviendas v WHERE v.calle = ? AND v.altura = ?");
	            	statement2.setString(1, resultSetPedido.getString("calle"));
	            	statement2.setInt(2, resultSetPedido.getInt("altura"));
	            	ResultSet resultSetVivienda = statement2.executeQuery();
	            	
            		
	            	//System.out.print("ok4");
	            	//conn.close();
	            	if(resultSetVivienda.next() /*&& resultSetDueño.next()*/){
	            		Connection conn2 = ConnectionManager.getConnection();
		            	PreparedStatement statement3 = conn2.prepareStatement("SELECT d.* FROM propietarios d WHERE d.dni = ?");
		            	statement3.setString(1, resultSetVivienda.getString("dni"));
		            	ResultSet resultSetDueño = statement3.executeQuery();
		            	
	            		if(resultSetDueño.next()) {
	            			
							Connection conn3 = ConnectionManager.getConnection();
			            	PreparedStatement statement4 = conn3.prepareStatement("SELECT d.* FROM dirección d WHERE d.calle = ? AND d.altura = ?");
			            	statement4.setString(1, resultSetPedido.getString("calle"));
			            	statement4.setInt(2, resultSetPedido.getInt("altura"));
			            	ResultSet resultSetDireccion = statement4.executeQuery();
			            	
							if(resultSetDireccion.next()) {
								
								DueñoDao dueñoDao = new DueñoDAOJDBC();
								Dueño dueño = new Dueño(resultSetDueño.getString("nombre") , resultSetDueño.getString("apellido") , resultSetVivienda.getString("dni"), resultSetDueño.getString("correo_electronico"));
								DireccionDao direccionDao = new DireccionDAOJDBC();
								Direccion direccion = new Direccion(resultSetDireccion.getString("calle"), resultSetDireccion.getString("altura"), resultSetDireccion.getString("codigo_postal"), resultSetDireccion.getString("longitud"), resultSetDireccion.getString("latitud"), resultSetDireccion.getString("barrio"));
			            		vivienda = new Vivienda(direccion, dueño);
			            		vivienda.setID(resultSetVivienda.getInt("codigo"));
			            		
			            		
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

				            	pedidos.add(pedido);
							}
							
	            		}
	            		//Connection conn2 = ConnectionManager.getConnection();
	            		//System.out.print("ok5");
	            		//ViviendaDao viviendaDao = new ViviendaDAOJDBC();
	            		
	            		
	            		//vivienda = viviendaDao.find(resultSetVivienda.getInt("codigo"));
	            		
	            		//conn = ConnectionManager.getConnection();
	            		//hacer otro select quizas? buscando el dueño y la direccion idk
	            		//vivienda.setDueño(resultSetVivienda.ge);
	            		//conn2.close();
	            		
	            	}
	            	//System.out.print("ok6");
	            	
	         
	            	/*ArrayList<Residuo>listaResiduos = new ArrayList<>();
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

	            	pedidos.add(pedido);*/
	            }
	        } catch (AppException e) {

				throw new Exception("Error al registrar una vivienda: "+e.getLocalizedMessage());
	        } finally {
	            ConnectionManager.disconnect();
	        }
	        return pedidos;
		}

		public boolean exists(String dni) throws AppException{
			return false;
		}

		
	
}

