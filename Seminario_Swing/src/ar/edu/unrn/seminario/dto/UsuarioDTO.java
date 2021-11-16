package ar.edu.unrn.seminario.dto;

import ar.edu.unrn.seminario.modelo.Rol;

public class UsuarioDTO {
	private String username;
	private String password;

	private String email;
	private Rol rol;
	private boolean activo;
	private String estado;

	public UsuarioDTO(String username, String password, String email, Rol rol, boolean activo,
			String estado) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.rol = rol;
		this.activo = activo;
		this.estado = estado;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

}