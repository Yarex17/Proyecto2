package Domain;

import java.util.ArrayList;

public class Usuario implements Comparable<Usuario> {

	private String nombre;
	private String contrasenna;
	private String rutaSkin;
	private int cantidadVictorias;
	private ArrayList<String> amigos;

	public Usuario(String nombre, String contrasenna, String rutaSkin, int cantidadVictorias) {
		this.nombre = nombre;
		this.contrasenna = contrasenna;
		this.rutaSkin = rutaSkin;
		this.cantidadVictorias = cantidadVictorias;
		this.amigos = new ArrayList<String>();
	}

	public Usuario(String nombre, String contrasenna, String rutaSkin, int cantidadVictorias,
			ArrayList<String> amigos) {
		this.nombre = nombre;
		this.contrasenna = contrasenna;
		this.rutaSkin = rutaSkin;
		this.cantidadVictorias = cantidadVictorias;
		this.amigos = amigos;
	}

	public Usuario(String nombre, String contrasenna) {
		this.nombre = nombre;
		this.contrasenna = contrasenna;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getContrasenna() {
		return contrasenna;
	}

	public void setContrasenna(String contrasenna) {
		this.contrasenna = contrasenna;
	}

	public String getRutaSkin() {
		return rutaSkin;
	}

	public void setRutaSkin(String rutaSkin) {
		this.rutaSkin = rutaSkin;
	}

	public int getCantidadVictorias() {
		return cantidadVictorias;
	}

	public void setCantidadVictorias(int cantidadVictorias) {
		this.cantidadVictorias = cantidadVictorias;
	}

	public ArrayList<String> getAmigos() {
		return amigos;
	}

	public void setAmigos(ArrayList<String> amigos) {
		this.amigos = amigos;
	}

	@Override
	public int compareTo(Usuario criterio) {
		return criterio.cantidadVictorias - this.cantidadVictorias;
	}

}
