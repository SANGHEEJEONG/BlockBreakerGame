import java.awt.*;

public class Paddle {
    private int x, y, width = 100, height = 20;

    public Paddle(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void moveLeft() {
        if (x > 0) x -= 10;
    }

    public void moveRight() {
        if (x + width < 900) x += 10;
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(x, y, width, height);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
