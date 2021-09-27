package ar.edu.unrn.seminario.accesos;

import java.sql.SQLException;
import java.util.List;

import ar.edu.unrn.seminario.modelo.Vivienda;

public interface ViviendaDao {
	void create(Vivienda v) throws SQLException, Exception;

	void update(Vivienda v);

	void remove(Vivienda v);

	Vivienda find(Integer codigo);

	List<Vivienda> findAll();

}
