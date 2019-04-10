package com.euhedral.game;

import com.euhedral.engine.Engine;

import java.awt.*;

public class EnemyAirBasic extends EnemyAir {

    public EnemyAirBasic(int x, int y) {
        super(x,y);
        width = Engine.intAtWidth640(32);
        height = width;
        color = Color.red;
        health = 1;
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        g.setColor(color);
        g.fillRect(x,y,width,height);
    }

    public void setX(int x) {
        this.x = x;
    }

    // Private Methods

    public void move() {
        y += velY;
    }
}
