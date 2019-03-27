package com.euhedral.game;

import java.awt.*;

public abstract class GameObject {

    protected Color color;
    // protected Texture tex;

    protected float x, y, width, height;
    protected ObjectID id;
    protected float velX = 0, velY = 0; // sets the initial velocities to 0, so the object is not moving unless stated otherwise.

    // this can be completely commented out if the game has no functional use of gravity
    protected float gravity = 1f, terminalVel;
    protected boolean gravityAffected = false, jumping = false; // every object is initialized to be not jumping or affected by gravity

    public GameObject(float x, float y, ObjectID id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public abstract void update();

    public abstract void render(Graphics g);

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

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public ObjectID getId() {
        return id;
    }

    public void setId(ObjectID id) {
        this.id = id;
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

    public boolean isGravityAffected() {
        return gravityAffected;
    }

    public void setGravityAffected(boolean gravityAffected) {
        this.gravityAffected = gravityAffected;
    }

    public boolean isJumping() {
        return jumping;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, (int) width, (int) height);
    }
}
