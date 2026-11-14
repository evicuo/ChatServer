package client;

import protocol.Packet;
import protocol.PacketIO;
import protocol.Protocol;
import server.ClientConnection;

import javax.xml.crypto.Data;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient implements Runnable {
    private final Socket socket;
    private final DataInputStream inpStream;
    private final DataOutputStream outStream;

    public ChatClient() throws IOException {
            socket = new Socket("localhost", 52123);
            inpStream = new DataInputStream(socket.getInputStream());
            outStream = new DataOutputStream(socket.getOutputStream());
    }

    public static void main(String[] args ) throws IOException {
        ChatClient client1 = new ChatClient();
        new Thread(client1).start();
        client1.readUserInputs();
    }

    @Override
    public void run() {
        listenAndPrintIncomingPackets();
    }

    public void listenAndPrintIncomingPackets(){
        try{
            while(true){
                System.out.println((PacketIO.read(inpStream).getText()));
            }
        } catch(EOFException e){
            e.printStackTrace();
        }  catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void readUserInputs() throws IOException {
        Scanner keyboard = new Scanner(System.in);
        while(keyboard.hasNextLine()){
            String message = keyboard.nextLine();
            Packet packet = new Packet(Protocol.CHAT, message);
            PacketIO.write(outStream, packet);
        }
        socket.close();

    }

}
