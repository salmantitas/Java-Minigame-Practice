import java.awt.*;

public class PlayerBullet extends Bullet{

    PlayerBullet(int x, int y) {
        super(x, y);
    }

    public void update() {
        y -= vel;
    };

    public void render(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillOval(x,y, width, height);
    };
}
