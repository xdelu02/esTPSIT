import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class ServerListener {

	public static ArrayList<ClientHandler> ar = new ArrayList<>();

	public static void main(String[] args) {
		ServerSocket ss = null;
		try {
			ss = new ServerSocket(60000);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Socket s = null;

		// Loop infinito per gestire le richieste dei client
		while (true) {
			// Accetto la richiesta in arrivo
			try {
				s = ss.accept();
				System.out.println("Nuova richiesta da un client " + s);
			} catch (IOException e) {
				e.printStackTrace();
			}

			// ottengo gli input e output streams
			DataInputStream dis = null;
			DataOutputStream dos = null;
			try {
				dis = new DataInputStream(s.getInputStream());
				dos = new DataOutputStream(s.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}

			// Creo un nuovo oggetto per gestire questa richiesta.

			ClientHandler ch = new ClientHandler(s, ar.size(), dis, dos);   //ar.size() è per assegnare un identificativo al client handler
			// Creo un thread per questo oggetto
			Thread t = new Thread(ch);

			// lo aggiungo alla lista dei client
			ar.add(ch);

			// starto il thread
			t.start();
		}
	}
} 
  