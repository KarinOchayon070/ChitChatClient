/* Developers details:
   - Karin Ochayon, 207797002
   - Dor Uzan, 205890510
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectionProxy {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    ConnectionProxy(String serverIP, int port) throws IOException {
        this.socket = new Socket(serverIP, port);
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void sendMessage(String message) {
        this.out.println(message);
    }

    public String receiveMessage() throws IOException {
        return this.in.readLine();
    }

    public boolean isConnected() {
        return this.socket.isConnected();
    }

    public void close() throws IOException {
        this.socket.close();
        this.in.close();
        this.out.close();
    }
}