package Domain;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ProyectilPlomo extends Proyectil {

	public ProyectilPlomo(int angulo, int velocidad, int x, int y, String direccion) {
		super(angulo, velocidad + 10, x, y, direccion);
		try {
			this.imagen = ImageIO.read(getClass().getResourceAsStream("/Assets/Bala.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.start();
	}

	@Override
	public int dano() {
		return 10;
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
