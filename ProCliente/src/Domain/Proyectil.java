package Domain;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Proyectil extends Thread {

	protected double angulo;
	protected double velocidad;
	protected double tiempo;
	protected double xReal;
	protected double yReal;
	protected boolean disparado;
	protected double posX;
	protected double posY;
	protected int xInicio;
	protected int yInicio;
	protected String direccion;
	protected BufferedImage imagen;

	protected double velocidadInicialX;
	protected double velocidadInicialY;
	protected int dano;

	public Proyectil(int angulo, int velocidad, int x, int y, String direccion) {
		this.angulo = angulo;
		this.velocidad = velocidad;
		this.tiempo = 0;
		this.posX = x;
		this.posY = y;
		this.xInicio = x;
		this.yInicio = y;
		this.xReal = this.posX;
		this.yReal = 900 - y;
		this.direccion = direccion;

		this.velocidadInicialX = this.velocidad * Math.cos(Math.toRadians(this.angulo));// ese angulo tiene que estar en
																						// radianes
		this.velocidadInicialY = this.velocidad * Math.sin(Math.toRadians(this.angulo));// ese angulo tiene que estar en
																						// radianes
		this.disparado = true;
	}

	public void dibujar(Graphics g) {
		g.drawImage(this.imagen, (int) this.posX, (int) this.posY, null);
	}

	public abstract int dano();

	public void actualizar() {
		if (this.direccion.equals("derecha")) {
			this.xReal = (this.xInicio + this.velocidadInicialX * this.tiempo);
			this.yReal = (this.yInicio + this.velocidadInicialY * this.tiempo
					+ (-9.8 * (this.tiempo * this.tiempo)) / 2);// el 9.8 es la gravedad del planeta el tiempo se
																// eleva al cuadrado, ese 2 es lo mismo que el 1/2
		} else {
			this.xReal = (this.xInicio - this.velocidadInicialX * this.tiempo);
			this.yReal = (this.yInicio + this.velocidadInicialY * this.tiempo
					+ (-9.8 * (this.tiempo * this.tiempo)) / 2);// el 9.8 es la gravedad del planeta el tiempo se
																// eleva al cuadrado, ese 2 es lo mismo que el 1/2
		}

		this.posX = this.xReal;
		this.posY = 800 - this.yReal;
	}

	public void run() {
		while (this.disparado) {
			this.actualizar();
			try {
				Thread.sleep(10);
				this.tiempo += 0.05;
			} catch (InterruptedException ex) {
				Logger.getLogger(Proyectil.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
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
