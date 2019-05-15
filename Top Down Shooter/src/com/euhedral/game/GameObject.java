package com.euhedral.game;

import com.euhedral.engine.Animation;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GameObject {
    protected float x, y;
    protected int width, height;
    protected ObjectID id;
    protected float velX = 0, velY = 0; // sets the initial velocities to 0, so the object is not moving unless stated otherwise.

    // this can be completely commented out if the game has no functional use of gravity
    protected float gravity = 1f, terminalVel;
    protected boolean gravityAffected = false, jumping = false; // every object is initialized to be not jumping or affected by gravity

    protected Color color;
    protected BufferedImage image;
    protected BufferedImage images[];

    protected Animation anim;
    protected int animationSpeed = 3;
    // protected Texture tex;

    public GameObject(float x, float y, ObjectID id) {
        this.x = x;
        this.y = y;
        this.id = id;

        initialize();
    }

    public GameObject(float x, float y, BufferedImage image, ObjectID id) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.image = image;

        initialize();
    }

    public GameObject(float x, float y, BufferedImage[] images, ObjectID id) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.images = images;

        initialize();
    }

    protected void initialize() {}

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
        return new Rectangle((int) (x + 0.2*width), (int) y,  (int) (0.6* width),  height/4);
    }

    public Rectangle getBoundsBottom() {
        return new Rectangle((int) (x + 0.2*width), (int) y + 3*height/4,  (int) (0.6* width),  height/4);
    }

    public Rectangle getBoundsLeft() {
        return new Rectangle((int) x, (int) (y + 0.35*height),  width/4,  (int) (height * 0.3));
    }

    public Rectangle getBoundsRight() {
        return new Rectangle((int) x + 3*width/4, (int) (y + 0.35*height),  width/4,  (int) (height * 0.3));
    }

    protected void setColor(Graphics g) {
        g.setColor(color);
    }

    protected void drawRect(Graphics g) {
        g.fillRect((int) x, (int) y, width, height);
    }

    protected void drawDefault(Graphics g) {
        if (images != null) {
            drawAnimation(g);
        }
        else if (image != null) {
            g.drawImage(image, (int) x, (int) y, null);
        }
        else {
            setColor(g);
            drawRect(g);
        }
    }

    protected void drawAnimation(Graphics g) {

    }

    protected void renderBounds(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        Color boundColor = Color.green;

//        g.setColor(Color.white);
//        g2d.draw(getBounds());

        g.setColor(boundColor);
        g2d.draw(getBoundsTop());
        g2d.draw(getBoundsBottom());
        g2d.draw(getBoundsLeft());
        g2d.draw(getBoundsRight());
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    protected void drawImage(Graphics g, BufferedImage image) {
        g.drawImage(image, (int) x, (int) y, null);
    }
}
