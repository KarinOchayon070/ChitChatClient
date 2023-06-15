/*
 Developers details:
   - Karin Ochayon, 207797002
   - Dor Uzan, 205890510
*/

/*
    This file (BackgroundPanel.java) defines a custom BackgroundPanel class that extends the JPanel class from the Swing framework in Java.
     This class is used to create a panel with a background image.
 */

package il.ac.hit.chatclient.view;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

class BackgroundPanel extends JPanel {

    // This variable "backgroundImage" stores the loaded background image
    private BufferedImage backgroundImage;

    /**
     * Constructs a new BackgroundPanel object with the specified image path.
     *
     * @param imagePath the path of the background image
     */
    public BackgroundPanel(String imagePath) {
        try {
            // Load the background image from the specified path
            backgroundImage = ImageIO.read(getClass().getResource(imagePath));
        } catch (IOException e) {
            // Print the stack trace if an exception occurs during image loading
            e.printStackTrace();
        }
        // Make the panel opaque, which allows the background image to be visible
        setOpaque(true);
    }

    /**
     * Overrides the paintComponent method to paint the background image.
     *
     * @param g the Graphics object used for painting
     */
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