package com.euhedral.engine;/*
* Do not modify
* */

import java.awt.*;
import java.awt.image.BufferStrategy;
import com.euhedral.game.GameController;

public class Engine extends Canvas implements Runnable{

    /*
    * By Default:
    * VERSION = 0.1
    * TITLE = "Euhedral com.euhedral.engine.Engine 0.14"
    * SCREEN_RATIO = 4.0/3.0
    * WIDTH = 640
    * HEIGHT = 480
    * BACKGROUND_COLOR = Color.BLACK
    */
    public static double VERSION = 0.144;
    public static String TITLE = "Euhedral com.euhedral.engine.Engine " + VERSION;
    public static double SCREEN_RATIO = 4.0/3.0;
    public static int WIDTH = 640;
    public static int HEIGHT = (int) (WIDTH / SCREEN_RATIO);
    public static Color BACKGROUND_COLOR = Color.BLACK;

    private double UPDATE_CAP = 1.0 / 60.0; //
    private boolean running;
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
        addMouseMotionListener(mouseInput);
        addMouseWheelListener(mouseInput);
        System.out.println("Game initialized");
        new Window(WIDTH, HEIGHT, TITLE, this);
    }

    public void start() {
        if (running) return;
        running = true;
        new Thread(this, "EngineMain-Thread").start();
    }

    private void stop() {
        if (!running) return;
        running = false;
    }

    @Override
    public void run() {
//        gameLoop();

        double target = 60.0;
        double nanosecondsPerCycle =  1000000000.0;
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        double unprocessedTime = 0.0;
        int fps = 0;
        int tps = 0;
        boolean render = false;
        while (running) {
            long now = System.nanoTime();
            unprocessedTime += (now - lastTime / nanosecondsPerCycle);
            lastTime = now;

            if (unprocessedTime >= 1) {
                update();
                unprocessedTime--;
                tps++;
                render = true;
            } else {
                render = false;
            }

            try {
//                Thread.sleep(1);
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (render) {
                render();
                fps++;
            }

            if (System.currentTimeMillis() - 1000 > timer) {
                timer += 1000;
                System.out.printf("FPS: %d | TPS: %d\n", fps, tps);
                fps = 0;
                tps = 0;
            }

        }

        System.exit(0);
    }

    public void update() {
        gameController.update();
    }

    /*
    * Pre-renders images using triple buffers and renders them on-screen;
    * */
    public void render() {
        BufferStrategy bs = getBufferStrategy(); // BufferStrategy loads the upcoming frames in memory (prerenders them)
        if (bs == null) {
            createBufferStrategy(3); // pre-renders three frames
            return;
        }

        Graphics g = bs.getDrawGraphics(); //

        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, WIDTH, WIDTH);

        gameController.render(g);

        g.dispose();
        bs.show(); //
    }

    /*
     * Game Loops created from youtube tutorials by Majoolwip: https://youtu.be/4iPEjFUZNsw
     * Updates the game every second
     * */
    public void gameLoop() {
        System.out.println("Game loop started");

        boolean render = false;
        double firstTime = 0;
        double lastTime = System.nanoTime() / 1000000000.0; // gets the current nanotime from the system. Divides it to derive the time in seconds
        double passedTime = 0;
        double unprocessedTime = 0; // keeps track of time which has been unprocessed, which can be caused by low fps

        final int TARGET_FPS = 60;
        final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
        long lastFPStime = 0;
        int fps = 0;

        while (running) {
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

            fps++;

            if (lastTime >= 1) // if a second has passed
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
     * com.euhedral.engine.GameState Functions *
     ***********************/

    public static void setState(GameState state) {
        currentState = state;
    }

    public static void gameState() {
        setState(GameState.Game);
    }

    public static void transitionState() {
        setState(GameState.Transition);
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
