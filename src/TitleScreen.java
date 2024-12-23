import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class TitleScreen extends JPanel {
    private JFrame frame;
    private Clip clip;  // 오디오 클립을 저장할 변수

    public TitleScreen(JFrame frame) {
        this.frame = frame;
        setBackground(Color.BLACK);

        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    stopMusic(); // 음악 정지
                    startGame();
                }
            }
        });

        playMusic("/gameBGM.wav"); // 배경 음악 재생
    }

    // 배경 음악 재생
    private void playMusic(String soundFileName) {
        try {
            URL url = getClass().getResource(soundFileName);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);  // 음악을 무한 반복
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

    // 게임 화면으로 전환
    private void startGame() {
        stopMusic();  // 게임 시작 시 음악 정지
        frame.remove(this);
        GameScreen gameScreen = new GameScreen(frame);
        frame.add(gameScreen);
        frame.revalidate();
        frame.repaint();
        gameScreen.requestFocusInWindow();
    }

    public void showTitleScreen() {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(this);
        frame.revalidate();
        frame.repaint();
        this.requestFocusInWindow();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 그라데이션 배경 설정
        Graphics2D g2d = (Graphics2D) g;
        GradientPaint gp = new GradientPaint(0, getHeight()*0.3f, Color.BLACK, 0, getHeight(), Color.BLUE);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        Font titleFont = new Font("Showcard Gothic", Font.BOLD, 60);
        Font normalFont = new Font("Comic Sans MS", Font.BOLD, 30);

        String text1 = "Java Programming";
        String text2 = "Homework #5";
        String subTitleText = "BLOCK BREAKER";
        String instructionText = "PRESS SPACEBAR TO PLAY";

        g.setColor(Color.WHITE);
        g.setFont(normalFont);
        drawCenteredString(g, text1, 180, getWidth());
        drawCenteredString(g, text2, 230, getWidth());
        g.setFont(titleFont);
        drawCenteredString(g, subTitleText, 350, getWidth());
        g.setColor(Color.YELLOW);
        g.setFont(normalFont);
        drawCenteredString(g, instructionText, 500, getWidth());
    }

    // 중앙에 문자열을 그리는 메서드
    private void drawCenteredString(Graphics g, String text, int y, int width) {
        FontMetrics metrics = g.getFontMetrics();
        int x = (width - metrics.stringWidth(text)) / 2;
        g.drawString(text, x, y);
    }
}
