import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ChatApp {
    private JFrame mainFrame;
    private BackgroundPanel backgroundPanel;

    public ChatApp() {
        mainFrame = new JFrame("Welcome To ChitChat App 😊");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(500, 600);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setResizable(false); // Disable window resizing

        backgroundPanel = new BackgroundPanel("background.png");
        mainFrame.add(backgroundPanel);

        JButton startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openChatPage();
            }
        });

        // Set button styles
        startButton.setBackground(new Color(120, 8, 247)); // Set a custom background color
        startButton.setForeground(Color.WHITE); // Set the foreground color (text color)
        startButton.setFont(new Font("Arial", Font.BOLD, 14)); // Set the font

        // Increase the button size
        startButton.setPreferredSize(new Dimension(150, 50));


        // Set button padding
        int padding = 20;
        startButton.setBorder(new EmptyBorder(padding, padding, padding, padding));


        // Change button background color on hover
        Color originalColor = startButton.getBackground();
        Color hoverColor = new Color(252, 220, 229); // Pink color
        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                startButton.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                startButton.setBackground(originalColor);
            }
        });

        // Align the button at the bottom center
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE); // Set the background color of the button panel
        buttonPanel.add(startButton);

        // Place the button panel at the bottom of the background panel
        backgroundPanel.setLayout(new BorderLayout());
        backgroundPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainFrame.setVisible(true);
    }

    private void openChatPage() {
        mainFrame.getContentPane().removeAll();
        mainFrame.setSize(800, 800);

        // Create the chat page components
        JPanel chatPanel = new JPanel(new BorderLayout());

        // Top Panel: Server, Port, Connect, and Disconnect
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.setBackground(new Color(252, 220, 229)); // Set background color

        JLabel serverLabel = new JLabel("Server:");
        JTextField serverField = new JTextField(10);

        JLabel portLabel = new JLabel("Port:");
        JTextField portField = new JTextField(10);

        JButton connectButton = new JButton("Connect");
        connectButton.setBackground(new Color(120, 8, 247)); // Set background color
        connectButton.setForeground(Color.WHITE); // Set text color
        connectButton.setEnabled(false);

        JButton disconnectButton = new JButton("Disconnect");
        disconnectButton.setBackground(new Color(120, 8, 247)); // Set background color
        disconnectButton.setForeground(Color.WHITE); // Set text color
        disconnectButton.setEnabled(false);

        topPanel.add(serverLabel);
        topPanel.add(serverField);
        topPanel.add(portLabel);
        topPanel.add(portField);
        topPanel.add(connectButton);
        topPanel.add(disconnectButton);

        // Middle Panel: Chat Messages
        JTextArea chatMessagesArea = new JTextArea();
        chatMessagesArea.setEditable(false);
        JScrollPane chatScrollPane = new JScrollPane(chatMessagesArea);

        // Bottom Panel: User Input, Recipient ComboBox, and Send Button
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JTextField userInputField = new JTextField();
        JComboBox<String> recipientComboBox = new JComboBox<>();
        JButton sendButton = new JButton("Send");
        bottomPanel.add(userInputField, BorderLayout.CENTER);
        bottomPanel.add(recipientComboBox, BorderLayout.WEST);
        bottomPanel.add(sendButton, BorderLayout.EAST);

        // Add components to the chat panel
        chatPanel.add(topPanel, BorderLayout.NORTH);
        chatPanel.add(chatScrollPane, BorderLayout.CENTER);
        chatPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Add chatPanel to the frame
        mainFrame.add(chatPanel);

        mainFrame.revalidate();
        mainFrame.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ChatApp();
            }
        });
    }
}

class BackgroundPanel extends JPanel {
    private BufferedImage backgroundImage;

    public BackgroundPanel(String imagePath) {
        try {
            backgroundImage = ImageIO.read(getClass().getResource(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setOpaque(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Scale the image to fit the panel's dimensions
        Image scaledImage = backgroundImage.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
        g.drawImage(scaledImage, 0, 0, this);
    }
}
