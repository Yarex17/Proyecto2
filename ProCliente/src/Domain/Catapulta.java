package Domain;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Catapulta extends Thread {

	private BufferedImage imagen;
	private int x, y, angulo, velocidad;
	private BarraVida vida;
	private ArrayList<Proyectil> proyectiles;
	private String direccion;
	private boolean usada;

	public Catapulta(int x, int y, String rutaImagen, String direccion) throws IOException {
		this.imagen = ImageIO.read(getClass().getResourceAsStream(rutaImagen));
		this.x = x;
		this.y = y;
		this.vida = new BarraVida(this.x, this.y - 20);
		this.proyectiles = new ArrayList<>();
		this.direccion = direccion;
		this.angulo = 20;
		this.velocidad = 100;
		this.usada = false;
	}

	public void tiempo() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.usada = false;
	}

	public void dibujarProyectiles(Graphics g) {
		for (int i = 0; i < this.proyectiles.size(); i++) {
			this.proyectiles.get(i).dibujar(g);
		}
	}

	public void actualizar() {
		for (int i = 0; i < this.proyectiles.size(); i++) {
			if (this.proyectiles.get(i).getPosX() < 0 || this.proyectiles.get(i).getPosX() > 800
					|| this.proyectiles.get(i).getPosY() < 0 || this.proyectiles.get(i).getPosY() > 600) {
				this.proyectiles.remove(i);
			} // if
		} // for
	} // actualizar

	public void dibujarCatapulta(Graphics g) {
		g.drawImage(this.imagen, this.x, this.y, null);
		this.vida.dibujarVidaCatapulta(g);
		g.drawString("Angulo: " + this.angulo, x + 20, y + 130);
		// g.drawString("Estado: " + this.usada, x, y);
	}

	public void fireRoca() {
		this.proyectiles.add(new Roca(this.angulo, this.velocidad, this.x, this.y, this.direccion));
	} // disparar

	public void firePlomo() {
		this.proyectiles.add(new ProyectilPlomo(this.angulo, this.velocidad, this.x, this.y, this.direccion));
	} // disparar

	public void fireIncendiario() {
		this.proyectiles.add(new MaterialIncendiario(this.angulo, this.velocidad, this.x, this.y, this.direccion));
	} // disparar

	public BufferedImage getImagen() {
		return imagen;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public ArrayList<Proyectil> getProyectiles() {
		return proyectiles;
	}

	public void setProyectiles(ArrayList<Proyectil> proyectiles) {
		this.proyectiles = proyectiles;
	}

	public BarraVida getVida() {
		return vida;
	}

	public void setVida(BarraVida vida) {
		this.vida = vida;
	}

	public int getAngulo() {
		return angulo;
	}

	public void setAngulo(int angulo) {
		this.angulo = angulo;
	}

	public int getVelocidad() {
		return velocidad;
	}

	public void setVelocidad(int velocidad) {
		this.velocidad = velocidad;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isUsada() {
		return usada;
	}

	public void setUsada(boolean usada) {
		this.usada = usada;
	}

} // class
