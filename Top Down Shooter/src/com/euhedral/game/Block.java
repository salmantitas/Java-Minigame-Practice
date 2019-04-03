package com.euhedral.game;

import java.awt.*;

public class Block extends GameObject {
    public Block(float x, float y) {
        super(x, y, ObjectID.Block);

        width = 32;
        height = width;
        color = Color.red;

    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics g) {
        g.setColor(color);
        g.fillRect((int) x, (int) y, width, height);
    }
}
