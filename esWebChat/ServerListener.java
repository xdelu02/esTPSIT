import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class ServerListener {
	private ServerSocket serverSocket;
	private Socket socket;
	private ArrayList<ClientHandler> userList;

	public ServerListener() {
		userList = new ArrayList<>();
	}

	public void listen() {

		try {
			serverSocket = new ServerSocket(60000);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Loop infinito per gestire le richieste dei client
		while (true) {
			// Accetto la richiesta in arrivo
			try {
				socket = serverSocket.accept();
				System.out.println("Nuova richiesta da un client " + socket);
			} catch (IOException e) {
				e.printStackTrace();
			}

			// Creo un nuovo oggetto per gestire questa richiesta.

			ClientHandler ch = new ClientHandler(socket, userList.size(), this);   //ar.size() è per assegnare un identificativo al client handler
			// Creo un thread per questo oggetto
			Thread t = new Thread(ch);

			// lo aggiungo alla lista dei client
			userList.add(ch);

			// starto il thread
			t.start();
		}
	}

	public void closeConnection(ClientHandler ch) {
		try {
			ch.getOutput().close();
			ch.getInput().close();
			ch.getSocket().close();
			userList.remove(ch);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendToAll(String msg) {
		for (ClientHandler ch : userList) {
			try {
				ch.getOutput().writeUTF(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new ServerListener().listen();
	}
} 
  