package Domain;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Castillo {

	private BufferedImage imagen;
	private int x;
	private int y;
	private BarraVida vida;

	public Castillo(int x, int y) throws IOException {
		this.imagen = ImageIO.read(getClass().getResourceAsStream("/Assets/Castillo.png"));
		this.x = x;
		this.y = y;
		this.vida = new BarraVida(this.x, this.y - 20);
	}

	public void dibujar(Graphics g) {
		g.drawImage(this.imagen, this.x + 13, this.y, null);
		this.vida.dibujarVidaCastillo(g);
	}

	public BufferedImage getImagen() {
		return imagen;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public BarraVida getVida() {
		return vida;
	}

	public void setVida(BarraVida vida) {
		this.vida = vida;
	}

} // class
