import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Ball {
    private int x, y; // 공의 위치
    private int dx, dy; // 공의 이동 방향
    private int radius = 5; // 공의 반지름
    private int speed = 3; // 공의 속도

    public Ball(int x, int y) {
        this.x = x;
        this.y = y;
        this.dx = speed; // 초기 x 방향 속도
        this.dy = -speed; // 초기 y 방향 속도
    }

    public void move() {
        x += dx;
        y += dy;

        // 벽과 충돌 처리
        if (x <= 0 || x >= 900) {
            dx = -dx; // x 방향 반전
        }

        if (y <= 0) {
            dy = -dy; // y 방향 반전
        }
    }

    public void reverseY() {
        dy = -dy;
    }

    public boolean isOutOfBounds(int frameHeight) {
        return y >= frameHeight; // 화면 아래로 벗어나는 경우 true 반환
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillOval(x, y, radius * 2, radius * 2);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, radius * 2, radius * 2);
    }

    public List<Ball> split() {
        List<Ball> newBalls = new ArrayList<>();
        System.out.println("공 복제 시작");

        // 좌하 방향
        Ball leftBall = new Ball(x, y);
        leftBall.setDirection(-1, 1);
        leftBall.setSpeed(speed); // 동일한 속도 적용
        newBalls.add(leftBall);

        // 중심 아래 방향
        Ball middleBall = new Ball(x, y);
        middleBall.setDirection(0, 1);
        middleBall.setSpeed(speed); // 동일한 속도 적용
        newBalls.add(middleBall);

        // 우하 방향
        Ball rightBall = new Ball(x, y);
        rightBall.setDirection(1, 1);
        rightBall.setSpeed(speed); // 동일한 속도 적용
        newBalls.add(rightBall);

        System.out.println("공 복제 완료: " + newBalls.size() + "개의 공 생성");
        return newBalls;
    }

    public void setDirection(int dx, int dy) {
        this.dx = dx * speed; // 방향 설정에 속도 적용
        this.dy = dy * speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
        // 속도 변경에 따라 방향도 재설정
        if (dx != 0) dx = (dx > 0 ? 1 : -1) * speed;
        if (dy != 0) dy = (dy > 0 ? 1 : -1) * speed;
    }

    public int getSpeed() {
        return speed;
    }
}
