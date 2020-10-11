import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    int portaServer = 9999;
    Socket miosocket;
    BufferedReader tastiera;
    String stringaUtente;
    String stringaDalServer;
    DataOutputStream outVersoServer;
    BufferedReader inDalServer;
    //byte[] ipAddr = new byte[] { (byte) 79, (byte) 43, (byte) 21, (byte) 9 };

    public Socket connetti() {
        System.out.println("Benvenuto nella calcolatrice client-server \nPremere Ctrl + D per terminare la connessione \n");
        try {
            tastiera = new BufferedReader(new InputStreamReader(System.in));
            //InetAddress address = InetAddress.getByAddress(ipAddr);
            miosocket = new Socket(InetAddress.getLocalHost(), portaServer);
            outVersoServer = new DataOutputStream(miosocket.getOutputStream());
            inDalServer = new BufferedReader(new InputStreamReader(miosocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Host sconosciuto");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Errore durante la connessione!");
            System.exit(1);
        }
        return miosocket;
    }

    public void comunica() {
        try {
            while (true) {
                for (int i=0; i<3; i++) {
                    System.out.println(inDalServer.readLine());
                    outVersoServer.writeBytes(tastiera.readLine() + '\n');
                }
                System.out.println(inDalServer.readLine() + '\n');
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
}