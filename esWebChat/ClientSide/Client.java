package ClientSide;

import java.io.*;
import java.net.Socket;

public class Client {
	private final int ServerPort = 61920;
	private final String hostname = "localhost";
	private String username;
	private DataInputStream input;
	private DataOutputStream output;
	private Socket socket;

	public Client() {
		try {
			socket = new Socket(hostname, ServerPort);
			input = new DataInputStream(socket.getInputStream());
			output = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean setUsername(String name) {
		sendMessage(name);
		if (readMessage().equals("Username accettato")) {
			username = name;
			return true;
		}
		return false;
	}

	public String getUsername() {
		return username;
	}

	public void sendMessage(String msg) {
		try {
			output.writeUTF(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String readMessage() {
		try {
			return input.readUTF();
		} catch (IOException e) {
		}
		return null;
	}

	public void closeConnection() {
		try {
			output.close();
			input.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
