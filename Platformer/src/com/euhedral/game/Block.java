package com.euhedral.game;

import com.euhedral.engine.Engine;

import java.awt.*;

public class Block extends GameObject {

    private int type;

    public Block(float x, float y, int type, ObjectId id) {
        super(x, y, id);
        width = Engine.intAtWidth640(32);
        height = width;
        color = Color.WHITE;
        this.type = type;
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics g) {
        if (type == 0) // dirt block
            g.drawImage(tex.block[0], (int)x, (int)y, null);
        if (type == 1) // grass block
            g.drawImage(tex.block[1], (int)x, (int)y, null);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, (int) width, (int) height);
    }

}
