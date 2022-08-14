package Domain;

public class Espada {

	private int dano;

	public Espada() {
		this.dano = 5;
	}

	public boolean Rango(int posX, int posY, int posXJugador, int posYJugador) {
		if (Math.sqrt(Math.pow((posX - posXJugador), 2) + Math.pow((posY - posYJugador), 2)) <= 100) {
			return true;
		}
		return false;
	} // Rango

	public int getDano() {
		return dano;
	}

	public void setDano(int dano) {
		this.dano = dano;
	}

	public void colision(Guerrero guerrero, Guerrero guerrero2) {
		if (Rango(guerrero2.getPosX(), guerrero2.getPosY(), guerrero.getPosX(), guerrero.getPosY())) {
			guerrero.getVida().setVida(guerrero.getVida().getVida() - getDano());
		}
	}

	public void colision(Catapulta catapulta, Guerrero guerrero2) {
		if (Rango(guerrero2.getPosX(), guerrero2.getPosY(), catapulta.getX(), catapulta.getY())) {
			catapulta.getVida().setVida(catapulta.getVida().getVida() - this.dano);
		}
	}

} // class
