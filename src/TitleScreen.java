import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TitleScreen extends JPanel {
    private JFrame frame;

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
                    startGame();
                }
            }
        });
    }

    // 게임 시작
    private void startGame() {
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
}
