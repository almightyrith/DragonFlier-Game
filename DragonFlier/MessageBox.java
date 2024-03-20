import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.Timer;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MessageBox extends DragonGameShape implements KeyListener{
    private Dragon dragon;
    private String message;
    private boolean display;
    private Timer displayTimer;
    private final int TIMER_DELAY = 1000; // Delay in milliseconds
    private final double MESSAGE_BOX_WIDTH_PERCENTAGE = 1.0 / 3; // 1/3 of the frame width
    private final double MESSAGE_BOX_HEIGHT_PERCENTAGE = 0.8; // 80% of the frame height
    private LocationAndSize locationAndSize; // Use LocationAndSize class here
    private KeyListener keyListener;

    public MessageBox(int x, int y, Dragon dragon) {
        this.dragon = dragon;
        this.locationAndSize = new LocationAndSize(x, y, 0, 0); // Initialize width and height as 0
    }

    public MessageBox(int x, int y, Dragon dragon, boolean display) {
        this.dragon = dragon;
        this.display = display;
        this.locationAndSize = new LocationAndSize(x, y, 0, 0); // Initialize width and height as 0
    }

    public MessageBox(int x, int y, Dragon dragon, LocationAndSize locationAndSize) {
        this.dragon = dragon;
        this.display = false; // Initialize display to false by default
        this.locationAndSize = locationAndSize; // Use the provided LocationAndSize instance
    }

    public MessageBox(Dragon dragon, String message, int frameWidth, int frameHeight) {
        this.dragon = dragon;
        this.message = message;
        this.display = false;

        // Calculate message box dimensions
        int messageBoxWidth = (int) (frameWidth * MESSAGE_BOX_WIDTH_PERCENTAGE);
        int messageBoxHeight = (int) (frameHeight * MESSAGE_BOX_HEIGHT_PERCENTAGE);
        int messageBoxX = (dragon.isFacingRight()) ? dragon.getX() + 100 : dragon.getX() - messageBoxWidth - 100;
        int messageBoxY = (frameHeight - messageBoxHeight) / 2; // Center vertically

        this.locationAndSize = new LocationAndSize(messageBoxX, messageBoxY, messageBoxWidth, messageBoxHeight);
        
        // Calculate display time based on number of words
        int numWords = message.split("\\s+").length;
        int displayTime = numWords / 4 * 1000; // Convert to milliseconds

        // Create and start display timer
        displayTimer = new Timer(displayTime, e -> {
            display = false;
            displayTimer.stop();
        });
    }
    
    public void setDisplay(boolean display) {
        this.display = display;
    }
    
    public void toggleDisplay() {
        display = !display;
        if (display) {
            displayTimer.start();
        } else {
            displayTimer.stop();
        }
    }

    @Override
    public void draw(Graphics g) {
        if (!display) return;

        // Draw message box
        g.setColor(Color.BLACK);
        g.fillRect(locationAndSize.getX(), locationAndSize.getY(), locationAndSize.getWidth(), locationAndSize.getHeight());
        g.setColor(Color.YELLOW);
        g.fillRect(locationAndSize.getX() - 5, locationAndSize.getY() - 5, locationAndSize.getWidth() + 10, locationAndSize.getHeight() + 10);

        // Wrap and draw message text
        int fontSize = 16;
        Font font = new Font("Arial", Font.PLAIN, fontSize);
        g.setFont(font);
        g.setColor(Color.WHITE);
        wrapText(g, message, locationAndSize.getX() + 10, locationAndSize.getY() + 10, locationAndSize.getWidth() - 20, locationAndSize.getHeight() - 20);
    }

    @Override
    public void move() {
        // Change x location based on dragon facing direction
        locationAndSize.setX((dragon.isFacingRight()) ? dragon.getX() + 100 : dragon.getX() - locationAndSize.getWidth() - 100);
    }

    /**
     * Wrap text to fit within the specified width.
     * @param g The Graphics object to draw with
     * @param text The text to wrap
     * @param x The x-coordinate to start drawing the text
     * @param y The y-coordinate to start drawing the text
     * @param width The maximum width of the text before wrapping
     * @param height The maximum height of the text before clipping
     */

    private void wrapText(Graphics g, String text, int x, int y, int width, int height) {
        // Split the text into words
        String[] words = text.split("\\s+");
      
        // Initialize variables
        StringBuilder currentLine = new StringBuilder();
        int lineY = y;
      
        for (String word : words) {
          // Check if adding the word exceeds the line width
          int textWidth = g.getFontMetrics().stringWidth(currentLine.toString() + " " + word);
          if (textWidth > width) {
            // Wrap the line and move to the next line
            g.drawString(currentLine.toString(), x, lineY);
            currentLine = new StringBuilder();
            lineY += g.getFontMetrics().getHeight(); // Move to next line
          }
          currentLine.append(" ").append(word);
        }
      
        // Draw the last line
        g.drawString(currentLine.toString(), x, lineY);
      }
      

    public void setKeyListener(KeyListener keyListener) {
        this.keyListener = keyListener;
    }

    public void bindTo(KeyListener listener) {
        this.keyListener = listener;
        // Now you can use the parameter directly
        // if (listener != null) {
        //     listener.keyPressed(e); Assuming you're inside keyPressed method
        //     Similar calls for keyReleased and keyTyped
        // }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (keyListener != null) {
            keyListener.keyPressed(e); // Use the parameter directly
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (keyListener != null) {
            keyListener.keyReleased(e);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (keyListener != null) {
            keyListener.keyTyped(e);
        }
    }
}
