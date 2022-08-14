package Domain;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ParedFortidicada {

	private int posX;
	private int posY;
	private BufferedImage imagen;

	public ParedFortidicada(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
		try {
			this.imagen = ImageIO.read(getClass().getResourceAsStream("/Assets/MuroRoca.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void dibujar(Graphics g) {
		g.drawImage(this.imagen, this.posX, this.posY, null);
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

	public BufferedImage getImagen() {
		return imagen;
	}

}
