package il.ac.hit.chatclient.view;/*
 Developers details:
   - Karin Ochayon, 207797002
   - Dor Uzan, 205890510
*/

/*
    This file (il.ac.hit.chatclient.client.StartPage.java) represents the il.ac.hit.chatclient.client.StartPage class, which is a JPanel that serves as the initial page of the chat application.
    It contains the main frame of the application (mainFrame), a background panel (backgroundPanel), and a chat page (chatPage).
    The il.ac.hit.chatclient.client.StartPage constructor initializes the main frame, sets its size and layout, and creates a background panel with an image.
    It also creates a "Start" button with custom styles and adds it to a button panel at the bottom of the background panel.
    The openChatPage() method is responsible for transitioning to the chat page when the "Start" button is clicked.
    It removes all components from the main frame, resizes the window, creates a new instance of the il.ac.hit.chatclient.client.ChatPage class, and adds it to the main frame.
    Finally, it ensures that the components are properly laid out and repainted on the screen.
 */

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;


public class StartPage extends JPanel {

    // Represents the main frame of the application, which is a window that contains all the graphical components of the user interface
    private JFrame mainFrame;

    // Represents an instance of the il.ac.hit.chatclient.ui.BackgroundPanel class, which is a custom panel that displays a background image
    private BackgroundPanel backgroundPanel;

    // Represents an instance of the il.ac.hit.chatclient.client.ChatPage class. It is used to refer to the chat page panel that is displayed when the user clicks the "Start" button
    private ChatPage chatPage;

    /**
     * Constructs a il.ac.hit.chatclient.client.StartPage object with the specified JFrame as the main frame of the application
     *
     * @param frame the JFrame representing the main frame of the application
     */
    public StartPage(JFrame frame) {

        // Set mainFrame style
        mainFrame = frame;
        mainFrame.setSize(500, 600);
        mainFrame.setLayout(new BorderLayout());
        backgroundPanel = new BackgroundPanel("/il/ac/hit/chatclient/images/background.png"); // Creating background (the image will be the background)
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


    /**
     * Transitions to the chat page by removing all components from the main frame, resizing the window
     * creating a new instance of the il.ac.hit.chatclient.client.ChatPage class, and adding it to the main frame
     * Finally, it ensures that the components are properly laid out and repainted on the screen
     */
    private void openChatPage() {
        // Remove all the components from the content pane of the mainFrame JFrame
        mainFrame.getContentPane().removeAll();
        // Resize the window
        mainFrame.setSize(900, 900);
        // Create new instance of the il.ac.hit.chatclient.client.ChatPage class and passes the mainFrame as an argument to its constructor
        chatPage = new ChatPage(mainFrame);

        backgroundPanel = new BackgroundPanel("/il/ac/hit/chatclient/images/title.png"); // Provide the path to the new image

        // This means that the chatPage will be displayed as a component within the mainFrame
        mainFrame.add(chatPage);
        // Ensures that the added chatPage component is properly laid out within the frame
        mainFrame.revalidate();
        // Forces the frame to be repainted on the screen, ensuring that any changes/updates made to the frame or its components are immediately visible to the user
        mainFrame.repaint();
    }
}
