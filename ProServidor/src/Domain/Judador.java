package Domain;

import java.util.Objects;

public class Judador {
	
	private String nombre;
	private String rutaSkin;
	private String partida;

	public Judador(String nombre, String rutaSkin, String partida) {
	
		this.nombre = nombre;
		this.rutaSkin = rutaSkin;
		this.partida = partida;
	}
	
	public Judador() {
		this.nombre = "";
		this.rutaSkin = "";
		this.partida = "";
	}
	
	
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRutaSkin() {
		return rutaSkin;
	}

	public void setRutaSkin(String rutaSkin) {
		this.rutaSkin = rutaSkin;
	}

	public String getPartida() {
		return partida;
	}

	public void setPartida(String partida) {
		this.partida = partida;
	}

	@Override
	public int hashCode() {
		return Objects.hash(nombre, partida, rutaSkin);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Judador other = (Judador) obj;
		return Objects.equals(nombre, other.nombre) && Objects.equals(partida, other.partida)
				&& Objects.equals(rutaSkin, other.rutaSkin);
	}	
	
}
