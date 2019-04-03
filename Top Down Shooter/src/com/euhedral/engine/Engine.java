package com.euhedral.engine;/*
 * Do not modify
 * */

import com.euhedral.game.GameController;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Engine extends Canvas {

    /*
     * By Default:
     * VERSION = 0.1
     * TITLE = "Euhedral com.euhedral.engine.Engine 0.151"
     * SCREEN_RATIO = 4.0/3.0
     * WIDTH = 640
     * HEIGHT = 480
     * BACKGROUND_COLOR = Color.BLACK
     */
    public static double VERSION = 0.163;
    public static String TITLE = "Top Down Shooter";
    public static double SCREEN_RATIO = 4.0/3.0;
    public static int WIDTH = 640;
    public static int HEIGHT = (int) (WIDTH / SCREEN_RATIO);
    public static Color BACKGROUND_COLOR = Color.BLACK;

    private boolean gameExit = false;
    private final double UPDATE_CAP = 1.0/60.0; // determines the frequency of game-updates. 1/60 means once every 60 seconds
    public static int timeInSeconds = 0;
    public static int timer = 0;

    public GameController gameController;

    public static GameState currentState = GameState.Game;

    public EngineKeyboard keyInput;
    public EngineMouse mouseInput;

    /*
     * Initializes variables:
     *   gameController
     *   keyInput
     *   mouseInput
     * Adds the input methods to the appropriate listeners
     * Creates the window
     */
    public Engine() {
        gameController = new GameController();

        keyInput = new EngineKeyboard(gameController);
        mouseInput = new EngineMouse(gameController);

        addKeyListener(keyInput);
        addMouseListener(mouseInput);
        addMouseMotionListener(mouseInput);
        System.out.println("Game initialized");
        new Window(WIDTH, HEIGHT, TITLE, this);
    }

    /*
     * Called every frame to update the game behavior.
     */
    public void update() {
        gameController.update();
    }

    /*
     * Called every frame to render graphics on the window.
     * Gets BufferStrategy and initializes it to 3 if it is null
     * Fills the screen with the background color and calls the gameController's render function
     * */
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

    /*
     * Game Loop created from youtube tutorials by Majoolwip: https://youtu.be/4iPEjFUZNsw
     * Updates the game every second
     * */
    public void gameLoop() {
        System.out.println("Game loop started");

        boolean render = false;
        double firstTime = 0;
        double lastTime = System.nanoTime() / 1000000000.0; // gets the current nanotime from the system. Divides it to derive the time in seconds
        double passedTime = 0;
        double unprocessedTime = 0; // keeps track of time which has been unprocessed, which can be caused by low fps

//        long lastTime = System.nanoTime();
        final int TARGET_FPS = 60;
        final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
        long lastFPStime = 0;
        int fps = 0;

        while (!gameExit) {
            firstTime = System.nanoTime() / 1000000000.0; // updates the firstTime to current time
            passedTime = firstTime - lastTime; // calculates the time elapsed between the two variables
            lastTime = firstTime; // updates the lastTime to the latest calculated current time
            unprocessedTime += passedTime; // idk

            // checks if enough time has been left without updating. Ensures that if updates couldn't be made, it will be
            while (unprocessedTime >= UPDATE_CAP) {
                unprocessedTime -= UPDATE_CAP;
                render = true;
                update();
            }

            if (render) {
                render();
            } else {
                //thread.sleep(1); // Puts the thread to sleep for a milisecond to free processor
            }

//            long now = System.nanoTime();
//            long updateLength = now - lastTime;
//            lastTime = now;
//            double delta = updateLength / ((double) OPTIMAL_TIME);
//            lastFPStime += updateLength;
//
////            while (delta >= 1) {
//            update();
////                System.out.println("Updating");
////                delta--;
////            }
//            if (!gameExit) {
//                render();
////                System.out.println("Rendering");
//            }

            fps++;

            if (lastFPStime >= 1) // if a second has passed
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
        System.out.println("Euhedral com.euhedral.engine.Engine " + VERSION + " Started");
        new Engine();
    }

    /***********************************************
     * Utility functions To Adjust Game Properties *
     ***********************************************/

    /*
    * Manually sets the width to the given value and updates the height according to the
    * aspect ratio
    * */
    public static void setWIDTH(int width) {
        WIDTH = width;
        updateHeight();
    }

    /*
     * Manually sets the height to the given value and updates the width according to the
     * aspect ratio
     * */
    public static void setHEIGHT(int height) {
        HEIGHT = height;
        updateWidth();
    }

    /*
    * Manually updates the aspect ratio of the game
    * */
    // HEIGHT = WIDTH / SCREEN_RATIO, that is WIDTH * numerator / denominator
    public void setSCREEN_RATIO(double denominator, double numerator) {
        SCREEN_RATIO = (1.0 * denominator) / (1.0 * numerator);
        updateHeight();
    }

    /*
    * Updates the width using the aspect ratio and the height
    * */
    private static void updateWidth() {
        WIDTH = (int) (HEIGHT * SCREEN_RATIO);
    }

    /*
     * Updates the height using the aspect ratio and the height
     * */
    private static void updateHeight() {
        HEIGHT = (int) (WIDTH / SCREEN_RATIO);
    }

    /*
     * Sets the title of the window
     * */
    public static void setTITLE(String title) {
        TITLE = title;
    }

    /*
     * Updates the background color of the window to the given color
     * */
    public static void setBACKGROUND_COLOR(Color color) {
        BACKGROUND_COLOR = color;
    }

    /*
     * Updates the background color of the window using the rgb values
     * */
    public static void setBACKGROUND_COLOR(int red, int green, int blue) {
        BACKGROUND_COLOR = new Color(red, green, blue);
    }

    /*
    * Returns the given percentage of a given parameter
    * */
    public static int perc(int parameter, double percentage) {
        return  (int) (parameter * percentage/ 100.0);
    }

    /*
    * Returns the given percentage of the screen width
    * */
    public static int percWidth(double percentage) {
        return perc(WIDTH, percentage);
    }

    /*
    * Returns the given percentage of the screen height
    * */
    public static int percHeight(double percentage) {
        return perc(HEIGHT, percentage);
    }

    /*
    *
    * */
    public static int intAtWidth640(int var) {
        float factor = 640/var;
        return (int) (WIDTH/factor);
    }

    /*
    *
    * */
    public static float floatAtWidth640(int var) {
        float factor = 640/var;
        return (float) (WIDTH/factor);
    }

    /***********************
     * com.euhedral.engine.GameState Functions *
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

    /*
    * Limits the given variable between min and max
    * */
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

    // Does not work because Integers aren't objects
//    public static void swapInt(int x, int y) {
//        int temp = x;
//        x = y;
//        y = temp;
//    }
}
