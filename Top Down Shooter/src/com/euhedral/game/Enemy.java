package com.euhedral.game;

import com.euhedral.engine.Animation;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Enemy extends GameObject {

    Random r = new Random();
    int choose = 0;
    int hp = 100;

    public Enemy(float x, float y) {
        super(x, y, ObjectID.Enemy);
    }

    public Enemy(float x, float y, BufferedImage image) {
        super(x, y, image, ObjectID.Enemy);
    }

    public Enemy(float x, float y, BufferedImage[] images) {
        super(x, y, images, ObjectID.Enemy);
    }

    @Override
    public void initialize() {
        color = Color.YELLOW;
        width = 32;
        height = 32;

        animationSpeed = 20;
        anim = new Animation(animationSpeed, images[0], images[1], images[2]);
    }

    @Override
    public void update() {
        x += velX;
        y += velY;

        moveRandomly();
        anim.runAnimation();
    }

    @Override
    public void render(Graphics g) {
        drawDefault(g);
//        renderBounds(g);
    }

    @Override
    public void drawAnimation(Graphics g) {
        if (velX == 0 && velY == 0) {
            drawImage(g, images[0]);
        } else
//            drawImage(g, images[0]);
            anim.drawAnimation(g, (int) x, (int) y); // buggy
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

    public void collision(GameObject object) {

        Rectangle blockBounds = object.getBounds();
        if (getBoundsRight().intersects(blockBounds)) {
            x = object.x - width;
        }

        else if (getBoundsLeft().intersects(blockBounds)) {
            x = object.x + width;
        }

        else if (getBoundsTop().intersects(blockBounds)) {
            double frac = (double) width/ (double) height;
            y = object.y + (int) (frac * height);
        }

        else if (getBoundsBottom().intersects(blockBounds)) {
            y = object.y - height;
        }
        int constant = 1;

        velX *= -constant;
        velY *= -constant;

        if (choose == 0) {
            velX = r.nextInt(4 - -4) + -4;
            velY = r.nextInt(4 - -4) + -4;
        }
    }
}
