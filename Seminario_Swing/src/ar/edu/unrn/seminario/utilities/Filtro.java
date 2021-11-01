package ar.edu.unrn.seminario.utilities;

import java.util.ArrayList;
import java.util.List;

public class Filtro {
	
	public static <T> List <T> filtrar(List <T> list , Predicate <T> comportamiento){
		List <T> resultado = new ArrayList<>();
		for(T t : list) {
			if(comportamiento.execute(t)) {
				resultado.add(t);
			}
		}
		//System.out.println(resultado.size());
		return resultado;
	}

}
