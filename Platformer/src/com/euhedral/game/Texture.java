package com.euhedral.game;

import com.euhedral.engine.BufferedImageLoader;

import java.awt.image.BufferedImage;

public class Texture {

    public BufferedImage[] block = new BufferedImage[2];
    public BufferedImage[] player = new BufferedImage[14];

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
        player[1] = ps.grabImage(2,1,32,64); // walking player right frame
        player[2] = ps.grabImage(3,1,32,64); // walking player right frame
        player[3] = ps.grabImage(4,1,32,64); // walking player right frame
        player[4] = ps.grabImage(5,1,32,64); // walking player right frame
        player[5] = ps.grabImage(6,1,32,64); // walking player right frame
        player[6] = ps.grabImage(7,1,32,64); // walking player right frame

        player[7] = ps.grabImage(8,1,32,64); // idle player frame
        player[8] = ps.grabImage(9,1,32,64); // walking player right frame
        player[9] = ps.grabImage(10,1,32,64); // walking player right frame
        player[10] = ps.grabImage(11,1,32,64); // walking player right frame
        player[11] = ps.grabImage(12,1,32,64); // walking player right frame
        player[12] = ps.grabImage(13,1,32,64); // walking player right frame
        player[13] = ps.grabImage(14,1,32,64); // walking player right frame
    }
}
