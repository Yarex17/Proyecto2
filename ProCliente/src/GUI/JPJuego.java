package GUI;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jdom.Element;

import Domain.ClientCliente;
import Domain.Juego;
import Utility.MetodoXMLString;

public class JPJuego extends JPanel implements KeyListener, MouseListener {

	private Juego juego;
	private ClientCliente client;
	private BufferedImage image;
	private JIFUnirseCrearPartida jifUnirseCrearPartida;
	private JDesktopPane jDesktopPane;
	private JFrame jFrame;

	public JPJuego(ClientCliente client, JIFUnirseCrearPartida jifUnirseCrearPartida, JDesktopPane jDesktopPane,
			JFrame jFrame) {
		this.setPreferredSize(new Dimension(800, 600));
		this.setFocusable(true);
		this.setRequestFocusEnabled(true);
		this.addKeyListener(this);
		this.addMouseListener(this);
		this.jFrame = jFrame;
		try {
			this.image = ImageIO.read(getClass().getResourceAsStream("/assets/FondoJuego.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.client = client;
		this.juego = this.client.getJuego();
		this.jifUnirseCrearPartida = jifUnirseCrearPartida;
		this.jDesktopPane = jDesktopPane;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(this.image, 0, 0, null);
		this.juego.dibujar(g);
		if (this.juego.getCastillo1().getVida().getVida() <= 0 || this.juego.getCastillo2().getVida().getVida() <= 0) {
			for (int i = 0; i < this.juego.getJugadores().size(); i++) {

				if (this.juego.getJugadores().get(i).getNombre().equals(this.client.getNombre())) {

					if (this.juego.getJugadores().get(i).getGuerrero().getEquipo().getCastillo().getVida()
							.getVida() > 0) {
						Element elementActual1 = new Element("Jugador").setAttribute("Remitente",
								this.client.getNombre());
						Element eInstruccion11 = MetodoXMLString.agregarInstruccionProtocolo("Gane", elementActual1);

						this.client.getEnviar().println(MetodoXMLString.xmlToString(eInstruccion11));
					}
				}
			}
			new JFJuego(client, jDesktopPane).setVisible(true);

			this.jFrame.dispose();

		}
		repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		for (int i = 0; i < this.juego.getJugadores().size(); i++) {
			if (this.juego.getNombreCliente().equals(juego.getJugadores().get(i).getNombre())
					&& juego.getJugadores().get(i).getGuerrero().getVida().getVida() > 0) {
				if (e.getKeyCode() == KeyEvent.VK_D) {
					if (this.juego.getJugadores().get(i).getGuerrero().getPosX() <= 675) {
						this.moverJugador(this.juego.getJugadores().get(i).getGuerrero().getPosX() + 30,
								this.juego.getJugadores().get(i).getGuerrero().getPosY());
					}
					return;
				} else if (e.getKeyCode() == KeyEvent.VK_A) {
					if (this.juego.getJugadores().get(i).getGuerrero().getPosX() >= 30) {
						this.moverJugador(this.juego.getJugadores().get(i).getGuerrero().getPosX() - 30,
								this.juego.getJugadores().get(i).getGuerrero().getPosY());
					}
					return;
				} else if (e.getKeyCode() == KeyEvent.VK_W) {
					if (this.juego.getJugadores().get(i).getGuerrero().getPosY() >= 30) {
						this.moverJugador(this.juego.getJugadores().get(i).getGuerrero().getPosX(),
								this.juego.getJugadores().get(i).getGuerrero().getPosY() - 30);
					}
					return;
				} else if (e.getKeyCode() == KeyEvent.VK_S) {
					if (this.juego.getJugadores().get(i).getGuerrero().getPosY() <= 380) {
						this.moverJugador(this.juego.getJugadores().get(i).getGuerrero().getPosX(),
								this.juego.getJugadores().get(i).getGuerrero().getPosY() + 30);
					}
					return;
				} else if (e.getKeyCode() == KeyEvent.VK_Z) {
					if (this.juego.getJugadores().get(i).getGuerrero().getEquipo().getCatapulta().getVida()
							.getVida() > 0) {
						if (this.juego.getJugadores().get(i).getGuerrero().getEquipo().getCatapulta().isUsada()) {
							this.dispararCatapulta("Incendiario");
						}
					}
					return;
				} else if (e.getKeyCode() == KeyEvent.VK_X) {
					if (this.juego.getJugadores().get(i).getGuerrero().getEquipo().getCatapulta().getVida()
							.getVida() > 0) {
						if (this.juego.getJugadores().get(i).getGuerrero().getEquipo().getCatapulta().isUsada()) {
							this.dispararCatapulta("Plomo");
						}
					}
					return;
				} else if (e.getKeyCode() == KeyEvent.VK_C) {
					if (this.juego.getJugadores().get(i).getGuerrero().getEquipo().getCatapulta().getVida()
							.getVida() > 0) {
						if (this.juego.getJugadores().get(i).getGuerrero().getEquipo().getCatapulta().isUsada()) {
							this.dispararCatapulta("Roca");
						}
					}
					return;
				} else if (e.getKeyCode() == KeyEvent.VK_Q) {
					this.juego.colisionGuerreroEnemigoEspada(this.juego.getJugadores().get(i).getGuerrero());
					this.juego.colisionCatapultaEnemigoEspada(this.juego.getJugadores().get(i).getGuerrero());
					this.espada();
					return;
				} else if (e.getKeyCode() == KeyEvent.VK_E) {
					if (this.juego.getJugadores().get(i).getGuerrero().getEquipo().getCatapulta().getVida()
							.getVida() > 0) {
						if (this.juego.getJugadores().get(i).getGuerrero().getEquipo().getCatapulta().getAngulo() < 40
								&& this.juego.getJugadores().get(i).getGuerrero().getEquipo().getCatapulta()
										.isUsada()) {
							this.juego.getJugadores().get(i).getGuerrero().getEquipo().getCatapulta()
									.setAngulo(this.juego.getJugadores().get(i).getGuerrero().getEquipo().getCatapulta()
											.getAngulo() + 1);
							this.angulo(this.juego.getJugadores().get(i).getGuerrero().getEquipo().getCatapulta()
									.getAngulo());

						}
					}
					return;
				} else if (e.getKeyCode() == KeyEvent.VK_R) {
					if (this.juego.getJugadores().get(i).getGuerrero().getEquipo().getCatapulta().getVida()
							.getVida() > 0) {
						if (this.juego.getJugadores().get(i).getGuerrero().getEquipo().getCatapulta().getAngulo() > 20
								&& this.juego.getJugadores().get(i).getGuerrero().getEquipo().getCatapulta()
										.isUsada()) {
							this.juego.getJugadores().get(i).getGuerrero().getEquipo().getCatapulta()
									.setAngulo(this.juego.getJugadores().get(i).getGuerrero().getEquipo().getCatapulta()
											.getAngulo() - 1);
							this.angulo(this.juego.getJugadores().get(i).getGuerrero().getEquipo().getCatapulta()
									.getAngulo());
						}
					}
					return;
				} else {
					if (e.getKeyCode() == KeyEvent.VK_CAPS_LOCK) {
						if (this.juego.getJugadores().get(i).getGuerrero().getEquipo().getCatapulta().getVida()
								.getVida() > 0) {
							if (!this.juego.getJugadores().get(i).getGuerrero().getEquipo().getCatapulta().isUsada()) {

								this.juego.getJugadores().get(i).getGuerrero().getEquipo().getCatapulta()
										.setUsada(true);
								this.usoCatapulta(this.juego.getJugadores().get(i).getGuerrero().getEquipo()
										.getCatapulta().isUsada());

							} else {
								this.juego.getJugadores().get(i).getGuerrero().getEquipo().getCatapulta()
										.setUsada(false);
							}
						} // if vida jugador
						return;
					} // if tecla

				}
				break;
			}

		}
	}

	public void usoCatapulta(Boolean estado) {
		Element elementActual1 = new Element("Jugador").setAttribute("Remitente", this.client.getNombre());
		Element eAngulo = new Element("Estado").addContent(estado + "");
		elementActual1.addContent(eAngulo);
		Element eInstruccion11 = MetodoXMLString.agregarInstruccionProtocolo("Estado", elementActual1);
		this.client.getEnviar().println(MetodoXMLString.xmlToString(eInstruccion11));
	} // espada

	public void angulo(int angulo) {
		Element elementActual1 = new Element("Jugador").setAttribute("Remitente", this.client.getNombre());
		Element eAngulo = new Element("Angulo").addContent(angulo + "");
		elementActual1.addContent(eAngulo);
		Element eInstruccion11 = MetodoXMLString.agregarInstruccionProtocolo("Angulo", elementActual1);
		this.client.getEnviar().println(MetodoXMLString.xmlToString(eInstruccion11));
	} // espada

	public void espada() {
		Element elementActual1 = new Element("Jugador").setAttribute("Remitente", this.client.getNombre());
		Element eInstruccion11 = MetodoXMLString.agregarInstruccionProtocolo("Espada", elementActual1);
		this.client.getEnviar().println(MetodoXMLString.xmlToString(eInstruccion11));
	} // espada

	public void dispararCatapulta(String tipoProyectil) {
		Element elementActual1 = new Element("Jugador").setAttribute("Remitente", this.client.getNombre());
		Element eProyectil = new Element("Proyectil").addContent(tipoProyectil);
		elementActual1.addContent(eProyectil);
		Element eInstruccion11 = MetodoXMLString.agregarInstruccionProtocolo("DispararCatapulta", elementActual1);
		this.client.getEnviar().println(MetodoXMLString.xmlToString(eInstruccion11));
	} // dispararCatapulta

	@Override
	public void mousePressed(MouseEvent e) {
		if (e != null) {
			for (int i = 0; i < this.juego.getJugadores().size(); i++) {
				if (this.juego.getNombreCliente().equals(juego.getJugadores().get(i).getNombre())
						&& this.juego.getJugadores().get(i).getGuerrero().getVida().getVida() > 0) {
					this.juego.getJugadores().get(i).getGuerrero().disparaEnergia(e.getX(), e.getY(),
							this.juego.getJugadores().get(i).getNombre());
					Element elementActual1 = new Element("Jugador").setAttribute("Remitente", this.client.getNombre());
					Element ePosX = new Element("posX").addContent(e.getX() + "");
					Element ePosY = new Element("posY").addContent(e.getY() + "");
					elementActual1.addContent(ePosX);
					elementActual1.addContent(ePosY);
					Element eInstruccion11 = MetodoXMLString.agregarInstruccionProtocolo("DispararEnergia",
							elementActual1);
					this.client.getEnviar().println(MetodoXMLString.xmlToString(eInstruccion11));
				}
			}
		} // if
	}

	private void moverJugador(int x, int y) {
		Element elementActual = new Element("Jugador").setAttribute("Remitente", this.client.getNombre());
		Element ePosX = new Element("posX").addContent(x + "");
		Element ePosY = new Element("posY").addContent(y + "");
		elementActual.addContent(ePosX);
		elementActual.addContent(ePosY);

		Element eInstruccion11 = MetodoXMLString.agregarInstruccionProtocolo("Mover", elementActual);
		this.client.getEnviar().println(MetodoXMLString.xmlToString(eInstruccion11));
	} // moverJugador

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public ClientCliente getClient() {
		return client;
	}

	public void setClient(ClientCliente client) {
		this.client = client;
	}

}
