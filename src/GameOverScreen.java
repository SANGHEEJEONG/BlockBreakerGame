import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.*;

public class GameOverScreen extends JPanel {
    private JFrame frame;
    private Clip clip; // 오디오 클립을 저장할 변수

    public GameOverScreen(JFrame frame) {
        this.frame = frame;
        this.setBackground(Color.BLACK);
        this.setLayout(new BorderLayout()); // 중앙 정렬을 위한 BorderLayout 설정

        // 텍스트 표시용 패널 생성
        JPanel textPanel = new JPanel();
        textPanel.setBackground(Color.BLACK);
        textPanel.setLayout(new GridLayout(2, 1)); // 위아래로 두 줄 배치

        JLabel gameOverLabel = new JLabel("GAME OVER", SwingConstants.CENTER);
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 50));
        gameOverLabel.setForeground(Color.WHITE);

        JLabel instructionLabel = new JLabel("PRESS SPACEBAR", SwingConstants.CENTER);
        instructionLabel.setFont(new Font("Arial", Font.BOLD, 30));
        instructionLabel.setForeground(Color.WHITE);

        textPanel.add(gameOverLabel);
        textPanel.add(instructionLabel);
        this.add(textPanel, BorderLayout.CENTER); // 중앙에 텍스트 배치

        // 스페이스바 입력 대기
        this.setFocusable(true);
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    stopMusic();
                    TitleScreen titleScreen = new TitleScreen(frame);
                    titleScreen.showTitleScreen();
                }
            }
        });

        playMusic("gameOverBGM.wav"); // 게임 오버 음악 재생
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

    public void showGameOverScreen() {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(this);
        frame.revalidate();
        frame.repaint();
        this.requestFocusInWindow(); // 포커스를 명시적으로 요청
    }
}
