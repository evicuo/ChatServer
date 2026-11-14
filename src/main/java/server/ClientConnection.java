package server;

import protocol.Packet;
import protocol.PacketIO;
import protocol.Protocol;

import java.io.*;
import java.net.Socket;

public class ClientConnection implements Runnable {

    private final Socket socket;
    private ChatRoom chatRoom;

    private DataInputStream inpStream;
    private DataOutputStream outStream;

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
            while(true){
                Packet packet = PacketIO.read(inpStream);
                System.out.println(packet.getText());
                handle(packet);
            }
        } catch (EOFException e) {
            System.out.println("Client left");
        } catch (IOException e){

        } finally {
            exit();
        }
    }

    /**
     * Executes methods based on type of packet the client received
     * @param packet The packet object created from byte packet
     */
    private void handle(Packet packet){
        if(packet.getType() == Protocol.CHAT){
            chatRoom.broadcast(this, packet.getText());
        }
    }

    /**
     * Sends a packet object to this client
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
    public void exit(){
        try{
            socket.close();
            chatRoom.removeClient(this);
            System.out.println("Client connection closed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
