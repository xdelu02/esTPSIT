import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerCalcolatrice {
    ServerSocket server = null;
    Socket client = null;
    String stringaRicevuta = null;
    int primoNumero=0, secondoNumero=0, ris=0;
    String risultato = "";
    BufferedReader inDalClient;
    DataOutputStream outVersoClient;

    //metodo che effettua la connessione

    public void attendi() {
        try {
            System.out.println("Server in esecuzione...");
            //creo un server sulla porta 9999
            server = new ServerSocket(9999);
            //rimane in attesa di un client
            client = server.accept();
            //chiudo la porta del server per vietare l'accesso ad altri client (comunicazione unicast)
            server.close();
            //associo due oggetti al socket del client per effettuare la scrittura e la lettura
            inDalClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
            outVersoClient = new DataOutputStream(client.getOutputStream());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("errore durante l'istanza del server");
            System.exit(1);
        }
    }

    public void comunica() {
        try {
            while (true) {
                outVersoClient.writeBytes("Inserisci il primo numero" + '\n');
                primoNumero = Integer.parseInt(inDalClient.readLine());
                outVersoClient.writeBytes("Inserisci il secondo numero" + '\n');
                secondoNumero = Integer.parseInt(inDalClient.readLine());
                outVersoClient.writeBytes("Inserisci il segno matematico (+ - * /)" + '\n');
                stringaRicevuta = inDalClient.readLine() + " ";
                switch (stringaRicevuta) {
                    case "+ ":
                        ris = primoNumero + secondoNumero;
                        break;
                    case "- ":
                        ris = primoNumero - secondoNumero;
                        break;
                    case "* ":
                        ris = primoNumero * secondoNumero;
                        break;
                    case "/ ":
                        ris = primoNumero / secondoNumero;
                        break;
                }
                outVersoClient.writeBytes("Risultato: " + ris + '\n');
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}