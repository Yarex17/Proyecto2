package Main;

import java.io.IOException;

import Domain.Server;


public class MainServer {

	public static void main(String[] args) {
		try {
			
			Server servidor = new Server(5025);
			servidor.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
