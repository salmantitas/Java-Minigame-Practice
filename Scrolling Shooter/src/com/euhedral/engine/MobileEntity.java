package com.euhedral.engine;

import java.awt.*;

public class MobileEntity extends Entity {

    protected float acceleration, frictionalForce;
    protected int horizontalMovement, verticalMovement;
    protected int minHorizontalMovement, minVerticalMovement;
    protected int maxHorizontalMovement, maxVerticalMovement;
    protected boolean moveLeft, moveRight, moveUp, moveDown;

    public MobileEntity(int x, int y) {
        super(x, y);
    }

    @Override
    protected void initialize() {

    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics g) {

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    protected void move() {
        x += velX;
        y += velY;
    }
}
