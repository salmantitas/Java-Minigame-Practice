package com.euhedral.game;

import com.euhedral.engine.Engine;

import java.awt.*;
import java.util.Random;

/*
 *  Standard Enemies, flies downwards and shoots a missile at intervals
 * */
public class EnemyBasic extends Enemy {

    public EnemyBasic(int x, int y, EnemyID enemyID, ContactID contactID) {
        super(x,y, enemyID, contactID);
        power = 1;
        width = Engine.intAtWidth640(32);
        height = width;
        color = Color.red;
        r = new Random();
        initialize();
    }

    public EnemyBasic(int x, int y, EnemyID enemyID, ContactID contactID, Color color) {
        this(x, y, enemyID, contactID);
        this.color = color;
    }

    @Override
    public void initialize() {
        if (enemyID == EnemyID.Basic) {
            power = 1;
            shootTimerDef = 250;
            velY = 1.8f;
            healthRange(3,5);
        }
        if (enemyID == EnemyID.Fast) {
            power = 2;
            shootTimerDef = 150;
            velY = 3f;
            healthRange(1,3);
        }
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
            double angle = 70;
            bullets.add(new BulletEnemy(x + width/2,y, angle));
            bullets.add(new BulletEnemy(x + width/2,y, angle + 2 * (90 - angle)));
            resetShooter();
        }
        else
            super.shoot();
    }

    public void setX(int x) {
        this.x = x;
    }

    // Private Methods
}
