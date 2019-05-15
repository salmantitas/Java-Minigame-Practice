package com.euhedral.game;

import java.awt.*;

public class Bullet extends GameObject {

    private int velocityFactor = 10;

    public Bullet(float x, float y, int mx, int my) {
        super(x, y, ObjectID.Bullet);
        width = 8;
        height = width;
        color = Color.orange;

        velX = (mx - x) / velocityFactor;
        velY = (my - y) / velocityFactor;
    }

    @Override
    public void update() {
        x += velX;
        y += velY;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(color);
        g.fillOval((int) x, (int) y, width, height);
    }
}
