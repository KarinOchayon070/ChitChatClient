/* Developers details:
   - Karin Ochayon, 207797002
   - Dor Uzan, 205890510
*/


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

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