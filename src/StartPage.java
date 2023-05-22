/* Developers details:
   - Karin Ochayon, 207797002
   - Dor Uzan, 205890510
*/

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class StartPage extends JPanel {

    // Represents the main frame of the application, which is a window that contains all the graphical components of the user interface
    private JFrame mainFrame;
    // Represents an instance of the BackgroundPanel class, which is a custom panel that displays a background image
    private BackgroundPanel backgroundPanel;

    // Represents an instance of the ChatPage class. It is used to refer to the chat page panel that is displayed when the user clicks the "Start" button
    private ChatPage chatPage;

    public StartPage(JFrame frame) {

        // Set mainFrame style
        mainFrame = frame;
        mainFrame.setSize(500, 600);
        mainFrame.setLayout(new BorderLayout());
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
        startButton.setBorder(new EmptyBorder(20, 20, 20, 20)); // Set button padding
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



    // This function is responsible for the actual chat page
    private void openChatPage() {
        // Remove all the components from the content pane of the mainFrame JFrame
        mainFrame.getContentPane().removeAll();
        // Resize the window
        mainFrame.setSize(900, 900);
        // Create new instance of the ChatPage class and passes the mainFrame as an argument to its constructor
        chatPage = new ChatPage(mainFrame);

        backgroundPanel= new BackgroundPanel("images/title.png"); // Provide the path to the new image

        // This means that the chatPage will be displayed as a component within the mainFrame
        mainFrame.add(chatPage);
        // Ensures that the added chatPage component is properly laid out within the frame
        mainFrame.revalidate();
        // Forces the frame to be repainted on the screen, ensuring that any changes/updates made to the frame or its components are immediately visible to the user
        mainFrame.repaint();
    }
}
