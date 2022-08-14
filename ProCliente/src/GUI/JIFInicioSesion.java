package GUI;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;

import Domain.ClientCliente;

public class JIFInicioSesion extends JInternalFrame {

	public JIFInicioSesion(ClientCliente cliente, JDesktopPane jDesktopPane, JFrame jFrame) {
		this.setBounds(0, -30, 785, 600);
		this.setBorder(null);
		this.setClosable(true);
		this.setLayout(null);
		this.add(new JPInicioSesion(this, cliente, jDesktopPane, jFrame));
	}

}
