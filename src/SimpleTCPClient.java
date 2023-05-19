import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class SimpleTCPClient {
    private Socket socket;

    public void connect(String serverIP, int port) throws IOException {
        socket = new Socket(serverIP, port);
        // Perform any additional setup or communication with the server here
    }

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("serverIpAddress", 1300);
            System.out.println("Connected to server: " + socket.getInetAddress().getHostAddress());

            // Get the input and output streams for reading from and writing to the server
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream());

            // Send data to the server
            writer.write("Hello server!\n");
            writer.flush();

            // Receive data from the server
            String response = reader.readLine();
            System.out.println("Server response: " + response);

            // Close the socket
            socket.close();
            System.out.println("Socket closed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
