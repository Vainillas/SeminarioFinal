package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.modelo.Rol;
import ar.edu.unrn.seminario.modelo.Usuario;

public class TestAcceso {

	public static void main(String[] args) {
		RolDao rolDao = new RolDAOJDBC();
		List<Rol> roles = rolDao.findAll();

		for (Rol rol : roles) {
			System.out.println("LOL");
		}

		UsuarioDao usuarioDao = new UsuarioDAOJDBC();
//		Usuario usuario = new Usuario("ldifabio", "1234", "Lucas", "ldifabio@unrn.edu.ar", new Rol(1, ""));
//		usuarioDao.create(usuario);
		
//		List<Usuario> usuarios = usuarioDao.findAll();
//			for (Usuario u: usuarios) {
//			System.out.println(u);
//		}
			
//		System.out.println(usuarioDao.find("ldifabio"));
	}

}
