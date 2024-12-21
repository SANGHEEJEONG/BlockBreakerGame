import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class TitleScreen extends JPanel {
    private JFrame frame;
    private Clip clip;  // 오디오 클립을 저장할 변수

    public TitleScreen(JFrame frame) {
        this.frame = frame;
        this.setBackground(Color.BLACK);
        this.setLayout(new BorderLayout()); // 중앙 정렬을 위해 BorderLayout 사용

        // 텍스트 표시용 JLabel
        JLabel titleLabel = new JLabel("PRESS SPACEBAR TO PLAY", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(Color.WHITE); // 텍스트 색상 설정
        this.add(titleLabel, BorderLayout.CENTER); // 중앙에 추가

        // 스페이스바 입력 대기
        this.setFocusable(true);
        this.addKeyListener(new KeyAdapter() {
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
            // 음악 파일 위치 지정
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
        GameScreen gameScreen = new GameScreen(frame); // GameScreen 클래스를 정의해야 합니다.
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
}
