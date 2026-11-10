import java.net.ServerSocket;

public class ChatServer {
    public static void main(String[] args) {
        boolean acceptingConnections = true;
        // Open a socket which receives packets from connected network with port 500
        try (ServerSocket serverSocket = new ServerSocket(500)) {
            System.out.println("Server started. Listening to port 500");
            while (acceptingConnections) {
                new ClientConnection(serverSocket.accept()).start();
                System.out.println("Client connected!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}