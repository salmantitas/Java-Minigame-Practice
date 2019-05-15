package com.euhedral.game;

import com.euhedral.engine.Engine;

import java.awt.*;

public abstract class EnemyAir extends Enemy{

    public EnemyAir(int x, int y) {
        super(x,y, ID.Air);
    }

    @Override
    public void update() {
        super.update();
        move();
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
    }

//    protected void shoot() {
//        bullets.add(new BulletEnemy(x + width/2,y));
//        shootTimer = shootTimerDef;
//    }

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

    public void move() {
        y += velY;
        x = Engine.clamp(x, 0, Engine.WIDTH - width);
    }
}