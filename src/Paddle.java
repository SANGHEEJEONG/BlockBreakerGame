import java.awt.*;

public class Paddle {
    private int x, y, width = 100, height = 20;
    private int speed = 15; // 이동 속도를 관리하는 변수

    public Paddle(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void moveLeft() {
        if (x > 0) x -= speed; // speed 변수를 사용하여 이동
    }

    public void moveRight() {
        if (x + width < 900) x += speed; // speed 변수를 사용하여 이동
    }

    public void setSpeed(int speed) {
        this.speed = speed; // 속도 설정 메서드
    }

    public int getSpeed() {
        return speed; // 현재 속도 반환
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(x, y, width, height);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
