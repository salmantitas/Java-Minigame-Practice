import java.awt.*;

public abstract class Bullet  {
    protected int x, y, vel;
    protected boolean collided;
    protected int width, height;
    protected Color color;

    Bullet(int x, int y) {
        this.x = x;
        this.y = y;
        collided = false;
        width = Engine.intAtWidth640(8)/2;
        height = Engine.intAtWidth640(24)/2;
        vel = Engine.intAtWidth640(2);
    }

    public abstract void update();

    public abstract void render(Graphics g);

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setVel(int vel) {
        this.vel = vel;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
