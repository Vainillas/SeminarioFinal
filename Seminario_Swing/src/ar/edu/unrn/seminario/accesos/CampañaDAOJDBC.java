package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import ar.edu.unrn.seminario.exceptions.AppException;
import ar.edu.unrn.seminario.exceptions.NotNullException;
import ar.edu.unrn.seminario.modelo.Campaña;

public class CampañaDAOJDBC implements CampañaDao{
	public void create(Campaña campaña) throws AppException{
		
	}

	public void update(Campaña campaña) {
		
	}

	public void remove() {
		
	}

	public Campaña find(int codigo) throws AppException, NotNullException{
		return null;
	}

	public List<Campaña> findAll() throws AppException, NotNullException{
		return null;
	}

	public boolean exists(int codigo) throws AppException{
		return false;
	}
}
