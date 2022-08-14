package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.spec.EllipticCurve;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdom.Element;

import Domain.ClientCliente;
import Utility.MetodoXMLString;

public class JIFInvitacion extends JInternalFrame implements ActionListener {

	private JLabel jlblTitulo;
	private JButton jbtnCerrar;
	private JTextField jtfBusqueda;
	private ClientCliente cliente;

	private String nombrePartida;

	public JIFInvitacion(ClientCliente cliente) {
		this.setLayout(null);
		this.nombrePartida = "";
		// this.setBounds(0, -25, 150, 530);
		this.setBorder(null);
		this.setClosable(true);
		this.setOpaque(false);
		this.setVisible(true);
		this.getContentPane().setBackground(new Color(0, 0, 0, 0));
		this.cliente = cliente;
		this.nombrePartida = "";
		init();

	}

	private void init() {

		this.jbtnCerrar = new JButton("Buscar");
		this.jbtnCerrar.setBounds(10, 80, 130, 20);
		this.jbtnCerrar.addActionListener(this);
		this.add(this.jbtnCerrar);

		this.jlblTitulo = new JLabel("Buscar Amigo");
		this.jlblTitulo.setBounds(10, 20, 300, 30);
		this.jlblTitulo.setFont(new Font("Arial", Font.BOLD, 25));
		this.add(this.jlblTitulo);

		this.jtfBusqueda = new JTextField();
		this.jtfBusqueda.setBounds(10, 50, 170, 20);
		this.add(this.jtfBusqueda);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jbtnCerrar) {
			if (!jtfBusqueda.getText().trim().isEmpty() && nombrePartida != "") {
				Element eUsuario = new Element("Usuario").setAttribute("Nombre", this.jtfBusqueda.getText());
				eUsuario.addContent(new Element("Partida").addContent(nombrePartida));
				Element eInstruccion = MetodoXMLString.agregarInstruccionProtocolo("Invitar", eUsuario);
				this.cliente.getEnviar().println(MetodoXMLString.xmlToString(eInstruccion));
				this.jtfBusqueda.setText("");
			}
		}
	}

	public String getNombrePartida() {
		return nombrePartida;
	}

	public void setNombrePartida(String nombrePartida) {
		this.nombrePartida = nombrePartida;
	}

}
