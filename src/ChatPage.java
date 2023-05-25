import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChatPage extends JPanel {

    private JFrame mainFrame;
    private SimpleTCPClient tcpClient;
    private JTextField serverField;
    private JPanel chatContainer;
    private JButton disconnectButton;
    private JButton connectButton;
    private JButton sendButton;
    private JTextField recipientInputField;
    private JTextField userInputField;
    private JTextField portField;
    private JTextField nickNameField;
    private ConnectionState currentState;

    public ChatPage(JFrame frame) {
        mainFrame = frame;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        currentState = new DisconnectedState();

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        topPanel.setBackground(Color.WHITE);

        ImageIcon titleIcon = new ImageIcon(getClass().getResource("images/title.png"));
        JLabel titleLabel = new JLabel(titleIcon);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(Color.WHITE);

        JLabel nickNameLabel = new JLabel("NickName:");
        nickNameField = new JTextField(10);
        nickNameField.setBackground(Color.WHITE);

        JLabel serverLabel = new JLabel("Server:");
        serverField = new JTextField(10);
        serverField.setText("127.0.0.1");
        serverField.setBackground(Color.WHITE);

        JLabel portLabel = new JLabel("Port:");
        portField = new JTextField(10);
        portField.setText("1300");
        portField.setBackground(Color.WHITE);

        connectButton = new JButton("Connect");
        connectButton.setBackground(new Color(120, 9, 247));
        connectButton.setForeground(Color.WHITE);
        connectButton.setFont(new Font("Arial", Font.BOLD, 14));
        connectButton.addActionListener(e -> currentState.handleOnConnect(this));
        connectButton.setForeground(Color.WHITE);


        disconnectButton = new JButton("Disconnect");
        disconnectButton.setBackground(new Color(120, 9, 247));
        disconnectButton.setForeground(Color.WHITE);
        disconnectButton.setFont(new Font("Arial", Font.BOLD, 14));
        disconnectButton.addActionListener(e -> currentState.handleOnDisconnect(this));
        disconnectButton.setForeground(Color.WHITE);

        buttonPanel.add(nickNameLabel);
        buttonPanel.add(nickNameField);
        buttonPanel.add(serverLabel);
        buttonPanel.add(serverField);
        buttonPanel.add(portLabel);
        buttonPanel.add(portField);
        buttonPanel.add(connectButton);
        buttonPanel.add(disconnectButton);

        topPanel.add(titleLabel, BorderLayout.NORTH);
        topPanel.add(buttonPanel, BorderLayout.CENTER);

        chatContainer = new JPanel();
        chatContainer.setLayout(new BoxLayout(chatContainer, BoxLayout.Y_AXIS));

        JScrollPane chatScrollPane = new JScrollPane(chatContainer);
        chatScrollPane.setBackground(Color.white);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(new EmptyBorder(10, 20, 20, 20));
        bottomPanel.setBackground(Color.WHITE);


        userInputField = new JTextField();
        userInputField.setPreferredSize(new Dimension(400, 30));


        recipientInputField  = new JTextField();
        recipientInputField.setText("global");
        recipientInputField.setPreferredSize(new Dimension(120, 30));
        recipientInputField.setBackground(Color.WHITE);

        sendButton = new JButton("Send");
        sendButton.setBackground(new Color(120, 9, 247));
        sendButton.addActionListener(e -> currentState.handleOnSendMessage(this));
        sendButton.setForeground(Color.WHITE);
        sendButton.setFont(new Font("Arial", Font.BOLD, 14));
        sendButton.setForeground(Color.WHITE);

        Color originalColor = sendButton.getBackground(); // Change button background color on hover
        Color hoverColor = new Color(252, 220, 229); // Pink color
        sendButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                sendButton.setBackground(hoverColor);

            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                sendButton.setBackground(originalColor);
            }
        });

        connectButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                connectButton.setBackground(hoverColor);

            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                connectButton.setBackground(originalColor);
            }
        });

        disconnectButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                disconnectButton.setBackground(hoverColor);

            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                disconnectButton.setBackground(originalColor);
            }
        });

        bottomPanel.add(userInputField, BorderLayout.CENTER);
        bottomPanel.add(recipientInputField, BorderLayout.WEST);
        bottomPanel.add(sendButton, BorderLayout.EAST);
        bottomPanel.setBackground(Color.white);

        add(topPanel, BorderLayout.NORTH);
        add(chatScrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        updateButtonState();
    }

    public void changeState(ConnectionState newState) {
        currentState = newState;
    }

    public void updateButtonState() {
        connectButton.setEnabled(currentState instanceof DisconnectedState);
        disconnectButton.setEnabled(currentState instanceof ConnectedState);
        sendButton.setEnabled(currentState instanceof ConnectedState);
    }

    public void renderMessage(Message message, boolean isSentByMe){
        String color = isSentByMe ? "#fcdce5" : "#7808f7";
        String containerStyles = "background-color: " + color + "; padding: 5px; line-height: 0.8; margin: 0;";
        String nickNameAndTimestampStyles = "font-size:8px";

        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timestamp = currentDateTime.format(formatter);

        boolean isGlobal = message.getRecipient().equals("global");
        String isGlobalMessage = isGlobal ? " (global)" : " (private)";

        String htmlMessage = "<html><div style='" + containerStyles + "'><strong style='" + nickNameAndTimestampStyles + "'>" + message.getNickName() + isGlobalMessage + "</strong><br><font>" + message.getMessage() + "</font><br><em style='" + nickNameAndTimestampStyles + "'>" + timestamp + "</em></div></html>";

        JLabel messageLabel = new JLabel(htmlMessage);
        messageLabel.setBackground(Color.green);
        messageLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        if(isSentByMe){
            messageLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        }



        this.getChatContainer().add(messageLabel);
        this.getChatContainer().revalidate();
        this.getChatContainer().repaint();
    }


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