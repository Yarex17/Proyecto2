package Domain;

public class Partida {

	private String nombre;

	public Partida(String nombre, String tipoPartida) {
		this.nombre = nombre;

	}

	public Partida(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String toString() {
		return nombre;
	}

}
