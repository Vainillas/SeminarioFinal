package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.modelo.Direccion;

public interface DireccionDao {
	void create(Direccion direccion);

	void update(Direccion direccion);

	void remove(String calle, Integer altura);

	void remove(Direccion direccion);

	Direccion find(String calle, Integer altura);

	List<Direccion> findAll();
}
