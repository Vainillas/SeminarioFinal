package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import ar.edu.unrn.seminario.exceptions.AppException;

public class ConnectionManager {
	private static String DRIVER = "com.mysql.jdbc.Driver";
	private static String URL_DB = "jdbc:mysql://localhost:3306/";
	protected static String DB = "grsu_seminario";
	protected static String user = "root";
	protected static String pass = "";
	protected static Connection conn = null;

	public static void connect() throws AppException {
		try {
			conn = DriverManager.getConnection(URL_DB + DB, user, pass);
		} catch (SQLException sqlEx) {
			throw new AppException("no se ha podido conectar a la base de datos");
		}
	}

	public static void disconnect() {
		if (conn != null) {
			try {
				conn.close();
				conn = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void reconnect() throws AppException {
		disconnect();
		connect();
	}

	public static Connection getConnection() throws AppException {
		if (conn == null) {
			connect();
		}
		return conn;
	}

}
