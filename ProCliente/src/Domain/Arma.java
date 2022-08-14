package Domain;

import java.awt.Graphics;
import java.util.ArrayList;

public class Arma {

	private ArrayList<Bala> bala;
	private int dano;

	public Arma() {
		this.bala = new ArrayList<>();
		this.dano = 8;
	}

	public void actualizar() {
		for (int i = 0; i < this.bala.size(); i++) {
			this.bala.get(i).movimiento();
			if (this.bala.get(i).getPosOriginX() < 0 || this.bala.get(i).getPosOriginX() > 800
					|| this.bala.get(i).getPosOriginY() < 0 || this.bala.get(i).getPosOriginY() > 600) {
				this.bala.remove(i);
			} // if
		} // for
	} // actualizar

	public void fire(int posX, int posY, int posXJugador, int posYJugador, String nombre) {
		this.bala.add(new Bala(posXJugador + 15, posYJugador + 15, posX, posY, nombre));
	} // disparar

	public void dibujar(Graphics g) {
		for (int i = 0; i < this.bala.size(); i++) {
			this.bala.get(i).draw(g);
		}
	}

	public int getDano() {
		return dano;
	}

	public void setDano(int dano) {
		this.dano = dano;
	}

	public ArrayList<Bala> getBala() {
		return bala;
	}

	public void setBala(ArrayList<Bala> bala) {
		this.bala = bala;
	}

}
