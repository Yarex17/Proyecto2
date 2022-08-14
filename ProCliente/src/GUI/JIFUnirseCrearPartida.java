package GUI;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;

import Domain.ClientCliente;

public class JIFUnirseCrearPartida extends JInternalFrame {

	public JIFUnirseCrearPartida(ClientCliente cliente, JDesktopPane jDesktopPane, JFJuego jfJuego) {
		this.setLayout(null);
		this.setBounds(0, -30, 785, 600);
		this.setBorder(null);
		this.setClosable(true);
		// this.setFocusable(true);
		this.add(new JPUnirseCrearPartida(this, cliente, jDesktopPane, jfJuego));
	}

}
