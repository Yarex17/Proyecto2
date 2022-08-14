package Domain;

import java.awt.Color;
import java.awt.Graphics;

public class BarraVida {

	private int posX;
	private int posY;
	private int vida;

	public BarraVida(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
		this.vida = 100;
	}

	public void dibujarVidaCatapulta(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(this.posX, this.posY + 120, 100, 15);

		g.setColor(Color.green);
		g.fillRect(this.posX, this.posY + 120, this.vida, 15);

		g.setColor(Color.black);
		g.drawRect(this.posX, this.posY + 120, 100, 15);

		g.setColor(Color.black);
		g.drawString(String.valueOf(this.vida), this.posX + 42, this.posY + 133);
	}

	public void dibujarVidaCastillo(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(this.posX, this.posY, 100, 15);

		g.setColor(Color.green);
		g.fillRect(this.posX, this.posY, this.vida, 15);

		g.setColor(Color.black);
		g.drawRect(this.posX, this.posY, 100, 15);

		g.setColor(Color.black);
		g.drawString(String.valueOf(this.vida), this.posX + 42, this.posY + 12);
	}

	public void dibujarVidaPersonaje(Graphics g, int posX, int posY) {
		g.setColor(Color.red);
		g.fillRect(posX - 8, posY + 100, 100, 15);

		g.setColor(Color.green);
		g.fillRect(posX - 8, posY + 100, this.vida, 15);

		g.setColor(Color.black);
		g.drawRect(posX - 8, posY + 100, 100, 15);

		g.setColor(Color.black);
		g.drawString(String.valueOf(this.vida), posX + 33, posY + 113);
	}

	public void setVida(int vida) {
		if (vida <= 0) {
			this.vida = 0;
		} else {
			this.vida = vida;
		}
	}

	public int getVida() {
		return vida;
	}

} // class
