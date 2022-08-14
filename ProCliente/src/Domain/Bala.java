package Domain;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Bala {

	private double posOriginX, posOriginY, posEndX, posEndY, m, b, velocity;
	private BufferedImage image;
	private String nombre;

	public Bala(double posOriginX, double posOriginY, double posEndX, double posEndY, String nombre) {
		this.posOriginX = posOriginX;
		this.posOriginY = posOriginY;
		this.posEndX = posEndX;
		this.posEndY = posEndY;
		this.m = (this.posEndY - this.posOriginY) / (this.posEndX - this.posOriginX);
		this.b = (this.posOriginY - (this.m * this.posOriginX));
		this.velocity = 1;
		this.nombre = nombre;
		try {
			this.image = ImageIO.read(getClass().getResourceAsStream(""));
		} catch (IOException e) {
			e.printStackTrace();
		}
	} // constructor

	public void draw(Graphics g) {
		// g.drawImage(this.image, (int)this.posOriginX, (int)this.posOriginY, null);
		g.setColor(Color.YELLOW);
		g.fillOval((int) this.posOriginX, (int) this.posOriginY, 10, 10);
	} // draw

	public void movimiento() {
		this.posOriginY = m * this.posOriginX + b;
		if (this.posOriginX >= this.posEndX) {
			if (4 < m || m < -5) {
				this.posOriginX -= 0.1;
				this.posEndX -= 0.5;
			} else {
				this.posOriginX -= this.velocity;
				this.posEndX -= this.velocity;
			}
		}
		if (this.posOriginX <= this.posEndX) {
			if (m > 2 || m < -5) {
				this.posOriginX += 0.5;
				this.posEndX += 0.5;
			} else {
				this.posOriginX += this.velocity;
				this.posEndX += this.velocity;
			}
		}
	} // move

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getPosOriginX() {
		return posOriginX;
	}

	public void setPosOriginX(double posOriginX) {
		this.posOriginX = posOriginX;
	}

	public double getPosOriginY() {
		return posOriginY;
	}

	public void setPosOriginY(double posOriginY) {
		this.posOriginY = posOriginY;
	}

} // class
