package Business;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;
import org.jdom.JDOMException;

import Data.UsuarioData;
import Domain.Usuario;
import Utility.MetosXMLStringServidor;

public class UsuarioBuisness {

	private UsuarioData usuarioData;

	public UsuarioBuisness() throws FileNotFoundException, IOException, JDOMException {
		this.usuarioData = new UsuarioData();
	}

	public boolean registrarUsuario(Usuario usuario) throws FileNotFoundException, IOException {
		if (this.existeUsuario(usuario.getNombre())==false) {
			this.usuarioData.registrarUsuario(usuario);
			return true;
		} else {
			return false;	
		}// if
	} // registrarUsuario

	public boolean existeUsuario(String nombre, String contrasenna) {
		return this.usuarioData.existeUsuario(nombre, contrasenna);
	} // existeUsuario
	public boolean existeUsuario(String nombre ) {
		return this.usuarioData.existeUsuario(nombre);
	} // existeUsuario

	public Usuario obtenerUsuario(String nombre) {
		return this.usuarioData.obtenerUsuario(nombre);
	} // obtenerUsuario
	
	public void actualizarUsuario(Usuario usuario) throws FileNotFoundException, IOException {
		this.usuarioData.actualizarUsuario(usuario);
	} // obtenerUsuario
	
	public ArrayList<Usuario> obtenerUsuarios() throws JDOMException, IOException {
		return this.usuarioData.obtenerUsuarios();
	}

} // class
