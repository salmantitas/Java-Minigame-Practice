import java.awt.*;

public class EnemyAirBasic extends EnemyAir {

    public EnemyAirBasic(int x, int y) {
        super(x,y);
        velX = Engine.floatAtWidth640(1);
        velY = Engine.floatAtWidth640(2)/2;
        width = Engine.intAtWidth640(32);
        height = width;
        color = Color.red;
    }

    public void render(Graphics g) {

        for (Bullet bullet: bullets) {
            bullet.render(g);
        }

        g.setColor(color);
        g.fillRect(x,y,width,height);
    }

    public void setX(int x) {
        this.x = x;
    }

    // Private Methods

    public void move() {
        y += velY;
    }
}
