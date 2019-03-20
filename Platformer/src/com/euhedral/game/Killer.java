package com.euhedral.game;

import java.awt.*;

public class Killer extends GameObject {


    public Killer(float x, float y, ObjectId id) {
        super(x, y, id);
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillRect((int) x, (int) y, 32, 32);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 32, 32);
    }
}
