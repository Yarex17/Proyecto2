package Domain;

import java.util.ArrayList;

public class Partida {
	
	private String nombre;
	private String chat;
	private ArrayList<ClientServidor> clientespartida;


	
	public Partida(String nombre) {
		this.clientespartida=new ArrayList<ClientServidor>();
		this.nombre = nombre;
	}

	public String getChat() {
		return chat;
	}

	public void setChat(String chat) {
		this.chat = chat;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public ArrayList<ClientServidor> getClientespartida() {
		return clientespartida;
	}

	public void setClientespartida(ArrayList<ClientServidor> clientespartida) {
		this.clientespartida = clientespartida;
	}

}
