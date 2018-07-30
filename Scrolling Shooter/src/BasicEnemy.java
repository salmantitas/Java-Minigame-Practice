import java.awt.*;
import java.util.LinkedList;

public class BasicEnemy extends Enemy{

    public BasicEnemy(int x, int y) {
        super(x,y);
    }

    public void update() {
        move();
        shootTimer--;
        if (shootTimer <= 0)
            shoot();

        for (Bullet bullet: bullets) {
            bullet.update();
        }
    }

    public void render(Graphics g) {

        for (Bullet bullet: bullets) {
            bullet.render(g);
        }

        g.setColor(color);
        g.fillRect(x,y,width,height);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void setX(int x) {
        this.x = x;
    }

    // Private Methods

    private void move() {
        y += velY;
    }
}
