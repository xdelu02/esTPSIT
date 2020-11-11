import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

class ClientHandler implements Runnable {
	private DataInputStream input;
	private DataOutputStream output;
	private ServerListener server;
	private final int posClient;
	private Socket socket;
	boolean isloggedin;
	private String username;

	// get
	public ClientHandler(Socket socket, int pos, ServerListener server) {
		this.server = server;
		posClient = pos;
		this.socket = socket;
		this.isloggedin = true;
	}

	public DataInputStream getInput() {
		return input;
	}

	public DataOutputStream getOutput() {
		return output;
	}

	public Socket getSocket() {
		return socket;
	}

	public String getUsername() {
		return username;
	}

	@Override
	public void run() {
		String received;
		try {
			input = new DataInputStream(socket.getInputStream());
			output = new DataOutputStream(socket.getOutputStream());
			username = input.readUTF();
		} catch (IOException e) {
			e.printStackTrace();
		}

		while (true) {
			try {
				// receive the string
				received = input.readUTF();
				if (received.equals("@@@CHIUDI***")) {
					server.closeConnection(this);
				}
				server.sendToAll(this.username + " : " + received);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}