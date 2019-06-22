package com.euhedral.game;

import java.awt.*;

public abstract class Entity {

    protected int x, y, width, height;
    protected float velX = 0, velY = 0;
    protected boolean falling = true, jumping = false, friction = false;
    protected Color color;

    public Entity(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public abstract void update();

    public abstract void render(Graphics g);

    public abstract Rectangle getBounds();

    // Getters & Setters

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public float getVelX() {
        return velX;
    }

    public void setVelX(float velX) {
        this.velX = velX;
    }

    public float getVelY() {
        return velY;
    }

    public void setVelY(float velY) {
        this.velY = velY;
    }

    public boolean isFalling() {
        return falling;
    }

    public boolean isJumping() {
        return jumping;
    }

    public void setFalling(boolean falling) {
        this.falling = falling;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
