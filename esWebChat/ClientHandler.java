import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

class ClientHandler implements Runnable {
	final DataInputStream dis;
	final DataOutputStream dos;
	private final int posClient;
	Socket s;
	boolean isloggedin;
	private String name;
	private int posClientDestinatario;

	// constructor
	public ClientHandler(Socket s, int pos, DataInputStream dis, DataOutputStream dos) {
		this.dis = dis;
		this.dos = dos;
		posClient = pos;
		this.s = s;
		this.isloggedin = true;
		setPosClientDestinatario();
	}

	private void setPosClientDestinatario() {
		if (posClient == 0) posClientDestinatario++;
	}

	public String getName() {
		return name;
	}

	@Override
	public void run() {
		String received;

		try {
			name = dis.readUTF();
			UIclientList.addClientToList(name);
		} catch (IOException e) {
			e.printStackTrace();
		}

		while (true) {
			try {
				// receive the string
				received = dis.readUTF();

				try {
					ServerListener.ar.get(posClientDestinatario).dos.writeUTF(this.name + " : " + received);
				}
				catch (Exception e){
					ServerListener.ar.get(posClient).dos.writeUTF("@@@NONONLINE###");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
}