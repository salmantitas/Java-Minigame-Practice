package com.euhedral.game;

import java.awt.image.BufferedImage;
import java.util.Random;

public class LevelGenerator {

    private GameController gameController;
    private Random random = new Random();

    public LevelGenerator(GameController gameController) {
        this.gameController = gameController;
    }

    public void loadImageLevel(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();

        System.out.println("Width, Height: " + w + " " + h);

        for (int j = h - 1; j >= 0; j--) {
            for (int i = w - 1; i >= 0; i--) {
                int pixel = image.getRGB(i, j);
                int r = (pixel >> 16) & 0xff;
                int g = (pixel >> 8) & 0xff;
                int b = pixel & 0xff;

                /*************
                 * Game Code *
                 *************/

                int choose = random.nextInt(500);

                int posX = i * 32, posY = j * 32;

                // Player
                if (r == 0 && g == 0 && b == 255)
                    gameController.spawnPlayer(posX, posY);
                // Block
                else if (r == 255 && g == 0 && b == 0)
                    gameController.spawn(posX, posY, ObjectID.Block);
//                    gameController.addObject(new Block(i* 32, j * 32 ));
                // Enemy
                else if (r == 76 && g == 255 && b == 0)
                    gameController.spawn(posX, posY, ObjectID.Enemy);
                else if (choose == 0) {
                    gameController.spawn(posX, posX, ObjectID.Crate);
                }
            }
        }


    }


}
