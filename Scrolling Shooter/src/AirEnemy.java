import java.awt.*;
import java.util.LinkedList;

public abstract class AirEnemy {

    protected int x, y, velX, velY;
    protected int width, height;
    protected boolean moveLeft, moveRight;
    protected Color color;
    protected final int shootTimerDef = 300;
    protected int shootTimer = shootTimerDef;
    protected LinkedList<Bullet> bullets = new LinkedList<>();

    public AirEnemy(int x, int y) {
        this.x = x;
        this.y = y;
        velX = Engine.intAtWidth640(1)/2;
        velY = Engine.intAtWidth640(2)/4;
        width = Engine.intAtWidth640(32);
        height = width;
        moveRight = false;
        moveLeft = false;
        color = Color.red;
    }

    public abstract void update();

    public abstract void render(Graphics g);

    protected void shoot() {
        bullets.add(new EnemyBullet(x + width/2,y));
        shootTimer = shootTimerDef;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public Bullet checkCollision(Player player) {
        Bullet b = null;
        for (Bullet bullet: bullets) {
            if (bullet.getBounds().intersects(player.getBounds())) {
                b = bullet;
            }
        }
        return b;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setVelX(int velX) {
        this.velX = velX;
    }

    public void setVelY(int velY) {
        this.velY =  velY;
    }

    // Private Methods

    private void move() {
        y += velY;
        x = Engine.clamp(x, 0, Engine.WIDTH - width);
    }
}
