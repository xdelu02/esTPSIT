public class mainServer {
    public static void main(String[] args) {
        ServerCalcolatrice servente = new ServerCalcolatrice();
        servente.attendi();
        servente.comunica();
    }
}