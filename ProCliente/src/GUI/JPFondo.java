package GUI;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class JPFondo extends JPanel {

	private BufferedImage image;

	public JPFondo() {
		this.setSize(800, 600);
		this.setLayout(null);
		try {
			this.image = ImageIO.read(getClass().getResourceAsStream("/assets/FondoLogin.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		g.drawImage(this.image, 0, -50, null);
	} // paintComponent

}
