package ar.edu.unrn.seminario.modelo;

import ar.edu.unrn.seminario.Helper.ConditionHelper;
import ar.edu.unrn.seminario.exceptions.DataEmptyException;
import ar.edu.unrn.seminario.exceptions.IncorrectEmailException;
import ar.edu.unrn.seminario.exceptions.NotNullException;
import ar.edu.unrn.seminario.exceptions.StateException;
import ar.edu.unrn.seminario.exceptions.StringNullException;

public class Usuario {
	private String usuario;
	private String contrasena;
	private String email;
	private Rol rol;
	

	private boolean activo;

	public Usuario(String usuario, String contrasena, String email, Rol rol) throws NotNullException,IncorrectEmailException, DataEmptyException, StringNullException{
		validarDatos(usuario, contrasena, email, rol);
		this.usuario = usuario;
		this.contrasena = contrasena;
		this.email = email;
		this.rol = rol;
		this.activo=false;
	}
	private void validarDatos(String usuario, String contrasena, String email, Rol rol) throws DataEmptyException, StringNullException, NotNullException, IncorrectEmailException {

		if(ConditionHelper.stringIsNull(usuario)) {
			
			throw new StringNullException("usuario nulo");
			}
		if(ConditionHelper.stringIsNull(contrasena)) {
			throw new StringNullException("contrasena");}
		if(ConditionHelper.IsNull(rol)) {
			throw new NotNullException("rol nula");}
		

		
		
		
		if(ConditionHelper.stringIsEmpty(usuario)) {throw new DataEmptyException("campo de usuario vacio.");}
		if(ConditionHelper.stringIsEmpty(contrasena)) {throw new DataEmptyException("Campo de password vacío.");}
		if(ConditionHelper.IsIncorrectEmail(email)) {throw new IncorrectEmailException("email incorrecto.");}
	}
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public boolean isActivo() {
		return activo;
	}

	public String obtenerEstado() {
		return isActivo() ? "ACTIVO" : "INACTIVO";
	}

	public void activar() throws StateException {
		if (!isActivo())
			this.activo = true;
		else
			throw new StateException("El usuario ya esta activo");

	}

	public void desactivar() throws StateException {
		if (isActivo())
			this.activo = false;
		else
			throw new StateException("El usuario ya esta desactivado");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Usuario [usuario=" + usuario + ", contrasena=" + contrasena + "email=" + email
				+ ", rol=" + rol + ", activo=" + activo + "]";
	}

}
