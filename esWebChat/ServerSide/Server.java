package ServerSide;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server {
	private ServerSocket serverSocket;
	private Socket socket;
	private HashMap<String, ClientHandler> userList;
	private int port = 61920;

	public Server() {
		userList = new HashMap<>();
	}

	public void listen() {

		try {
			serverSocket = new ServerSocket(port);

			// Loop infinito per gestire le richieste dei client
			while (true) {
				// Accetto la richiesta in arrivo
				socket = serverSocket.accept();
				System.out.println("Nuova richiesta da un client " + socket);

				// Creo un nuovo oggetto per gestire questa richiesta.

				ClientHandler ch = new ClientHandler(socket, this);
				// Creo un thread per questo oggetto
				Thread t = new Thread(ch);
				// starto il thread
				t.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean addClientToList(String username, ClientHandler ch) {
		if(userList.containsKey(username)) return false;
		userList.put(username, ch);
		return true;
	}

	public void sendToAll(String msg) {
		for (ClientHandler ch : userList.values()) {
			try {
				ch.getOutput().writeUTF(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void sendToOne(String username,String sender ,String msg) {
		try {
			userList.get(username).getOutput().writeUTF(sender + "<Privato> : "+msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendListOfClient(String username) {
		DataOutputStream dos = userList.get(username).getOutput();
		try {
			dos.writeUTF("@ls@");
			dos.writeUTF(""+userList.size());
			for (String s : userList.keySet()) {
				dos.writeUTF(s);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void closeConnection(String clientToRemove, ClientHandler ch) {
		try {
			System.out.println("rimozione "+ch.getSocket());
			ch.getOutput().close();
			ch.getInput().close();
			ch.getSocket().close();
			userList.remove(clientToRemove,ch);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Server().listen();
	}
}