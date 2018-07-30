import java.awt.*;

public class Life extends Projectile {

    public Life(float x) {
        super(x, 0);
        Id = GameController.ID.Life;
        color = Color.GREEN;
        speed = Engine.floatAtWidth640(1);
        width = Engine.intAtWidth640(16);
        height = width;
        velX = 0;
    }

    public void update() {
//        x += 0;
        y += speed;
    }

    public void render(Graphics g) {
        g.setColor(color);
        g.fillOval((int) x, (int) y, width, height);
    }
}
