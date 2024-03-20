import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePane extends JPanel implements KeyListener {
    private Dragon dragon;
    private Background background;
    private MessageBox instructionsBox;
    private boolean instructionsVisible;

    public GamePane(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setFocusable(true);
        addKeyListener(this);
        setBackground(Color.WHITE);

        // Initialize the dragon, background, and instructions box
        dragon = new Dragon(50, 50, width / 2, height - 200);
        background = new Background("background.jpg", width, height);
        instructionsBox = new MessageBox(100, 100, dragon);
        instructionsBox.bindTo(this); //Bind the MessageBox to key events

        instructionsVisible = false;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        background.draw(g);
        dragon.draw(g);
        if (instructionsVisible) {
            instructionsBox.draw(g);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_I) {
            if (instructionsVisible) {
                instructionsBox.setDisplay(false);
                instructionsVisible = false;
            } else {
                instructionsBox.setDisplay(true);
                instructionsVisible = true;
            }
        } else {
            dragon.keyPressed(e);
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        dragon.keyReleased(e);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Dragon Flier");
        GamePane gamePane = new GamePane(800, 600);
        frame.getContentPane().add(gamePane);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
