import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BlockManager {
    private List<Block> blocks;
    private static final int LEFT_WALL_WIDTH = 10; // 왼쪽 벽의 너비
    private static final int RIGHT_WALL_WIDTH = 20; // 오른쪽 벽의 너비

    public BlockManager(int panelWidth, int panelHeight, int round) {
        blocks = new ArrayList<>();
        int rows = round * 3; // 라운드에 따라 행 수 증가
        int cols = round * 3; // 라운드에 따라 열 수 증가
        int padding = 7; // 블록 간의 간격(패딩)

        // 왼쪽과 오른쪽 벽 너비를 고려한 패널의 사용 가능한 너비
        int availableWidth = panelWidth - LEFT_WALL_WIDTH - RIGHT_WALL_WIDTH;

        // 블록 너비와 높이를 패딩을 고려하여 계산
        int blockWidth = (availableWidth - (cols - 1) * padding) / cols;
        int blockHeight = (panelHeight - (rows - 1) * padding) / rows;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int x = LEFT_WALL_WIDTH + col * (blockWidth + padding); // 왼쪽 벽 너비를 고려한 x 좌표
                int y = row * (blockHeight + padding); // 패딩 추가된 y 좌표
                blocks.add(new Block(x, y, blockWidth, blockHeight));
            }
        }
    }

    public List<Ball> checkCollision(Ball ball, ScoreManager scoreManager) {
        List<Ball> newBalls = new ArrayList<>();
        for (Block block : blocks) {
            if (!block.isDestroyed() && ball.getBounds().intersects(block.getBounds())) {
                ball.reflectWithAngle();
                if (block.getColor() != Color.BLUE) {
                    newBalls.addAll(ball.split());
                }
                block.destroy();
                scoreManager.addScore(10);
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

    public int getRemainingBlocks() {
        int count = 0;
        for (Block block : blocks) {
            if (!block.isDestroyed()) {
                count++;
            }
        }
        return count;
    }
}
