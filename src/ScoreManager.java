public class ScoreManager {
    private static ScoreManager instance; // 싱글톤 인스턴스
    private int highScore = 0; // 최고 점수
    private int currentScore = 0; // 현재 점수

    // 생성자를 private으로 선언하여 외부에서 인스턴스를 생성하지 못하게 함
    private ScoreManager() {}

    // 싱글톤 인스턴스 접근 메서드
    public static ScoreManager getInstance() {
        if (instance == null) {
            instance = new ScoreManager();
        }
        return instance;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = Math.max(this.highScore, highScore);
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public void resetCurrentScore() {
        this.currentScore = 0;
    }

    public void addScore(int score) {
        this.currentScore += score;
        setHighScore(currentScore);
    }
}
