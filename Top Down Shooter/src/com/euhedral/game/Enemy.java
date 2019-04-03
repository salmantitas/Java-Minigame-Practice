package com.euhedral.game;

import com.euhedral.engine.Engine;

import java.awt.*;
import java.util.Random;

public class Enemy extends GameObject {

    Random r = new Random();
    int choose = 0;
    int hp = 100;

    public Enemy(float x, float y) {
        super(x, y, ObjectID.Enemy);
        color = Color.YELLOW;
        width = 32;
        height = 32;
    }

    @Override
    public void update() {
        x += velX;
        y += velY;

        moveRandomly();
    }

    @Override
    public void render(Graphics g) {
        drawDefault(g);
    }

    private void moveRandomly() {
        choose = r.nextInt(10);

        if (choose == 0) {
            velX = r.nextInt(4 - -4) + -4;
            velY = r.nextInt(4 - -4) + -4;
        }
    }

    public Rectangle getBoundsBig() {
        return new Rectangle((int) x - 16, (int) y - 16, 64, 64);
    }

    public void collision() {
        int constant = 10;

        x += constant * velX;
        x += constant * velY;
        velX *= -1;
        velY *= -1;

//        if (choose == 0) {
//            velX = r.nextInt(4 - -4) + -4;
//            velY = r.nextInt(4 - -4) + -4;
//        }
    }
}
