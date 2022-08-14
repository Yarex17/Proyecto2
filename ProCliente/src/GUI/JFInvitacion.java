package GUI;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.jdom.Element;

import Domain.ClientCliente;
import Domain.Partida;
import Utility.MetodoXMLString;

public class JFInvitacion extends JFrame implements ActionListener {
	private JLabel jlblTitulo;
	private JButton jbtnAceptar;
	private JButton jbtnRechazar;
	private String nombrePartida;
	private ClientCliente client;

	public JFInvitacion(String nombrePartida, ClientCliente client) {

		this.client = client;
		this.nombrePartida = nombrePartida;
		this.setBounds(0, 0, 400, 150);

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLayout(null);
		this.setLocationRelativeTo(null);
		init();
	}

	private void init() {
		this.jlblTitulo = new JLabel("te han invitado a jugar");
		this.jlblTitulo.setBounds(30, 10, 400, 30);
		this.jlblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
		this.add(this.jlblTitulo);

		this.jbtnAceptar = new JButton("Aceptar");
		this.jbtnAceptar.setBounds(50, 50, 100, 20);
		this.jbtnAceptar.addActionListener(this);

		this.add(this.jbtnAceptar);

		this.jbtnRechazar = new JButton("Rechazar");
		this.jbtnRechazar.setBounds(200, 50, 100, 20);
		this.jbtnRechazar.addActionListener(this);

		this.add(this.jbtnRechazar);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.jbtnAceptar) {
			Element ePartida = MetodoXMLString.partidaToXML(new Partida(nombrePartida));
			Element eInstruccion = MetodoXMLString.agregarInstruccionProtocolo("Aceptar Invitacion", ePartida);
			this.client.getEnviar().println(MetodoXMLString.xmlToString(eInstruccion));
			this.dispose();
		} else {
			if (e.getSource() == this.jbtnRechazar) {
				this.dispose();

			}
		}

	}
}
