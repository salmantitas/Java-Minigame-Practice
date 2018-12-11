package com.euhedral.game;

import com.euhedral.engine.Engine;

import java.awt.*;

public class Block extends GameObject {

    public Block(float x, float y, ObjectId id) {
        super(x, y, id);
        width = Engine.intAtWidth640(32);
        height = width;
        color = Color.WHITE;
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics g) {
        g.setColor(color);
        g.drawRect((int) x, (int) y, (int) width, (int) height);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, (int) width, (int) height);
    }

}
