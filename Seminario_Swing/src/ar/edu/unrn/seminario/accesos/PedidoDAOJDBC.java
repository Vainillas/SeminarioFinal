package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import ar.edu.unrn.seminario.gui.PedidoDeRetiro;
import ar.edu.unrn.seminario.modelo.Vivienda;

public class PedidoDAOJDBC implements PedidoDao {

	public void create(PedidoDeRetiro p) throws Exception {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn.prepareStatement
					("INSERT INTO pedidos(calle,altura,observacion,carga,fecha,vidrio,plastico,metal,carton) "
							+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
			statement.setString(1, p.getVivienda().getDireccion().getCalle());
			statement.setInt(2, p.getVivienda().getDireccion().getAltura());
			statement.setString(3, p.getObservacion());
			statement.setInt(4, p.getCargaPesada());
			statement.setDate(5, p.getFecha());
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
			pedido = new PedidoDeRetiro(resultSetPedido.getString("observacion"),
					resultSetPedido.getInt("carga"),
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
	}
}

