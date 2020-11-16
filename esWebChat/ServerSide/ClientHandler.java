package ServerSide;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.StringTokenizer;

class ClientHandler implements Runnable {
	private DataInputStream input;
	private DataOutputStream output;
	private Server server;
	private Socket socket;
	private String username;

	public ClientHandler(Socket socket, Server server) {
		this.server = server;
		this.socket = socket;
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
		} catch (IOException e) {
		}

		while (true) {
			try {
				received = input.readUTF();

				if (server.addClientToList(received, this)) {
					output.writeUTF("Username accettato");
					username = received;
					break;
				}
				output.writeUTF("Username non accettato");
			} catch (IOException e) {
			}
		}
		while (true) {

			try {
				// receive the string
				received = input.readUTF();

				if (received.equals("@ls@")) {
					server.sendListOfClient(username);
				} else if (received.equals("@close@")) {
					server.closeConnection(username, this);
				} else {
					try {
						StringTokenizer st = new StringTokenizer(received, "#");
						String MsgToSend = st.nextToken();
						String recipient = st.nextToken();
						server.sendToOne(recipient, username, MsgToSend);
						server.sendToOne(username, username, MsgToSend);
					} catch (Exception e) {
						server.sendToAll(username + " : " + received);
					}
				}


			} catch (IOException e) {
			}
		}
	}

}