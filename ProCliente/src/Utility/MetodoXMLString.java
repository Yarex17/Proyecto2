package Utility;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import Domain.Partida;
import Domain.Usuario;

public class MetodoXMLString {

	public static String xmlToString(Element element) {
		XMLOutputter outputter = new XMLOutputter(Format.getCompactFormat());
		String xmlStringElement = outputter.outputString(element);
		xmlStringElement = xmlStringElement.replace("\n", "");
		return xmlStringElement;
	} // xmlToString

	public static Element stringToXML(String stringElement) throws JDOMException, IOException {
		SAXBuilder saxBuilder = new SAXBuilder();
		StringReader stringReader = new StringReader(stringElement);
		Document doc = saxBuilder.build(stringReader);
		return doc.getRootElement();
	} // stringToXML 

	public static Element agregarInstruccionProtocolo(String instruccion, Element element) {
		Element eInstrucccion = new Element("Instruccion");
		eInstrucccion.setAttribute("Accion", instruccion);
		eInstrucccion.addContent(element);
		return eInstrucccion;
	} // agregarInstruccionProtocolo

	public static Element instruccionProtocolo(String instruccion) {
		Element eInstrucccion = new Element("Instruccion");
		eInstrucccion.setAttribute("Accion", instruccion);
		return eInstrucccion;
	} // agregarInstruccionProtocolo
	
	public static Usuario xmltoUsuarioLoging(Element eUsuario) {
		return new Usuario(eUsuario.getAttributeValue("Nombre"), eUsuario.getChild("Contrasena").getValue());
	}

	public static Element usuarioLogingToXML(Usuario usuario) {

		Element eUsuario = new Element("Usuario");
		eUsuario.setAttribute("Nombre", usuario.getNombre());

		Element eContrasenna = new Element("Contrasena");
		eContrasenna.addContent(usuario.getContrasenna());

		eUsuario.addContent(eContrasenna);

		return eUsuario;

	}

	public static Element UsuariosToXML(ArrayList<Usuario> usuarios) {
		Element eUsuarios = new Element("Usuarios");
		for (int i = 0; i < usuarios.size(); i++) {
			eUsuarios.addContent(UsuarioToXML(usuarios.get(i)));
		}
		return eUsuarios;

	}

	public static Element UsuarioToXML(Usuario usuario) {

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
			eAmigo.addContent(usuario.getAmigos().get(i));
			eListaAmigos.addContent(eAmigo);
		}

		eUsuario.addContent(eContrasenna);
		eUsuario.addContent(eRuta);
		eUsuario.addContent(eListaAmigos);
		eUsuario.addContent(eVictorias);

		return eUsuario;
	}

	public static Usuario xmltoUsuario(Element eusuario) throws JDOMException, IOException {
		
		ArrayList<String> amigos = new ArrayList<String>();
		List listaAmigos = eusuario.getChildren();

		for (Object object : listaAmigos) {
			Element eAmigo = (Element) object;

			amigos.add(eAmigo.getChild("Amigo").getAttributeValue("Nombre"));
		}

		Usuario usuario2 = new Usuario(eusuario.getAttributeValue("Nombre"), eusuario.getChild("Contrasena").getValue(),
				eusuario.getChild("RutaSkin").getValue(),
				Integer.parseInt(eusuario.getChild("CantidadVictorias").getValue()), amigos);
		
		return usuario2;
	} // stringToXML
	
	public static Element usuarioSimpleToXML(Usuario usuario) {

		Element eUsuario = new Element("Usuario");
		eUsuario.setAttribute("Nombre", usuario.getNombre());

		Element eContrasenna = new Element("Contrasena");
		eContrasenna.addContent(usuario.getContrasenna());
		return eUsuario;
	}

	public static Element partidaToXML(Partida partida) {
		Element eUsuario = new Element("Partida");
		eUsuario.setAttribute("Nombre", partida.getNombre());
		return eUsuario;
	} // partidaToXML
	
	public static Usuario xmlToUsuario(Element eUsuario) throws JDOMException, IOException {

		ArrayList<String> amigos = new ArrayList<String>();
		
		List listaAmigos = eUsuario.getChildren("Amigos");
		for (Object object : listaAmigos) {
			Element eAmigo = (Element) object;
			if (eAmigo.getAttribute("Amigo")!=null) {
				amigos.add(eAmigo.getChild("Amigo").getAttributeValue("Nombre"));
			}
			
		}
		
		return new Usuario(eUsuario.getAttributeValue("Nombre"), eUsuario.getChild("Contrasena").getValue(),
				eUsuario.getChild("RutaSkin").getValue(),
				Integer.parseInt(eUsuario.getChild("CantidadVictorias").getValue()), amigos);
	} // xmlToUsuario

	public static ArrayList<Usuario> xmlToUsuarios(Element eUsarios) throws JDOMException, IOException {
		ArrayList<Usuario> Usuarios = new ArrayList<Usuario>();
		List listaAmigos = eUsarios.getChild("Usuarios").getChildren();
		for (Object object : listaAmigos) {
			Element eUsuario = (Element) object;
			Usuarios.add(xmlToUsuario(eUsuario));
		} // for
		return Usuarios;
	} // xmlToUsuarios

} // class
