package Data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import Domain.Usuario;
import Utility.MetosXMLStringServidor;
import Utility.Rutas;

public class UsuarioData {
	private Document document;
	private Element root;

	public UsuarioData() throws FileNotFoundException, IOException, JDOMException {
		File archivo = new File(Rutas.RUTAUSUARIOS);
		if (archivo.exists()) {
			SAXBuilder saxBuilder = new SAXBuilder();
			saxBuilder.setIgnoringElementContentWhitespace(true);
			this.document = saxBuilder.build(Rutas.RUTAUSUARIOS);
			this.root = this.document.getRootElement();
		} else {
			this.root = new Element("Usuarios");
			this.document = new Document(this.root);
			guardarXML();
		}
	}

	private void guardarXML() throws FileNotFoundException, IOException {
		XMLOutputter xmlOutputter = new XMLOutputter();
		xmlOutputter.output(this.document, new PrintWriter(Rutas.RUTAUSUARIOS));
	} // guardarX

	public boolean registrarUsuario(Usuario usuario) throws FileNotFoundException, IOException {

		Element eUsuario = new Element("Usuario");
		eUsuario.setAttribute("Nombre", usuario.getNombre());

		Element eContrasenna = new Element("Contrasena");
		eContrasenna.addContent(usuario.getContrasenna());

		Element eRuta = new Element("RutaSkin");
		eRuta.addContent(usuario.getRutaSkin());

		Element eVictorias = new Element("CantidadVictorias");
		eVictorias.addContent(usuario.getCantidadVictorias() + "");

		Element eListaAmigos = new Element("Amigos");
		for (int i = 0; i < usuario.getAmigos().size(); i++) {
			Element eAmigo = new Element("Amigo");
			eAmigo.setAttribute("Nombre",usuario.getAmigos().get(i));
			eListaAmigos.addContent(eAmigo);
		}

		eUsuario.addContent(eContrasenna);
		eUsuario.addContent(eRuta);
		eUsuario.addContent(eVictorias);
		eUsuario.addContent(eListaAmigos);

		this.root.addContent(eUsuario);
		this.guardarXML();

		return true;
	} // registrarUsuario

	public boolean existeUsuario(String nombre, String constrasena) {
		List usuariolist = this.root.getChildren();
		for (Object object : usuariolist) {
			Element eAcutual = (Element) object;
			if (nombre.equalsIgnoreCase(eAcutual.getAttributeValue("Nombre"))
					&& constrasena.equals(eAcutual.getChild("Contrasena").getValue())) {
				return true;
			}
		}
		return false;
	} // existeUsuario
	
	public boolean existeUsuario(String nombre) {
		List usuariolist = this.root.getChildren();
		for (Object object : usuariolist) {
			Element eAcutual = (Element) object;
			if (nombre.equalsIgnoreCase(eAcutual.getAttributeValue("Nombre"))) {
				return true;
			}
		}
		return false;
	} // existeUsuario
	
	public ArrayList<Usuario> obtenerUsuarios() throws JDOMException, IOException {
		ArrayList<Usuario> usuarios = new ArrayList<>();
		List usuariolist = this.root.getChildren();
		for (Object object : usuariolist) {
			Element eAcutual = (Element) object;
			usuarios.add(MetosXMLStringServidor.xmlToUsuario(eAcutual));
		}
		return usuarios;
	}

	public Usuario obtenerUsuario(String nombre) {
		Usuario usuario = null;
		List usuariolist = this.root.getChildren();
		for (Object object : usuariolist) {
			Element eAcutual = (Element) object;
			if (eAcutual.getAttributeValue("Nombre").equalsIgnoreCase(nombre)) {
				List listaElementos = eAcutual.getChild("Amigos").getChildren();

				ArrayList<String> amigos = new ArrayList<>();

				for (Object objetoActual : listaElementos) {
					Element elementoActual = (Element) objetoActual;
					if (elementoActual.getChild("Amigo")!=null) {
					amigos.add(elementoActual.getAttributeValue("Nombre"));
					}
					
					
				} // for
				return new Usuario(eAcutual.getAttributeValue("Nombre"), eAcutual.getChild("Contrasena").getValue(),
						eAcutual.getChild("RutaSkin").getValue(),
						Integer.parseInt(eAcutual.getChild("CantidadVictorias").getValue()), amigos);
			}
		}
		return usuario;
	} // obtenerUsuario

	public void actualizarUsuario(Usuario usuario) throws FileNotFoundException, IOException {
		List usuariolist = this.root.getChildren();
		for (Object object : usuariolist) {
			Element eAcutual = (Element) object;
			if (eAcutual.getAttributeValue("Nombre").equalsIgnoreCase(usuario.getNombre())) {
				this.root.removeContent(eAcutual);
				this.root.addContent(MetosXMLStringServidor.usuarioToXML(usuario));
				this.guardarXML();
				break;
			}
		}
	} // obtenerUsuario

} // class
