package ar.edu.unrn.seminario.modelo;

import ar.edu.unrn.seminario.Helper.ConditionHelper;
import ar.edu.unrn.seminario.exceptions.DataEmptyException;
import ar.edu.unrn.seminario.exceptions.IncorrectEmailException;
import ar.edu.unrn.seminario.exceptions.StringNullException;

public class UsuarioIngreso  {
	private String user;
	private String password;
	public UsuarioIngreso(String user, String password)throws DataEmptyException, StringNullException, DataEmptyException, IncorrectEmailException {
		validarDatos(user,password);
		this.user = user;
		this.password = password;
		
	}
	public void validarDatos(String user, String password) throws IncorrectEmailException, DataEmptyException, StringNullException{
		
		if(ConditionHelper.stringIsEmpty(user)) { throw new DataEmptyException("nombre de usuario vacio");}
		if(ConditionHelper.stringIsNull(user)) {throw new StringNullException ("nombre de usuario nulo");}
		if(ConditionHelper.stringIsEmpty(password)) { throw new DataEmptyException("password vacia");}
		if(ConditionHelper.stringIsNull(password)) { throw new StringNullException("password null");}
		
 	}
	public String getUser() {
		return this.user;
	}
	public String getPassword() {
		return this.password;
	}

}
