package com.euhedral.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Block extends GameObject {

    public Block(float x, float y) {
        super(x, y, ObjectID.Block);
    }

    public Block(float x, float y, BufferedImage image) {
        super(x,y, image, ObjectID.Block);
    }

    @Override
    public void initialize() {
        width = 32;
        height = width;
        color = Color.black;
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics g) {
            drawDefault(g);

    }

}
