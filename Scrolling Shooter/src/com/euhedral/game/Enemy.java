package com.euhedral.game;

import com.euhedral.engine.Engine;
import com.euhedral.engine.MobileEntity;

import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

public class Enemy extends MobileEntity {

    protected int health;
    protected ContactID contactId;
    protected int power = 1;
    protected EnemyID enemyID;
    protected float offscreenVelY;
    protected boolean moveLeft, moveRight;
    protected Color color;
    protected int shootTimerDef = 250;
    protected int shootTimer = shootTimerDef;
    protected LinkedList<Bullet> bullets = new LinkedList<>();
    protected boolean inscreen = false;
    protected Camera cam;
    protected Random r;
    protected int score = 50;
    protected int distance;
    protected int shotNum = 0;
    protected int movementTimer;

    public Enemy(int x, int y, EnemyID enemyID) {
        super(x, y, EntityID.Enemy);
        this.enemyID = enemyID;
        contactId = ContactID.Air;
        velY = 1.95f;
        offscreenVelY = velY;
        moveRight = false;
        moveLeft = false;
        cam = GameController.getCamera();
    }

    public Enemy(int x, int y, EnemyID enemyID, ContactID contactId) {
        this(x, y, enemyID);
        this.contactId = contactId;
    }

//    public Enemy(int x, int y)

    public void damage() {
        this.health--;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean isInscreen() {
        return inscreen;
    }

    public void setInscreen(boolean inscreen) {
        this.inscreen = inscreen;
    }

    public void update() {

        shootTimer--;
        if (!inscreen) {
            inscreen = y > cam.getMarker() + 100;
        }
        if (inscreen) {
            if (shootTimer <= 0) {
                shoot();
            }
        }

//        for (Bullet bullet: bullets) {
//            bullet.update();
//        }

        move();
    }

    public void render(Graphics g) {
//        for (Bullet bullet: bullets) {
//            bullet.render(g);
//        }
    }

    public void move() {
//        super.move();
        x = Engine.clamp(x, 0, Engine.WIDTH - width);

        if (inscreen) {
            moveInScreen();
        } else {
            y += offscreenVelY;

        }
    }

    protected void shoot() {
        shotNum++;
        resetShooter();
    }

    protected void shootDownDefault() {
        bullets.add(new BulletEnemy(x + width/2,y, 90));
    }

    protected void resetShooter() {
        shootTimer = shootTimerDef;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    protected void healthRange(int min, int max) {
        health = r.nextInt(max - min) + min;
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

    public ContactID getID() {
        return contactId;
    }

    public int getScore() {
        return score;
    }

    public LinkedList<Bullet> getBullets() {
        return bullets;
    }

    public void clearBullets() {
        bullets.clear();
    }

    public void moveInScreen() {

    }

    public void moveHorizontally() {

    }

}
