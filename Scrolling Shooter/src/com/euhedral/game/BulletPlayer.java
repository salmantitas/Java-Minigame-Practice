package com.euhedral.game;

import com.euhedral.engine.Engine;

import java.awt.*;

public class BulletPlayer extends Bullet{

    protected ID id;

    BulletPlayer(int x, int y, ID id) {
        super(x, y);
        this.id = id;
        width = Engine.intAtWidth640(8)/3;
        height = Engine.intAtWidth640(24)/3;
        vel = Engine.intAtWidth640(5);
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
