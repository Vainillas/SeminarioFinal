package ar.edu.unrn.seminario.utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Filtro {
	
	public static <T> List <T> filtrar(List <T> list , Predicate <T> comportamiento){
		List <T> resultado = new ArrayList<>();
		for(T t : list) {
			if(comportamiento.execute(t)) {
				resultado.add(t);
			}
		}
		return resultado;
	}
	
	public static <T> List <T> filtrar(List <T> list , Comparator comparator){
		List <T> resultado = new ArrayList<>();		
		Collections.sort(list,comparator);
		return list;
	}

}
