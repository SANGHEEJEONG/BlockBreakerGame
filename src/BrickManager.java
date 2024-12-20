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

    public List<Ball> checkCollision(Ball ball) {
        List<Ball> newBalls = new ArrayList<>();
        for (Block block : blocks) {
            if (!block.isDestroyed() && ball.getBounds().intersects(block.getBounds())) {
                ball.reverseY();
                if (block.getColor() == Color.YELLOW) {
                    // 공 복제를 Ball 클래스에 위임
                    newBalls.addAll(ball.split());
                }
                block.destroy();
                break;
            }
        }
        return newBalls;
    }

    public void draw(Graphics g) {
        for (Block block : blocks) {
            block.draw(g);
        }
    }

    public List<Block> getBlocks() {
        return blocks;
    }
}
