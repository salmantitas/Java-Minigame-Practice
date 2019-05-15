package com.euhedral.game;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class BulletEnemy extends Bullet{

    private double dir;

    BulletEnemy(int x, int y, double dir) {
        super(x, y);
        this.dir = dir;
    }

    public void update() {
        if (dir == 0.5) {
            x += vel/2;
            y += vel;
        }

        else if (dir == -0.5) {
            x -= vel/2;
            y += vel;
        }

        else
            y += vel;
    };

    public void render(Graphics g) {
        g.setColor(Color.orange);
        g.fillOval(x,y, width, height);
    }
}
