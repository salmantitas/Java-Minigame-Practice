/*
* Do not modify
* */

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Engine extends Canvas {

    /*
    * By Default:
    * VERSION = 0.1
    * TITLE = "Euhedral Engine 0.14"
    * SCREEN_RATIO = 4.0/3.0
    * WIDTH = 640
    * HEIGHT = 480
    * BACKGROUND_COLOR = Color.BLACK
    */
    public static double VERSION = 0.15;
    public static String TITLE = "Euhedral Engine " + VERSION;
    public static double SCREEN_RATIO = 4.0/3.0;
    public static int WIDTH = 640;
    public static int HEIGHT = (int) (WIDTH / SCREEN_RATIO);
    public static Color BACKGROUND_COLOR = Color.BLACK;

    private boolean gameExit = false;
    public static int timeInSeconds = 0;
    public static int timer = 0;

    public GameController gameController;

    public static GameState currentState = GameState.Game;

    public EngineKeyboard keyInput;
    public EngineMouse mouseInput;

    public Engine() {
        gameController = new GameController();

        keyInput = new EngineKeyboard(gameController);
        mouseInput = new EngineMouse(gameController);

        addKeyListener(keyInput);
        addMouseListener(mouseInput);
        System.out.println("Game initialized");
        new Window(WIDTH, HEIGHT, TITLE, this);
    }

    public void update() {
        gameController.update();
    }

    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, WIDTH, WIDTH);

        gameController.render(g);

        g.dispose();
        bs.show();
    }

    public void gameLoop() {
        System.out.println("Game loop started");
        long lastTime = System.nanoTime();
        final int TARGET_FPS = 60;
        final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
        long lastFPStime = 0;
        int fps = 0;

        while (!gameExit) {
            long now = System.nanoTime();
            long updateLength = now - lastTime;
            lastTime = now;
            double delta = updateLength / ((double) OPTIMAL_TIME);
            lastFPStime += updateLength;

//            while (delta >= 1) {
                update();
//                System.out.println("Updating");
//                delta--;
//            }
            if (!gameExit) {
                render();
//                System.out.println("Rendering");
            }

            fps++;

            if (lastFPStime >= 1000000000) // if a second has passed
            {
                timeInSeconds++;
                System.out.println("FPS: " + fps);
                lastFPStime = 0;
                fps = 0;
            }
        }
        System.out.println("Game loop exited");
    }

    public static void main(String[] args) {
        System.out.println("Euhedral Engine " + VERSION + " Started");
        new Engine();
    }

    /***************************************
     * Functions To Adjust Game Properties *
     ***************************************/

    public static void setWIDTH(int width) {
        WIDTH = width;
        updateHeight();
    }

    public static void setHEIGHT(int height) {
        HEIGHT = height;
        updateWidth();
    }

    // HEIGHT = WIDTH / SCREEN_RATIO, that is WIDTH * numerator / denominator
    public void setSCREEN_RATIO(double denominator, double numerator) {
        SCREEN_RATIO = (1.0 * denominator) / (1.0 * numerator);
        updateHeight();
    }

    private static void updateWidth() {
        WIDTH = (int) (HEIGHT * SCREEN_RATIO);
    }

    private static void updateHeight() {
        HEIGHT = (int) (WIDTH / SCREEN_RATIO);
    }

    public static void setTITLE(String title) {
        TITLE = title;
    }

    public static void setBACKGROUND_COLOR(Color color) {
        BACKGROUND_COLOR = color;
    }

    public static void setBACKGROUND_COLOR(int red, int green, int blue) {
        BACKGROUND_COLOR = new Color(red, green, blue);
    }

    public static int perc(int parameter, double percentage) {
        return  (int) (parameter * percentage/ 100.0);
    }

    public static int percWidth(double percentage) {
        return perc(WIDTH, percentage);
    }

    public static int percHeight(double percentage) {
        return perc(HEIGHT, percentage);
    }

    public static int intAtWidth640(int var) {
        float factor = 640/var;
        return (int) (WIDTH/factor);
    }

    public static float floatAtWidth640(int var) {
        float factor = 640/var;
        return (float) (WIDTH/factor);
    }

    /***********************
     * GameState Functions *
     ***********************/

    public static void setState(GameState state) {
        currentState = state;
    }

    public static void gameState() {
        setState(GameState.Game);
    }

    public static void menuState() {
        setState(GameState.Menu);
    }

    public static void gameOverState() {
        setState(GameState.GameOver);
    }

    public static void pauseState() {
        setState(GameState.Pause);}

    /*********************
     * Utility Functions *
     *********************/

    public static int clamp(int var, int min, int max) {
        if (var <= min)
            return min;
        if (var >= max)
            return max;
        else return var;
    }

    public static void printTimer() {
        System.out.println(timer);
    }
}
