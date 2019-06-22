package com.euhedral.game;

import com.euhedral.engine.Engine;

import java.awt.*;

public class BulletPlayer extends Bullet{

    protected ID id;
    private double dir;

    BulletPlayer(int x, int y, ID id, double dir) {
        super(x, y);
        this.id = id;
        this.dir = dir;
        width = 5;
        height = width * 3;
        vel = 12;
    }

    public void update() {
        if (dir == 0.5) {
            x += vel/2;
            y -= vel;
        }

        else if (dir == -0.5) {
            x -= vel/2;
            y -= vel;
        }

        else
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
