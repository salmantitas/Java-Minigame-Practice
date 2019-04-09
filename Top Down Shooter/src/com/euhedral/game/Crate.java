package com.euhedral.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Crate extends GameObject {
    public Crate(float x, float y) {
        super(x, y, ObjectID.Crate);
    }

    public Crate(float x, float y, BufferedImage image) {
        super(x, y, image, ObjectID.Crate);
    }

    @Override
    public void initialize() {
        color = Color.cyan;
        width = 32;
        height = width;
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics g) {
        drawDefault(g);
    }
}
