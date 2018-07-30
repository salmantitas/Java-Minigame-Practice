import javafx.scene.shape.Circle;

import java.awt.*;

public abstract class Projectile {

    protected float x, y;
    protected GameController.ID Id;
    protected float velX, velY, speed;
    protected int width, height;
    Color color;

    public Projectile(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public abstract void update();

    public abstract void render(Graphics g);

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, width, height);
    }

    public GameController.ID getId() {
        return Id;
    }

}
