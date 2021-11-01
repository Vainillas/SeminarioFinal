package ar.edu.unrn.seminario.utilities;

public interface  OrderingPredicate <T> {
	boolean execute(T t, T t1);
}
