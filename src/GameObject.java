import java.awt.*;

public abstract class GameObject {
    protected int x, y, width, height;

    public abstract void update();
    public abstract void draw(Graphics g);

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
