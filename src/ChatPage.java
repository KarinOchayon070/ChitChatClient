/* Developers details:
   - Karin Ochayon, 207797002
   - Dor Uzan, 205890510
*/

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ChatPage extends JPanel {

    // Declare a private instance variable named mainFrame of type JFrame
    private JFrame mainFrame;
    private SimpleTCPClient tcpClient;

    public void setTCPClient(SimpleTCPClient client) {
        this.tcpClient = client;
    }


    public ChatPage(JFrame frame) {
        mainFrame = frame; // Allows the ChatPage instance to have access to the same JFrame object as the calling code
        setLayout(new BorderLayout()); // North, South, East, West, and Center
        setBackground(Color.WHITE); // Make the background to be white


        // ----------------------------------------------------- TOP PANEL -----------------------------------------------------

        // Top Panel: Title (image of "ChitChat" title), Server, Port, Connect and Disconnect
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        topPanel.setBackground(Color.white);

        // Title Image
        ImageIcon titleIcon = new ImageIcon(getClass().getResource("images/title.png"));
        JLabel titleLabel = new JLabel(titleIcon);

        // NickName, Server, Port, Connect, and Disconnect
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(Color.WHITE);

        // NickName
        JLabel nickNameLabel = new JLabel("NickName:");
        JTextField nickNameField = new JTextField(10);
        nickNameField.setBackground(Color.WHITE);

        // Server
        JLabel serverLabel = new JLabel("Server:");
        JTextField serverField = new JTextField(10);
        serverField.setBackground(Color.WHITE);

        // Port
        JLabel portLabel = new JLabel("Port:");
        JTextField portField = new JTextField(10);
        portField.setBackground(Color.WHITE);

        // Connect
        JButton connectButton = new JButton("Connect");
        connectButton.setBackground(new Color(120, 9, 247));
        connectButton.setForeground(Color.WHITE);
        connectButton.setFont(new Font("Arial", Font.BOLD, 14));
        connectButton.setEnabled(false);

        // Disconnect
        JButton disconnectButton = new JButton("Disconnect");
        disconnectButton.setBackground(new Color(120, 9, 247));
        disconnectButton.setForeground(Color.WHITE);
        disconnectButton.setFont(new Font("Arial", Font.BOLD, 14));
        disconnectButton.setEnabled(false);

        // Add the components to the buttonPanel
        buttonPanel.add(nickNameLabel);
        buttonPanel.add(nickNameField);
        buttonPanel.add(serverLabel);
        buttonPanel.add(serverField);
        buttonPanel.add(portLabel);
        buttonPanel.add(portField);
        buttonPanel.add(connectButton);
        buttonPanel.add(disconnectButton);

        // Add the components to the topPanel
        topPanel.add(titleLabel, BorderLayout.NORTH);
        topPanel.add(buttonPanel, BorderLayout.CENTER);

        // ----------------------------------------------------- MIDDLE PANEL -----------------------------------------------------

        // Middle Panel: Chat Messages
        JTextArea chatMessagesArea = new JTextArea();
        chatMessagesArea.setEditable(false);
        chatMessagesArea.setBackground(Color.WHITE);
        chatMessagesArea.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane chatScrollPane = new JScrollPane(chatMessagesArea);

        // ----------------------------------------------------- BOTTOM PANEL -----------------------------------------------------

        // Bottom Panel: User input, ComboBox (to which user to send the message?) and Send Button
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(new EmptyBorder(10, 20, 20, 20));
        bottomPanel.setBackground(Color.white);

        // User input
        JTextField userInputField = new JTextField();
        userInputField.setPreferredSize(new Dimension(400, 30));

        // ComboBox (to which user to send the message?)
        JComboBox<String> recipientComboBox = new JComboBox<>();
        recipientComboBox.setPreferredSize(new Dimension(120, 30));
        recipientComboBox.setBackground(Color.WHITE);

        // "Send" button
        JButton sendButton = new JButton("Send");
        sendButton.setBackground(new Color(120, 9, 247));
        sendButton.setForeground(Color.WHITE);
        sendButton.setFont(new Font("Arial", Font.BOLD, 14));

        // Add the components to the bottomPanel
        bottomPanel.add(userInputField, BorderLayout.CENTER);
        bottomPanel.add(recipientComboBox, BorderLayout.WEST);
        bottomPanel.add(sendButton, BorderLayout.EAST);

        // ----------------------------------------------------- ADD ALL -----------------------------------------------------

        add(topPanel, BorderLayout.NORTH);
        add(chatScrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
}
