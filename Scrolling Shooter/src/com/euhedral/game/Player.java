package com.euhedral.game;

import com.euhedral.engine.Engine;
import com.euhedral.engine.MobileEntity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class Player extends MobileEntity {

    // Shooting Entity
    private boolean canShoot;
    private int shootTimer = 0;
    private final int shootTimerDefault = 10;
    private LinkedList<Bullet> bullets = new LinkedList<>();

    // Personal
    private int levelHeight;
    private int power;
    private boolean ground = false;
    private boolean airBullet = true;

    // Test
    private int mx, my;
    private boolean destinationGiven = false;

    // Graphics
    private BufferedImage image;

    public Player(int x, int y, int levelHeight) {
        super(x,y, EntityID.Player);
        this.levelHeight = levelHeight;
        width = Engine.intAtWidth640(32);
        height = width;
        color = Color.darkGray;

        velX = 0;
        velY = 0;
        acceleration = 0.05f;
        minVelY = 3;
        minVelX = 4;
        velY = minVelY;
        velX = minVelX;
        maxVelY = 2 * minVelY;
        maxVelX = 2 * minVelX;

        friction = true;
        frictionalForce = 0.9f;

        moveRight = false;
        moveLeft = false;
        moveDown = false;
        moveUp = false;

        this.power = 1;
    }

    public Player(int x, int y, int levelHeight, BufferedImage image) {
        this(x, y, levelHeight);
        this.image = image;
    }

    public void update() {
//        System.out.println("Player at (" + x + ", " + y + ")");
        move();
        shootTimer--;

        if (canShoot && shootTimer <= 0)
            shoot();

        for (Bullet bullet : bullets) {
            bullet.update();
        }

    }

    public void render(Graphics g) {
//        g.fillRect(mx, my, 10, 10);

        for (Bullet bullet : bullets) {
            bullet.render(g);
        }

        g.setColor(color);
        g.fillRect(x, y, width, height); //stub
    }

    public Bullet checkCollision(Enemy enemy) {
        Bullet b = null;
        for (Bullet bullet : bullets) {
            BulletPlayer bulletPlayer = (BulletPlayer) bullet;
            if (bulletPlayer.getBounds().intersects(enemy.getBounds()) &&
                    (bulletPlayer.getContactId() == enemy.getID() || bulletPlayer.getContactId() == ContactID.Air && enemy.getID() == ContactID.Boss)) {
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

    // This function governs the movement of the player
    @Override
    protected void move() {
        super.move();

        int clampOffsetX = - 5 * width / 4;
        int clampOffsetY = height;

//        System.out.printf("VelX: %f | VelY: %f\n", velX, velY);

        x = Engine.clamp(x, 0, Engine.WIDTH + clampOffsetX);
        y = Engine.clamp(y, 5900, levelHeight + clampOffsetY);

        if (destinationGiven) {
            int positionOffset = 5;
            if (mx == y && my == y) {
                destinationGiven = false;
            }
            if (Math.abs(mx - x) < positionOffset) {
                moveLeft = false;
                moveRight = false;
            } else if (mx > x) {
                moveLeft = false;
                moveRight = true;
            } else if (mx < x) {
                moveLeft = true;
                moveRight = false;
            }
            if (Math.abs(my - y) < positionOffset) {
                moveUp = false;
                moveDown = false;
            } else if (my > y) {
                moveUp = false;
                moveDown = true;
            } else if (my < y) {
                moveUp = true;
                moveDown = false;
            }
        }

        // Moving Left
        if (moveLeft && !moveRight) {
//            velX = - horizontalMovement; // stub
            velX -= acceleration;
            velX = Engine.clamp(velX, - maxVelX, - minVelX);
        }

        // Moving Right
        else if (moveRight && !moveLeft) {
//            velX = horizontalMovement; // stub
            velX += acceleration;
            velX = Engine.clamp(velX, minVelX, maxVelX);
        }

        // Not Moving Left or Right
        else if (!moveLeft && !moveRight || (moveLeft && moveRight)) {
//            velX = 0; // stub
            if (velX > 0) {
                velX -= frictionalForce;
            } if (velX < 0) {
                velX += frictionalForce;
            }
        }

        // Moving Up
        if (moveUp && !moveDown) {
//            velY = -verticalMovement; // stub
            velY -= acceleration;
            velY = Engine.clamp(velY, - maxVelY, - minVelY);
        }

        // Moving Down
        else if (moveDown && !moveUp) {
//            velY = horizontalMovement;
            velY += acceleration;
            velY = Engine.clamp(velY, minVelY, maxVelY);

        }

        // Not Moving Up or Down
        else if (!moveUp && !moveDown || (moveUp && moveDown)) {
//            velY = 0; // stub
            if (velY > 0) {
                velY -= frictionalForce;
            }
            if (velY < 0) {
                velY += frictionalForce;
            }
        }
    }

    private void shoot() {
        if (power == 5) {
            // todo
        } else if (power == 4) {
            // todo
        } else if (power == 3) {
            // todo
        } else if (power == 2) {
            if (airBullet) {
                bullets.add(new BulletPlayerAir(x + 4, y, -90));
                bullets.add(new BulletPlayerAir(x + width - 8, y, -90));
            } else {
                bullets.add(new BulletPlayerGround(x + 8, y, -90));
                bullets.add(new BulletPlayerGround(x + width - 16, y, -90));
            }
        } else {
            if (airBullet) {
                bullets.add(new BulletPlayerAir(x + width / 2, y, -90));
            } else {
                bullets.add(new BulletPlayerGround(x + width / 2, y, -90));
            }
        }
        // reset shoot timer to default
        shootTimer = shootTimerDefault;
    }

    public void giveDestination(int mx, int my) {
        this.mx = mx - width / 2;
        this.my = levelHeight - Engine.percHeight(83.5) + my;
        System.out.println("Destination: " + this.my);
        destinationGiven = true;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getMx() {
        return mx;
    }

    public int getMy() {
        return my;
    }

    public int getPower() {
        return power;
    }

    public void setGround(Boolean ground) {
        this.ground = ground;
    }
}
