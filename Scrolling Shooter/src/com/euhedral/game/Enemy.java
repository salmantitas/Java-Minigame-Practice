package com.euhedral.game;

import com.euhedral.engine.Engine;

import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

public abstract class Enemy {

    protected int x, y, health;
    protected ID id;
    protected float velX;
    protected float velY = Engine.floatAtWidth640(2)/2;;
    protected int width, height;
    protected int power = 1;
    protected boolean moveLeft, moveRight;
    protected Color color;
    protected int shootTimerDef = 250;
    protected int shootTimer = shootTimerDef;
    protected LinkedList<Bullet> bullets = new LinkedList<>();
    protected boolean inscreen = false;
    protected Camera cam;
    protected Random r;

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

    public Enemy(int x, int y, ID id) {
        this.x = x;
        this.y = y;
        this.id = id;
        moveRight = false;
        moveLeft = false;
        cam = GameController.getCamera();
    }

    public void update() {
        shootTimer--;
        if (!inscreen)
            inscreen = y > cam.getMarker() + 256;
        if (inscreen)
            if (shootTimer <= 0)
                shoot();

        for (Bullet bullet: bullets) {
            bullet.update();
        }
    }

    public void render(Graphics g) {
        for (Bullet bullet: bullets) {
            bullet.render(g);
        }
    }

    public abstract void move();

    protected void shoot() {
        resetShooter();
        bullets.add(new BulletEnemy(x + width/2,y, 1));
//        shootTimer = shootTimerDef;
    }

    protected void resetShooter() {
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

    protected void healthRange(int min, int max) {
        health = r.nextInt(max - min) + min;
    }

}
