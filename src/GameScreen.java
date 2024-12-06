import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class GameScreen extends JPanel implements Runnable {
    private JFrame frame;
    private Paddle paddle;
    private Ball ball;
    private List<Block> blocks;
    private boolean gameOver = false;

    public GameScreen(JFrame frame) {
        this.frame = frame;
        this.setBackground(Color.BLACK);

        // 초기화
        paddle = new Paddle(350, 500);
        ball = new Ball(400, 450);
        blocks = new ArrayList<>();

        // 블록 생성
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                blocks.add(new Block(100 * i + 50, 30 * j + 50));
            }
        }

        // 키 입력 처리
        this.setFocusable(true);
        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    paddle.moveLeft();
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    paddle.moveRight();
                }
            }
        });

        // 게임 스레드 시작
        Thread gameThread = new Thread(this);
        gameThread.start();
    }

    // 게임 로직
    @Override
    public void run() {
        while (!gameOver) {
            ball.move();
            ball.checkCollision(paddle, blocks);
            repaint();
            try {
                Thread.sleep(10); // 10ms마다 화면 갱신
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        showGameOverScreen();
    }

    // 게임 오버 화면으로 전환
    private void showGameOverScreen() {
        frame.remove(this);
        GameOverScreen gameOverScreen = new GameOverScreen(frame);
        frame.add(gameOverScreen);
        frame.revalidate();
        frame.repaint();
    }

    // 화면 그리기
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 블록 그리기
        for (Block block : blocks) {
            block.draw(g);
        }

        // 라켓 그리기
        paddle.draw(g);

        // 공 그리기
        ball.draw(g);

        if (gameOver) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString("GAME OVER", 250, 300);
        }
    }
}
