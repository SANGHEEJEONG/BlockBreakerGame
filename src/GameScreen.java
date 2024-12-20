import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameScreen extends JPanel implements Runnable {
    private JFrame frame;
    private Paddle paddle;
    private Ball ball;
    private BrickManager brickManager;
    private boolean gameOver = false;

    public GameScreen(JFrame frame) {
        this.frame = frame;
        this.setBackground(Color.BLACK);

        // 초기화
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

        initializeGame();

        // 게임 스레드 시작
        new Thread(this).start();
    }

    private void initializeGame() {
        paddle = new Paddle(350, 550);
        ball = new Ball(390, 530);
        brickManager = new BrickManager(900, 420);
    }

    @Override
    public void run() {
        while (!gameOver) {
            ball.move();
            brickManager.checkCollision(ball);

            if (ball.getBounds().intersects(paddle.getBounds())) {
                ball.reverseY();
            }

            if (ball.isOutOfBounds(frame.getHeight())) {
                System.out.println("Game Over 조건 충족");
                gameOver = true;
                break;
            }


            repaint();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        GameOverScreen gameOverScreen = new GameOverScreen(frame);
        gameOverScreen.showGameOverScreen();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paddle.draw(g);
        ball.draw(g);
        brickManager.draw(g);

        if (gameOver) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString("GAME OVER", 250, 300);
        }
    }
}
