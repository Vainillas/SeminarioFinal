package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.Date;
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
import ar.edu.unrn.seminario.modelo.OrdenDeRetiro;
import ar.edu.unrn.seminario.modelo.PedidoDeRetiro;
import ar.edu.unrn.seminario.modelo.Recolector;
import ar.edu.unrn.seminario.modelo.Residuo;
import ar.edu.unrn.seminario.modelo.TipoResiduo;
import ar.edu.unrn.seminario.modelo.Visita;
import ar.edu.unrn.seminario.modelo.Vivienda;

public class VisitaDAOJDBC implements VisitaDao{

		public void create(Visita visita) throws AppException{
			try {
	            Connection conn = ConnectionManager.getConnection();
	            PreparedStatement statement = conn.prepareStatement
	                    ("INSERT INTO visitas(fecha, observacion, codigoOrden) "
	                            + "VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
	            
	            statement.setDate(1, visita.getFecha());
	            
	            statement.setString(2, visita.getObservacion());
	            
	            statement.setInt(3, visita.getCodOrden());
	            
	            int cantidad = statement.executeUpdate();
	            
	            if (cantidad > 0) {
	                System.out.println("Insertando " + cantidad + " registros");
	            } else {
	                System.out.println("Error al actualizar");
	                // TODO: disparar Exception propia
	            }
	            
	            ResultSet clave = statement.getGeneratedKeys();
	            clave.next();
	            int codigoVisita = clave.getInt(1);
	            clave.close(); 
	            
	            Connection conn2 = ConnectionManager.getConnection();
	            PreparedStatement statement2 = conn2.prepareStatement
	                    ("INSERT INTO residuos_visita(codigo_visita , nombre_residuo , cantidad) "
	                            + "VALUES (?, ?, ?)");
	            
	            for(int i=0; i < visita.getResiduosExtraidos().size() ; i++) {
		            statement2.setInt(1, codigoVisita);
		            //statement2.setString(2, visita.getResiduosExtraidos().get(i).getTipo().getNombre()); 
		            statement2.setString(2, visita.getResiduosExtraidos().get(i).getTipo().getNombre());
		            statement2.setInt(3, visita.getResiduosExtraidos().get(i).getCantidadKg());
		            statement2.executeUpdate();
	            }
	            
	        } catch (SQLException e) {
	            throw new AppException("Error al crear una Visita: " + e.getMessage());
	        } finally {
	            ConnectionManager.disconnect();
	        }
		}

		public void update(Visita visita) {
			
		}

		public void remove() {
			
		}

		public void remove(Visita visita) {
			
		}

		public Visita find(int codigo) throws AppException, NotNullException{
			Visita visita = null;
			
			TipoResiduo tipoResiduo = null;
			Residuo residuo = null;

			
			try {
				Connection conn = ConnectionManager.getConnection();
				PreparedStatement statement = conn.prepareStatement("SELECT * FROM visitas v "+"WHERE v.codigo = ?");
				statement.setInt(1,codigo);
				ResultSet resultSetVisita = statement.executeQuery();
				if(resultSetVisita.next()) {
					
					ArrayList<Residuo> listaResiduos = new ArrayList<Residuo>(); 
					
					Connection conn2 = ConnectionManager.getConnection();
					PreparedStatement statement2 = conn2.prepareStatement("SELECT * FROM residuos_visita rv "+"WHERE rv.codigo_visita = ?");
					statement2.setInt(1,codigo);
					ResultSet resultSetResiduosVisita = statement.executeQuery();
					
					Connection conn3 = ConnectionManager.getConnection();
					
					while(resultSetResiduosVisita.next()){
						
						PreparedStatement statement3 = conn3.prepareStatement("SELECT * FROM residuos r WHERE r.nombre = ?");
						statement3.setString(1, resultSetResiduosVisita.getString("nombre_residuo"));
						ResultSet resultSetTipoResiduo = statement3.executeQuery();

						if(resultSetTipoResiduo.next()) {
							tipoResiduo = new TipoResiduo(resultSetTipoResiduo.getInt("puntaje"), resultSetTipoResiduo.getString("nombre"));
							residuo = new Residuo(tipoResiduo, resultSetResiduosVisita.getInt("cantidad"));
							listaResiduos.add(residuo);
						}
						
					}
					
					visita= new Visita(resultSetVisita.getString("observacion"), listaResiduos,resultSetVisita.getInt("codigoOrden"),resultSetVisita.getInt("codigo"));
				}
			} catch (SQLException | DataEmptyException  e) {
				throw new AppException("Error al Buscar Visita: " + e.getMessage());
			} finally {
			ConnectionManager.disconnect();
			}
			return visita;

		}

		public List<Visita> findAll() throws AppException, IncorrectEmailException, NotNullException{
			
			ArrayList<Visita> listaVisitas = new ArrayList<Visita>(); 
			
			Visita visita = null;
			
			TipoResiduo tipoResiduo = null;
			Residuo residuo = null;

			
			try {
				Connection conn = ConnectionManager.getConnection();
				PreparedStatement statement = conn.prepareStatement("SELECT * FROM visitas v ");
				
				ResultSet resultSetVisita = statement.executeQuery();
				while(resultSetVisita.next()) {
					
					ArrayList<Residuo> listaResiduos = new ArrayList<Residuo>(); 
					
					Connection conn2 = ConnectionManager.getConnection();
					PreparedStatement statement2 = conn2.prepareStatement("SELECT * FROM residuos_visita rv ");
					
					ResultSet resultSetResiduosVisita = statement2.executeQuery();
					
					Connection conn3 = ConnectionManager.getConnection();
					
					while(resultSetResiduosVisita.next()){
						
						PreparedStatement statement3 = conn3.prepareStatement("SELECT * FROM residuos r WHERE r.nombre = ?");
						statement3.setString(1, resultSetResiduosVisita.getString("nombre_residuo"));
						ResultSet resultSetTipoResiduo = statement3.executeQuery();

						if(resultSetTipoResiduo.next()) {
							tipoResiduo = new TipoResiduo(resultSetTipoResiduo.getInt("puntaje"), resultSetTipoResiduo.getString("nombre"));
							residuo = new Residuo(tipoResiduo, resultSetResiduosVisita.getInt("cantidad"));
							listaResiduos.add(residuo);
						}
						
					}
					
					visita= new Visita(resultSetVisita.getString("observacion"), listaResiduos,resultSetVisita.getInt("codigoOrden"),resultSetVisita.getInt("codigo"));
					listaVisitas.add(visita);
				}
			} catch (SQLException | DataEmptyException  e) {
				throw new AppException("Error al Buscar Visita: " + e.getMessage());
			} finally {
			ConnectionManager.disconnect();
			}
			return listaVisitas;
		}

		public boolean exists() throws AppException{
			return false;
			
		}
	
}