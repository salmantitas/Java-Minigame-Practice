import java.awt.*;
import java.util.LinkedList;

public class Player {

    private int x, y, velX, velY, otherMovement, forwardMovement;
    private int width, height;
    private int power;
    private boolean moveLeft, moveRight, moveUp, moveDown;
    private Color color;
    private boolean canShoot;
    private final int shootTimerDef = 200;
    private int shootTimer = 0;
    private LinkedList<Bullet> bullets = new LinkedList<>();
    private boolean airBullet = true;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        velX = 0;
        velY = 0;
        this.power = 1;
        otherMovement = Engine.intAtWidth640(1)/2;
        forwardMovement = Engine.intAtWidth640(2)/2;
        width = Engine.intAtWidth640(24);
        height = width;
        moveRight = false;
        moveLeft = false;
        moveDown = false;
        moveUp = false;
        color = Color.darkGray;
    }

    public void update() {
        move();
        shootTimer--;

        if (canShoot && shootTimer <= 0)
            shoot();

        for (Bullet bullet: bullets) {
            bullet.update();
        }

    }

    public void render(Graphics g) {
//        g.fillRect(x + Engine.perc(width,40), y ,Engine.perc(width,12*2),height); // Body
//        g.fillRect(x, y + Engine.perc(height, 30) ,width, Engine.perc(height,12.5)); // Wingspan
//        g.fillRect(x + Engine.perc(width,25), y ,width/2, Engine.perc(height,4)); // Fans


        for (Bullet bullet: bullets) {
            bullet.render(g);
        }

        g.setColor(color);
        g.fillRect(x,y,width,height);
    }

    public Bullet checkCollision(Enemy  enemy) {
        Bullet b = null;
        for (Bullet bullet: bullets) {
            BulletPlayer bulletPlayer = (BulletPlayer) bullet;
            if (bulletPlayer.getBounds().intersects(enemy.getBounds()) && bulletPlayer.getId() == enemy.getID()) {
                b = bulletPlayer;
            }
        }
        return b;
    }

    public void moveLeft(boolean b) {
        moveLeft = b;
    }

    public void moveRight(boolean b) {
        moveRight = b;
    }

    public void moveUp(boolean b) {
        moveUp = b;
    }

    public void moveDown(boolean b) {
        moveDown = b;
    }

    public void canShoot(boolean b) {
        canShoot = b;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void setX(int x) {
        this.x = x;
    }

    public void switchBullet() {
        airBullet = !airBullet;
    }

    // Private Methods

    private void move() {
        x += velX;
        y += velY;

//        x = Engine.clamp(x, 0, Engine.WIDTH - width);
//        y = Engine.clamp(y, 0, Engine.HEIGHT - height);

        if (moveLeft && !moveRight) {
            velX = -otherMovement;
        }
        else if (moveRight && !moveLeft) {
            velX = otherMovement;
        }
        else if (!moveLeft && !moveRight || (moveLeft && moveRight))
            velX = 0;

        if (moveUp && !moveDown) {
            velY = -forwardMovement;
        }
        else if (moveDown && !moveUp ) {
            velY = otherMovement;
        }
        else if (!moveUp && !moveDown|| (moveUp && moveDown))
            velY = 0;
    }

    private void shoot() {
        if (power == 1) {
            if (airBullet)
                bullets.add(new BulletPlayerAir(x + width / 2, y));
            else
                bullets.add(new BulletPlayerGround(x + width / 2, y));
        }
        // reset shoot timer to default
        shootTimer = shootTimerDef;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
