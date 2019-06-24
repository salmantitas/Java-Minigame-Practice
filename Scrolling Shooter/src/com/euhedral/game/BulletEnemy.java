package com.euhedral.game;

import java.awt.*;

public class BulletEnemy extends Bullet{

    BulletEnemy(int x, int y) {
        super(x, y);
    }

    BulletEnemy(int x, int y, double angle) {
        super(x, y, angle);
    }

//    public void update() {
//
//    }

    public void render(Graphics g) {
        g.setColor(Color.orange);
        g.fillOval(x,y, width, height);
    }
}
