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

public class MetosXMLStringServidor {
	
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

	public static Element usuariosToXML(ArrayList<Usuario> usuarios) {

		Element root = new Element("Usuarios");

		for (int i = 0; i < usuarios.size(); i++) {

			Element eUsuario = new Element("Usuario");
			eUsuario.setAttribute("Nombre", usuarios.get(i).getNombre());

			Element eContrasenna = new Element("Contrasena");
			eContrasenna.addContent(usuarios.get(i).getContrasenna());

			Element eRuta = new Element("RutaSkin");
			eRuta.addContent(usuarios.get(i).getRutaSkin());

			Element eVictorias = new Element("CantidadVictorias");
			eVictorias.addContent(usuarios.get(i).getCantidadVictorias() + "");

			Element eListaAmigos = new Element("Amigos");
			for (int j = 0; j < usuarios.get(i).getAmigos().size(); j++) {
				Element eAmigo = new Element("Amigo");
				eAmigo.addContent(usuarios.get(i).getAmigos().get(i));
				eListaAmigos.addContent(eAmigo);
			}

			eUsuario.addContent(eContrasenna);
			eUsuario.addContent(eRuta);
			eUsuario.addContent(eVictorias);
			eUsuario.addContent(eListaAmigos);

			root.addContent(eUsuario);

		} // root
		return root;
	} // usuariosToXML

	public static Element usuarioToXML(Usuario usuario) {
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
	} // usuarioToXML

	public static Usuario xmlToUsuario(Element eUsuario) throws JDOMException, IOException {

		ArrayList<String> amigos = new ArrayList<String>();
		List listaAmigos = eUsuario.getChildren();

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
	
	public static Element xmls(ArrayList<Usuario>usuarios) {
		Element element = new Element("Usuarios");
		for (int i = 0; i < usuarios.size(); i++) {
			element.addContent(usuarioToXML(usuarios.get(i)));
		}
		return element;
		
	}
	
	public static Element partidaToXML(Partida partida) {
		Element eUsuario = new Element("Partida");
		eUsuario.setAttribute("Nombre", partida.getNombre());
		return eUsuario;
	} // partidaToXML
	public static Element partidastoXML(ArrayList<Partida> usuarios) {
		Element element = new Element("Partidas");
		for (int i = 0; i < usuarios.size(); i++) {
			element.addContent(partidaToXML(usuarios.get(i)));
		}
		return element;
		
	}
	
	public static Element booleanToXML(boolean d) {
		Element element = new Element("Boolean");
		element.setAttribute("Boolean",d+"");
		return element;
		
	}

} // fin clase
