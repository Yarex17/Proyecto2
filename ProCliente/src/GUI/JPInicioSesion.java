package GUI;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdom.Element;

import Domain.ClientCliente;
import Domain.Observador;
import Utility.MetodoXMLString;

public class JPInicioSesion extends JPanel implements ActionListener, Observador {

	private JTextField jtfNombre, jtfContrasena;
	private JLabel jlblTitulo, jlblNombre, jlblContrasena;
	private JButton jbtnRegistrar, jbtnCerrar;
	private ClientCliente client;
	private JDesktopPane jDesktopPane;
	private JIFInicioSesion jifInicioSesion;
	private BufferedImage image;
	private JFrame jFrame;

	public JPInicioSesion(JIFInicioSesion jifInicioSesion, ClientCliente cliente, JDesktopPane jDesktopPane,
			JFrame jFrame) {
		this.setSize(800, 600);
		this.setLayout(null);
		this.jifInicioSesion = jifInicioSesion;
		this.jDesktopPane = jDesktopPane;
		this.jFrame = jFrame;
		try {
			this.image = ImageIO.read(getClass().getResourceAsStream("/assets/FondoLogin.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.client = cliente;
		this.client.agregarObservador(this);
		init();
	} // Constructor

	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		g.drawImage(this.image, 0, -50, null);
	} // paintComponent

	private void init() {

		this.jlblTitulo = new JLabel("Iniciar sesion");
		this.jlblTitulo.setBounds(300, 10, 300, 30);
		this.jlblTitulo.setFont(new Font("Arial", Font.BOLD, 25));
		this.add(this.jlblTitulo);

		this.jlblNombre = new JLabel("Nombre:");
		this.jlblNombre.setBounds(300, 50, 300, 30);
		this.jlblNombre.setFont(new Font("Arial", Font.BOLD, 18));
		this.add(this.jlblNombre);

		this.jtfNombre = new JTextField();
		this.jtfNombre.setBounds(380, 56, 100, 20);
		this.add(this.jtfNombre);

		this.jlblContrasena = new JLabel("Contrasena:");
		this.jlblContrasena.setBounds(270, 90, 300, 30);
		this.jlblContrasena.setFont(new Font("Arial", Font.BOLD, 18));
		this.add(this.jlblContrasena);

		this.jtfContrasena = new JTextField();
		this.jtfContrasena.setBounds(380, 96, 100, 20);
		this.add(this.jtfContrasena);

		this.jbtnRegistrar = new JButton("Iniciar sesion");
		this.jbtnRegistrar.setBounds(230, 130, 150, 20);
		this.jbtnRegistrar.addActionListener(this);
		this.add(this.jbtnRegistrar);

		this.jbtnCerrar = new JButton("Cerrar");
		this.jbtnCerrar.setBounds(410, 130, 150, 20);
		this.jbtnCerrar.addActionListener(this);
		this.add(this.jbtnCerrar);
	} // init

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.jbtnCerrar) {
			this.jifInicioSesion.dispose();
		} else if (e.getSource() == this.jbtnRegistrar) {
			if (!this.jtfNombre.getText().trim().isEmpty() || !this.jtfContrasena.getText().trim().isEmpty()) {
				Element eUsuario = new Element("Usuario").setAttribute("Nombre", this.jtfNombre.getText());
				eUsuario.addContent(new Element("Contrasena").addContent(this.jtfContrasena.getText()));
				Element eInstruccion = MetodoXMLString.agregarInstruccionProtocolo("Inicio sesion", eUsuario);
				this.client.getEnviar().println(MetodoXMLString.xmlToString(eInstruccion));
			}
		}
	} // actionPerformed

	public void upDate() {
		if (this.client.isLogueo()) {
			this.jDesktopPane.remove(this);
			this.client.setLogueo(false);
			this.client.setNombre(this.jtfNombre.getText());
			this.jifInicioSesion.dispose();
			this.jFrame.dispose();
			new JFJuego(client, jDesktopPane).setVisible(true);
			// JIFUnirseCrearPartida jifUnirseCrearPartida = new
			// JIFUnirseCrearPartida(this.client, this.jDesktopPane);
			// this.jDesktopPane.add(jifUnirseCrearPartida);
			// jifUnirseCrearPartida.setVisible(true);
		} else {
			// pegar una etiqueta que diga usuario no logueado al final
		}
	}

} // fin clase