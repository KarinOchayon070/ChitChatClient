/*
 Developers details:
   - Karin Ochayon, 207797002
   - Dor Uzan, 205890510
*/

// This file (Main.java) is responsible for invoking the "ChatApp" class on the EDT to start the Swing application and initialize the user interface

import il.ac.hit.chatclient.view.ChatApp;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        /* SwingUtilities.invokeLater() method ensures that the GUI components are created and updated on the Event Dispatch Thread (EDT),
           which is the thread responsible for handling Swing events and user interactions.

         */
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Create an instance of the ChatApp class to initialize the GUI
                new ChatApp();
            }
        });
    }
}