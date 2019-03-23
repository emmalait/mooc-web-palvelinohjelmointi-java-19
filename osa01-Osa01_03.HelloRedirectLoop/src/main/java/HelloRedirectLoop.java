
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class HelloRedirectLoop {

    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(8080);
        int requests = 0;
        
        while (true) {
            Socket socket = server.accept();
            Scanner reader = new Scanner(socket.getInputStream());
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            
            String input = reader.nextLine();
            
            requests++;
            System.out.println("Request #" + requests);
            
            if (input.contains("/quit")) {
                break;
            }
            
            writer.println("HTTP/1.1 302 Found");
            writer.println("Location: http://localhost:8080");
            writer.println();
            
            writer.flush();   
            
            writer.close();
            reader.close();
            socket.close();
        }

    }
}
