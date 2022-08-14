package GUI;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.jdom.Element;

import Domain.ClientCliente;
import Domain.Observador;
import Domain.Usuario;
import Utility.MetodoXMLString;

public class JIFRanking extends JInternalFrame implements Observador {

	private JLabel jlblTitulo;
	private ClientCliente client;
	private JTable jtUsuarios;
	private DefaultTableModel dtmUsuarios;

	public JIFRanking(ClientCliente client) {
		this.setLayout(null);
		this.setBounds(0, 0, 785, 530);
		this.setBorder(null);
		this.setClosable(true);
		this.setOpaque(false);
		this.getContentPane().setBackground(new Color(0, 0, 0, 0));
		this.client = client;
		this.client.agregarObservador(this);
		init();
	}

	private void init() {

		this.jlblTitulo = new JLabel("Ranking");
		this.jlblTitulo.setBounds(350, 5, 300, 30);
		this.jlblTitulo.setFont(new Font("Arial", Font.BOLD, 25));
		this.add(this.jlblTitulo);

		this.dtmUsuarios = new DefaultTableModel();

		this.jtUsuarios = new JTable(this.dtmUsuarios);
		this.jtUsuarios.setBounds(200, 290, 270, 250);

		JScrollPane jspActores = new JScrollPane(this.jtUsuarios);
		jspActores.setBounds(5, 40, 775, 460);
		jspActores.setOpaque(false);
		this.add(jspActores);

		this.dtmUsuarios.addColumn("Nombre");
		this.dtmUsuarios.addColumn("Vistorias");
		this.jtUsuarios.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
		this.jtUsuarios.getTableHeader().setOpaque(false);
		this.jtUsuarios.getTableHeader().setBackground(new Color(32, 136, 203));
		this.jtUsuarios.getTableHeader().setForeground(new Color(255, 255, 255));
		this.jtUsuarios.setShowVerticalLines(false);
		this.jtUsuarios.setEnabled(false);
		this.jtUsuarios.getTableHeader().setReorderingAllowed(false);

		Element eInstruccion = MetodoXMLString.instruccionProtocolo("Obtener Usuarios");
		String stringInstruccionXML = MetodoXMLString.xmlToString(eInstruccion);
		this.client.getEnviar().println(stringInstruccionXML);

	}

	@Override
	public void upDate() {
		if (this.client.getUsuariosRanking() != null) {
			this.dtmUsuarios.setRowCount(0);
			ArrayList<Usuario> usuarios = this.client.getUsuariosRanking();
			Collections.sort(usuarios);
			for (int i = 0; i < usuarios.size(); i++) {
				this.dtmUsuarios
						.addRow(new Object[] { usuarios.get(i).getNombre(), usuarios.get(i).getCantidadVictorias() });
			}
		}
	}

}
