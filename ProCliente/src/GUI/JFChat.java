package GUI;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.jdom.Element;

import Domain.ClientCliente;
import Domain.Observador;
import Utility.MetodoXMLString;

public class JFChat extends JFrame implements ActionListener, Observador {

	private JTextField jtfMensaje;
	private JLabel jlblMensaje;
	private JButton jbtnEnviar;
	private JTextArea jtaMensajes;
	private ClientCliente client;

	public JFChat(ClientCliente client) {
		this.setSize(300, 300);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLayout(null);
		this.setTitle("Cliente");
		this.setLocationRelativeTo(null);
		this.client = client;
		this.client.agregarObservador(this);
		init();
		this.add(new JPFondo());
		upDate();
	}

	private void init() {

		this.jlblMensaje = new JLabel("Mensaje:");
		this.jlblMensaje.setBounds(5, 5, 70, 20);
		this.jlblMensaje.setFont(new Font("Arial", Font.PLAIN, 15));
		this.add(this.jlblMensaje);

		this.jtfMensaje = new JTextField();
		this.jtfMensaje.setBounds(65, 8, 140, 18);
		this.add(this.jtfMensaje);

		this.jbtnEnviar = new JButton("Enviar");
		this.jbtnEnviar.setBounds(210, 7, 69, 18);
		this.jbtnEnviar.addActionListener(this);
		this.add(this.jbtnEnviar);

		this.jtaMensajes = new JTextArea();
		this.jtaMensajes.setBounds(5, 30, 273, 225);
		this.add(this.jtaMensajes);

		JScrollPane jSscroll2 = new JScrollPane(jtaMensajes);
		jSscroll2.setBounds(5, 30, 273, 225);
		jSscroll2.setVisible(true);
		this.add(jSscroll2);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.jbtnEnviar) {
			if (!this.jtfMensaje.getText().trim().isEmpty()) {
				Element elementMensaje1 = new Element("Jugador").setAttribute("Remitente", this.client.getNombre());
				Element eMensaje = new Element("Mensaje")
						.addContent(this.client.getNombre() + ":" + this.jtfMensaje.getText() + "\n");
				elementMensaje1.addContent(eMensaje);
				Element eInstruccion11 = MetodoXMLString.agregarInstruccionProtocolo("Chat", elementMensaje1);
				this.client.getEnviar().println(MetodoXMLString.xmlToString(eInstruccion11));
				this.jtfMensaje.setText("");
			}
		}
	}

	@Override
	public void upDate() {
		if (this.client.getChat() != null) {
			this.jtaMensajes.setText(this.client.getChat());
		}
	}

}
