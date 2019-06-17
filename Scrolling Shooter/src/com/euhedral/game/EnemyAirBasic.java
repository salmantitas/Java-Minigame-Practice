package com.euhedral.game;

import com.euhedral.engine.Engine;

import java.awt.*;
import java.util.Random;

/*
 *  Standard Enemies, flies downwards and shoots a missile at intervals
 * */
public class EnemyAirBasic extends EnemyAir {

    public EnemyAirBasic(int x, int y, int power) {
        super(x,y);
        width = Engine.intAtWidth640(32);
        height = width;
        color = Color.red;
        r = new Random();
        healthRange(3,5);
        this.power = power;
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        g.setColor(color);
        g.fillRect(x,y,width,height);
    }

    @Override
    protected void shoot() {
        if (power == 2) {
            bullets.add(new BulletEnemy(x + width/2,y, 0.5));
            bullets.add(new BulletEnemy(x + width/2,y, -0.5));
            resetShooter();
        }
        else
            super.shoot();
    }

    public void setX(int x) {
        this.x = x;
    }

    // Private Methods

    public void move() {
        y += velY;
    }
}
