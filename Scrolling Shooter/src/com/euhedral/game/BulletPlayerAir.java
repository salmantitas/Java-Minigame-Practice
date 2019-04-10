package com.euhedral.game;

import java.awt.*;

public class BulletPlayerAir extends BulletPlayer{

    BulletPlayerAir(int x, int y) {
        super(x, y, ID.Air);
        color = Color.YELLOW;
    }
}
