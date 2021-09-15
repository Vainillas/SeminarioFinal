package ar.edu.unrn.seminario.exceptions;

@SuppressWarnings("serial")
public class IncorrectEmailException extends Exception{
	public IncorrectEmailException(String message) {
		super(message);
		
	}
}
