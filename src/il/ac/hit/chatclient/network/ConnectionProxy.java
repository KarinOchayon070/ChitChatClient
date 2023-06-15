/*
 Developers details:
   - Karin Ochayon, 207797002
   - Dor Uzan, 205890510
*/

/*
    This file (ConnectionProxy.java) defines a ConnectionProxy class that facilitates communication with a server over a socket connection.
    - The constructor initializes a socket connection with the specified server IP and port, and creates the necessary input and output streams.
    - The sendMessage method sends a message to the server by writing it to the output stream.
    - The receiveMessage method reads a message from the server by reading from the input stream.
    - The isConnected method checks if the socket connection is currently open.
    - The close method closes the socket connection and releases associated resources.
 */

package il.ac.hit.chatclient.network;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectionProxy {

    // Socket object for the connection
    private Socket socket;

    // PrintWriter object for writing data to the server
    private PrintWriter out;

    // BufferedReader object for reading data from the server
    private BufferedReader in;


    /**
     * Constructor for creating a connection proxy with the specified server IP and port
     * @param serverIP The server IP address
     * @param port The port number
     * @throws IOException If an I/O error occurs when creating the connection
     */
    ConnectionProxy(String serverIP, int port) throws IOException {
        this.socket = new Socket(serverIP, port);
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    /**
     * Sends a message to the server
     * @param message The message to be sent
     */
    public void sendMessage(String message) {
        this.out.println(message);
    }

    /**
     * Receives a message from the server
     * @return The received message
     * @throws IOException If an I/O error occurs when receiving the message
     */
    public String receiveMessage() throws IOException {
        return this.in.readLine();
    }

    /**
     * Checks if the connection is currently open
     * @return true if the connection is open, false otherwise
     */
    public boolean isConnected() {
        return this.socket.isConnected();
    }

    /**
     * Closes the connection
     * @throws IOException If an I/O error occurs when closing the connection
     */
    public void close() throws IOException {
        this.socket.close();
        this.in.close();
        this.out.close();
    }
}