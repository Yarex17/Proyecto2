package Domain;

import java.io.IOException;
import java.util.ArrayList;

public class ListaCliente {

	private static ListaCliente singletonPartidasJugadores;
	private ArrayList<ClientServidor> clientes;

	private ListaCliente() { 
		this.clientes = new ArrayList<>();
	}

	public static ListaCliente obtenerInstancia() throws IOException {
		if (singletonPartidasJugadores == null) {
			singletonPartidasJugadores = new ListaCliente();
		}
		return singletonPartidasJugadores;
	}// obtenerInstancia

	public ArrayList<ClientServidor> getClientes() { 
		return clientes;
	}

	public void setAtiendeCliente(ArrayList<ClientServidor> atiendeCliente) {
		this.clientes = atiendeCliente;
	}

}
