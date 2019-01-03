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

        for (int j = 0; j < h; j++) {
            for (int i = 0; i < w; i++) {
                int pixel = image.getRGB(i,j);
                int r = (pixel >> 16) & 0xff;
                int g = (pixel >> 8) & 0xff;
                int b = pixel & 0xff;

                // Game Code//


            }
        }


}
