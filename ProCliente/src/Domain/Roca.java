package Domain;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Roca extends Proyectil {

	public Roca(int angulo, int velocidad, int x, int y, String direccion) {
		super(angulo, velocidad, x, y, direccion);
		try {
			this.imagen = ImageIO.read(getClass().getResourceAsStream("/Assets/Piedra.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.start();
	}

	@Override
	public int dano() {
		return 15;
	}

	public String getDireccion() {
		return direccion;
	}

	public double getPosX() {
		return posX;
	}

	public double getPosY() {
		return posY;
	}

	public void setDisparado(boolean disparado) {
		this.disparado = disparado;
	}

	public BufferedImage getImagen() {
		return imagen;
	}

}
