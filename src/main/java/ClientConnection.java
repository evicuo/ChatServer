import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientConnection extends Thread {
    private Socket socket = null;
    public ClientConnection(Socket socket){
        super("ConnectionThread");
        this.socket = socket;
    }

    public void run(){
        try (BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()))){
            String line;
            while((line = input.readLine()) != null ){
                System.out.println("Message: " + line);
            }
            socket.close();
            System.out.println("Exited thread");
        } catch(IOException e){
            e.printStackTrace();
        }
    }

}
