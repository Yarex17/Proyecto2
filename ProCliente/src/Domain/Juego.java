package Domain;

import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;

public class Juego extends Thread {

	private ArrayList<Jugador> jugadores;
	private Catapulta catapulta1, catapulta2;
	private Castillo castillo1, castillo2;
	private ParedFortidicada paredFortidicada1, paredFortidicada2;
	private int jugadoresEnPartida;
	private String nombreCliente;
	private Equipo equipo1, equipo2;

	public Juego() throws IOException {
		this.jugadores = new ArrayList<>();
		this.catapulta1 = new Catapulta(125, 400, "/Assets/Catapulta1.png", "derecha");
		this.catapulta2 = new Catapulta(565, 400, "/Assets/Catapulta2.png", "izquierda");
		this.castillo1 = new Castillo(5, 180);
		this.castillo2 = new Castillo(680, 180);
		this.equipo1 = new Equipo(castillo1, catapulta1);
		this.equipo2 = new Equipo(castillo2, catapulta2);
		this.paredFortidicada1 = new ParedFortidicada(320, 20);
		this.paredFortidicada2 = new ParedFortidicada(320, 390);
		this.nombreCliente = "";
	}

	public void dibujar(Graphics g) {
		for (int i = 0; i < this.jugadores.size(); i++) {
			this.jugadores.get(i).getGuerrero().dibujar(g);
			g.drawString(this.jugadores.get(i).getNombre(), this.jugadores.get(i).getGuerrero().getPosX() - 20,
					this.jugadores.get(i).getGuerrero().getPosY() - 20);
		}
		this.catapulta1.dibujarCatapulta(g);
		this.catapulta2.dibujarCatapulta(g);
		this.castillo1.dibujar(g);
		this.castillo2.dibujar(g);
		this.paredFortidicada1.dibujar(g);
		this.paredFortidicada2.dibujar(g);
		this.colisionCastilloBala();
		this.colisionCatapultaBala();
		this.colisionMuroRoca();
		this.colisionCastilloCatapulta();
	}

	public void colisionMuroRoca() {
		for (int i = 0; i < this.jugadores.size(); i++) {
			this.jugadores.get(i).getGuerrero().colisionRoca(this.paredFortidicada1);
			this.jugadores.get(i).getGuerrero().colisionRoca(this.paredFortidicada2);
		} // for
	}

	public void colisionCastilloCatapulta() {
		for (int i = 0; i < this.jugadores.size(); i++) {
			if (this.jugadores.get(i).getGuerrero().getEquipo() != this.equipo1) {
				if (this.jugadores.get(i).getGuerrero().colisionCastilloEnemigo(castillo1)) {
					return;
				} // if
			} else {
				if (this.jugadores.get(i).getGuerrero().colisionCastilloEnemigo(castillo2)) {
					return;
				} // if
			} // else
		} // for
	} // colisionCastilloBala

	public void colisionCastilloBala() {
		for (int i = 0; i < this.jugadores.size(); i++) {
			if (this.jugadores.get(i).getGuerrero().getEquipo().getCastillo() != this.castillo1) {
				if (this.jugadores.get(i).getGuerrero().colisionCastillo(castillo1)) {
					return;
				} // if
			} else {
				if (this.jugadores.get(i).getGuerrero().colisionCastillo(castillo2)) {
					return;
				} // if
			} // else
		} // for
	} // colisionCastilloBala

	public void colisionCatapultaBala() {
		for (int i = 0; i < this.jugadores.size(); i++) {
			if (this.jugadores.get(i).getGuerrero().getEquipo().getCatapulta() != this.catapulta1) {
				if (this.jugadores.get(i).getGuerrero().colisionCatapulta(catapulta1)) {
					return;
				} // if
			} else {
				if (this.jugadores.get(i).getGuerrero().colisionCatapulta(catapulta2)) {
					return;
				} // if
			} // else
		} // for
	} // colisionCatapultaBala

	public void colisionCatapultaEnemigoEspada(Guerrero guerrero) {
		if (guerrero.getEquipo() == this.equipo1) {
			guerrero.getEspada().colision(this.catapulta2, guerrero);
		} else {
			guerrero.getEspada().colision(this.catapulta1, guerrero);
		}
	} // colisionCatapultaBala

	public void colisionGuerreroEnemigoEspada(Guerrero guerrero) {
		if (guerrero.getEquipo() == this.equipo1) {
			for (int i = 0; i < this.jugadores.size(); i++) {
				if (this.jugadores.get(i).getGuerrero().getEquipo() != this.equipo1) {
					guerrero.getEspada().colision(this.jugadores.get(i).getGuerrero(), guerrero);
				}
			}
		} else {
			for (int i = 0; i < this.jugadores.size(); i++) {
				if (this.jugadores.get(i).getGuerrero().getEquipo() != this.equipo2) {
					guerrero.getEspada().colision(this.jugadores.get(i).getGuerrero(), guerrero);
				}
			}
		}
	} // colisionGuerreroEnemigoEspada

	public Catapulta getCatapulta1() {
		return catapulta1;
	}

	public void setCatapulta1(Catapulta catapulta1) {
		this.catapulta1 = catapulta1;
	}

	public Catapulta getCatapulta2() {
		return catapulta2;
	}

	public void setCatapulta2(Catapulta catapulta2) {
		this.catapulta2 = catapulta2;
	}

	public Castillo getCastillo1() {
		return castillo1;
	}

	public void setCastillo1(Castillo castillo1) {
		this.castillo1 = castillo1;
	}

	public Castillo getCastillo2() {
		return castillo2;
	}

	public void setCastillo2(Castillo castillo2) {
		this.castillo2 = castillo2;
	}

	public ArrayList<Jugador> getJugadores() {
		return jugadores;
	}

	public void setJugadores(ArrayList<Jugador> jugadores) {
		this.jugadores = jugadores;
	}

	public int getJugadoresEnPartida() {
		return jugadoresEnPartida;
	}

	public void setJugadoresEnPartida(int jugadoresEnPartida) {
		this.jugadoresEnPartida = jugadoresEnPartida;
	}

	public String getNombreCliente() {
		return nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	public Equipo getEquipo1() {
		return equipo1;
	}

	public void setEquipo1(Equipo equipo1) {
		this.equipo1 = equipo1;
	}

	public Equipo getEquipo2() {
		return equipo2;
	}

	public void setEquipo2(Equipo equipo2) {
		this.equipo2 = equipo2;
	}

} // class
