package utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Ordenamiento {
	public static <T> List <T> filtrar(List <T> list , Predicate <T> comportamiento){
		
		List <T> resultado = new ArrayList<>();
		resultado = resultado.stream().sorted((v1,v2)->{
			if(comportamiento.execute(v1)){
				return 1;
			}
			else {
				return -1;
			}
		}).collect(Collectors.toList());			
		for(T t : list) {
			if(comportamiento.execute(t)) {
				resultado.add(t);
			}
			
		}
		//System.out.println(resultado.size());
		return resultado;
	}
	
}
