import java.awt.*;

public class EnemyBullet extends Bullet{

    EnemyBullet(int x, int y) {
        super(x, y);
    }

    public void update() {
        y += vel;
    };

    public void render(Graphics g) {
        g.setColor(Color.orange);
        g.fillOval(x,y, width, height);
    };
}
