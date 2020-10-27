import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
	final static int ServerPort = 60000;
	private DataInputStream dis;
	private DataOutputStream dos;
	private Socket s;
	private InetAddress ip;

	public Client() {
		try {
			ip = InetAddress.getByName("localhost");
			s = new Socket(ip, ServerPort);
			dis = new DataInputStream(s.getInputStream());
			dos = new DataOutputStream(s.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendMessage(String msg) {
		try {
			// write on the output stream
			dos.writeUTF(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String readMessage() {
		try {
			return dis.readUTF();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
