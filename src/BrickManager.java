import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BrickManager {
    private List<Block> blocks;

    public BrickManager(int panelWidth, int panelHeight, int round) {
        blocks = new ArrayList<>();
        int rows = round * 3; // 라운드에 따라 행 수 증가
        int cols = round * 3; // 라운드에 따라 열 수 증가
        int padding = 7; // 블록 간의 간격(패딩)

        // 블록 너비와 높이를 패딩을 고려하여 계산
        int blockWidth = (panelWidth - (cols - 1) * padding) / cols;
        int blockHeight = (panelHeight - (rows - 1) * padding) / rows;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int x = col * (blockWidth + padding); // 패딩 추가된 x 좌표
                int y = row * (blockHeight + padding); // 패딩 추가된 y 좌표
                blocks.add(new Block(x, y, blockWidth, blockHeight));
            }
        }
    }

    public List<Ball> checkCollision(Ball ball) {
        List<Ball> newBalls = new ArrayList<>();
        for (Block block : blocks) {
            if (!block.isDestroyed() && ball.getBounds().intersects(block.getBounds())) {
                ball.reflectWithAngle();
                if (block.getColor() != Color.BLUE) {
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
