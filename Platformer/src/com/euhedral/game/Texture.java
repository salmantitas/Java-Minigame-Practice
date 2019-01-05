package com.euhedral.game;

import com.euhedral.engine.BufferedImageLoader;

import java.awt.image.BufferedImage;

public class Texture {

    public BufferedImage[] block = new BufferedImage[2];
    public BufferedImage[] player = new BufferedImage[1];

    SpriteSheet bs, ps;
    private BufferedImage block_sheet = null;
    private BufferedImage player_sheet = null;

    public Texture() {
        BufferedImageLoader loader = new BufferedImageLoader();
        try {
            block_sheet = loader.loadImage("/block_sheet.png");
            player_sheet = loader.loadImage("/player_sheet.png");
        } catch (Exception e) {
            e.printStackTrace();
        }

        bs = new SpriteSheet(block_sheet);
        ps = new SpriteSheet(player_sheet);

        getTextures();
    }

    private void getTextures() {
        block[0] = bs.grabImage(2,1,32,32); // rock block
        block[1] = bs.grabImage(1,1,32,32); // grass block

        player[0] = ps.grabImage(1,1,32,64); // idle player frame
    }
}
