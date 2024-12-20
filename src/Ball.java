import java.awt.*;

public class Ball {
    private int x, y, dx = 2, dy = -2, radius = 5;

    public Ball(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move() {
        x += dx;
        y += dy;

        // 벽과 충돌 처리
        if (x <= 0 || x >= 900) {
            dx = -dx;
        }

        if (y <= 0) {
            dy = -dy;
        }
    }

    public void reverseY() {
        dy = -dy;
    }

    public boolean isOutOfBounds(int frameHeight) {
        return y >= frameHeight; // 화면 위 또는 아래로 벗어나는 경우 true 반환
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillOval(x, y, radius * 2, radius * 2);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, radius * 2, radius * 2);
    }
}
