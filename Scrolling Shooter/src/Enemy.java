import java.awt.*;
import java.util.LinkedList;

public abstract class Enemy {

    protected int x, y;
    protected ID id;
    protected float velX, velY;
    protected int width, height;
    protected boolean moveLeft, moveRight;
    protected Color color;
    protected final int shootTimerDef = 300;
    protected int shootTimer = shootTimerDef;
    protected LinkedList<Bullet> bullets = new LinkedList<>();
    protected boolean inscreen = false;
    protected Camera cam;

    public Enemy(int x, int y, ID id) {
        this.x = x;
        this.y = y;
        this.id = id;
        moveRight = false;
        moveLeft = false;
        cam = GameController.getCamera();
    }

    public void update() {
        move();
        shootTimer--;
        if (!inscreen)
            inscreen = y > cam.getY();
        if (inscreen)
            if (shootTimer <= 0)
                shoot();

        for (Bullet bullet: bullets) {
            bullet.update();
        }
    }

    public abstract void render(Graphics g);

    protected void shoot() {
        bullets.add(new BulletEnemy(x + width/2,y));
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

    public ID getID() {
        return id;
    }

    // Private Methods

    private void move() {
        y += velY;
        x = Engine.clamp(x, 0, Engine.WIDTH - width);
    }

}
