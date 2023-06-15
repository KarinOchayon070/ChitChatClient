/*
 Developers details:
   - Karin Ochayon, 207797002
   - Dor Uzan, 205890510
*/

/*
    This file (ChatPage.java) defines a JPanel-based chat page component for a chat application,
    providing UI elements such as input fields, buttons, and message rendering.
*/

package il.ac.hit.chatclient.view;
import il.ac.hit.chatclient.objects.Message;
import il.ac.hit.chatclient.network.SimpleTCPClient;
import il.ac.hit.chatclient.state.ConnectedState;
import il.ac.hit.chatclient.state.ConnectionState;
import il.ac.hit.chatclient.state.DisconnectedState;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChatPage extends JPanel {

    // Represents the main frame of the application, which is a window that contains all the graphical components of the user interface
    private JFrame mainFrame;

    // Represents the TCP client used for communication with the server
    private SimpleTCPClient tcpClient;

    // Represents the text field for entering the server address
    private JTextField serverField;

    // Represents the container panel for displaying chat messages
    private JPanel chatContainer;

    // Represents the button for disconnecting from the server
    private JButton disconnectButton;

    // Represents the button for connecting to the server
    private JButton connectButton;

    // Represents the button for sending messages
    private JButton sendButton;

    // Represents the text field for entering the recipient of the message
    private JTextField recipientInputField;

    // Represents the text field for entering the user's message
    private JTextField userInputField;

    // Represents the text field for entering the port number
    private JTextField portField;

    // Represents the text field for entering the user's nickname
    private JTextField nickNameField;

    // Represents the current connection state of the chat application
    private ConnectionState currentState;

    /***
     * Represents the chat page panel within the application
     * @param frame The main JFrame instance
     */
    public ChatPage(JFrame frame) {

        // Set the mainFrame variable to the provided JFrame instance
        mainFrame = frame;

        // Set the layout of the panel to BorderLayout
        setLayout(new BorderLayout());

        // Set the background color of the panel to white
        setBackground(Color.WHITE);

        // Set the initial state of the connection to DisconnectedState
        currentState = new DisconnectedState();

        // Create a panel for the top section of the chat page, using BorderLayout
        JPanel topPanel = new JPanel(new BorderLayout());

        // Set the border of the top panel to create spacing around its contents
        topPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Set the background color of the top panel to white
        topPanel.setBackground(Color.WHITE);

        // Create an ImageIcon for the title image
        ImageIcon titleIcon = new ImageIcon(getClass().getResource("/il/ac/hit/chatclient/images/title.png"));

        // Create a label to display the title image
        JLabel titleLabel = new JLabel(titleIcon);

        // Create a panel for the buttons section, using FlowLayout with center alignment and spacing
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        // Set the background color of the button panel to white
        buttonPanel.setBackground(Color.WHITE);

        // Create a label for the nickname field
        JLabel nickNameLabel = new JLabel("NickName:");

        // Create a text field for entering the nickname
        nickNameField = new JTextField(10);

        // Set the background color of the nickname field to white
        nickNameField.setBackground(Color.WHITE);

        // Create a label for the server field
        JLabel serverLabel = new JLabel("Server:");

        // Create a text field for entering the server address
        serverField = new JTextField(10);

        // Set the default value of the server field to "127.0.0.1"
        serverField.setText("127.0.0.1");

        // Set the background color of the server field to white
        serverField.setBackground(Color.WHITE);

        // Create a label for the port field
        JLabel portLabel = new JLabel("Port:");

        // Create a text field for entering the port number
        portField = new JTextField(10);

        // Set the default value of the port field to "1300"
        portField.setText("1300");

        // Set the background color of the port field to white
        portField.setBackground(Color.WHITE);

        // Create a "Connect" button
        connectButton = new JButton("Connect");

        // Set the background color of the "Connect" button
        connectButton.setBackground(new Color(120, 9, 247));

        // Set the text color of the "Connect" button to white
        connectButton.setForeground(Color.WHITE);

        // Set the font of the "Connect" button to Arial, bold, size 14
        connectButton.setFont(new Font("Arial", Font.BOLD, 14));

        // Add an action listener to the "Connect" button to handle the connect event
        /* According to what was learned in the lecture, the Observers design pattern is already implemented in Java.
           For example - the use here of "addActionListener".
           (ie Java implemented it behind the scenes).
         */
        connectButton.addActionListener(e -> currentState.handleOnConnect(this));

        // Set the text color of the "Connect" button to white
        connectButton.setForeground(Color.WHITE);

        // Create a "Disconnect" button
        disconnectButton = new JButton("Disconnect");

        // Set the background color of the "Disconnect" button
        disconnectButton.setBackground(new Color(120, 9, 247));

        // Set the text color of the "Disconnect" button to white
        disconnectButton.setForeground(Color.WHITE);

        // Set the font of the "Disconnect" button to Arial, bold, size 14
        disconnectButton.setFont(new Font("Arial", Font.BOLD, 14));

        // Add an action listener to the "Disconnect" button to handle the disconnect event
        /* According to what was learned in the lecture, the Observers design pattern is already implemented in Java.
           For example - the use here of "addActionListener".
           (ie Java implemented it behind the scenes).
         */
        disconnectButton.addActionListener(e -> currentState.handleOnDisconnect(this));

        // Set the text color of the "Disconnect" button to white
        disconnectButton.setForeground(Color.WHITE);

        // Add the nickname label
        buttonPanel.add(nickNameLabel);

        // Add the nickname input field
        buttonPanel.add(nickNameField);

        // Add the server label
        buttonPanel.add(serverLabel);

        // Add the server input field
        buttonPanel.add(serverField);

        // Add the port label
        buttonPanel.add(portLabel);

        // Add the port input field
        buttonPanel.add(portField);

        // Add the connect button
        buttonPanel.add(connectButton);

        // Add the disconnect button
        buttonPanel.add(disconnectButton);

        // Add the title label
        topPanel.add(titleLabel, BorderLayout.NORTH);

        // Add the button panel
        topPanel.add(buttonPanel, BorderLayout.CENTER);

        // Create a panel for holding the chat messages
        chatContainer = new JPanel();
        chatContainer.setLayout(new BoxLayout(chatContainer, BoxLayout.Y_AXIS));

        // Create a scroll pane for the chat messages panel
        JScrollPane chatScrollPane = new JScrollPane(chatContainer);
        chatScrollPane.setBackground(Color.WHITE);

        // Create a panel for the bottom section of the chat page, using BorderLayout
        JPanel bottomPanel = new JPanel(new BorderLayout());

        // Set the border of the bottom panel to create spacing around its contents
        bottomPanel.setBorder(new EmptyBorder(10, 20, 20, 20));

        // Set the background color of the bottom panel to white
        bottomPanel.setBackground(Color.WHITE);

        // Create a text field for user input
        userInputField = new JTextField();
        userInputField.setPreferredSize(new Dimension(400, 30));

        // Create a text field for entering the recipient
        recipientInputField = new JTextField();
        recipientInputField.setText("global");
        recipientInputField.setPreferredSize(new Dimension(120, 30));
        recipientInputField.setBackground(Color.WHITE);

        // Create a "Send" button
        sendButton = new JButton("Send");

        // Set the background color of the "Send" button
        sendButton.setBackground(new Color(120, 9, 247));

        // Add an action listener to the "Send" button to handle sending messages
        /* According to what was learned in the lecture, the Observers design pattern is already implemented in Java.
           For example - the use here of "addActionListener".
           (ie Java implemented it behind the scenes).
         */
        sendButton.addActionListener(e -> currentState.handleOnSendMessage(this));

        // Set the text color of the "Send" button to white
        sendButton.setForeground(Color.WHITE);

        // Set the font of the "Send" button to Arial, bold, size 14
        sendButton.setFont(new Font("Arial", Font.BOLD, 14));

        // Set the text color of the "Send" button to white
        sendButton.setForeground(Color.WHITE);

        // Store the original background color of the "Send" button
        Color originalColor = sendButton.getBackground();

        // Define the hover color for the button
        Color hoverColor = new Color(252, 220, 229); // Pink color

        // Add mouse listener to the sendButton to change its background color on mouse hover
        sendButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                // Check if the current state is ConnectedState
                if (currentState instanceof ConnectedState) {
                    // Change the background color of sendButton to the hover color
                    sendButton.setBackground(hoverColor);
                }
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                // Restore the original background color of sendButton on mouse exit
                sendButton.setBackground(originalColor);
            }
        });
        // Add mouse listener to the connectButton to change its background color on mouse hover
        connectButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                // Check if the current state is DisconnectedState
                if (currentState instanceof DisconnectedState) {
                    // Change the background color of connectButton to the hover color
                    connectButton.setBackground(hoverColor);
                }
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                // Restore the original background color of connectButton on mouse exit
                connectButton.setBackground(originalColor);
            }
        });
        // Add mouse listener to the disconnectButton to change its background color on mouse hover
        disconnectButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                // Check if the current state is ConnectedState
                if (currentState instanceof ConnectedState) {
                    // Change the background color of disconnectButton to the hover color
                    disconnectButton.setBackground(hoverColor);
                }
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                // Restore the original background color of disconnectButton on mouse exit
                disconnectButton.setBackground(originalColor);
            }
        });

        // Add the userInputField to the center region of the bottomPanel (this field allows the user to input their message)
        bottomPanel.add(userInputField, BorderLayout.CENTER);

        // Add the recipientInputField to the west region of the bottomPanel (this field allows the user to specify the recipient of the message)
        bottomPanel.add(recipientInputField, BorderLayout.WEST);

        // Add the sendButton to the east region of the bottomPanel (this button is used to send the message)
        bottomPanel.add(sendButton, BorderLayout.EAST);

        // Set the background color of the bottomPanel to white
        bottomPanel.setBackground(Color.white);

        // Add the topPanel to the north region of the current JPanel
        add(topPanel, BorderLayout.NORTH);

        // Add the chatScrollPane to the center region of the current JPanel
        add(chatScrollPane, BorderLayout.CENTER);

        // Add the bottomPanel to the south region of the current JPanel
        add(bottomPanel, BorderLayout.SOUTH);

        // Update the button states based on the current state
        updateButtonState();
    }

    /**
     * Changes the state of the chat application to the given newState
     *
     * @param newState The new state to be set for the chat application
     */
    public void changeState(ConnectionState newState) {
        currentState = newState;
    }

    /**
     * Updates the state of the buttons based on the current state of the chat application
     * Enables or disables the buttons depending on the current state
     */
    public void updateButtonState() {
        connectButton.setEnabled(currentState instanceof DisconnectedState);
        disconnectButton.setEnabled(currentState instanceof ConnectedState);
        sendButton.setEnabled(currentState instanceof ConnectedState);
    }

    /**
     * Renders a message in the chat interface
     * Displays the message with appropriate styling and formatting
     *
     * @param message    The message to be rendered
     * @param isSentByMe Indicates whether the message is sent by the current user
     */
    public void renderMessage(Message message, boolean isSentByMe) {
        // Determine the background color based on whether the message is sent by the current user
        String color = isSentByMe ? "#fcdce5" : "#7808f7";
        String containerStyles = "background-color: " + color + "; padding: 5px; line-height: 0.8; margin: 0;";
        String nickNameAndTimestampStyles = "font-size:12px";

        // Get the current timestamp
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timestamp = currentDateTime.format(formatter);

        // Determine whether the message is global or private
        boolean isGlobal = message.getRecipient().equals("global");
        String isGlobalMessage = isGlobal ? " (global):" : " (private):";

        // Create the HTML-formatted message
        String htmlMessage = "<html><div style='" + containerStyles + "'><strong style='" + nickNameAndTimestampStyles + "'>" + message.getNickName() + isGlobalMessage + "</strong><br><font>" + message.getMessage() + "</font><br><em style='" + nickNameAndTimestampStyles + "'>" + timestamp + "</em></div></html>";

        // Create a label with the HTML-formatted message
        JLabel messageLabel = new JLabel(htmlMessage);
        messageLabel.setBackground(Color.green);
        messageLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // Set the alignment of the message label based on whether it is sent by the current user
        if (isSentByMe) {
            messageLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        }

        // Add the message label to the chat container and update the UI
        this.getChatContainer().add(messageLabel);
        this.getChatContainer().revalidate();
        this.getChatContainer().repaint();
    }


    // Getters & setters
    public JFrame getMainFrame() {
        return mainFrame;
    }

    public SimpleTCPClient getTcpClient() {
        return tcpClient;
    }

    public JTextField getServerField() {
        return serverField;
    }

    public JPanel getChatContainer() {
        return chatContainer;
    }

    public JTextField getRecipientInputField() {
        return recipientInputField;
    }

    public JTextField getUserInputField() {
        return userInputField;
    }

    public JTextField getPortField() {
        return portField;
    }

    public JTextField getNickNameField() {
        return nickNameField;
    }
    public void setTcpClient(SimpleTCPClient tcpClient) {
        this.tcpClient = tcpClient;
    }
}