package com.euhedral.game;

import java.awt.*;

public abstract class GameObject {

    protected float x, y, width, height;
    protected ObjectId id;
    protected float velX = 0, velY = 0;
    protected boolean falling = true, jumping = false;
    protected Color color;

    public GameObject(float x, float y, ObjectId id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public abstract void update();

    public abstract void render(Graphics g);

    public abstract Rectangle getBounds();

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public ObjectId getId() {
        return id;
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
