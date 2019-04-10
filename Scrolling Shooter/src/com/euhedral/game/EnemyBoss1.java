package com.euhedral.game;

import com.euhedral.engine.Engine;

import java.awt.*;

public class EnemyBoss1 extends EnemyBoss{

    int distToCover = Engine.HEIGHT/12;

    public EnemyBoss1(int x, int y) {
        super(x,y);
        height = Engine.intAtWidth640(48);
        width = height*3;
        this.x = x - width/2;
        color = Color.orange;
        velX = Engine.intAtWidth640(2);
        health = 20;
        left = true;
    }

//    @Override
//    public void update() {
//        super.update();
//        move();
//    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        g.setColor(color);
        g.fillRect((int) x, (int) y, width, height);
    }

    @Override
    public void shoot() {
        // left gun
        bullets.add(new BulletEnemy((int) (1.1 * x), y + height / 2));

        // right gun
        bullets.add(new BulletEnemy(x + (int) (0.8 * width) , y + height / 2));
        shootTimer = shootTimerDef;

//        System.out.println("Shooting at (" + (x + width/2) + ", " + y + ")" );
    }

    public void moveInScreen() {
        if (distToCover > 0) {
            distToCover--;
            y += velY;
        }
        else {
            if (!left)
                x += velX;
            else
                x -= velX;
        }

        int min = Engine.percWidth(25) , max = Engine.percWidth(75) - (int) 1.8 * width;
        if (x < min)
            left = false;
        if (x > max)
            left = true;
    }

    // Private Methods

    // Needs to override


}
