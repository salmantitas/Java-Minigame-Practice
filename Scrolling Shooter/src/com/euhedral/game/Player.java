package com.euhedral.game;

import com.euhedral.engine.Engine;

import java.awt.*;
import java.util.LinkedList;

public class Player {

    private int x, y, velX, velY, horizontalMovement, verticalMovement;
    private int width, height;
    private int power;
    private boolean moveLeft, moveRight, moveUp, moveDown;
    private Color color;
    private boolean canShoot;
    private boolean ground = false;
    private final int shootTimerDefault = 12;
    private int shootTimer = 0;
    private LinkedList<Bullet> bullets = new LinkedList<>();
    private boolean airBullet = true;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        velX = 0;
        velY = 0;
        this.power = 1;
        verticalMovement = Engine.intAtWidth640(2);
        horizontalMovement = Engine.intAtWidth640(3);
        width = Engine.intAtWidth640(24);
        height = width;
        moveRight = false;
        moveLeft = false;
        moveDown = false;
        moveUp = false;
        color = Color.darkGray;
    }

    public void update() {
//        System.out.println("com.euhedral.game.Player at (" + x + ", " + y + ")");
        move();
        shootTimer--;

        if (canShoot && shootTimer <= 0)
            shoot();

        for (Bullet bullet: bullets) {
            bullet.update();
        }

    }

    public void render(Graphics g) {
//        g.fillRect(x + com.euhedral.engine.Engine.perc(width,40), y ,com.euhedral.engine.Engine.perc(width,12*2),height); // Body
//        g.fillRect(x, y + com.euhedral.engine.Engine.perc(height, 30) ,width, com.euhedral.engine.Engine.perc(height,12.5)); // Wingspan
//        g.fillRect(x + com.euhedral.engine.Engine.perc(width,25), y ,width/2, com.euhedral.engine.Engine.perc(height,4)); // Fans


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
            if (bulletPlayer.getBounds().intersects(enemy.getBounds()) &&
                    (bulletPlayer.getId() == enemy.getID() || bulletPlayer.getId() == ID.Air && enemy.getID() == ID.Boss )) {
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
        if (ground)
            airBullet = !airBullet;
    }

    // Private Methods

    private void move() {
        x += velX;
        y += velY;

        x = Engine.clamp(x, 0, Engine.WIDTH - width);
//        y = com.euhedral.engine.Engine.clamp(y, 0, com.euhedral.engine.Engine.HEIGHT - height);

        if (moveLeft && !moveRight) {
            velX = -horizontalMovement;
        }
        else if (moveRight && !moveLeft) {
            velX = horizontalMovement;
        }
        else if (!moveLeft && !moveRight || (moveLeft && moveRight))
            velX = 0;

        if (moveUp && !moveDown) {
            velY = -verticalMovement;
        }
        else if (moveDown && !moveUp ) {
            velY = horizontalMovement;
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
        if (power == 2) {
            if (airBullet) {
                bullets.add(new BulletPlayerAir(x + 8, y));
                bullets.add(new BulletPlayerAir(x + width - 16, y));
            }
            else {
                bullets.add(new BulletPlayerGround(x + 8, y));
                bullets.add(new BulletPlayerGround(x + width - 16, y));
            }
        }
        // reset shoot timer to default
        shootTimer = shootTimerDefault;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getPower() {
        return power;
    }

    public void setGround(Boolean ground) {
        this.ground = ground;
    }
}
