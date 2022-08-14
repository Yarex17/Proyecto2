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

public class JFIntrucciones extends JFrame {

	private JLabel jlblTitulo;
	private JLabel jlblA;
	private JLabel jlblD;
	private JLabel jlblW;
	private JLabel jlblS;
	private JLabel jlblQ;
	private JLabel jlblMouse;
	private JLabel jlblZ;
	private JLabel jlblX;
	private JLabel jlblC;
	private JLabel jlblE;
	private JLabel jlblR;
	private JLabel jlblBloc;

	public JFIntrucciones() {
		this.setSize(300, 300);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLayout(null);
		this.setLocationRelativeTo(null);
		init();
		this.add(new JPFondo());
	}

	private void init() {

		this.jlblTitulo = new JLabel("Instrucciones");
		this.jlblTitulo.setBounds(5, 5, 200, 20);
		this.jlblTitulo.setFont(new Font("Arial", Font.PLAIN, 20));
		this.add(this.jlblTitulo);

		this.jlblA = new JLabel("A: Izquierda:");
		this.jlblA.setBounds(5, 30, 100, 20);
		this.jlblA.setFont(new Font("Arial", Font.PLAIN, 15));
		this.add(this.jlblA);

		this.jlblD = new JLabel("D: Derecha");
		this.jlblD.setBounds(5, 45, 100, 20);
		this.jlblD.setFont(new Font("Arial", Font.PLAIN, 15));
		this.add(this.jlblD);

		this.jlblW = new JLabel("W: Arriba");
		this.jlblW.setBounds(5, 60, 100, 20);
		this.jlblW.setFont(new Font("Arial", Font.PLAIN, 15));
		this.add(this.jlblW);

		this.jlblS = new JLabel("S: Abajo");
		this.jlblS.setBounds(5, 75, 100, 20);
		this.jlblS.setFont(new Font("Arial", Font.PLAIN, 15));
		this.add(this.jlblS);

		this.jlblQ = new JLabel("Q: Espada");
		this.jlblQ.setBounds(5, 90, 100, 20);
		this.jlblQ.setFont(new Font("Arial", Font.PLAIN, 15));
		this.add(this.jlblQ);

		this.jlblMouse = new JLabel("Mouse: BolaEnergia");
		this.jlblMouse.setBounds(5, 105, 175, 20);
		this.jlblMouse.setFont(new Font("Arial", Font.PLAIN, 15));
		this.add(this.jlblMouse);

		this.jlblZ = new JLabel("Z: Incendiario");
		this.jlblZ.setBounds(5, 120, 100, 20);
		this.jlblZ.setFont(new Font("Arial", Font.PLAIN, 15));
		this.add(this.jlblZ);

		this.jlblX = new JLabel("X: Plomo");
		this.jlblX.setBounds(5, 135, 100, 20);
		this.jlblX.setFont(new Font("Arial", Font.PLAIN, 15));
		this.add(this.jlblX);

		this.jlblC = new JLabel("C: Roca");
		this.jlblC.setBounds(5, 150, 100, 20);
		this.jlblC.setFont(new Font("Arial", Font.PLAIN, 15));
		this.add(this.jlblC);

		this.jlblE = new JLabel("E: Aumenta angulo");
		this.jlblE.setBounds(5, 165, 150, 20);
		this.jlblE.setFont(new Font("Arial", Font.PLAIN, 15));
		this.add(this.jlblE);

		this.jlblR = new JLabel("R: Disminuir angulo");
		this.jlblR.setBounds(5, 180, 150, 20);
		this.jlblR.setFont(new Font("Arial", Font.PLAIN, 15));
		this.add(this.jlblR);

		this.jlblBloc = new JLabel("Bloc Mayus: Usar Catapulta");
		this.jlblBloc.setBounds(5, 200, 200, 20);
		this.jlblBloc.setFont(new Font("Arial", Font.PLAIN, 15));
		this.add(this.jlblBloc);
//		this.jlblMensaje = new JLabel("Instrucciones:");
//		this.jlblMensaje.setBounds(5, 5, 70, 20);
//		this.jlblMensaje.setFont(new Font("Arial", Font.PLAIN, 15));
//		this.add(this.jlblMensaje);
	}

}
