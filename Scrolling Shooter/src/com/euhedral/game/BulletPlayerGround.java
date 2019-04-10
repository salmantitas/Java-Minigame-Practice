package com.euhedral.game;

import java.awt.*;

public class BulletPlayerGround extends BulletPlayer{

    BulletPlayerGround(int x, int y) {
        super(x, y, ID.Ground);
        color = Color.GREEN;
    }
}
