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

public class JFJuego extends JFrame implements ActionListener {

	private JDesktopPane jDesktopPane;
	private JMenuBar jmbBarra;
	private JMenu jmRanking, jmSalir, jmCerrarSesion, jmChat;
	private JMenuItem jmiCerrarSesion, jmiRanking, jmiSalir, jmiChat;
	private JIFUnirseCrearPartida jifUnirseCrearPartida;
	private ClientCliente client;
	private JMenu jmIntru;
	private JMenuItem jmiInstru;

	public JFJuego(ClientCliente cliente, JDesktopPane jDesktopPane) {

		this.setBounds(0, 0, 800, 600);

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.jDesktopPane = jDesktopPane;
		this.add(this.jDesktopPane);
		this.client = cliente;
		init();

	} // constructor

	private void init() {

		this.jmbBarra = new JMenuBar();
		this.jmbBarra.setBounds(0, 0, 800, 30);

		this.jmChat = new JMenu("Chat");
		this.jmChat.addActionListener(this);
		this.jmbBarra.add(this.jmChat);

		this.jmIntru = new JMenu("Instrucciones");
		this.jmIntru.setEnabled(false);
		this.jmIntru.addActionListener(this);
		this.jmbBarra.add(this.jmIntru);

		this.jmiInstru = new JMenuItem("Instrucciones");
		this.jmiInstru.addActionListener(this);
		this.jmIntru.add(this.jmiInstru);

		this.jmCerrarSesion = new JMenu("Cerrar sesion");
		this.jmCerrarSesion.addActionListener(this);
		this.jmbBarra.add(this.jmCerrarSesion);

		this.jmRanking = new JMenu("Ranking");
		this.jmRanking.addActionListener(this);
		this.jmbBarra.add(this.jmRanking);

		this.jmSalir = new JMenu("Salir");
		this.jmSalir.addActionListener(this);
		this.jmbBarra.add(this.jmSalir);

		this.jmiChat = new JMenuItem("Chat");
		this.jmiChat.addActionListener(this);
		this.jmChat.add(this.jmiChat);

		this.jmiRanking = new JMenuItem("Ranking");
		this.jmiRanking.addActionListener(this);
		this.jmRanking.add(this.jmiRanking);

		this.jmiCerrarSesion = new JMenuItem("Cerrar sesion");
		this.jmiCerrarSesion.addActionListener(this);
		this.jmCerrarSesion.add(this.jmiCerrarSesion);

		this.jmiSalir = new JMenuItem("Salir");
		this.jmiSalir.addActionListener(this);
		this.jmSalir.add(this.jmiSalir);

		this.add(this.jmbBarra);

		this.jmChat.setEnabled(false);

		this.jifUnirseCrearPartida = new JIFUnirseCrearPartida(this.client, this.jDesktopPane, this);
		this.jDesktopPane.add(jifUnirseCrearPartida);
		jifUnirseCrearPartida.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.jmiSalir) {
			Element instruccion = MetodoXMLString.instruccionProtocolo("Cerrar");
			this.client.getEnviar().println(MetodoXMLString.xmlToString(instruccion));
			try {
				this.client.setSesionIniciada(false);
				this.client.getSocket().close();
			} catch (IOException e1) {

				// e1.printStackTrace();
			}
			this.dispose();
		} else if (e.getSource() == this.jmiChat) {
			new JFChat(client).setVisible(true);
		} else if (e.getSource() == this.jmiRanking) {
			JIFRanking jifRanking = new JIFRanking(this.client);
			this.jDesktopPane.add(jifRanking);
			jifRanking.setVisible(true);
		} else {
			if (e.getSource() == this.jmiCerrarSesion) {
				this.client.setLogueo(false);
				Element instruccion = MetodoXMLString.instruccionProtocolo("CerrarSesion");
				this.client.getEnviar().println(MetodoXMLString.xmlToString(instruccion));
				new JFVentanaPrincipal(this.client).setVisible(true);
				;
				this.dispose();
			} else {
				if (e.getSource() == this.jmiInstru) {
					new JFIntrucciones().setVisible(true);
					;
				}
			}
		}
	}

	public JMenu getJmChat() {
		return jmChat;
	}

	public void setJmChat(JMenu jmChat) {
		this.jmChat = jmChat;
	}

	public JMenu getJmIntru() {
		return jmIntru;
	}

	public void setJmIntru(JMenu jmIntru) {
		this.jmIntru = jmIntru;
	}

}