package Domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

import org.jdom.Element;
import org.jdom.JDOMException;

import Business.UsuarioBuisness;
import Utility.MetosXMLStringServidor;

public class ClientServidor extends Thread {

	private PrintStream send;
	private BufferedReader receive;
	private Socket socket;
	private String lectura;
	private Usuario usuario;
	private Partida partida;
	private ListaCliente listaClientes;
	private ListaPartida partidas;
	private Judador judador;
	private boolean sesionIniciada;

	public ClientServidor(Socket socket) throws IOException {
		this.socket = socket;
		this.send = new PrintStream(this.socket.getOutputStream());
		this.receive = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		this.listaClientes = ListaCliente.obtenerInstancia();
		this.partidas = ListaPartida.obtenerInstancia();
		this.partida = new Partida("");
		this.judador = new Judador();
		this.sesionIniciada = true;
	}

	@Override
	public synchronized void run() {
		try {
			while (sesionIniciada == true) {

				this.lectura = this.receive.readLine();
				Element eInstruccion = null;
				eInstruccion = MetosXMLStringServidor.stringToXML(lectura);
				switch (eInstruccion.getAttributeValue("Accion")) {
				case "Registrar Usuario":
					UsuarioBuisness bussi=new UsuarioBuisness();
					Boolean registro=bussi.registrarUsuario(MetosXMLStringServidor.xmlToUsuario(eInstruccion.getChild("Usuario")));
				
					this.send.println(MetosXMLStringServidor
							.xmlToString(MetosXMLStringServidor.agregarInstruccionProtocolo("Inicio Sesion",
									MetosXMLStringServidor.booleanToXML(registro))));
					break;
				case "Inicio sesion":
					boolean existeCliente = false;
					for (int i = 0; i < this.listaClientes.getClientes().size(); i++) {
						if (this.listaClientes.getClientes().get(i).getUsuario().getNombre()
								.equals(eInstruccion.getChild("Usuario").getAttributeValue("Nombre"))) {
							existeCliente = true;
						}
					}
					if (new UsuarioBuisness().existeUsuario(
							eInstruccion.getChild("Usuario").getAttributeValue("Nombre"),
							eInstruccion.getChild("Usuario").getChild("Contrasena").getValue())
							&& existeCliente == false) {
						this.listaClientes.getClientes().add(this);
						this.usuario = new UsuarioBuisness()
								.obtenerUsuario(eInstruccion.getChild("Usuario").getAttributeValue("Nombre"));
						this.send.println(MetosXMLStringServidor
								.xmlToString(MetosXMLStringServidor.instruccionProtocolo("Si logueo")));
					
					} else {
						this.send.println(MetosXMLStringServidor
								.xmlToString(MetosXMLStringServidor.instruccionProtocolo("No logueo")));
					}
					
					break;
				case "Crear partida":
					this.partida = new Partida(eInstruccion.getChild("Partida").getAttributeValue("Nombre"));
					this.partidas.getPartidas().add(this.partida);
					this.partida.getClientespartida().add(this);

					for (int i = 0; i < this.listaClientes.getClientes().size(); i++) {

						this.listaClientes.getClientes().get(i).getSend()
								.println(MetosXMLStringServidor
										.xmlToString(MetosXMLStringServidor.agregarInstruccionProtocolo("Crear partida",
												MetosXMLStringServidor.partidastoXML(this.partidas.getPartidas()))));

					}

					break;
				case "Invitar":

					for (int i = 0; i < this.listaClientes.getClientes().size(); i++) {
						if (this.listaClientes.getClientes().get(i).getUsuario().getNombre()
								.equalsIgnoreCase(eInstruccion.getChild("Usuario").getAttributeValue("Nombre"))) {
							this.listaClientes.getClientes().get(i).getSend()
									.println(MetosXMLStringServidor.xmlToString(eInstruccion));

						}
					}
					break;
				case "CerrarSesion":

					for (int i = 0; i < this.listaClientes.getClientes().size(); i++) {
						if (this.listaClientes.getClientes().get(i).getUsuario().getNombre()
								.equals(this.usuario.getNombre())) {
							this.listaClientes.getClientes().remove(i);
						}
					}
					break;
				case "Lista usuario":
					ArrayList<Usuario> usuariosEnPartida = new ArrayList<>();
					for (int i = 0; i < this.partidas.getPartidas().size(); i++) {
						if (eInstruccion.getChild("Partida").getAttributeValue("Nombre")
								.equals(this.partidas.getPartidas().get(i).getNombre())) {
							for (int j = 0; j < this.partidas.getPartidas().get(i).getClientespartida().size(); j++) {
								usuariosEnPartida.add(
										this.partidas.getPartidas().get(i).getClientespartida().get(j).getUsuario());
							}
						}
					}
					for (int i = 0; i < this.partida.getClientespartida().size(); i++) {
						this.partida.getClientespartida().get(i).getSend()
								.println(MetosXMLStringServidor.xmlToString(
										MetosXMLStringServidor.agregarInstruccionProtocolo("Lista usuarios",
												MetosXMLStringServidor.xmls(usuariosEnPartida))));
					}
					break;
				case "Obtener usuarios":
					ArrayList<Usuario> usuariosRakiin = new ArrayList<>();
					for (int i = 0; i < this.partidas.getPartidas().size(); i++) {
						if (eInstruccion.getChild("Partida").getAttributeValue("Nombre")
								.equals(this.partidas.getPartidas().get(i).getNombre())) {
							for (int j = 0; j < this.partidas.getPartidas().get(i).getClientespartida().size(); j++) {
								usuariosRakiin.add(
										this.partidas.getPartidas().get(i).getClientespartida().get(j).getUsuario());
							}
						}
					}
					for (int i = 0; i < this.listaClientes.getClientes().size(); i++) {
						this.listaClientes.getClientes().get(i).getSend()
								.println(MetosXMLStringServidor.xmlToString(
										MetosXMLStringServidor.agregarInstruccionProtocolo("Obtener usuarios",
												MetosXMLStringServidor.xmls(usuariosRakiin))));
					}
					break;
				case "Obtener Partidas":
					ArrayList<Partida> listaPartidas = new ArrayList<>();
					for (int i = 0; i < this.partidas.getPartidas().size(); i++) {
						listaPartidas.add(this.partidas.getPartidas().get(i));
					}
					for (int i = 0; i < this.listaClientes.getClientes().size(); i++) {
						this.listaClientes.getClientes().get(i).getSend()
								.println(MetosXMLStringServidor.xmlToString(
										MetosXMLStringServidor.agregarInstruccionProtocolo("Lista Partidas",
												MetosXMLStringServidor.partidastoXML(listaPartidas))));
					}
					break;

				case "Aceptar Invitacion":
					String nameParitida = eInstruccion.getChild("Partida").getAttributeValue("Nombre");
					Boolean existeusuariopartida1 = false;
					Element ePartida1 = null;

					for (int i = 0; i < this.partidas.getPartidas().size(); i++) {
						if (this.partidas.getPartidas().get(i).getNombre().equals(nameParitida)) {
							for (int j = 0; j < this.partidas.getPartidas().get(i).getClientespartida().size(); j++) {
								if (this.partidas.getPartidas().get(i).getClientespartida().get(j).getUsuario()
										.getNombre().equals(usuario.getNombre())) {
									existeusuariopartida1 = true;
								}
							}

							if (this.partidas.getPartidas().get(i).getClientespartida().size() < 5
									&& !existeusuariopartida1) {
								this.partida = this.partidas.getPartidas().get(i);
								this.partida.getClientespartida().add(this);

								Boolean amigo = false;
								for (int j = 0; j < this.partida.getClientespartida().get(0).getUsuario().getAmigos()
										.size(); j++) {
									if (this.partida.getClientespartida().get(0).getUsuario().getAmigos().get(j)
											.equals(this.usuario.getNombre())) {
										amigo = true;
									}
								}
								if (!amigo) {
									this.partida.getClientespartida().get(0).getUsuario().getAmigos()
											.add(this.usuario.getNombre());

									new UsuarioBuisness()
											.actualizarUsuario(this.partida.getClientespartida().get(0).getUsuario());
									this.partida.getClientespartida().get(0)
											.setUsuario(new UsuarioBuisness().obtenerUsuario(
													this.partida.getClientespartida().get(0).getUsuario().getNombre()));
								}
							}
						}

					}

					ArrayList<Usuario> usuarios1 = new ArrayList<>();
					for (int i = 0; i < this.partida.getClientespartida().size(); i++) {
						usuarios1.add(this.partida.getClientespartida().get(i).getUsuario());
					}
					if (this.partida.getClientespartida().size() < 5) {
						Element eUsuariosEnPartida = MetosXMLStringServidor.xmls(usuarios1);
						Element eInstruccionUsuariosEnPartida = MetosXMLStringServidor
								.agregarInstruccionProtocolo("Lista usuarios", eUsuariosEnPartida);
						String stringInstruccionUsuariosEnPartidaXML = MetosXMLStringServidor
								.xmlToString(eInstruccionUsuariosEnPartida);
						for (int i = 0; i < this.partida.getClientespartida().size(); i++) {

							this.partida.getClientespartida().get(i).getSend()
									.println(stringInstruccionUsuariosEnPartidaXML);

						}
						ePartida1 = MetosXMLStringServidor.instruccionProtocolo("Unido con exito");
					} else {
						ePartida1 = MetosXMLStringServidor.instruccionProtocolo("Partida llena");
					}
					this.send.println(MetosXMLStringServidor.xmlToString(ePartida1));

					break;

				case "Unirse a partida":
					String name = eInstruccion.getChild("Partida").getAttributeValue("Nombre");
					Boolean existeusuariopartida = false;
					Element ePartida = null;

					for (int i = 0; i < this.partidas.getPartidas().size(); i++) {
						if (this.partidas.getPartidas().get(i).getNombre().equals(name)) {
							for (int j = 0; j < this.partidas.getPartidas().get(i).getClientespartida().size(); j++) {
								if (this.partidas.getPartidas().get(i).getClientespartida().get(j).getUsuario()
										.getNombre().equals(usuario.getNombre())) {
									existeusuariopartida = true;
								}
							}

							if (this.partidas.getPartidas().get(i).getClientespartida().size() < 5
									&& !existeusuariopartida) {
								this.partida = this.partidas.getPartidas().get(i);
								this.partida.getClientespartida().add(this);

							}
						}

					}

					ArrayList<Usuario> usuarios = new ArrayList<>();
					for (int i = 0; i < this.partida.getClientespartida().size(); i++) {
						usuarios.add(this.partida.getClientespartida().get(i).getUsuario());
					}
					if (this.partida.getClientespartida().size() < 5) {
						Element eUsuariosEnPartida = MetosXMLStringServidor.xmls(usuarios);
						Element eInstruccionUsuariosEnPartida = MetosXMLStringServidor
								.agregarInstruccionProtocolo("Lista usuarios", eUsuariosEnPartida);
						String stringInstruccionUsuariosEnPartidaXML = MetosXMLStringServidor
								.xmlToString(eInstruccionUsuariosEnPartida);
						for (int i = 0; i < this.partida.getClientespartida().size(); i++) {

							this.partida.getClientespartida().get(i).getSend()
									.println(stringInstruccionUsuariosEnPartidaXML);

						}
						ePartida = MetosXMLStringServidor.instruccionProtocolo("Unido con exito");
					} else {
						ePartida = MetosXMLStringServidor.instruccionProtocolo("Partida llena");
					}
					this.send.println(MetosXMLStringServidor.xmlToString(ePartida));

					break;

				case "Iniciar partida": // problema

					int numeroJugador = 0;

					Element ejugadores = new Element("Jugadores");

					for (int i = 0; i < this.partida.getClientespartida().size(); i++) {

						Element eJugador = new Element("Jugador");
						eJugador.setAttribute("Nombre",
								this.partida.getClientespartida().get(i).getUsuario().getNombre());

						Element eRuta = new Element("Ruta");
						eRuta.addContent(this.partida.getClientespartida().get(i).getUsuario().getRutaSkin());

						eJugador.addContent(eRuta);

						ejugadores.addContent(eJugador);

						this.partida.getClientespartida().get(i).getJudador().setNombre("jugador" + numeroJugador);
						this.partida.getClientespartida().get(i).getJudador()
								.setRutaSkin(this.partida.getClientespartida().get(i).getUsuario().getRutaSkin());

					} // for

					Element eInstruccionInicioPartida1 = MetosXMLStringServidor
							.agregarInstruccionProtocolo("Inicio partidas", ejugadores);
					String stringInstruccionInicioPartidaXML1 = MetosXMLStringServidor
							.xmlToString(eInstruccionInicioPartida1);
					if (this.partida.getClientespartida().size() == 2
							|| this.partida.getClientespartida().size() == 4) {
						for (int i = 0; i < this.partida.getClientespartida().size(); i++) {

							this.partida.getClientespartida().get(i).getSend()
									.println(stringInstruccionInicioPartidaXML1);
						} // for
					} // if

					break;

				case "Mover": // falta
					for (int i = 0; i < this.partida.getClientespartida().size(); i++) {
						if (this.partida.getClientespartida().get(i).getPartida().getNombre()
								.equals(this.partida.getNombre())) {
							this.partida.getClientespartida().get(i).getSend()
									.println(MetosXMLStringServidor.xmlToString(eInstruccion));
						}
					}
					break;
				case "DispararEnergia":
					for (int i = 0; i < this.partida.getClientespartida().size(); i++) {
						if (this.partida.getClientespartida().get(i).getUsuario().getNombre() != this.usuario
								.getNombre()) {

							this.partida.getClientespartida().get(i).getSend()
									.println(MetosXMLStringServidor.xmlToString(eInstruccion));

						}
					}
					break;
				case "DispararCatapulta":
					for (int i = 0; i < this.partida.getClientespartida().size(); i++) {
						this.partida.getClientespartida().get(i).getSend()
								.println(MetosXMLStringServidor.xmlToString(eInstruccion));
					}
					break;
				case "Espada":
					for (int i = 0; i < this.partida.getClientespartida().size(); i++) {
						if (this.partida.getClientespartida().get(i).getUsuario().getNombre() != this.usuario
								.getNombre()) {
							this.partida.getClientespartida().get(i).getSend()
									.println(MetosXMLStringServidor.xmlToString(eInstruccion));

						}
					}
					break;
				case "Angulo":
					for (int i = 0; i < this.partida.getClientespartida().size(); i++) {
						if (this.partida.getClientespartida().get(i).getUsuario().getNombre() != this.usuario
								.getNombre()) {
							this.partida.getClientespartida().get(i).getSend()
									.println(MetosXMLStringServidor.xmlToString(eInstruccion));

						}
					}
					break;

				case "Chat":
					if (this.partida.getChat() != null) {
						this.partida.setChat(this.partida.getChat() + "\n"
								+ eInstruccion.getChild("Jugador").getChild("Mensaje").getValue());
						Element eChat = new Element("Mensajes").setAttribute("Mensaje", this.partida.getChat());
						Element eInstruccionChat = MetosXMLStringServidor.agregarInstruccionProtocolo("Chat", eChat);
						for (int i = 0; i < this.partida.getClientespartida().size(); i++) {
							this.partida.getClientespartida().get(i).getSend()
									.println(MetosXMLStringServidor.xmlToString(eInstruccionChat));
						}
					} else {
						this.partida.setChat(eInstruccion.getChild("Jugador").getChild("Mensaje").getValue());
						Element eChat = new Element("Mensajes").setAttribute("Mensaje", this.partida.getChat());
						Element eInstruccionChat = MetosXMLStringServidor.agregarInstruccionProtocolo("Chat", eChat);
						for (int i = 0; i < this.partida.getClientespartida().size(); i++) {
							this.partida.getClientespartida().get(i).getSend()
									.println(MetosXMLStringServidor.xmlToString(eInstruccionChat));
						}
					}
					break;

				case "Gane":

					this.usuario.setCantidadVictorias(this.usuario.getCantidadVictorias() + 1);
					new UsuarioBuisness().actualizarUsuario(this.usuario);
					for (int i = 0; i < this.partidas.getPartidas().size(); i++) {
						if (this.partidas.getPartidas().get(i).getNombre().equals(this.partida.getNombre())) {
							for (int j = 0; j < this.partidas.getPartidas().get(i).getClientespartida().size(); j++) {
								this.partidas.getPartidas().get(i).getClientespartida().get(j)
										.setPartida(new Partida(""));
								this.partidas.getPartidas().get(i).getClientespartida().remove(j);
							}
							this.partidas.getPartidas().remove(i);
						}
					}
					for (int i = 0; i < this.listaClientes.getClientes().size(); i++) {
						this.listaClientes.getClientes().get(i).getSend()
								.println(MetosXMLStringServidor
										.xmlToString(MetosXMLStringServidor.agregarInstruccionProtocolo("Crear partida",
												MetosXMLStringServidor.partidastoXML(this.partidas.getPartidas()))));
					}

					break; // Obtener Usuarios
				case "Obtener Usuarios":
					Element eInstruccionUsuarios = MetosXMLStringServidor.agregarInstruccionProtocolo(
							"Obtener Usuarios",
							MetosXMLStringServidor.usuariosToXML(new UsuarioBuisness().obtenerUsuarios()));
					this.send.println(MetosXMLStringServidor.xmlToString(eInstruccionUsuarios));
					break;
				case "Cerrar":
					for (int i = 0; i < this.listaClientes.getClientes().size(); i++) {
						if (this.listaClientes.getClientes().get(i).getUsuario().getNombre()
								.equals(this.usuario.getNombre())) {
							this.listaClientes.getClientes().remove(i);
						}
					}
					this.sesionIniciada = false;
					this.socket.close();

					break;
				case "Estado":
					for (int i = 0; i < this.partida.getClientespartida().size(); i++) {
						if (this.partida.getClientespartida().get(i).getUsuario().getNombre() != this.usuario
								.getNombre()) {
							this.partida.getClientespartida().get(i).getSend()
									.println(MetosXMLStringServidor.xmlToString(eInstruccion));

						}
					}
					break;
				default:
					break;
				}
			} // while

		} catch (JDOMException e) {
			// e.printStackTrace();
		} catch (IOException e) {
			// e.printStackTrace();
		}
	}

	public void escribirACliente(String send) {
		this.send.println(send);
	}

	public PrintStream getSend() {
		return send;
	}

	public void setSend(PrintStream send) {
		this.send = send;
	}

	public Partida getPartida() {
		return partida;
	}

	public void setPartida(Partida partida) {
		this.partida = partida;
	}

	public ListaCliente getListaClientes() {
		return listaClientes;
	}

	public void setListaClientes(ListaCliente listaClientes) {
		this.listaClientes = listaClientes;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Judador getJudador() {
		return judador;
	}

	public void setJudador(Judador judador) {
		this.judador = judador;
	}

}
