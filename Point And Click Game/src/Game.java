import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class Game extends Canvas {

    public static int SCREEN_MULT = 2;

    public static int WIDTH = 640 * SCREEN_MULT;
    public static int HEIGHT = WIDTH * 3 / 4;
    public static String TITLE = "Mouse Shooter";
    public static int minX = Game.WIDTH * 1 / 10;
    public static int maxX = Game.WIDTH * 8 / 10;
    public static int minY = Game.HEIGHT * 2 /10;
    public static int maxY = Game.HEIGHT * 60 / 100;

    public static int buttonSize = WIDTH / 20;
    public static boolean gameExit = false;
    public static int MAX_SCORE = 10000;
    public static int decreaseFactor = 1000/6;
    public static int timer;
    public static int spawnTimer = 0;
    public static int killed = 0;
    public static int score = 0;


    UIController uiController;
    GameController gameController;
    Spawner spawner;

    enum STATE {
        Menu,
        Help,
        Pause,
        Game,
        Over
    }

    enum BUTTON_TYPE {
        Start,
        Help,
        Back,
        Pause,
        Quit
    }

    public static STATE gameState;

    public Game() {
        gameState = STATE.Menu;
        uiController = new UIController();
        gameController = new GameController();
        spawner = new Spawner(gameController);

        addMouseListener(new MouseInput(uiController, gameController));

        new Window(WIDTH, HEIGHT, TITLE, this);
    }

    public void run() {
        long lastTime = System.nanoTime();
        final int TARGET_FPS = 60;
        final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
        long lastFPStime = 0;
        int fps = 0;
        resetTimer();

        while (!gameExit) {
            long now = System.nanoTime();
            long updateLength = now - lastTime;
            lastTime = now;

            double delta = updateLength / ((double) OPTIMAL_TIME);
            lastFPStime += updateLength;
            fps++;

            if (lastFPStime >= 1000000000) // if a second has passed
            {
                if (gameState == STATE.Game) {
                    timer--;
                    MAX_SCORE -= decreaseFactor;
                }
                else if (gameState == STATE.Pause) {}
                else {
//                    timer = 0;
//                    spawnTimer = 0;
//                    killed = 0;
//                    MAX_SCORE = 10000;
                    spawner.reset();
                }
                System.out.println("FPS: " + fps);
                lastFPStime = 0;
                fps = 0;
            }

//            while (delta >= 1)

            update();
            render();
        }
    }

    public void update() {
        if (gameState == STATE.Game) {
            gameController.update();
            spawner.update();
        }
        if (MAX_SCORE <= 0)
            gameState = STATE.Over;
    }

    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        uiController.render(g);
        if (gameState == STATE.Game || gameState == STATE.Pause) {
            gameController.render(g);
        }


        g.dispose();
        bs.show();
//        System.out.println("Rendering...");
    }

    // Helpers

    private static void resetTimer() {
        timer = MAX_SCORE/decreaseFactor;
    }

    public static void endGame() {
        MAX_SCORE = 10000;
        resetTimer();
        spawnTimer = 0;
        killed = 0;
        System.out.println("Ending Game");
    }

    public static int randRange(int min, int max) {
        Random r = new Random();
        return r.nextInt(max) + min + 1;
    }

    public static void main(String[] args) {
        new Game();
    }
}
