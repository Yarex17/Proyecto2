package Domain;

import java.io.IOException;
import java.util.ArrayList;

public class ListaPartida {

	private static ListaPartida singletonPartidas;
	private ArrayList<Partida> partidas;

	private ListaPartida() {
		this.partidas = new ArrayList<>();
	}

	public static ListaPartida obtenerInstancia() throws IOException {
		if (singletonPartidas == null) {
			singletonPartidas = new ListaPartida();
		}
		return singletonPartidas;
	}// obtenerInstancia

	public ArrayList<Partida> getPartidas() {
		return partidas;
	}

	public void setPartidas(ArrayList<Partida> partidas) {
		this.partidas = partidas;
	}

}
