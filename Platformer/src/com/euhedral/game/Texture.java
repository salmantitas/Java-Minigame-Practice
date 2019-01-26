package com.euhedral.game;

import com.euhedral.engine.BufferedImageLoader;

import java.awt.image.BufferedImage;

public class Texture {

    public BufferedImage[] block = new BufferedImage[2];
    public BufferedImage[] player = new BufferedImage[14];
    public BufferedImage[] player_jump = new BufferedImage[18];

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

        // right-facing

        // idle
        player[0] = ps.grabImage(1,1,32,64); // idle player frame

        // walking
        player[1] = ps.grabImage(2,1,32,64); // walking player frame
        player[2] = ps.grabImage(3,1,32,64); // walking player frame
        player[3] = ps.grabImage(4,1,32,64); // walking player frame
        player[4] = ps.grabImage(5,1,32,64); // walking player frame
        player[5] = ps.grabImage(6,1,32,64); // walking player frame
        player[6] = ps.grabImage(7,1,32,64); // walking player frame

        // jumping
        player_jump[0] = ps.grabImage(1, 2, 32, 64);
        player_jump[1] = ps.grabImage(2, 2, 32, 64);
        player_jump[2] = ps.grabImage(3, 2, 32, 64);
        player_jump[3] = ps.grabImage(4, 2, 32, 64);
        player_jump[4] = ps.grabImage(5, 2, 32, 64);
        player_jump[5] = ps.grabImage(6, 2, 32, 64);
        player_jump[6] = ps.grabImage(7, 2, 32, 64);
        player_jump[7] = ps.grabImage(8, 2, 32, 64);
        player_jump[8] = ps.grabImage(9, 2, 32, 64) ;

        // left-facing
        //idle
        player[7] = ps.grabImage(8,1,32,64); // idle player frame

        // walking
        player[8] = ps.grabImage(9,1,32,64); // walking player frame
        player[9] = ps.grabImage(10,1,32,64); // walking player frame
        player[10] = ps.grabImage(11,1,32,64); // walking player frame
        player[11] = ps.grabImage(12,1,32,64); // walking player frame
        player[12] = ps.grabImage(13,1,32,64); // walking player frame
        player[13] = ps.grabImage(14,1,32,64); // walking player frame

        // jumping
        player_jump[9] = ps.grabImage(9, 3, 32, 64);
        player_jump[10] = ps.grabImage(8, 3, 32, 64);
        player_jump[11] = ps.grabImage(7, 3, 32, 64);
        player_jump[12] = ps.grabImage(6, 3, 32, 64);
        player_jump[13] = ps.grabImage(5, 3, 32, 64);
        player_jump[14] = ps.grabImage(4, 3, 32, 64);
        player_jump[15] = ps.grabImage(3, 3, 32, 64);
        player_jump[16] = ps.grabImage(2, 3, 32, 64);
        player_jump[17] = ps.grabImage(1, 3, 32, 64) ;
    }
}
