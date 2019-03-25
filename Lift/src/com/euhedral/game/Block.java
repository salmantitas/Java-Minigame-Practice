package com.euhedral.game;

import com.euhedral.engine.Engine;

import java.awt.*;

public class Block extends GameObject {
    public Block(float x, float y) {
        super(x, y, ObjectID.Block);
        width = Engine.intAtWidth640(32);
        height = width;
        color = Color.WHITE;
        velX = 1;
    }

    @Override
    public void update() {
        x -= velX;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(color);
        g.fillRect((int) x, (int) y, (int) width, (int) height);
    }
}
