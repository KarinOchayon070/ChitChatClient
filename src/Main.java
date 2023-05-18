/* Developers details:
   - Karin Ochayon, 207797002
   - Dor Uzan, 205890510
*/

import javax.swing.*;

// Main class is responsible for invoking the "ChatApp" class on the EDT to start the Swing application and initialize the user interface.
public class Main {
    public static void main(String[] args) {
        /*SwingUtilities.invokeLater() method ensures that the GUI components are created and updated on the Event Dispatch Thread (EDT),
         which is the thread responsible for handling Swing events and user interactions.
         */
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ChatApp();
                /*The Runnable object's run method is implemented inline using an anonymous inner class. This method is responsible for creating
                 an instance of the ChatApp class and initializing the GUI.
                * */
            }
        });
    }
}
