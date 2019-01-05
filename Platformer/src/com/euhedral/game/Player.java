package com.euhedral.game;

import com.euhedral.engine.Engine;

import java.awt.*;

public class Player extends GameObject {

    private float gravity = 0.01f;
    private final float MAX_SPEED = 10;
    private final float speed = Engine.intAtWidth640(1);
    private final float jumpSpeed = speed * 1.7f;

    public Player(float x, float y, ObjectId id) {
        super(x, y, id);
        width = Engine.intAtWidth640(32);
        height = width * 2;
        color = Color.BLUE;
    }

    @Override
    public void update() {
        x += velX;
        y += velY;

        if (jumping || falling) {
            velY += gravity;

            if (velY > MAX_SPEED)
                velY = MAX_SPEED;
        }

    }

    @Override
    public void render(Graphics g) {
        g.setColor(color);
        g.drawImage(tex.player[0], (int) x, (int) y, null);

//        renderBounds(g);
    }

    public void renderBounds(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g.setColor(Color.red);
        g2d.draw(getBounds());
        g2d.draw(getBoundsRight());
        g2d.draw(getBoundsLeft());
        g2d.draw(getBoundsTop());
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) ( x + width/2 - (width/4)), (int) (y + height/2), (int) width/2, (int) height /2);
    }

    public Rectangle getBoundsTop() {
        return new Rectangle((int) (x + width/2 - (width/4)), (int) y, (int) width / 2, (int) height /2);
    }

    public Rectangle getBoundsRight() {
        return new Rectangle((int) (x + width - 5), (int) y + 5, (int) 5, (int) height - 10);
    }

    public Rectangle getBoundsLeft() {
        return new Rectangle((int) x, (int) y + 5, (int) 5, (int) height - 10);
    }

    public float getHeight() {
        return height;
    }

    public float getSpeed() {
        return speed;
    }

    public float getJumpSpeed() {
        return jumpSpeed;
    }
}
