package server;

import protocol.Packet;
import protocol.PacketIO;
import protocol.Protocol;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

public class ClientConnection implements Runnable {

    private final Socket socket;
    private final ChatRoom chatRoom;

    private final DataInputStream inpStream;
    private final DataOutputStream outStream;

    public ClientConnection(Socket socket, ChatRoom chatRoom) throws IOException {
        this.socket = socket;
        this.chatRoom = chatRoom;

        this.outStream = new DataOutputStream(socket.getOutputStream());
        this.inpStream = new DataInputStream(socket.getInputStream());
    }


    /**
     * Method that runs when server acknowledges a client joining.
     * Listens to client's input stream for incoming packets, instantiates packet object
     * Prints message from packet to server output
     * Sends packet to handle method
     */
    public void run() {
        chatRoom.addClient(this);
        try {
            while (true) {
                Packet packet = PacketIO.read(inpStream).orElseThrow();
                System.out.println(packet.text());
                handle(packet);
            }
        } catch (EOFException e) {
            System.out.println("Client left");
        } catch (IOException e) {
            System.out.println("Internal error occured");
        } finally {
            exit();
        }
    }

    /**
     * Executes methods based on type of packet the client received
     *
     * @param packet The packet object created from byte packet
     */
    private synchronized void handle(Packet packet) {
        if (packet.type() == Protocol.CHAT) {
            chatRoom.broadcast(this, packet.text());
        }
    }

    /**
     * Sends a packet object to this client
     *
     * @param packet
     * @throws IOException
     */
    public void send(Packet packet) throws IOException {
        PacketIO.write(outStream, packet);
    }


    /**
     * Closes client socket and removes client from chatroom
     * To be executed upon client disconnect/crash
     */
    public void exit() {
        try {
            socket.close();
            chatRoom.removeClient(this);
            System.out.println("Client connection closed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
