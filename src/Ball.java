import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Ball {
    private double x, y; // 공의 위치
    private double dx, dy; // 공의 이동 방향
    private int radius = 5; // 공의 반지름
    private double speed; // 공의 속도

    public Ball(int x, int y) {
        this.x = x;
        this.y = y;

        // 초기 속도 및 각도 설정
        speed = 6; // 3~5 사이 속도
        double angle = Math.toRadians(Math.random() * 90 + 45); // 45도~135도 랜덤 각도
        dx = Math.cos(angle) * speed;
        dy = -Math.abs(Math.sin(angle) * speed); // 위쪽(음수 방향)으로 설정
    }

    public void move() {
        x += dx;
        y += dy;

        // 벽과 충돌 처리
        if (x <= 0 || x >= 900 - radius * 2) {
            reflectX(); // x 방향 반사
        }

        if (y <= 0) {
            reflectY(); // y 방향 반사
        }
    }

    private void reflectX() {
        dx = -dx;
        applyAngleAdjustment(); // 각도 조정
    }

    private void reflectY() {
        dy = -dy;
        applyAngleAdjustment(); // 각도 조정
    }

    private void applyAngleAdjustment() {
        // ±10도 정도의 각도 변화
        double angleAdjustment = Math.toRadians((Math.random() - 0.5) * 20); // -10도 ~ +10도
        double currentAngle = Math.atan2(dy, dx);
        double newAngle = currentAngle + angleAdjustment;

        // 새로운 방향 벡터 계산
        double newSpeed = Math.sqrt(dx * dx + dy * dy); // 기존 속도 유지
        dx = Math.cos(newAngle) * newSpeed;
        dy = Math.sin(newAngle) * newSpeed;
    }

    public void reverseY() {
        reflectY();
    }

    public boolean isOutOfBounds(int frameHeight) {
        return y >= frameHeight; // 화면 아래로 벗어나는 경우 true 반환
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillOval((int)x, (int)y, radius * 2, radius * 2);
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, radius * 2, radius * 2);
    }

    public List<Ball> split() {
        List<Ball> newBalls = new ArrayList<>();

        double currentAngle = Math.atan2(dy, dx); // 현재 각도 계산

        // -30도 방향 공
        double leftAngle = currentAngle - Math.toRadians(30); // -30도
        Ball leftBall = new Ball((int)x, (int)y);
        leftBall.setAngle(leftAngle);
        leftBall.setSpeed(speed); // 기존 공과 동일한 속도
        newBalls.add(leftBall);

        // +30도 방향 공
        double rightAngle = currentAngle + Math.toRadians(30); // +30도
        Ball rightBall = new Ball((int)x, (int)y);
        rightBall.setAngle(rightAngle);
        rightBall.setSpeed(speed); // 기존 공과 동일한 속도
        newBalls.add(rightBall);

        return newBalls;
    }

    public void setAngle(double angle) {
        dx = Math.cos(angle) * speed;
        dy = Math.sin(angle) * speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
        double magnitude = Math.sqrt(dx * dx + dy * dy);
        dx = (dx / magnitude) * speed;
        dy = (dy / magnitude) * speed;
    }
}
