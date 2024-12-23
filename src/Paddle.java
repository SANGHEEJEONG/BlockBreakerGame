import java.awt.*;

public class Paddle {
    private int x, y, width = 150, height = 20;
    private int speed = 5; // 이동 속도를 관리하는 변수

    public Paddle(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void moveLeft() {
        if (x > 0) x -= speed; // speed 변수를 사용하여 이동
    }

    public void moveRight() {
        if (x + width < 885) x += speed; // speed 변수를 사용하여 이동
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(x, y, width, height);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public int getX() {
        return x;
    }

    public int getWidth() {
        return width;
    }
}
