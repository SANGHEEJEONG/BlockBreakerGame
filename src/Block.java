import java.awt.*;
import javax.swing.*;
import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

public class Block {
    private int x, y, width, height;
    private boolean destroyed = false;
    private float alpha = 1.0f;
    private Color color;
    private Timer blinkTimer;
    private final Random random = new Random();

    public Block(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        // 색상을 랜덤으로 초기화
        if (Math.random() < 0.5) {
            this.color = Color.YELLOW;
            startBlinking();
        } else {
            this.color = Color.BLUE;
        }
    }

    private void startBlinking() {
        int delay = 300 + random.nextInt(1700);
        blinkTimer = new Timer(delay, e -> {
            color = color.equals(Color.YELLOW) ? Color.decode("#FFE400") : Color.YELLOW;
        });
        blinkTimer.start();
    }

    public void draw(Graphics g) {
        if (!destroyed || alpha > 0) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            Color shadowColor = new Color(0, 0, 0, (int)(100 * alpha));
            g2d.setColor(shadowColor);
            g2d.fillRoundRect(x + 5, y + 5, width, height, 10, 10);

            g2d.setColor(color);
            g2d.fillRoundRect(x, y, width, height, 10, 10);

            g2d.setColor(color.darker());
            g2d.setStroke(new BasicStroke(2));
            g2d.drawRoundRect(x, y, width, height, 10, 10);
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void destroy() {
        if (!destroyed) {
            destroyed = true;
            if (blinkTimer != null) {
                blinkTimer.stop();
            }
            startFading();
            playSound("breakBGM.wav");
        }
    }

    private void startFading() {
        Timer fadeTimer = new Timer(50, e -> {
            alpha -= 0.1f;
            if (alpha <= 0) {
                ((Timer) e.getSource()).stop();
                alpha = 0;
            }
        });
        fadeTimer.start();
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public Color getColor() {
        return color;
    }

    private void playSound(String soundFileName) {
        try {
            URL url = getClass().getResource(soundFileName);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {

        }
    }
}
