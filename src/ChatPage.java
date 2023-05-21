import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;

public class ChatPage extends JPanel {

    private JFrame mainFrame;
    private SimpleTCPClient tcpClient;

    private JTextField serverField;
    private JPanel chatContainer;
    private JButton disconnectButton;
    private JButton sendButton;
    private JButton connectButton;
    private JTextField userInputField;
    private JTextField portField;
    private JTextField nickNameField;

    private interface ConnectionState {
        void handleOnConnect(ChatPage chatPage);

        void handleOnDisconnect(ChatPage chatPage);

        void handleOnSendMessage(ChatPage chatPage);
    }

    private class ConnectedState implements ConnectionState {
        @Override
        public void handleOnConnect(ChatPage chatPage) {
            // Already connected, do nothing
        }

        @Override
        public void handleOnDisconnect(ChatPage chatPage) {
            chatPage.changeState(new DisconnectedState());
            chatPage.updateButtonState();
        }

        @Override
        public void handleOnSendMessage(ChatPage chatPage) {
            String nickName = chatPage.nickNameField.getText();
            String userInput = chatPage.userInputField.getText();

            Message message = new Message(nickName, userInput);
            chatPage.tcpClient.sendMessage(message);

            JTextArea textArea = new JTextArea();
            textArea.setText(message.getNickName() + ": " + message.getMessage());
            textArea.setEditable(false);
            textArea.setBackground(Color.GREEN);
            textArea.setFont(new Font("Arial", Font.PLAIN, 14));
            textArea.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

            chatPage.chatContainer.add(textArea);
            chatPage.chatContainer.revalidate();
            chatPage.chatContainer.repaint();
        }
    }

    private class DisconnectedState implements ConnectionState {
        @Override
        public void handleOnConnect(ChatPage chatPage) {
            if(nickNameField.getText().isEmpty() || serverField.getText().isEmpty() || portField.getText().isEmpty()){
                JOptionPane.showMessageDialog(chatPage.mainFrame, "Must fill all fields!", " Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int port = Integer.parseInt(chatPage.portField.getText());
            String server = chatPage.serverField.getText();

            try {
                chatPage.tcpClient = new SimpleTCPClient(server, port, chatPage.chatContainer);
                JOptionPane.showMessageDialog(chatPage.mainFrame, "Connected to server successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                chatPage.changeState(new ConnectedState());
                chatPage.updateButtonState();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(chatPage.mainFrame, "Failed to connect to the server.", "Connection Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        @Override
        public void handleOnDisconnect(ChatPage chatPage) {
            // Already disconnected, do nothing
        }

        @Override
        public void handleOnSendMessage(ChatPage chatPage) {
            // Cannot send message when disconnected, do nothing
        }
    }

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

        disconnectButton = new JButton("Disconnect");
        disconnectButton.setBackground(new Color(120, 9, 247));
        disconnectButton.setForeground(Color.WHITE);
        disconnectButton.setFont(new Font("Arial", Font.BOLD, 14));
        disconnectButton.addActionListener(e -> currentState.handleOnDisconnect(this));

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
        chatScrollPane.setBackground(Color.WHITE);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(new EmptyBorder(10, 20, 20, 20));
        bottomPanel.setBackground(Color.WHITE);

        userInputField = new JTextField();
        userInputField.setPreferredSize(new Dimension(400, 30));

        JComboBox<String> recipientComboBox = new JComboBox<>();
        recipientComboBox.setPreferredSize(new Dimension(120, 30));
        recipientComboBox.setBackground(Color.WHITE);

        sendButton = new JButton("Send");
        sendButton.setBackground(new Color(120, 9, 247));
        sendButton.addActionListener(e -> currentState.handleOnSendMessage(this));
        sendButton.setForeground(Color.WHITE);
        sendButton.setFont(new Font("Arial", Font.BOLD, 14));

        bottomPanel.add(userInputField, BorderLayout.CENTER);
        bottomPanel.add(recipientComboBox, BorderLayout.WEST);
        bottomPanel.add(sendButton, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);
        add(chatScrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        updateButtonState();
    }

    private void changeState(ConnectionState newState) {
        currentState = newState;
    }

    private void updateButtonState() {
        connectButton.setEnabled(currentState instanceof DisconnectedState);
        disconnectButton.setEnabled(currentState instanceof ConnectedState);
        sendButton.setEnabled(currentState instanceof ConnectedState);
    }

    // Create a main method to run the program
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Chat Application");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new ChatPage(frame));
            frame.pack();
            frame.setVisible(true);
        });
    }
}