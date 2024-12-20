import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BrickManager {
    private List<Block> blocks;

    public BrickManager(int panelWidth, int panelHeight) {
        blocks = new ArrayList<>();
        int rows = 3;
        int cols = 3;
        int blockWidth = panelWidth / cols;
        int blockHeight = panelHeight / rows;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                blocks.add(new Block(col * blockWidth, row * blockHeight, blockWidth, blockHeight));
            }
        }
    }

    public void draw(Graphics g) {
        for (Block block : blocks) {
            block.draw(g);
        }
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public void checkCollision(Ball ball) {
        for (Block block : blocks) {
            if (!block.isDestroyed() && ball.getBounds().intersects(block.getBounds())) {
                ball.reverseY(); // Ball 클래스의 메서드로 속도 반전 처리
                block.destroy();
                break;
            }
        }
    }
}
