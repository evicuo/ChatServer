package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

class ChatServer {
    private final int PORT = 52123;
    private final boolean acceptingConnections = true;
    private ChatRoom chatRoom;


    static void main(String[] args) {
        ChatServer server = new ChatServer();
        server.start();
    }

    /**
     * Starts a server which accepts client connections via defined port
     *
     */
    public void start() {

        try (ServerSocket serSocket = new ServerSocket(PORT)) {
            System.out.println("Server started successfully!");
            chatRoom = new ChatRoom();

            while (acceptingConnections) {
                Socket clientSocket = serSocket.accept();
                try {
                    acceptClient(clientSocket);
                } catch (IOException e){
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Takes an accepted socket, creates a new ClientConnection, and starts a new thread to handle
     *
     * @param clientSocket
     * @throws IOException
     */

    private final void acceptClient(Socket clientSocket) throws IOException {
        Runnable clientConnection = new ClientConnection(clientSocket, chatRoom);
        Thread clientThread = new Thread(clientConnection);
        clientThread.start();
    }

    public void stop(ServerSocket serSocket) {
        try {
            serSocket.close();
            System.out.println("Server socket closed");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}