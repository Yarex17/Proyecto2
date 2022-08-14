package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.jdom.Element;

import Domain.ClientCliente;
import Utility.MetodoXMLString;

public class JFVentanaPrincipal extends JFrame implements ActionListener {

	private JDesktopPane jDesktopPane;
	private JMenuBar jmbBarra;
	private JMenu jmRegitrar, jmIniciarSesion, jmRanking, jmSalir;
	private JMenuItem jmiRegistrarJugador, jmiIniciarSesion, jmiRanking, jmiSalir;

	private ClientCliente client;

	public JFVentanaPrincipal() {

		this.setBounds(0, 0, 800, 600);

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setLocationRelativeTo(null);
		// this.setFocusable(true);
		this.jDesktopPane = new JDesktopPane();
		this.jDesktopPane.setBounds(0, 30, 784, 531);
		this.add(this.jDesktopPane);
		try {
			this.client = new ClientCliente("localHost", 5025);
		} catch (IOException e) {
			e.printStackTrace();

		}
		// new JFChat(client).setVisible(true);
		init();

		this.jDesktopPane.add(new JPFondo());
	} // constructor

	public JFVentanaPrincipal(ClientCliente client) {

		this.setBounds(0, 0, 800, 600);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setLocationRelativeTo(null);
		this.setFocusable(true);
		this.jDesktopPane = new JDesktopPane();
		this.jDesktopPane.setBounds(0, 30, 784, 531);
		this.add(this.jDesktopPane);

		this.client = client;

		init();

		this.jDesktopPane.add(new JPFondo());
	}

	private void init() {

		this.jmbBarra = new JMenuBar();
		this.jmbBarra.setBounds(0, 0, 800, 30);

		this.jmRegitrar = new JMenu("Registrar");
		this.jmRegitrar.addActionListener(this);
		this.jmbBarra.add(this.jmRegitrar);

		this.jmIniciarSesion = new JMenu("Iniciar sesion");
		this.jmIniciarSesion.addActionListener(this);
		this.jmbBarra.add(this.jmIniciarSesion);

		this.jmRanking = new JMenu("Ranking");
		this.jmRanking.addActionListener(this);
		this.jmbBarra.add(this.jmRanking);

		this.jmSalir = new JMenu("Salir");
		this.jmSalir.addActionListener(this);
		this.jmbBarra.add(this.jmSalir);

		this.jmiRegistrarJugador = new JMenuItem("Registrar Jugador");
		this.jmiRegistrarJugador.addActionListener(this);
		this.jmRegitrar.add(this.jmiRegistrarJugador);

		this.jmiIniciarSesion = new JMenuItem("Iniciar sesion");
		this.jmiIniciarSesion.addActionListener(this);
		this.jmIniciarSesion.add(this.jmiIniciarSesion);

		this.jmiRanking = new JMenuItem("Ranking");
		this.jmiRanking.addActionListener(this);
		this.jmRanking.add(this.jmiRanking);

		this.jmiSalir = new JMenuItem("Salir");
		this.jmiSalir.addActionListener(this);
		this.jmSalir.add(this.jmiSalir);

		this.add(this.jmbBarra);

		JIFInicioSesion jifInicioSesion = new JIFInicioSesion(this.client, this.jDesktopPane, this);
		jifInicioSesion.setVisible(true);
		this.jDesktopPane.add(jifInicioSesion);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.jmiRegistrarJugador) {
			JIFRegistrarJugador jifRegistrarJugador = new JIFRegistrarJugador(this.client);
			this.jDesktopPane.add(jifRegistrarJugador);
			jifRegistrarJugador.setVisible(true);
		} else if (e.getSource() == this.jmiIniciarSesion) {
			JIFInicioSesion jifInicioSesion = new JIFInicioSesion(this.client, this.jDesktopPane, this);
			this.jDesktopPane.add(jifInicioSesion);
			jifInicioSesion.setVisible(true);
		} else if (e.getSource() == this.jmiRanking) {
			JIFRanking jifRanking = new JIFRanking(this.client);
			this.jDesktopPane.add(jifRanking);
			jifRanking.setVisible(true);
		} else {
			if (this.jmiSalir == e.getSource()) {
				Element instruccion = MetodoXMLString.instruccionProtocolo("Cerrar");
				this.client.getEnviar().println(MetodoXMLString.xmlToString(instruccion));
				try {

					this.client.getSocket().close();
					this.client.setSesionIniciada(false);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					// e1.printStackTrace();
				}
				this.dispose();
			}
		}
	}

}