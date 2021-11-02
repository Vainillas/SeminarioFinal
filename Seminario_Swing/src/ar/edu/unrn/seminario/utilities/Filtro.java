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
		//System.out.println(resultado.size());
		return resultado;
	}
	public static <T> List <T> filtrar(List <T> list , Comparator comparator){
		List <T> resultado = new ArrayList<>();
		//resultado = Collections.sort(list,comparator );
		
		Collections.sort(list,comparator);
		
			/*for(int i = 1 ; i< list.size()-1;i++) {
				for(int j = 0;j<list.size();j++) {
				if(comportamiento.execute(list.get(i), list.get(j))) {
					resultado.add(list.get(i));
				}
			}*/
			
	//	}
		
		return list;
	}

}
