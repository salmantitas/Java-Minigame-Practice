import java.awt.*;

public class Rock extends Projectile {

    public Rock(float x, float y, float playerX, float playerY, int playerWidth) {
        super(x,y);
        Id = GameController.ID.Projectile;
        color = new Color(98, 48, 20);

        float diffX = x - playerX - playerWidth/2;
        float diffY = y - playerY - playerWidth/2;
        float distance = (float) Math.sqrt( (x - playerX * (x - playerX) +
                (y - playerY)*(y - playerY)));

        this.velX = ((-1/distance) * diffX);
        this.velY = ((-1/distance) * diffY);

        speed = Engine.floatAtWidth640(3);
        width = Engine.intAtWidth640(16);
        height = width;
    }

    public void update() {
        x += velX * speed;
        y += velY * speed;
    }

    public void render(Graphics g) {
        g.setColor(color);
        g.fillOval((int) x, (int) y, width, height);
    }
}
