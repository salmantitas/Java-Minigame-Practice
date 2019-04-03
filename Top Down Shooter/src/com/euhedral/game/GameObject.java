package com.euhedral.game;

import com.euhedral.engine.Engine;

import java.awt.*;

public abstract class GameObject {

    protected Color color;
    // protected Texture tex;

    protected float x, y;
    protected int width, height;
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

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
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
        return new Rectangle((int) x, (int) y,  width,  height);
    }

    public Rectangle getBoundsTop() {
        return new Rectangle((int) (x + 0.2*width), (int) y,  (int) (0.6* width),  height/2);
    }

    public Rectangle getBoundsBottom() {
        return new Rectangle((int) (x + 0.2*width), (int) y + height/2,  (int) (0.6* width),  height/2);
    }

    public Rectangle getBoundsLeft() {
        return new Rectangle((int) x, (int) (y + 0.2*height),  width/2,  (int) (height * 0.6));
    }

    public Rectangle getBoundsRight() {
        return new Rectangle((int) x + width/2, (int) (y + 0.2*height),  width/2,  (int) (height * 0.6));
    }

    protected void setColor(Graphics g) {
        g.setColor(color);
    }

    protected void drawRect(Graphics g) {
        g.fillRect((int) x, (int) y, width, height);
    }

    protected void drawDefault(Graphics g) {
        setColor(g);
        drawRect(g);
    }

    protected void renderBounds(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        Color boundColor = Color.green;
        g.setColor(boundColor);
//        g2d.draw(getBounds());
        g2d.draw(getBoundsTop());
        g2d.draw(getBoundsBottom());
        g2d.draw(getBoundsRight());
        g2d.draw(getBoundsLeft());
    }

}
