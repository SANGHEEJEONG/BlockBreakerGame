import javax.swing.*;
import java.awt.*;

public class GameOverScreen extends JPanel {
    private JFrame frame;

    public GameOverScreen(JFrame frame) {
        this.frame = frame;
        this.setBackground(Color.BLACK);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.drawString("GAME OVER", 250, 300);
    }
}
