package com.euhedral.game;

import com.euhedral.engine.Engine;

import java.awt.*;

public class Player extends GameObject {

    private float gravity = 0.01f;
    private final float MAX_SPEED = 10;
    private final float speed = Engine.intAtWidth640(1);
    private final float jumpSpeed = speed * 1.7f;
    private boolean left = false;

    private Animation playerWalkRight;
    private Animation playerWalkLeft;
    private Animation playerJumpRight;
    private Animation playerJumpLeft;

    public Player(float x, float y, ObjectId id) {
        super(x, y, id);
        width = Engine.intAtWidth640(32);
        height = width * 2;
        color = Color.BLUE;
        playerWalkRight = new Animation(30, tex.player[1], tex.player[2], tex.player[3], tex.player[4], tex.player[5], tex.player[6]);
        playerWalkLeft = new Animation(30, tex.player[7], tex.player[8], tex.player[9], tex.player[10], tex.player[11], tex.player[12]);
        playerJumpRight = new Animation(30, tex.player_jump[0], tex.player_jump[1], tex.player_jump[2], tex.player_jump[3], tex.player_jump[7], tex.player_jump[8]);
        playerJumpLeft = new Animation(30, tex.player_jump[9], tex.player_jump[10],
                tex.player_jump[14], tex.player_jump[15], tex.player_jump[16], tex.player_jump[17]);
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

        playerWalkRight.runAnimation();
        playerWalkLeft.runAnimation();
        playerJumpRight.runAnimation();
        playerJumpLeft.runAnimation();
    }

    @Override
    public void render(Graphics g) {
        g.setColor(color);

        if (jumping) {
            if (left)
                playerJumpLeft.drawAnimation(g, (int) x, (int) y);
            else
                playerJumpRight.drawAnimation(g, (int) x, (int) y);
        } else {
            if (velX != 0) {
                if (velX > 0)
                    playerWalkRight.drawAnimation(g,(int) x, (int) y);
                if (velX < 0)
                    playerWalkLeft.drawAnimation(g, (int) x, (int) y);
            }
            else {
                if (left)
                    g.drawImage(tex.player[13], (int) x, (int) y, null);
                else
                    g.drawImage(tex.player[0], (int) x, (int) y, null);
            }
        }


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

    public void setLeft(boolean left) {
        this.left = left;
    }
}
