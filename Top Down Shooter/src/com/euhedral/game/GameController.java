package com.euhedral.game;

import com.euhedral.engine.BufferedImageLoader;
import com.euhedral.engine.Engine;
import com.euhedral.engine.GameState;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Random;

public class GameController {
    private UIHandler uiHandler;
    private Random r = new Random();

    // Manually set the com.euhedral.engine.Window information here
    private int gameWidth = Engine.WIDTH;
    private int gameHeight = Engine.HEIGHT;
    private String gameTitle = Engine.TITLE;
    private Color gameBackground = Engine.BACKGROUND_COLOR;

    // Common game variables -- Comment out whichever is unnecessary
    private int score = 0;
    private int scoreX = Engine.percWidth(5);
    private int scoreY = Engine.percHeight(15);
    private int scoreSize = Engine.percWidth(4);
    private int lives = 3;
    private final int healthDef = 100;
    private int health = healthDef;
    private LinkedList<Integer> highScore = new LinkedList<>();
    private int highScoreNumbers = 5;
    private boolean updateHighScore = false;

    private Player player;
    private LinkedList<GameObject> gameObjects = new LinkedList<>();
    private BufferedImageLoader loader;
    private LevelGenerator levelGenerator;
    private BufferedImage level1;
    private Camera camera;

    /******************
     * User variables *
     ******************/

    int posX, posY;



    public GameController() {

        /******************
         * Window Setting *
         ******************/
        Engine.setTITLE(gameTitle);
        Engine.setWIDTH(gameWidth);
        Engine.setBACKGROUND_COLOR(gameBackground);

        gameHeight = Engine.HEIGHT;

        uiHandler = new UIHandler();

        initialize();
    }

    private void initialize() {
        /*************
         * Game Code *
         *************/

        camera = new Camera(0,0);
        loader = new BufferedImageLoader();
        level1 = loader.loadImage("/level1.png");

        levelGenerator = new LevelGenerator(this);
        spawn();
    }

    private void spawn() {
        levelGenerator.loadImageLevel(level1);
    }

    public void update() {
        Engine.timer++;

        if (Engine.currentState == GameState.Quit)
            System.exit(1);

        if (Engine.currentState != GameState.Pause && Engine.currentState != GameState.Game)
            resetGame();

        if (Engine.currentState == GameState.Game) {

            boolean endGameCondition = false;

            if (endGameCondition) {
                Engine.gameOverState();
                enableHighScoreUpdate();
                resetGame();
            }

            /*************
             * Game Code *
             *************/

            updateObjects();
            camera.update(player);
            checkCollisions();
//            if (player.canShoot()) {
//                addObject(new Bullet(player.getX(), player.getY(), posX, posY));
//            }
        }
    }

    public void render(Graphics g) {

        if (Engine.currentState == GameState.Highscore) {
            drawHighScore(g);
        }

        if (Engine.currentState == GameState.Game || Engine.currentState == GameState.Pause || Engine.currentState == GameState.GameOver) {

            /*************
             * Game Code *
             *************/

            Graphics2D g2d = (Graphics2D) g;

            g2d.translate(-camera.getX(), -camera.getY());

            renderObjects(g);

            g2d.translate(camera.getX(), camera.getY());

        }

        /***************
         * Engine Code *
         ***************/

        // The UI must be rendered after everything else, to ensure that it is on top
        uiHandler.render(g);
    }

    private void setupHighScore() {
        for (int i = 0; i < highScoreNumbers; i++) {
            highScore.add(0);
        }
    }

    public void resetGame() {

        Engine.timer = 0;

        /*************
         * Game Code *
         *************/

    }

    public void keyPressed(int key) {
        /*************
         * Game Code *
         *************/

        if (key == KeyEvent.VK_W)
            player.setUp(true);
        if (key == KeyEvent.VK_S)
            player.setDown(true);
        if (key == KeyEvent.VK_A)
            player.setLeft(true);
        if (key == KeyEvent.VK_D)
            player.setRight(true);
    }

    public void keyReleased(int key) {
        /*************
         * Game Code *
         *************/

        if (key == KeyEvent.VK_W)
            player.setUp(false);
        if (key == KeyEvent.VK_S)
            player.setDown(false);
        if (key == KeyEvent.VK_A)
            player.setLeft(false);
        if (key == KeyEvent.VK_D)
            player.setRight(false);


    }

    public void mousePressed(int mx, int my) {
        /*************
         * Game Code *
         *************/

        playerShoot(mx, my);
    }

    public void mouseReleased(int mx, int my) {
        checkButtonAction(mx, my);

        player.canShoot(false);
    }

    public void mouseMoved(int mx, int my) {
//        posX = (int) (mx + camera.getX());
//        posY = (int) (my + camera.getY());
    }

    private void checkButtonAction(int mx, int my) {
        uiHandler.checkButtonAction(mx, my);
    }

    private void enableHighScoreUpdate() {
        updateHighScore = true;
    }

    private void updateHighScore() {
        if (updateHighScore) {
            int toAddIndex = 0;
            for (int hs: highScore) {
                if (hs > score) {
                    toAddIndex++;
                }
                else break;
            }
            highScore.add(toAddIndex, score);
            updateHighScore = false;
        }
    }

    /***************************
     * Object Helper Functions *
     ***************************/

    public void addObject(GameObject object) {
        gameObjects.add(object);
    }

    private void updateObjects() {
        for (int i = 0; i < gameObjects.size(); i++) {
            GameObject object = gameObjects.get(i);
            object.update();
        }
    }

    private void renderObjects(Graphics g) {
        for (int i = 0; i < gameObjects.size(); i++) {
            GameObject object = gameObjects.get(i);
            object.render(g);
        }
    }

    /***************************
     * Render Helper Functions *
     ***************************/

    private void drawScore(Graphics g) {
        int posX = Engine.percWidth(1);
        int posY = Engine.percHeight(5);

        g.setFont(new Font("arial", 1, 20));
        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, posX, posY);
    }

    private void drawLives(Graphics g) {
        int x = Engine.intAtWidth640(10);
        int y = x/2;
        int sep = x*2; //x/5;
        int width = Engine.intAtWidth640(16);
        int height = width;
        Color color = Color.GREEN;
        for (int i = 0; i < lives; i++)
        {
            g.setColor(color);
            g.fillOval(x + sep * i, y, width, height);
        }
    }

    private void drawHealth(Graphics g) {
        int x = Engine.intAtWidth640(10);
        int y = x/2;
        int width = Engine.intAtWidth640(2);
        int height = width*6;
        Color backColor = Color.lightGray;
        Color healthColor = Color.GREEN;
        g.setColor(backColor);
        g.fillRect(x,y, healthDef * width, height);
        g.setColor(healthColor);
        g.fillRect(x,y, health * width, height);
    }

    public void drawHighScore(Graphics g) {
        g.setFont(new Font("arial", 1, 20));
        g.setColor(Color.WHITE);
        for (int i = 0; i < highScoreNumbers; i++) {
            g.drawString("Score " + (i+1) + ": " + highScore.get(i), Engine.percWidth(75), Engine.percHeight( 10 * i + 10));
        }
    }

    /******************
     * User functions *
     ******************/

    public void spawnPlayer(int w, int h) {
        player = new Player(w,h);
        addObject(player);
    }

    private void checkCollisions() {
        for (int i = 0; i < gameObjects.size(); i++) {
            GameObject gameObject = gameObjects.get(i);

            if (gameObject.getId() == ObjectID.Block)
                // check collision with player
                if (player.getBounds().intersects(gameObject.getBounds())) {
                    player.collision();
                } else {
                    // check collision with bullets
//                    for (int j = i + 1; j < gameObjects.size(); j++) {
//                        if (gameObjects.get(j).getId() == ObjectID.Bullet) {
//                            GameObject bullet = gameObjects.get(j);
//                            if (bullet.getBounds().intersects(gameObject.getBounds())) {
//                                gameObjects.remove(bullet);
//                            }
//                        }
//                    }
                }
        }
    }

    private void playerShoot(int mx, int my) {
        posX = (int) (mx + camera.getX());
        posY = (int) (my + camera.getY());

        addObject(new Bullet(player.getX(), player.getY(), posX, posY));
//        player.canShoot(true);
    }
}