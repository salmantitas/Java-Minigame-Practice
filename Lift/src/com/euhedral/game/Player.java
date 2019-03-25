package com.euhedral.game;

import com.euhedral.engine.Engine;

import java.awt.*;

public class Player extends GameObject {

    private float riseVelocity;;

    public Player(float x, float y) {
        super(x, y, ObjectID.Player);

        // stub
        gravityAffected = true;
        color = Color.BLUE;
        width = Engine.intAtWidth640(32);
        height = width;

        gravity = Engine.floatAtWidth640(5);
        riseVelocity = gravity * 2;
    }

    @Override
    public void update() {
        y += velY;

        y = Engine.clamp((int) y, 0, Engine.HEIGHT - (int) (1.8 * height));
        if (gravityAffected) {
            if (jumping) {
                velY = gravity - riseVelocity;
            }
            else {
                velY = gravity;
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(color);
        g.fillRect((int) x, (int) y, (int) width, (int) height);
    }
}
