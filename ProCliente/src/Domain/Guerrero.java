package Domain;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Guerrero extends Thread {

	private int posX;
	private int posY;
	private BarraVida vida;
	private int moverGuerrero;
	private BufferedImage imagen;
	private Espada espada;
	private Arma arma;
	private String direccionGuerrero;
	private Equipo equipo;

	public Guerrero(int posX, int posY, String rutaGuerrero, Equipo equipo) throws IOException {
		this.posX = posX;
		this.posY = posY;
		this.vida = new BarraVida(posX + 10, posY - 20);
		this.imagen = ImageIO.read(getClass().getResourceAsStream(rutaGuerrero));
		this.espada = new Espada();
		this.arma = new Arma();
		this.equipo = equipo;
	}

	public void run() {
		while (true) {
			if (this.direccionGuerrero == "arriba") {
				this.posY = posY + this.moverGuerrero;
			} else {
				if (this.direccionGuerrero == "abajo") {
					this.posY = posY + this.moverGuerrero;
				} else {
					if (this.direccionGuerrero == "izquierda") {
						this.posX = posX + this.moverGuerrero;
					} else {
						if (this.direccionGuerrero == "derecha") {
							this.posX = posX + this.moverGuerrero;
						}
					}
				}
			}
			this.direccionGuerrero = "";
		}
	}

	public void dibujar(Graphics g) {
		g.drawImage(this.imagen, this.posX, this.posY, null);
		this.vida.dibujarVidaPersonaje(g, this.posX, this.posY);
		this.arma.dibujar(g);
		this.arma.actualizar();
		this.equipo.getCatapulta().dibujarProyectiles(g);
	}

	public void disparaEnergia(int posX, int posY, String nombre) {
		this.arma.fire(posX, posY, this.posX, this.posY, nombre);
	} // disparar

	public boolean colisionCastilloEnemigo(Castillo castillo) {
		for (int i = 0; i < this.equipo.getCatapulta().getProyectiles().size(); i++) {
			if (this.equipo.getCatapulta().getProyectiles().get(i).getPosX() <= castillo.getX() + 75
					&& this.equipo.getCatapulta().getProyectiles().get(i).getPosX() >= castillo.getX()
					&& this.equipo.getCatapulta().getProyectiles().get(i).getPosY() <= castillo.getY() + 200
					&& this.equipo.getCatapulta().getProyectiles().get(i).getPosY() >= castillo.getY()) {
				castillo.getVida().setVida(castillo.getVida().getVida() - this.arma.getDano());
				this.equipo.getCatapulta().getProyectiles().remove(i);
				return true;
			} // if
		} // for
		return false;
	}

	public boolean colisionCastillo(Castillo castillo) {
		for (int i = 0; i < this.arma.getBala().size(); i++) {
			if (this.arma.getBala().get(i).getPosOriginX() <= castillo.getX() + 75
					&& this.arma.getBala().get(i).getPosOriginX() >= castillo.getX()
					&& this.arma.getBala().get(i).getPosOriginY() <= castillo.getY() + 200
					&& this.arma.getBala().get(i).getPosOriginY() >= castillo.getY()) {
				if (castillo.getVida().getVida() > 0) {
					castillo.getVida().setVida(castillo.getVida().getVida() - this.arma.getDano());
					this.arma.getBala().remove(i);
				}
				return true;
			} // if
		} // for
		return false;
	} // colisionCastillo

	public void colisionRoca(ParedFortidicada paredFortidicada) {
		for (int i = 0; i < this.arma.getBala().size(); i++) {
			if (this.arma.getBala().get(i).getPosOriginX() <= paredFortidicada.getPosX() + 150
					&& this.arma.getBala().get(i).getPosOriginX() >= paredFortidicada.getPosX()
					&& this.arma.getBala().get(i).getPosOriginY() <= paredFortidicada.getPosY() + 150
					&& this.arma.getBala().get(i).getPosOriginY() >= paredFortidicada.getPosY()) {
				this.arma.getBala().remove(i);
				return;
			} // if
		} // for
	}

	public boolean colisionCatapulta(Catapulta catapulta) {
		for (int i = 0; i < this.arma.getBala().size(); i++) {
			if (this.arma.getBala().get(i).getPosOriginX() <= catapulta.getX() + 75
					&& this.arma.getBala().get(i).getPosOriginX() >= catapulta.getX()
					&& this.arma.getBala().get(i).getPosOriginY() <= catapulta.getY() + 200
					&& this.arma.getBala().get(i).getPosOriginY() >= catapulta.getY()) {
				if (catapulta.getVida().getVida() > 0) {
					catapulta.getVida().setVida(catapulta.getVida().getVida() - this.arma.getDano());
					this.arma.getBala().remove(i);
				}
				return true;
			} // if
		} // for
		return false;
	} // colisionCastillo

	public boolean colisionGuerreroEnemigoEspada(Guerrero guerrero) {
		if (this.espada.Rango(guerrero.getPosX(), guerrero.getPosY(), this.posX, this.posY)) {
			guerrero.getVida().setVida(guerrero.getVida().getVida() - this.espada.getDano());
			return true;
		}
		return false;
	} // colisionGuerreroEnemigoEspada

	public boolean colisionCatapultaEspada(Catapulta catapulta) {
		if (this.espada.Rango(catapulta.getX(), catapulta.getY(), this.posX, this.posY)) {
			catapulta.getVida().setVida(catapulta.getVida().getVida() - this.espada.getDano());
			return true;
		}
		return false;
	} // colisionCatapultaEnemigo

	public void moverGuerrero(String direccion) {
		switch (direccion) {
		case "abajo":
			this.posY += 30;
			break;
		case "arriba":
			this.posY += -30;
			break;
		case "derecha":
			this.posX += 30;
			break;
		case "izquierda":
			this.posX += -30;
			break;
		default:
			break;
		}
		this.direccionGuerrero = direccion;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public Espada getEspada() {
		return espada;
	}

	public void setEspada(Espada espada) {
		this.espada = espada;
	}

	public BufferedImage getImagen() {
		return imagen;
	}

	public BarraVida getVida() {
		return vida;
	}

	public void setVida(BarraVida vida) {
		this.vida = vida;
	}

	public Arma getArma() {
		return arma;
	}

	public void setArma(Arma arma) {
		this.arma = arma;
	}

	public Equipo getEquipo() {
		return equipo;
	}

	public void setEquipo(Equipo equipo) {
		this.equipo = equipo;
	}

}
