package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.jdom.Element;

import Domain.ClientCliente;
import Domain.Usuario;
import Utility.MetodoXMLString;

public class JPRegistrarJugador extends JPanel implements ActionListener {

	private JTextField jtfNombre, jtfContrasena;
	private JLabel jlblTitulo, jlblPersonaje, jlblNombre, jlblContrasena;
	private JButton jbtnRegistrar, jbtnCerrar;
	private JRadioButton jrb1, jrb2, jrb3, jrb4;
	private ButtonGroup bGroup;
	private ClientCliente client;
	private String ruta;
	private BufferedImage imagen, imagen1, imagen2, imagen3, imagen4;
	private JIFRegistrarJugador jifRegistrarJugador;

	public JPRegistrarJugador(JIFRegistrarJugador jifRegistrarPersona, ClientCliente cliente) {
		this.setSize(800, 600);
		this.setLayout(null);
		this.jifRegistrarJugador = jifRegistrarPersona;
		try {
			this.imagen = ImageIO.read(getClass().getResourceAsStream("/assets/FondoLogin.png"));
			this.imagen1 = ImageIO.read(getClass().getResourceAsStream("/Assets/1.png"));
			this.imagen2 = ImageIO.read(getClass().getResourceAsStream("/Assets/2.png"));
			this.imagen3 = ImageIO.read(getClass().getResourceAsStream("/Assets/3.png"));
			this.imagen4 = ImageIO.read(getClass().getResourceAsStream("/Assets/4.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.client = cliente;
		init();
	} // Constructor

	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		g.drawImage(this.imagen, 0, -50, null);
		g.drawImage(this.imagen1, 25, 290, null);
		g.drawImage(this.imagen2, 215, 290, null);
		g.drawImage(this.imagen3, 415, 290, null);
		g.drawImage(this.imagen4, 610, 290, null);
	} // paintComponent

	private void init() {

		this.jlblTitulo = new JLabel("Registrar jugador");
		this.jlblTitulo.setBounds(300, 50, 300, 30);
		this.jlblTitulo.setFont(new Font("Arial", Font.BOLD, 25));
		this.add(this.jlblTitulo);

		this.jlblNombre = new JLabel("Nombre:");
		this.jlblNombre.setBounds(300, 100, 300, 30);
		this.jlblNombre.setFont(new Font("Arial", Font.BOLD, 18));
		this.add(this.jlblNombre);

		this.jtfNombre = new JTextField();
		this.jtfNombre.setBounds(380, 105, 100, 20);
		this.add(this.jtfNombre);

		this.jlblContrasena = new JLabel("Contrasena:");
		this.jlblContrasena.setBounds(270, 145, 300, 30);
		this.jlblContrasena.setFont(new Font("Arial", Font.BOLD, 18));
		this.add(this.jlblContrasena);

		this.jtfContrasena = new JTextField();
		this.jtfContrasena.setBounds(380, 150, 100, 20);
		this.add(this.jtfContrasena);

		this.jlblPersonaje = new JLabel("Seleccione un personaje");
		this.jlblPersonaje.setBounds(220, 210, 400, 40);
		this.jlblPersonaje.setFont(new Font("Arial", Font.BOLD, 30));
		this.add(this.jlblPersonaje);

		this.jrb1 = new JRadioButton();
		this.jrb1.setBounds(90, 270, 30, 20);
		this.jrb1.setBackground(new Color(0, 0, 0, 0));
		this.jrb1.addActionListener(this);
		this.jrb1.setSelected(true);
		this.jrb1.setOpaque(false);
		this.add(this.jrb1);

		this.jrb2 = new JRadioButton();
		this.jrb2.setBounds(285, 270, 30, 20);
		this.jrb2.setBackground(new Color(0, 0, 0, 0));
		this.jrb2.addActionListener(this);
		this.jrb2.setOpaque(false);
		this.add(this.jrb2);

		this.jrb3 = new JRadioButton();
		this.jrb3.setBounds(485, 270, 30, 20);
		this.jrb3.setBackground(new Color(228, 217, 206));
		this.jrb3.addActionListener(this);
		this.jrb3.setOpaque(false);
		this.add(this.jrb3);

		this.jrb4 = new JRadioButton();
		this.jrb4.setBounds(695, 270, 30, 20);
		this.jrb4.setBackground(new Color(0, 0, 0, 0));
		this.jrb4.setSelected(true);
		this.jrb4.addActionListener(this);
		this.jrb4.setOpaque(false);
		this.add(this.jrb4);

		this.bGroup = new ButtonGroup();
		this.bGroup.add(this.jrb1);
		this.bGroup.add(this.jrb2);
		this.bGroup.add(this.jrb3);
		this.bGroup.add(this.jrb4);

		this.jbtnRegistrar = new JButton("Registrar jugador");
		this.jbtnRegistrar.setBounds(150, 500, 250, 30);
		this.jbtnRegistrar.setFont(new Font("Arial", Font.BOLD, 25));
		this.jbtnRegistrar.addActionListener(this);
		this.add(this.jbtnRegistrar);

		this.jbtnCerrar = new JButton("Cerrar");
		this.jbtnCerrar.setBounds(410, 500, 250, 30);
		this.jbtnCerrar.setFont(new Font("Arial", Font.BOLD, 25));
		this.jbtnCerrar.addActionListener(this);
		this.add(this.jbtnCerrar);

		this.ruta = "/Assets/Skin1.png";

	} // init

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.jbtnCerrar) {
			this.jifRegistrarJugador.dispose();
		} else if (e.getSource() == this.jrb1) {
			if (this.jrb1.isSelected()) {
				this.ruta = "/Assets/Skin1.png";
			}

		} else if (e.getSource() == this.jrb2) {
			if (this.jrb2.isSelected()) {
				this.ruta = "/Assets/Skin2.png";
			}

		} else if (e.getSource() == this.jrb3) {
			if (this.jrb3.isSelected()) {
				this.ruta = "/Assets/Skin3.png";
			}
		} else if (e.getSource() == this.jrb4) {
			if (this.jrb4.isSelected()) {

				this.ruta = "/Assets/Skin4.png";
			}

		}
		if (e.getSource() == this.jbtnRegistrar) {
			if (!this.jtfNombre.getText().trim().isEmpty() && !this.jtfContrasena.getText().trim().isEmpty()
					&& this.ruta != null) {
				Element eUsuario = MetodoXMLString.UsuarioToXML(
						new Usuario(this.jtfNombre.getText(), this.jtfContrasena.getText(), this.ruta, 0));
				this.jtfNombre.setText("");
				this.jtfContrasena.setText("");
				Element eInstruccion = MetodoXMLString.agregarInstruccionProtocolo("Registrar Usuario", eUsuario);
				this.client.getEnviar().println(MetodoXMLString.xmlToString(eInstruccion));
			}
		}
	} // actionPerformed

} // fin clase
