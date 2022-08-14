package Domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.jdom.Element;
import org.jdom.JDOMException;

import GUI.JFInvitacion;
import GUI.JIFInvitacion;
import Utility.MetodoXMLString;

public class ClientCliente extends Thread implements SujetoObservable {

	private BufferedReader recibir;
	private PrintStream enviar;
	private Socket socket;
	private String lectura;
	private boolean logueo, unido, partidaIniciada;
	private ArrayList<Observador> observadores;
	private ArrayList<Partida> partidas;
	private ArrayList<Usuario> usuarios, usuariosRanking;
	private Juego juego;
	private String nombre;
	private String chat;
	private boolean sesionIniciada;

	public ClientCliente(String ip, int socketPortNumber) throws UnknownHostException, IOException {
		// this.juego = new Juego();
		this.socket = new Socket(ip, socketPortNumber);
		this.recibir = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		this.enviar = new PrintStream(this.socket.getOutputStream());
		this.observadores = new ArrayList<>();
		this.partidas = new ArrayList<Partida>();
		this.usuarios = new ArrayList<>();
		this.partidaIniciada = false;
		this.sesionIniciada = true;
		this.start();
	}

	public synchronized void run() {
		try {
			while (sesionIniciada == true) {
				this.lectura = this.recibir.readLine();
				/// System.out.println(lectura);
				Element eInstruccion = MetodoXMLString.stringToXML(this.lectura);
				switch (eInstruccion.getAttributeValue("Accion")) {
				
				case "Inicio Sesion":
					Element eBoolean=eInstruccion.getChild("Boolean");
if (Boolean.valueOf(eBoolean.getAttributeValue("Boolean"))) {
	JOptionPane.showMessageDialog(null, "Registro con exito");
	
}else {
	JOptionPane.showMessageDialog(null, "Registro fallido");
}
					this.notificar();
					break;
				case "Si logueo":
					this.logueo = true;
					
					this.notificar();
					break;
				case "No logueo":
					this.logueo = false;
					this.notificar();
					break;
				case "Invitar":
					Element ePartida = eInstruccion.getChild("Usuario");
					new JFInvitacion(ePartida.getChild("Partida").getValue(), this).setVisible(true);
					;
					this.notificar();
					break;
				case "Crear partida":
					List partidas = eInstruccion.getChild("Partidas").getChildren();
					this.partidas = new ArrayList<Partida>();
					for (Object object : partidas) {
						Element ePartidas = (Element) object;

						this.partidas.add(new Partida(ePartidas.getAttributeValue("Nombre")));

					} // for

					this.notificar();
					break;
				case "Unido con exito":
					this.unido = true;
					this.notificar();
					break;
				case "Partida llena":
					this.unido = false;
					this.notificar();
					break;
				case "Lista usuarios":
					this.usuarios = MetodoXMLString.xmlToUsuarios(eInstruccion);
					this.notificar();
					break;
				case "Inicio partidas": // problemas
					this.juego = new Juego();
					ArrayList<String> nombre = new ArrayList<>();
					ArrayList<String> ruta = new ArrayList<>();

					this.juego.setNombreCliente(this.nombre);

					List jugadores = eInstruccion.getChild("Jugadores").getChildren();

					for (Object object : jugadores) {
						Element eJugador = (Element) object;

						nombre.add(eJugador.getAttributeValue("Nombre"));

						ruta.add(eJugador.getChild("Ruta").getValue());

					} // for

					if (nombre.size() == 2) {
						this.juego.setJugadores(new ArrayList<>());
						this.juego.getJugadores().add(new Jugador(150, 200, ruta.get(0), this.juego.getEquipo1()));
						this.juego.getJugadores().add(new Jugador(450, 200, ruta.get(1), this.juego.getEquipo2()));
						this.juego.getJugadores().get(0).setNombre(nombre.get(0));
						this.juego.getJugadores().get(1).setNombre(nombre.get(1));
						this.juego.setJugadoresEnPartida(2);
						this.partidaIniciada = true;
						this.notificar();
					} else if (nombre.size() == 4) {
						this.juego.setJugadores(new ArrayList<>());
						this.juego.getJugadores().add(new Jugador(150, 100, ruta.get(0), this.juego.getEquipo1()));
						this.juego.getJugadores().add(new Jugador(450, 100, ruta.get(1), this.juego.getEquipo2()));
						this.juego.getJugadores().add(new Jugador(150, 300, ruta.get(2), this.juego.getEquipo1()));
						this.juego.getJugadores().add(new Jugador(450, 300, ruta.get(3), this.juego.getEquipo2()));
						this.juego.getJugadores().get(0).setNombre(nombre.get(0));
						this.juego.getJugadores().get(1).setNombre(nombre.get(1));
						this.juego.getJugadores().get(2).setNombre(nombre.get(2));
						this.juego.getJugadores().get(3).setNombre(nombre.get(3));
						this.juego.setJugadoresEnPartida(4);
						this.partidaIniciada = true;
						this.notificar();
					}

					break;
				case "Mover":
					Element element = eInstruccion.getChild("Jugador");
					String remitente11 = element.getAttributeValue("Remitente");
					for (int i = 0; i < juego.getJugadores().size(); i++) {
						if (juego.getJugadores().get(i).getNombre().equals(remitente11)) {
							juego.getJugadores().get(i).getGuerrero()
									.setPosX(Integer.parseInt(element.getChild("posX").getValue()));
							juego.getJugadores().get(i).getGuerrero()
									.setPosY(Integer.parseInt(element.getChild("posY").getValue()));
						}
					}
					break;
				case "DispararEnergia":
					Element element11 = eInstruccion.getChild("Jugador");
					String remitente111 = element11.getAttributeValue("Remitente");
					for (int i = 0; i < juego.getJugadores().size(); i++) {
						if (juego.getJugadores().get(i).getNombre().equals(remitente111)) {
							this.juego.getJugadores().get(i).getGuerrero().disparaEnergia(
									Integer.parseInt(element11.getChild("posX").getValue()),
									Integer.parseInt(element11.getChild("posY").getValue()),
									element11.getAttributeValue("Remitente"));
							break;
						}
					}

				case "Espada":
					Element element1111 = eInstruccion.getChild("Jugador");
					String remitente11111 = element1111.getAttributeValue("Remitente");
					for (int i = 0; i < this.juego.getJugadores().size(); i++) {
						if (this.juego.getJugadores().get(i).getNombre().equals(remitente11111)) {
							this.juego.colisionGuerreroEnemigoEspada(this.juego.getJugadores().get(i).getGuerrero());
							this.juego.colisionCatapultaEnemigoEspada(this.juego.getJugadores().get(i).getGuerrero());
						}
					}
					break;
				case "DispararCatapulta":
					Element element111 = eInstruccion.getChild("Jugador");
					String remitente1111 = element111.getAttributeValue("Remitente");
					for (int i = 0; i < this.juego.getJugadores().size(); i++) {
						if (this.juego.getJugadores().get(i).getNombre().equals(remitente1111)) {
							if (element111.getChild("Proyectil").getValue().equals("Roca")) {
								this.juego.getJugadores().get(i).getGuerrero().getEquipo().getCatapulta().fireRoca();
								break;
							} else if (element111.getChild("Proyectil").getValue().equals("Plomo")) {
								this.juego.getJugadores().get(i).getGuerrero().getEquipo().getCatapulta().firePlomo();
								break;
							} else if (element111.getChild("Proyectil").getValue().equals("Incendiario")) {
								this.juego.getJugadores().get(i).getGuerrero().getEquipo().getCatapulta()
										.fireIncendiario();
								break;
							}
						}
					}
					break;
				case "Angulo":
					Element element11111 = eInstruccion.getChild("Jugador");
					String remitente111111 = element11111.getAttributeValue("Remitente");
					for (int i = 0; i < this.juego.getJugadores().size(); i++) {
						if (this.juego.getJugadores().get(i).getNombre().equals(remitente111111)) {
							this.juego.getJugadores().get(i).getGuerrero().getEquipo().getCatapulta()
									.setAngulo(Integer.parseInt(element11111.getChild("Angulo").getValue()));
						}
					}
					break;

				case "Estado":

					Element eElement = eInstruccion.getChild("Jugador");
					String eRemiten = eElement.getAttributeValue("Remitente");
					for (int i = 0; i < this.juego.getJugadores().size(); i++) {
						if (this.juego.getJugadores().get(i).getNombre().equals(eRemiten)) {
							if (Boolean.valueOf(eElement.getChild("Estado").getValue())) {// pregunto si el uso de la
																							// catapulta del otro
																							// jugador es true
								if (this.juego.getJugadores().get(i).getGuerrero().getEquipo().getCatapulta()
										.isUsada()) {// si el uso actual de esta catapulta es verdadaro lo convierte en
														// falso
									this.juego.getJugadores().get(i).getGuerrero().getEquipo().getCatapulta()
											.setUsada(false);
								}
							}

						}
					}
					break;
				case "Chat":
					this.chat = eInstruccion.getChild("Mensajes").getAttributeValue("Mensaje");
					this.notificar();
					break;
				case "Obtener Usuarios":
					this.usuariosRanking = MetodoXMLString.xmlToUsuarios(eInstruccion);
					this.notificar();
					break;
				default:
					break;
				} // switch
			} // while
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JDOMException e) {
			e.printStackTrace();
		}
	} // run

	@Override
	public void agregarObservador(Observador observador) {
		this.observadores.add(observador);
	}

	@Override
	public void notificar() {
		for (int i = 0; i < this.observadores.size(); i++) {
			if (this.observadores.get(i) != null) {
				this.observadores.get(i).upDate();
			} else {
				this.observadores.remove(i);
			}
		}
	} // notificar

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean isPartidaIniciada() {
		return partidaIniciada;
	}

	public void setPartidaIniciada(boolean partidaIniciada) {
		this.partidaIniciada = partidaIniciada;
	}

	public ArrayList<Observador> getObservadores() {
		return observadores;
	}

	public void setObservadores(ArrayList<Observador> observadores) {
		this.observadores = observadores;
	}

	public ArrayList<Partida> getPartidas() {
		return partidas;
	}

	public void setPartidas(ArrayList<Partida> partidas) {
		this.partidas = partidas;
	}

	public String getLectura() {
		return lectura;
	}

	public void setLectura(String lectura) {
		this.lectura = lectura;
	}

	public BufferedReader getRecibir() {
		return recibir;
	}

	public void setRecibir(BufferedReader recibir) {
		this.recibir = recibir;
	}

	public PrintStream getEnviar() {
		return enviar;
	}

	public void setEnviar(PrintStream enviar) {
		this.enviar = enviar;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public boolean isLogueo() {
		return logueo;
	}

	public void setLogueo(boolean logueo) {
		this.logueo = logueo;
	}

	public boolean isUnido() {
		return unido;
	}

	public void setUnido(boolean unido) {
		this.unido = unido;
	}

	public ArrayList<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(ArrayList<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Juego getJuego() {
		return juego;
	}

	public void setJuego(Juego juego) {
		this.juego = juego;
	}

	public String getChat() {
		return chat;
	}

	public void setChat(String chat) {
		this.chat = chat;
	}

	public ArrayList<Usuario> getUsuariosRanking() {
		return usuariosRanking;
	}

	public void setUsuariosRanking(ArrayList<Usuario> usuariosRanking) {
		this.usuariosRanking = usuariosRanking;
	}

	public boolean isSesionIniciada() {
		return sesionIniciada;
	}

	public void setSesionIniciada(boolean sesionIniciada) {
		this.sesionIniciada = sesionIniciada;
	}

} // class
