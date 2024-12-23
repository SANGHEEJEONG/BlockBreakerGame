import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GameScreen extends JPanel implements Runnable {
    private JFrame frame;
    private Paddle paddle;
    private List<Ball> balls;
    private BlockManager brickManager;
    private ScoreManager scoreManager;
    private boolean gameOver = false;

    private int currentRound = 1;
    private final int totalRounds = 10;

    private boolean leftPressed = false;
    private boolean rightPressed = false;

    public GameScreen(JFrame frame) {
        this.frame = frame;
        setBackground(Color.BLACK);
        setFocusable(true);
        setupKeyBindings();
        initializeGame();
        new Thread(this).start();
    }

    private void setupKeyBindings() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        leftPressed = true;
                        break;
                    case KeyEvent.VK_RIGHT:
                        rightPressed = true;
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) leftPressed = false;
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) rightPressed = false;
            }
        });
    }

    private void initializeGame() {
        paddle = new Paddle(350, 600);
        balls = new ArrayList<>();
        balls.add(new Ball(390, 530, currentRound));
        brickManager = new BlockManager(900, 420, currentRound);
        scoreManager = ScoreManager.getInstance();
        scoreManager.resetCurrentScore();
    }

    private void initializeRound() {
        balls.clear();
        balls.add(new Ball(390, 530, currentRound));
        brickManager = new BlockManager(900, 420, currentRound);
    }

    private boolean isRoundComplete() {
        return brickManager.getRemainingBlocks() == 0;
    }

    @Override
    public void run() {
        while (!gameOver) {
            processGameLogic();
            repaint();
            sleepThread();
        }
        displayGameOverScreen();
    }

    private void processGameLogic() {
        if (leftPressed) paddle.moveLeft();
        if (rightPressed) paddle.moveRight();
        updateBalls();
        checkGameConditions();
    }

    private void updateBalls() {
        List<Ball> newBalls = new ArrayList<>();
        for (int i = 0; i < balls.size(); i++) {
            Ball ball = balls.get(i);
            ball.move();
            if (ball.getBounds().intersects(paddle.getBounds())) {
                playSound("bounceBGM.wav");
                ball.calculateReboundAngle(paddle);
            }
            newBalls.addAll(brickManager.checkCollision(ball, scoreManager));
            if (ball.isOutOfBounds(frame.getHeight())) {
                balls.remove(i--);
            }
        }
        balls.addAll(newBalls);
    }

    private void checkGameConditions() {
        if (balls.isEmpty() && brickManager.getRemainingBlocks() > 0) {
            gameOver = true;
        } else if (isRoundComplete()) {
            if (currentRound < totalRounds) {
                currentRound++;
                initializeRound();
            } else {
                gameOver = true;
            }
        }
    }

    private void sleepThread() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void displayGameOverScreen() {
        GameOverScreen gameOverScreen = new GameOverScreen(frame, scoreManager);
        gameOverScreen.showGameOverScreen();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGameElements(g);
    }

    private void drawGameElements(Graphics g) {
        paddle.draw(g);
        balls.forEach(ball -> ball.draw(g));
        brickManager.draw(g);
        if (gameOver) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString("GAME OVER", 250, 300);
        }
    }

    private void playSound(String soundFileName) {
        try {
            URL url = getClass().getResource(soundFileName);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
