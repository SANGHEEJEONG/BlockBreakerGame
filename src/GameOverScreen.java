import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class GameOverScreen extends JPanel {
    private JFrame frame;
    private Clip clip; // 오디오 클립을 저장할 변수
    private boolean isVisibleText2 = true; // text2의 표시 상태를 제어
    private Timer timer; // 깜빡임 효과를 위한 타이머

    public GameOverScreen(JFrame frame) {
        this.frame = frame;
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    stopMusic();
                    stopBlinking(); // 깜빡임 중지
                    TitleScreen titleScreen = new TitleScreen(frame);
                    titleScreen.showTitleScreen();
                }
            }
        });

        playMusic("gameOverBGM.wav"); // 게임 오버 음악 재생
        startBlinking(); // 텍스트 깜빡임 시작
    }

    // 배경 음악 재생
    private void playMusic(String soundFileName) {
        try {
            URL url = getClass().getResource(soundFileName);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // 음악 정지
    private void stopMusic() {
        if (clip != null) {
            clip.stop();
            clip.close();
        }
    }

    // 깜빡임 효과 시작
    private void startBlinking() {
        timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                isVisibleText2 = !isVisibleText2; // 표시 상태를 반전
                repaint(); // 컴포넌트 다시 그리기 요청
            }
        }, 0, 500); // 0.5초마다 실행
    }

    // 깜빡임 효과 중지
    private void stopBlinking() {
        if (timer != null) {
            timer.cancel(); // 타이머 작업 취소
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);

        // 그라데이션 배경 설정
        Graphics2D g2d = (Graphics2D) g;
        GradientPaint gp = new GradientPaint(0, getHeight()*0.3f, Color.BLACK, 0, getHeight(), Color.BLUE);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        Font titleFont = new Font("Showcard Gothic", Font.BOLD, 60);
        Font normalFont = new Font("Comic Sans MS", Font.BOLD, 30);

        String text1 = "GAME OVER";

        g.setColor(Color.WHITE);
        g.setFont(titleFont);
        drawCenteredString(g, text1, this.getHeight() / 2 - 50, this.getWidth());

        if (isVisibleText2) {
            String text2 = "PRESS SPACEBAR!";
            g.setFont(normalFont);
            g.setColor(Color.YELLOW);
            drawCenteredString(g, text2, this.getHeight() / 2 + 50, this.getWidth());
        }
    }

    // 중앙에 문자열을 그리는 메서드
    private void drawCenteredString(Graphics g, String text, int yPos, int width) {
        FontMetrics metrics = g.getFontMetrics(g.getFont());
        int x = (width - metrics.stringWidth(text)) / 2;
        g.drawString(text, x, yPos);
    }

    public void showGameOverScreen() {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(this);
        frame.revalidate();
        frame.repaint();
        this.requestFocusInWindow(); // 포커스를 명시적으로 요청
    }
}
