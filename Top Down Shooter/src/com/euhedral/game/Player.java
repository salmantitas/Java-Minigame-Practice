package com.euhedral.game;

import com.euhedral.engine.Animation;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends GameObject {

//    private float riseVelocity;
    private boolean up = false, down = false, left = false, right = false, shoot = false;
//    private int ammo = 100;

    public Player(float x, float y) {
        super(x, y, ObjectID.Player);
    }

    public Player(float x, float y, BufferedImage image) {
        super(x, y, image, ObjectID.Player);
    }

    public Player(float x, float y, BufferedImage[] images) {
        super(x, y, images, ObjectID.Player);
    }

    @Override
    public void initialize() {
        /*************
         * Game Code *
         *************/

        color = Color.blue;
        width = 32;
        height = 48;

        animationSpeed = 10;
        anim = new Animation(animationSpeed, images[0], images[1], images[2]);
    }

    @Override
    public void update() {
        x += velX;
        y += velY;

        if (up)
            velY = -5;
        else if (!down)
            velY = 0;

        if (down)
            velY = 5;
        else if (!up)
            velY = 0;

        if (right)
            velX = 5;
        else if (!left)
            velX = 0;

        if (left)
            velX = -5;
        else if (!right)
            velX = 0;

        anim.runAnimation();
    }

    @Override
    public void render(Graphics g) {
        drawDefault(g);
        drawAnimation(g);
    }

    @Override
    public void drawAnimation(Graphics g) {
        if (velX == 0 && velY == 0) {
            drawImage(g, images[0]);
        } else
            anim.drawAnimation(g, (int) x, (int) y);
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean canShoot() {
        return shoot;
    }

    public void canShoot(boolean shoot) {
        this.shoot = shoot;
    }

    /******************
     * User functions *
     ******************/

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
    }

//    public void reduceAmmo() {
//        ammo--;
//    }
//
//    public boolean hasAmmo() {
//        return ammo > 0;
//    }
}

