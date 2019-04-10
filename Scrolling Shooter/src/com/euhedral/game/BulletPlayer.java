package com.euhedral.game;

import java.awt.*;

public class BulletPlayer extends Bullet{

    protected ID id;

    BulletPlayer(int x, int y, ID id) {
        super(x, y);
        this.id = id;
    }

    public void update() {
        y -= vel;
    };

    public void render(Graphics g) {
        g.setColor(color);
        g.fillOval(x,y, width, height);
    }

    public ID getId() {
        return id;
    }
}
