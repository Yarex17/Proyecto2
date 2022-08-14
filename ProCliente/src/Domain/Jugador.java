package Domain;

import java.io.IOException;

public class Jugador {

	private String nombre;
	private String rutaGuerrero;
	private Guerrero guerrero;

	public Jugador(int cordX, int cordY, String rutaImagen, Equipo equipo) throws IOException {
		this.nombre = "";
		this.rutaGuerrero = "";
		this.guerrero = new Guerrero(cordX, cordY, rutaImagen, equipo);
	}

	public Guerrero getGuerrero() {
		return guerrero;
	}

	public void setGuerrero(Guerrero guerrero) {
		this.guerrero = guerrero;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRutaGuerrero() {
		return rutaGuerrero;
	}

	public void setRutaGuerrero(String rutaGuerrero) {
		this.rutaGuerrero = rutaGuerrero;
	}

}
