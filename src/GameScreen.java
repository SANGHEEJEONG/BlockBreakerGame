import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class GameScreen extends JPanel implements Runnable {
    private JFrame frame;
    private Paddle paddle;
    private List<Ball> balls; // 공 리스트로 관리
    private BrickManager brickManager;
    private boolean gameOver = false;

    // 라운드 관련 변수
    private int currentRound = 1; // 현재 라운드
    private int totalRounds = 3;  // 총 라운드 수

    // 키 상태 변수
    private boolean leftPressed = false;
    private boolean rightPressed = false;

    public GameScreen(JFrame frame) {
        this.frame = frame;
        this.setBackground(Color.BLACK);

        // 초기화
        this.setFocusable(true);
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    leftPressed = true;
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    rightPressed = true;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    leftPressed = false;
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    rightPressed = false;
                }
            }
        });

        initializeGame();

        // 게임 스레드 시작
        new Thread(this).start();
    }

    private void initializeGame() {
        paddle = new Paddle(350, 600);
        balls = new ArrayList<>();
        balls.add(new Ball(390, 530)); // 첫 번째 공 추가
        brickManager = new BrickManager(900, 420, currentRound);
    }

    private void initializeRound() {
        // 라운드 초기화: 벽돌 및 공 재설정
        balls.clear();
        balls.add(new Ball(390, 530)); // 새로운 공 추가
        brickManager = new BrickManager(900, 420, currentRound); // 새로운 벽돌 배치
        System.out.println("라운드 " + currentRound + " 시작!");
    }

    private boolean isRoundComplete() {
        return brickManager.getRemainingBlocks() == 0; // 벽돌이 모두 파괴되었는지 확인
    }

    @Override
    public void run() {
        while (!gameOver) {
            // 라켓 움직임 처리
            if (leftPressed) {
                paddle.moveLeft();
            }
            if (rightPressed) {
                paddle.moveRight();
            }

            // 공 및 게임 로직 처리
            List<Ball> newBalls = new ArrayList<>();
            for (int i = 0; i < balls.size(); i++) {
                Ball ball = balls.get(i);
                ball.move();

                if (ball.getBounds().intersects(paddle.getBounds())) {
                    ball.reverseY();
                    ball.calculateReboundAngle(paddle);
                }

                newBalls.addAll(brickManager.checkCollision(ball));

                if (ball.isOutOfBounds(frame.getHeight())) {
                    balls.remove(ball); // 화면 아래로 벗어난 공 제거
                    i--; // 리스트에서 공 제거 후 인덱스 조정
                }
            }

            balls.addAll(newBalls); // 복제된 공 추가

            if (balls.isEmpty() && brickManager.getRemainingBlocks() > 0) {
                System.out.println("Game Over 조건 충족");
                gameOver = true;
                break;
            }

            if (isRoundComplete()) { // 라운드 완료 확인
                if (currentRound < totalRounds) {
                    currentRound++;
                    initializeRound(); // 다음 라운드 초기화
                } else {
                    System.out.println("모든 라운드 완료!");
                    gameOver = true; // 모든 라운드 종료
                    break;
                }
            }

            repaint();
            try {
                Thread.sleep(10); // 화면 갱신 주기
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
        for (Ball ball : balls) { // 모든 공 그리기
            ball.draw(g);
        }
        brickManager.draw(g);

        // 라운드 정보 표시
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("라운드: " + currentRound + "/" + totalRounds, 10, 20);

        if (gameOver) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString("GAME OVER", 250, 300);
        }
    }
}
