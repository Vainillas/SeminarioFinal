package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.exceptions.AppException;
import ar.edu.unrn.seminario.modelo.Recolector;

public class RecolectorDAOJDBC implements RecolectorDao {
	public void create(Recolector recolector) throws Exception{
		
	}

	public void update(Recolector recolector) {
		
	}

	public void remove(String dni) {
		
	}

	public void remove(Recolector recolector) {
		
	}

	public Recolector find(String dni) {
		return null;
	}

	public List<Recolector> findAll() throws AppException{
		return null;
	}

	public boolean exists(String dni) throws AppException{
		return false;
	}
}
