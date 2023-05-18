/* Developers details:
   - Karin Ochayon, 207797002
   - Dor Uzan, 205890510
*/

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ChatApp {

    // Represents the main frame of the application, which is a window that contains all the graphical components of the user interface
    private JFrame mainFrame;
    // Represents an instance of the BackgroundPanel class, which is a custom panel that displays a background image
    private BackgroundPanel backgroundPanel;

    // Represents an instance of the ChatPage class. It is used to refer to the chat page panel that is displayed when the user clicks the "Start" button
    private ChatPage chatPage;

    public ChatApp() {

        // Set mainFrame style
        mainFrame = new JFrame("Welcome To ChitChat App 😊");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(500, 600);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setResizable(false); // Disable window resizing
        backgroundPanel = new BackgroundPanel("images/background.png"); // Creating background (the image will be the background)
        mainFrame.add(backgroundPanel);

        // Creating "Start" button
        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> openChatPage()); // When the user clicks on "Start" transfer him to the actual chat page

        // Set "Start" button styles
        startButton.setBackground(new Color(120, 8, 247)); // Set a custom background color (purple)
        startButton.setForeground(Color.WHITE); // Set the foreground color (text color = white)
        startButton.setFont(new Font("Arial", Font.BOLD, 14)); // Set the font
        startButton.setPreferredSize(new Dimension(150, 50)); // Increase the button size
        int padding = 20; // Set button padding
        startButton.setBorder(new EmptyBorder(padding, padding, padding, padding));
        Color originalColor = startButton.getBackground(); // Change button background color on hover
        Color hoverColor = new Color(252, 220, 229); // Pink color
        startButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                startButton.setBackground(hoverColor);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
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


    // This function is responsable for the actual chat page
    private void openChatPage() {
        // Remove all the components from the content pane of the mainFrame JFrame
        mainFrame.getContentPane().removeAll();
        // Resize the window
        mainFrame.setSize(800, 800);
        // Create new instance of the ChatPage class and passes the mainFrame as an argument to its constructor
        chatPage = new ChatPage(mainFrame);
        // This means that the chatPage will be displayed as a component within the mainFrame
        mainFrame.add(chatPage);
        // Ensures that the added chatPage component is properly laid out within the frame
        mainFrame.revalidate();
        // Forces the frame to be repainted on the screen, ensuring that any changes/updates made to the frame or its components are immediately visible to the user
        mainFrame.repaint();
    }

    // This class is responsible for creating a panel with a background image
    class BackgroundPanel extends JPanel {

        // This variable "backgroundImage" stores the loaded background image
        private BufferedImage backgroundImage;

        // Inside the constructor, the image is loaded and if an exception occurs, it is printed to the console
        public BackgroundPanel(String imagePath) {
            try {
                backgroundImage = ImageIO.read(getClass().getResource(imagePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
            // Make the panel opaque, which allows the background image to be visible
            setOpaque(true);
        }

        @Override
        protected void paintComponent(Graphics g) {
            // This line invokes the superclass's paintComponent() method to ensure that the panel is painted correctly
            super.paintComponent(g);
            // Scale the image to fit the panel's dimensions
            Image scaledImage = backgroundImage.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
            // Specifies the coordinates (0, 0) to position the image at the top-left corner of the panel
            g.drawImage(scaledImage, 0, 0, this);
        }
    }
}
