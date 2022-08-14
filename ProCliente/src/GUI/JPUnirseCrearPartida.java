package GUI;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.jdom.Element;

import Domain.ClientCliente;
import Domain.Observador;
import Domain.Partida;
import Utility.MetodoXMLString;

public class JPUnirseCrearPartida extends JPanel implements ActionListener, Observador {

	private JTextField jtfNombre;
	private JLabel jlblTitulo, jlblJugadoresEnPartida, jlblTipoPartida, jlblNombre;
	private JButton jbtnIniciar, jbtnCerrar, jbtnCrear, jbtnUnirse, jbtnVerificar;
	private JRadioButton jrbCrearPartida, jrbUnirsePartida;
	private ButtonGroup bgAccion;
	private ClientCliente client;
	private JComboBox<Partida> jcbpartidas;
	private DefaultTableModel dtmJugadores;
	private JTable jtJugadores;
	private JScrollPane jspJugadores;
	private JLabel jlblPartida;
	private BufferedImage image;
	private JIFUnirseCrearPartida jifUnirseCrearPartida;
	private JDesktopPane jDesktopPane;
	private JFJuego jfJuego;
	private JIFInvitacion jifInvitacion;
	private JButton jbtnLista;
	private String nombrePartida;
	boolean uso;

	public JPUnirseCrearPartida(JIFUnirseCrearPartida jifUnirseCrearPartida, ClientCliente client,
			JDesktopPane jDesktopPane, JFJuego jfJuego) {
		this.uso = true;
		this.setSize(800, 600);
		this.setLayout(null);
		this.jifUnirseCrearPartida = jifUnirseCrearPartida;
		this.jfJuego = jfJuego;
		try {
			this.image = ImageIO.read(getClass().getResourceAsStream("/assets/FondoLogin.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.jDesktopPane = jDesktopPane;
		this.client = client;
		this.client.agregarObservador(this);
		this.nombrePartida = "";
		init();
		// upDate();
	}

	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		g.drawImage(this.image, 0, -50, null);
	} // paintComponent

	private void init() {

		this.jlblTitulo = new JLabel("Unirse o crear partida");
		this.jlblTitulo.setBounds(235, 10, 400, 30);
		this.jlblTitulo.setFont(new Font("Arial", Font.BOLD, 30));
		this.add(this.jlblTitulo);

		this.jrbCrearPartida = new JRadioButton("Crear partida");
		this.jrbCrearPartida.setBounds(270, 50, 100, 20);
		this.jrbCrearPartida.setBackground(null);
		this.jrbCrearPartida.addActionListener(this);
		this.jrbCrearPartida.setOpaque(false);
		this.add(this.jrbCrearPartida);

		this.jrbUnirsePartida = new JRadioButton("Unirse a partida");
		this.jrbUnirsePartida.setBounds(400, 50, 200, 20);
		this.jrbUnirsePartida.setBackground(null);
		this.jrbUnirsePartida.addActionListener(this);
		this.jrbUnirsePartida.setOpaque(false);
		this.add(this.jrbUnirsePartida);

		this.bgAccion = new ButtonGroup();
		this.bgAccion.add(this.jrbCrearPartida);
		this.bgAccion.add(this.jrbUnirsePartida);

		this.jlblPartida = new JLabel("Seleccione una partida:");
		this.jlblPartida.setBounds(150, 70, 300, 30);
		this.jlblPartida.setFont(new Font("Arial", Font.BOLD, 20));
		this.jlblPartida.setVisible(false);
		this.add(this.jlblPartida);

		this.jlblTipoPartida = new JLabel("Tipo partida");
		this.jlblTipoPartida.setBounds(350, 70, 300, 30);
		this.jlblTipoPartida.setFont(new Font("Arial", Font.BOLD, 17));
		this.jlblTipoPartida.setVisible(false);
		this.add(this.jlblTipoPartida);

		this.jlblNombre = new JLabel("Nombre de partida:");
		this.jlblNombre.setBounds(185, 115, 300, 30);
		this.jlblNombre.setFont(new Font("Arial", Font.BOLD, 18));
		this.jlblNombre.setVisible(false);
		this.add(this.jlblNombre);

		this.jtfNombre = new JTextField();
		this.jtfNombre.setBounds(355, 121, 100, 20);
		this.jtfNombre.setVisible(false);
		this.add(this.jtfNombre);

		this.jbtnCrear = new JButton("Crear partida");
		this.jbtnCrear.setBounds(461, 120, 120, 20);
		this.jbtnCrear.addActionListener(this);
		this.jbtnCrear.setVisible(false);
		this.add(this.jbtnCrear);

		this.jlblJugadoresEnPartida = new JLabel("Jugadores en la partida");
		this.jlblJugadoresEnPartida.setBounds(245, 150, 300, 30);
		this.jlblJugadoresEnPartida.setFont(new Font("Arial", Font.BOLD, 25));
		this.jlblJugadoresEnPartida.setVisible(false);
		this.add(this.jlblJugadoresEnPartida);

		this.dtmJugadores = new DefaultTableModel();

		this.jtJugadores = new JTable(this.dtmJugadores);
		this.jtJugadores.setBounds(200, 300, 270, 250);
		this.jtJugadores.setVisible(false);

		this.jspJugadores = new JScrollPane(this.jtJugadores);
		this.jspJugadores.setBounds(245, 190, 280, 180);
		this.jspJugadores.setOpaque(false);
		this.jspJugadores.setVisible(false);
		this.add(this.jspJugadores);

		this.dtmJugadores.addColumn("Jugadores en la partida");

		this.jbtnIniciar = new JButton("Iniciar partida");
		this.jbtnIniciar.setBounds(245, 380, 120, 20);
		this.jbtnIniciar.addActionListener(this);
		this.jbtnIniciar.setVisible(false);
		this.add(this.jbtnIniciar);

		this.jbtnCerrar = new JButton("Cerrar partida");
		this.jbtnCerrar.setBounds(405, 380, 120, 20);
		this.jbtnCerrar.addActionListener(this);
		this.jbtnCerrar.setVisible(false);
		this.add(this.jbtnCerrar);

		this.jcbpartidas = new JComboBox<>();
		this.jcbpartidas.setBounds(375, 77, 120, 20);
		this.jcbpartidas.setVisible(false);
		this.add(this.jcbpartidas);

		this.jbtnVerificar = new JButton("Verificar partida");
		this.jbtnVerificar.setBounds(498, 77, 128, 20);
		this.jbtnVerificar.addActionListener(this);
		this.jbtnVerificar.setVisible(false);
		this.add(this.jbtnVerificar);

		this.jbtnUnirse = new JButton("Unirse a la partida");
		this.jbtnUnirse.setBounds(630, 77, 137, 20);
		this.jbtnUnirse.addActionListener(this);
		this.jbtnUnirse.setVisible(false);
		this.add(this.jbtnUnirse);

		this.jifInvitacion = new JIFInvitacion(client);
		this.jifInvitacion.setVisible(false);
		this.jifInvitacion.setBounds(0, -25, 200, 300);
		this.add(jifInvitacion);

		this.jbtnLista = new JButton("Invitar Amigo");
		this.jbtnLista.addActionListener(this);
		this.jbtnLista.setBounds(10, 10, 140, 20);
		this.jbtnLista.setVisible(false);
		this.add(this.jbtnLista);// -------------------
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == this.jbtnVerificar) {
			if (this.jcbpartidas.getSelectedItem() != null) {
				Element ePartida = MetodoXMLString.partidaToXML((Partida) this.jcbpartidas.getSelectedItem());
				this.client.getEnviar().println(MetodoXMLString
						.xmlToString(MetodoXMLString.agregarInstruccionProtocolo("Obtener usuarios", ePartida)));
			}
		} else if (e.getSource() == this.jbtnCerrar) {
			// this.dispose();
		} else if (e.getSource() == this.jbtnUnirse) {
			this.jrbCrearPartida.setEnabled(false);
			this.jrbUnirsePartida.setEnabled(false);
			this.jbtnUnirse.setEnabled(false);
			this.jcbpartidas.setEnabled(false);

			Element ePartida = MetodoXMLString.partidaToXML((Partida) this.jcbpartidas.getSelectedItem());
			this.jcbpartidas.removeItem(this.jcbpartidas.getSelectedItem());

			Element eInstruccion = MetodoXMLString.agregarInstruccionProtocolo("Unirse a partida", ePartida);
			this.client.getEnviar().println(MetodoXMLString.xmlToString(eInstruccion));

		} else if (e.getSource() == this.jbtnCrear) {
			Element ePartida = MetodoXMLString.partidaToXML(new Partida(this.jtfNombre.getText()));
			Element eInstruccion = MetodoXMLString.agregarInstruccionProtocolo("Crear partida", ePartida);
			this.client.getEnviar().println(MetodoXMLString.xmlToString(eInstruccion));
			this.jbtnCrear.setEnabled(false);

			this.jrbCrearPartida.setEnabled(false);
			this.jrbUnirsePartida.setEnabled(false);
			this.jtfNombre.setEnabled(false);
			this.nombrePartida = this.jtfNombre.getText();
			this.jifInvitacion.setNombrePartida(nombrePartida);
			Element ePartida1 = MetodoXMLString.partidaToXML(new Partida(this.jtfNombre.getText()));
			this.client.getEnviar().println(MetodoXMLString
					.xmlToString(MetodoXMLString.agregarInstruccionProtocolo("Obtener usuarios", ePartida1)));

		} else if (e.getSource() == this.jrbCrearPartida) {
			this.jlblNombre.setVisible(true);
			this.jtfNombre.setVisible(true);
			this.jbtnCrear.setVisible(true);
			this.jlblJugadoresEnPartida.setVisible(true);
			this.jspJugadores.setVisible(true);
			this.jbtnIniciar.setVisible(true);
			this.jbtnCerrar.setVisible(true);

			this.jlblTipoPartida.setVisible(true);
			this.jspJugadores.setBounds(245, 190, 280, 180);
			this.jlblPartida.setVisible(false);
			this.jcbpartidas.setVisible(false);
			this.jbtnUnirse.setVisible(false);
			this.jbtnVerificar.setVisible(false);
			this.jbtnLista.setVisible(true);
		} else if (e.getSource() == this.jrbUnirsePartida) {
			this.jbtnLista.setVisible(false);
			this.jlblNombre.setVisible(false);
			this.jtfNombre.setVisible(false);
			this.jbtnCrear.setVisible(false);
			this.jlblJugadoresEnPartida.setVisible(false);
			this.jbtnIniciar.setVisible(false);
			this.jbtnCerrar.setVisible(false);
			this.jlblTipoPartida.setVisible(false);
			this.jtJugadores.setVisible(true);
			this.jspJugadores.setVisible(true);
			this.jspJugadores.setBounds(245, 110, 280, 180);
			this.jlblPartida.setVisible(true);
			this.jcbpartidas.setVisible(true);
			this.jbtnUnirse.setVisible(true);
			this.jbtnVerificar.setVisible(true);
		} else if (e.getSource() == this.jbtnIniciar) {
			Element eInstruccion1 = MetodoXMLString.instruccionProtocolo("Iniciar partida");
			this.client.getEnviar().println(MetodoXMLString.xmlToString(eInstruccion1));
		} else {
			if (e.getSource() == this.jbtnLista) {

				if (uso == true) {
					this.jifInvitacion.setVisible(true);
					this.jbtnLista.setBounds(10, 110, 140, 20);
					this.jbtnLista.setText("Cerrar");

					uso = false;
				} else {
					if (uso == false) {
						this.jifInvitacion.setVisible(false);
						this.jbtnLista.setBounds(10, 10, 140, 20);
						this.jbtnLista.setText("Invitar Amigo");
						uso = true;
					}
				}

			}
		}
	} // constructor

	@Override
	public void upDate() {
		if (this.client.isPartidaIniciada() != false) {
			this.setBounds(0, -25, 785, 555);

			this.client.setPartidaIniciada(false);
			this.jifUnirseCrearPartida.setContentPane(
					new JPJuego(this.client, this.jifUnirseCrearPartida, this.jDesktopPane, this.jfJuego));
			this.jfJuego.getJmChat().setEnabled(true);
			this.jfJuego.getJmIntru().setEnabled(true);
		}
		if (this.client.getPartidas() != null) {
			this.jcbpartidas.removeAllItems();
			for (int i = 0; i < this.client.getPartidas().size(); i++) {
				this.jcbpartidas.addItem(this.client.getPartidas().get(i));
			}
		}

		if (this.client.getUsuarios() != null) {
			this.dtmJugadores.setRowCount(0);
			for (int i = 0; i < this.client.getUsuarios().size(); i++) {
				this.dtmJugadores.addRow(new Object[] { this.client.getUsuarios().get(i).getNombre() });
			}
		}
	} // upDate

	public JFJuego getJfJuego() {
		return jfJuego;
	}

	public void setJfJuego(JFJuego jfJuego) {
		this.jfJuego = jfJuego;
	}

	public String getNombrePartida() {
		return nombrePartida;
	}

	public void setNombrePartida(String nombrePartida) {
		this.nombrePartida = nombrePartida;
	}

}