import java.awt.*;
import javax.swing.*;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Random;

public class Block {
    private int x, y, width, height;
    private boolean destroyed = false;
    private Color color; // 블록의 색상을 저장
    private Timer blinkTimer; // 반짝임 효과를 위한 타이머
    private Random random = new Random(); // 랜덤 객체 생성

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
        int delay = 300 + random.nextInt(1700); // 0.3초에서 2초 사이의 간격

        blinkTimer = new Timer(delay, new ActionListener() {
            private boolean bright = true;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (bright) {
                    color = Color.decode("#FFE400");
                } else {
                    color = Color.YELLOW;
                }
                bright = !bright;
            }
        });
        blinkTimer.start();
    }

    public void draw(Graphics g) {
        if (!destroyed) {
            Graphics2D g2d = (Graphics2D) g;
            Color shadowColor = new Color(0, 0, 0, 100);
            g2d.setColor(shadowColor);
            g2d.fillRoundRect(x + 5, y + 5, width, height, 10, 10);

            g2d.setColor(color);
            g2d.fillRoundRect(x, y, width, height, 10, 10);

            GradientPaint highlight = new GradientPaint(
                    x, y, color.brighter(),
                    x, y + height / 2, color
            );
            g2d.setPaint(highlight);
            g2d.fillRoundRect(x, y, width, height / 2, 10, 10);

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
                blinkTimer.stop(); // 타이머 정지
            }
            playSound("breakBGM.wav"); // 소리 재생
        }
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
            e.printStackTrace();
        }
    }
}
