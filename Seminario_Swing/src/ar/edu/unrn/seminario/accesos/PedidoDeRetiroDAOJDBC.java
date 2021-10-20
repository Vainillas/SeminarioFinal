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
import ar.edu.unrn.seminario.modelo.TipoResiduo;

import ar.edu.unrn.seminario.modelo.TipoResiduo;
import ar.edu.unrn.seminario.modelo.Vivienda;


public class PedidoDeRetiroDAOJDBC implements PedidoDeRetiroDao{

		public void create(PedidoDeRetiro p) throws AppException{
			try {
	            Connection conn = ConnectionManager.getConnection();
	            PreparedStatement statement = conn.prepareStatement
	                    ("INSERT INTO pedidos(calle,altura,observacion,carga,fecha) "
	                            + "VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
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
			Direccion direccion = null;

			TipoResiduo tipoResiduo = null;
			Residuo residuo = null;

			try {
				Connection conn = ConnectionManager.getConnection();
				PreparedStatement statement = conn.prepareStatement("SELECT * FROM pedidos p "+"WHERE p.codigo = ?");
				statement.setInt(1,codigo);
				ResultSet resultSetPedido = statement.executeQuery();
				if(resultSetPedido.next()) {
					statement = conn.prepareStatement("SELECT * FROM viviendas v WHERE v.calle = ? AND v.altura = ?");
					statement.setString(1, resultSetPedido.getString("calle"));
					statement.setInt(2, resultSetPedido.getInt("altura"));
					ResultSet resultSetVivienda = statement.executeQuery();
					if(resultSetVivienda.next()) {
						Connection conn2 = ConnectionManager.getConnection();
						PreparedStatement statement3 = conn2.prepareStatement("SELECT * FROM propietarios p WHERE p.dni = ?");
						statement3.setString(1, resultSetVivienda.getString("dni"));
						ResultSet resultSetDueño = statement3.executeQuery();
						if(resultSetDueño.next()) {

							Connection conn3 = ConnectionManager.getConnection();
							PreparedStatement statement4 = conn3.prepareStatement("SELECT * FROM dirección d WHERE d.calle = ? AND d.altura = ?");
							statement4.setString(1, resultSetPedido.getString("calle"));
							statement4.setInt(2, resultSetPedido.getInt("altura"));
							ResultSet resultSetDireccion = statement4.executeQuery();

							if(resultSetDireccion.next()) {

								DueñoDao dueñoDao = new DueñoDAOJDBC();
								dueño = new Dueño(resultSetDueño.getString("nombre") , resultSetDueño.getString("apellido") , resultSetDueño.getString("dni"), resultSetDueño.getString("correo_electronico"));
								DireccionDao direccionDao = new DireccionDAOJDBC();
								direccion = new Direccion(resultSetDireccion.getString("calle"), resultSetDireccion.getString("altura"), resultSetDireccion.getString("codigo_postal"), resultSetDireccion.getString("longitud"), resultSetDireccion.getString("latitud"), resultSetDireccion.getString("barrio"));
								vivienda = new Vivienda(direccion, dueño);
								vivienda.setID(resultSetVivienda.getInt("codigo"));
							}
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
									tipoResiduo = new TipoResiduo(resultSetTipoResiduo.getInt("puntaje"), resultSetTipoResiduo.getString("nombre"));
									residuo = new Residuo(tipoResiduo, resultSetResiduo.getInt("cantidad"));
									listaResiduos.add(residuo);
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
	        Direccion direccion = null;
	        
	        TipoResiduo tipoResiduo = null;
    		Residuo residuo = null;
	        
	        try {
	        	Connection conn = ConnectionManager.getConnection();
	            PreparedStatement statement = conn.prepareStatement("SELECT * FROM pedidos p ");
	            ResultSet resultSetPedido = statement.executeQuery();
	            
	            while(resultSetPedido.next()) {
	            	conn = ConnectionManager.getConnection();
	            	PreparedStatement statement2 = conn.prepareStatement("SELECT v.codigo, v.dni FROM viviendas v WHERE v.calle = ? AND v.altura = ?");
	            	statement2.setString(1, resultSetPedido.getString("calle"));
	            	statement2.setInt(2, resultSetPedido.getInt("altura"));
	            	ResultSet resultSetVivienda = statement2.executeQuery();

	            	
            		
	            	
	            	if(resultSetVivienda.next()){
	            		Connection conn2 = ConnectionManager.getConnection();
		            	PreparedStatement statement3 = conn2.prepareStatement("SELECT * FROM propietarios d WHERE d.dni = ?");
		            	statement3.setString(1, resultSetVivienda.getString("dni"));
		            	ResultSet resultSetDueño = statement3.executeQuery();
		            	
	            		if(resultSetDueño.next()) {
	            			
							Connection conn3 = ConnectionManager.getConnection();
			            	PreparedStatement statement4 = conn3.prepareStatement("SELECT * FROM dirección d WHERE d.calle = ? AND d.altura = ?");
			            	statement4.setString(1, resultSetPedido.getString("calle"));
			            	statement4.setInt(2, resultSetPedido.getInt("altura"));
			            	ResultSet resultSetDireccion = statement4.executeQuery();
			            	
							if(resultSetDireccion.next()) { 
								
								DueñoDao dueñoDao = new DueñoDAOJDBC();
								dueño = new Dueño(resultSetDueño.getString("nombre") , resultSetDueño.getString("apellido") , resultSetVivienda.getString("dni"), resultSetDueño.getString("correo_electronico"));
								DireccionDao direccionDao = new DireccionDAOJDBC();
								direccion = new Direccion(resultSetDireccion.getString("calle"), resultSetDireccion.getString("altura"), resultSetDireccion.getString("codigo_postal"), resultSetDireccion.getString("longitud"), resultSetDireccion.getString("latitud"), resultSetDireccion.getString("barrio"));
			            		vivienda = new Vivienda(direccion, dueño);
			            		vivienda.setID(resultSetVivienda.getInt("codigo"));
			            		
			            		
			            		ArrayList<Residuo>listaResiduos = new ArrayList<>();
				            	
				            	Connection conn4 = ConnectionManager.getConnection();
				            	
				            	PreparedStatement statement5 = conn3.prepareStatement("SELECT * FROM residuos_pedido p WHERE p.codigo_pedido = ?");
				            	statement5.setInt(1, resultSetPedido.getInt("codigo"));
				            	ResultSet resultSetResiduo = statement5.executeQuery();
				            	
				            	while(resultSetResiduo.next()) {
				            		
				            		PreparedStatement statement6 = conn3.prepareStatement("SELECT * FROM residuos r WHERE r.nombre = ?");
					            	statement6.setString(1, resultSetResiduo.getString("nombre_residuo"));
					            	ResultSet resultSetTipoResiduo = statement6.executeQuery();
					            	
					            	if(resultSetTipoResiduo.next()) {
					            		tipoResiduo = new TipoResiduo(resultSetTipoResiduo.getInt("puntaje"), resultSetTipoResiduo.getString("nombre"));
					            		residuo = new Residuo(tipoResiduo, resultSetResiduo.getInt("cantidad"));
					            		listaResiduos.add(residuo);
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
				            			vivienda,
				            			resultSetPedido.getInt("codigo"));
				            			

				            	pedidos.add(pedido);
							}
							
	            		}


	            	
	            	
	            }

	            	

	            	}} catch (AppException e) {


	        } catch (SQLException | DataEmptyException | NotNullException | StringNullException | DateNullException  | NotNumberException e   ) {

				throw new AppException("Error al encontrar todos los Pedidos: " + e.getMessage());
	        } finally {
	            ConnectionManager.disconnect();
	        }
	        return pedidos;
		}
	       

		public boolean exists(String dni) {
			return false;
		}

		
	
}

