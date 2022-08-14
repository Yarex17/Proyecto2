package GUI;

import javax.swing.JInternalFrame;

import Domain.ClientCliente;

public class JIFRegistrarJugador extends JInternalFrame {

	public JIFRegistrarJugador(ClientCliente cliente) {
		this.setBounds(0, -30, 785, 600);
		this.setBorder(null);
		this.setClosable(true);
		this.setLayout(null);
		this.add(new JPRegistrarJugador(this, cliente));
	}

} // fin clase
