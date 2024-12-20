import java.awt.*;

public class Block {
    private int x, y, width, height;
    private boolean destroyed = false;
    private Color color; // 블록의 색상을 저장

    public Block(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        // 색상을 랜덤으로 초기화 (한 번만 설정됨)
        if (Math.random() < 0.5) {
            this.color = Color.YELLOW;
        } else {
            this.color = Color.BLUE;
        }
    }

    public void draw(Graphics g) {
        if (!destroyed) {
            g.setColor(color); // 고정된 색상 사용
            g.fillRect(x, y, width, height);
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void destroy() {
        destroyed = true;
    }

    public boolean isDestroyed() {
        return destroyed;
    }
}
