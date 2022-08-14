package Domain;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server extends Thread {

	private int socketPortNumber;
	private ServerSocket serverSocket;
	private InetAddress address;
	private ListaCliente listaCliente;
	private ListaPartida partidas;
 
	public Server(int socketPortNumber) throws IOException {
		this.socketPortNumber = socketPortNumber;
		this.serverSocket = new ServerSocket(this.socketPortNumber);
		this.address = InetAddress.getLocalHost();
		this.listaCliente=ListaCliente.obtenerInstancia();
		this.partidas = ListaPartida.obtenerInstancia();
	} // constructor

	public void run() {
		try {
			while (true) {
				//System.out.println(address);
				Socket socket = this.serverSocket.accept();
				ClientServidor clientServidor = new ClientServidor(socket);
				clientServidor.start();
			} // while true
		} catch (IOException e) {
			e.printStackTrace();
		}
	} // run

	public int getSocketPortNumber() {
		return socketPortNumber;
	}

	public void setSocketPortNumber(int socketPortNumber) {
		this.socketPortNumber = socketPortNumber;
	}

	public ServerSocket getServerSocket() {
		return serverSocket;
	}

	public void setServerSocket(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}

	public InetAddress getAddress() {
		return address;
	}

	public void setAddress(InetAddress address) {
		this.address = address;
	}

} // fin clase
