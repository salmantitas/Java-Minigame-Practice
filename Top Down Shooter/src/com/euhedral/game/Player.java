package com.euhedral.game;

import com.euhedral.engine.Engine;

import java.awt.*;

public class Player extends GameObject {

//    private float riseVelocity;
    private boolean up = false, down = false, left = false, right = false, shoot = false;

    public Player(float x, float y) {
        super(x, y, ObjectID.Player);
        initialize();
    }

    private void initialize() {
        /*************
         * Game Code *
         *************/

        color = Color.blue;
        width = 32;
        height = 48;

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
    }

    @Override
    public void render(Graphics g) {
//        g.setColor(color);
//        g.fillRect((int) x, (int) y, width, height);
        drawDefault(g);
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

    public void collision() {
        x -= velX;
        y -= velY;
//        velX = 0; velY = 0;
    }

}
