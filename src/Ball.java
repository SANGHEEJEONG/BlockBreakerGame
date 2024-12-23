import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Ball {
    private double x, y;
    private double dx, dy;
    private int radius = 5;
    private double speed;
    private int round;

    public Ball(int x, int y, int initialRound) {
        this.x = x;
        this.y = y;
        this.round = initialRound;

        updateSpeedForRound();

        double angle = Math.toRadians(Math.random() * 45 + 45);
        dx = Math.cos(angle) * speed;
        dy = -Math.abs(Math.sin(angle) * speed); // 위쪽으로 설정
    }

    public void updateSpeedForRound() {
        this.speed = 6 + round - 1; // 기본 속력 6에 라운드마다 1씩 증가
    }

    public void move() {
        x += dx;
        y += dy;

        // 벽과 충돌 처리
        if (x <= 0 || x >= 885 - radius * 2) {
            reflectX();
        }

        if (y <= 0) {
            reflectY();
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

    public void reflectWithAngle() {
        dx = -dx;
        dy = -dy;
        adjustAngle(5); // 5도 각도 조정
    }

    private void adjustAngle(double degrees) {
        double angleAdjustment = Math.toRadians(degrees);
        double currentAngle = Math.atan2(dy, dx);
        double newAngle;

        if (dx < 0) {
            if (dy < 0) {
                newAngle = currentAngle + angleAdjustment;
            } else {
                newAngle = currentAngle - angleAdjustment;
            }
        } else {
            newAngle = currentAngle + (Math.random() > 0.5 ? angleAdjustment : -angleAdjustment);
        }

        dx = Math.cos(newAngle) * speed;
        dy = Math.sin(newAngle) * speed;
    }

    private void applyAngleAdjustment() {
        double angleAdjustment = Math.toRadians((Math.random() - 0.5) * 20);
        double currentAngle = Math.atan2(dy, dx);
        double newAngle = currentAngle + angleAdjustment;

        double newSpeed = Math.sqrt(dx * dx + dy * dy); // 기존 속도 유지
        dx = Math.cos(newAngle) * newSpeed;
        dy = Math.sin(newAngle) * newSpeed;
    }

    public void calculateReboundAngle(Paddle paddle) {
        int paddleX = paddle.getX();
        int paddleWidth = paddle.getWidth();
        int ballCenter = (int) (x + radius);

        // 패들에 충돌한 위치에 따라 각도를 계산
        double hitPosition = (double) (ballCenter - paddleX) / paddleWidth;
        dx = (hitPosition - 0.5) * 2 * speed;
        dy = -Math.abs(dy);

        // 속력을 다시 계산하여 일정하게 유지
        double currentSpeed = Math.sqrt(dx * dx + dy * dy);
        double adjustmentFactor = speed / currentSpeed;

        // dx와 dy를 조정하여 속력을 일정하게 유지
        dx *= adjustmentFactor;
        dy *= adjustmentFactor;
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
        double leftAngle = currentAngle - Math.toRadians(20);
        Ball leftBall = new Ball((int)x, (int)y,round);
        leftBall.setAngle(leftAngle);
        leftBall.setSpeed(speed);
        newBalls.add(leftBall);

        // +30도 방향 공
        double rightAngle = currentAngle + Math.toRadians(20);
        Ball rightBall = new Ball((int)x, (int)y,round);
        rightBall.setAngle(rightAngle);
        rightBall.setSpeed(speed);
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
