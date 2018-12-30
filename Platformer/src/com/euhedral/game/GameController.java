package com.euhedral.game;

import com.euhedral.engine.Engine;
import com.euhedral.engine.GameState;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Random;

public class GameController {
    private UIHandler uiHandler;
    private Random r = new Random();

    // Manually set the com.Window information here
    private int gameWidth = Engine.WIDTH;
    private int gameHeight = Engine.HEIGHT;
    private String gameTitle = Engine.TITLE;
    private Color gameBackground = Engine.BACKGROUND_COLOR;

    // Common game variables
    private int score = 0;
    private int scoreX = Engine.percWidth(5);
    private int scoreY = Engine.percHeight(15);
    private int scoreSize = Engine.percWidth(4);
    private int lives = 3;
    private final int healthDef = 100;
    private int health = healthDef;
    private Random rand = new Random();
    private LinkedList<Integer> highScore = new LinkedList<>();
    private int highScoreNumbers = 5;
    private boolean updateHighScore = false;

    /******************
     * User variables *
     ******************/

    private Block block = new Block(-50, -50, ObjectId.Block);
    private float blockSize = block.getWidth();
    private Camera cam;
    public Player player;

    private BufferedImage level = null;

    public LinkedList<GameObject> objects = new LinkedList<>();

    public GameController() {

        /******************
         * Window Setting *
         ******************/
        Engine.setTITLE(gameTitle);
        Engine.setWIDTH(gameWidth);
        Engine.setBACKGROUND_COLOR(gameBackground);

        gameHeight = Engine.HEIGHT;

        uiHandler = new UIHandler();

        /*************
         * Game Code *
         *************/
        initGame();


    }

    private void initGame() {
        BufferedImageLoader loader = new BufferedImageLoader();
        level = loader.loadImage("/level.png");
        loadImageLevel(level);
//        createLevel();
//        player = new Player(100, 100, ObjectId.Player);
//        objects.add(player);
        cam = new Camera(player.getX(), 0);
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

            cam.update(player);

            for (int i = 0; i < objects.size(); i++) {

                GameObject object = objects.get(i);
                objects.get(i).update();

                if (object.getId() == ObjectId.Block) {

                    if (player.getBoundsRight().intersects(object.getBounds())) {
                        player.setX(object.x - player.getWidth());
                    }

                    if (player.getBoundsLeft().intersects(object.getBounds())) {
                        player.setX(object.x + player.getWidth());
                    }

                    if (player.getBoundsTop().intersects(object.getBounds())) {
                        player.setY(object.y + player.getHeight()/2);
                        player.setVelY(0);
                        player.setFalling(false);
                        player.setJumping(false);
                    }

                    if (player.getBounds().intersects(object.getBounds())) {
                        player.setY(object.y - player.getHeight());
                        player.setVelY(0);
                        player.setFalling(false);
                        player.setJumping(false);
                    }
                    else {
                        player.setFalling(true);
                    }
                }

            }
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

            // Camera start
            g2d.translate(cam.getX(), cam.getY());

            for (int i = 0; i < objects.size(); i++) {
                objects.get(i).render(g);
            }

            // Camera end
            g2d.translate(-cam.getX(), -cam.getY());

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

    public void checkButtonAction(int mx, int my) {
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
     * Render Helper Functions *
     ***************************/

    private void drawScore(Graphics g) {
        g.setFont(new Font("arial", 1, 20));
        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 300, 300);
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

    public void addObject(GameObject object) {
        objects.add(object);
    }

    public void removeObject(GameObject object) {
        objects.remove(object);
    }

    private void createLevel() {
        // Floor
        for (int i = 0; i < Engine.WIDTH * 2 + blockSize; i += blockSize)
            addObject(new Block(i, Engine.HEIGHT-blockSize*Engine.intAtWidth640(3), ObjectId.Block));

        // Platform
        for (int i = 0; i < 15; i ++)
            addObject(new Block(Engine.percWidth(25) + i * blockSize, Engine.percHeight(60), ObjectId.Block));

        // LeftWall
        for (int i = 0; i < Engine.HEIGHT + blockSize; i += blockSize)
            addObject(new Block(0, i, ObjectId.Block));
    }

    private void loadImageLevel(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();

        System.out.println("Width, Height: " + w + " " + h);

        for (int j = 0; j < h; j++) {
            for (int i = 0; i < w; i++) {
                int pixel = image.getRGB(i,j);
                int r = (pixel >> 16) & 0xff;
                int g = (pixel >> 8) & 0xff;
                int b = pixel & 0xff;

                // If white blocks
                if (r == 255 && g == 255 && b == 255)
                    addObject(new Block(i * blockSize, j * blockSize, ObjectId.Block));
                // if blue block
                if (r == 0 && g == 0 && b == 255 && player == null) {
                    player = new Player(i * blockSize, j * blockSize, ObjectId.Player);
                    addObject(player);
                }
            }
        }


    }
}
