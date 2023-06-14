package il.ac.hit.chatclient.view;/*
 Developers details:
   - Karin Ochayon, 207797002
   - Dor Uzan, 205890510
*/

/*
    This file (ChatApp) represents a ChatApp class in a chat client application.
    The ChatApp class represents the main entry point of the chat client application.
    It creates a JFrame (main frame) as the application window, sets its style, and makes it non-resizable.
    It initializes and adds a StartPage component to the main frame, ensuring proper layout, and forces the frame to be repainted for immediate visibility to the user.
 */

import javax.swing.*;

public class ChatApp {

    // Represents the main frame of the application, which is a window that contains all the graphical components of the user interface
    private JFrame mainFrame;

    // Represents an instance of the StartPage class. It is used to represent the start page panel.
    private StartPage startPage;
    public ChatApp() {
        // Set mainFrame style
        mainFrame = new JFrame("Welcome To ChitChat App 😊");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false); // Disable window resizing

        // Creating a new instance of the StartPage class and assigning it to the startPage variable, with the mainFrame as a parameter
        startPage = new StartPage(mainFrame);

        // This means that the chatPage will be displayed as a component within the mainFrame
        mainFrame.add(startPage);
        // Ensures that the added chatPage component is properly laid out within the frame
        mainFrame.revalidate();
        // Forces the frame to be repainted on the screen, ensuring that any changes/updates made to the frame or its components are immediately visible to the user
        mainFrame.repaint();
    }
}
