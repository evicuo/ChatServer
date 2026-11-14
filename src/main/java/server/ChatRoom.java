package server;

import protocol.Packet;
import protocol.Protocol;

import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

public class ChatRoom {
    /**
     * Thread-safe arraylist which holds active client connections (ClientConnection)
     **/
    private CopyOnWriteArrayList<ClientConnection> chatRoom;

    /**
     * Construct a new ChatRoom
     */
    public ChatRoom(){
        chatRoom = new CopyOnWriteArrayList<>();
    }

    /**
     * Check whether a client is contained within chat room list.
     * @param clientSocket Client Connection object
     * @return Boolean: Client is/isn't (true/false) in chat room list.
     */
    public boolean isInChatRoom(ClientConnection clientSocket){
        return chatRoom.contains(clientSocket);
    }

    /**
     * Remove a client from the chat room
     * @param clientConnection Client connection object
     */
    public void removeClient(ClientConnection clientConnection){
        chatRoom.remove(clientConnection);
    }

    /**
     * Add a client to the chat room
     * @param clientConnection Client connection object
     */
    public void addClient(ClientConnection clientConnection){
        chatRoom.add(clientConnection);
    }

    /**
     * Broadcasts a message sent from a client to all other clients contained within the chatroom
     * @param sender Sender of message to be broadcast
     * @param string Message to be broadcast
     */
    public void broadcast(ClientConnection sender, String string){
        chatRoom.stream()
                .filter(c -> c!= sender)
                .forEach(c -> {
                    try {
                        c.send(new Packet(Protocol.CHAT, "Broadcast: " + string));
                    } catch (EOFException e) {
                        System.out.println("Attempted to send to a client, client does not exist");
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                });
    }
}