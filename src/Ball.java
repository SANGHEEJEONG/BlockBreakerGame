
import java.awt.*;

public class Ball {
    private int x, y, dx = 2, dy = -2;
    private final int RADIUS = 10;

    public Ball(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move() {
        x += dx;
        y += dy;

        // 벽과 충돌
        if (x <= 0 || x >= 790) {
            dx = -dx;
        }
        if (y <= 0) {
            dy = -dy;
        }
    }

    public void checkCollision(Paddle paddle, java.util.List<Block> blocks) {
        if (getBounds().intersects(paddle.getBounds())) {
            dy = -dy; // 라켓과 충돌 시 반사
        }

        // 블록과 충돌 시 처리
        for (Block block : blocks) {
            if (getBounds().intersects(block.getBounds())) {
                dy = -dy;
                blocks.remove(block); // 블록 제거
                break;
            }
        }

        // 게임 오버 처리 (화면 밖으로 나가면)
        if (y >= 600) {
            dy = 0;
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillOval(x, y, RADIUS * 2, RADIUS * 2);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, RADIUS * 2, RADIUS * 2);
    }
}
