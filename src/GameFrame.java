import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel cards;

    public GameFrame() {
        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);
        cards.add(new TitleScreen(this), "Title");
        cards.add(new GameScreen(this), "Game");
        cards.add(new GameOverScreen(this), "GameOver");

        add(cards);
        setTitle("Block Breaker Game");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public void showGameScreen() {
        cardLayout.show(cards, "Game");  // 타이틀에서 게임 화면으로 전환
    }

    public void showGameOverScreen() {
        cardLayout.show(cards, "GameOver");  // 게임 오버 화면으로 전환
    }

    public void showTitleScreen() {
        cardLayout.show(cards, "Title");  // 타이틀 화면으로 전환
    }
}
