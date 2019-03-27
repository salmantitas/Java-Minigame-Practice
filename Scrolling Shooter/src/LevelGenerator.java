import java.awt.image.BufferedImage;

public class LevelGenerator {

    private GameController gameController;

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

                // Game Code//
                if (r == 0 && g == 0 && b == 255)
                    gameController.spawnPlayer(i*32, j*32, h*32);
                if (r == 255 && g == 0 && b == 0)
                    gameController.spawnEnemy(i*32, j*32, ID.Air);
                if (r == 255 && g == 150 && b == 244)
                    gameController.spawnEnemy(i*32, j*32, ID.Ground);
                if (r == 255 && g == 216 && b == 0)
                    gameController.spawnBoss(i*32, j*32);
            }
        }

        gameController.spawnFlag();
        gameController.setLevelHeight(h*32);
    }


}
