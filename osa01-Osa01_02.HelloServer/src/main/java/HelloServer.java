

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class HelloServer {

    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(8080);
        
        while (true) {
            Socket socket = server.accept();
            Scanner reader = new Scanner(socket.getInputStream());
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            
            String input = reader.nextLine();
            System.out.println(input);
            
            if (input.contains("/quit")) {
                break;
            }
            
            writer.println("HTTP/1.1 200 OK");
            writer.println("Content-Type: text/html");
            writer.println();
            List<String> file = Files.readAllLines(Paths.get("index.html"));
            for (String line : file) {
                writer.println(line);
            }
            writer.flush();   
            
            writer.close();
            reader.close();
            socket.close();
        }

    }
}
