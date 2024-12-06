import java.awt.*;

public class Paddle {
    private int x, y;
    private final int WIDTH = 100, HEIGHT = 20;

    public Paddle(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void moveLeft() {
        if (x > 0) {
            x -= 10;
        }
    }

    public void moveRight() {
        if (x + WIDTH < 800) {
            x += 10;
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(x, y, WIDTH, HEIGHT);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }
}
