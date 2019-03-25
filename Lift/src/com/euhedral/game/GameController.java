package com.euhedral.game;

import com.euhedral.engine.Engine;
import com.euhedral.engine.GameState;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.security.Key;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class GameController {
    private UIHandler uiHandler;
    private Random r = new Random();

    // Manually set the com.euhedral.engine.Window information here
    private int gameWidth = 1000;
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
    private LinkedList<Integer> highScore = new LinkedList<>();
    private int highScoreNumbers = 5;
    private boolean updateHighScore = false;

    private ArrayList<GameObject> gameObjects;

    /******************
     * User variables *
     ******************/

    private Player player;
    private boolean collision = false;

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
        gameObjects = new ArrayList<>();

        player = new Player(Engine.WIDTH/3, Engine.HEIGHT/2);
        gameObjects.add(player);

        // stub
        gameObjects.add(new Block(Engine.WIDTH/2, Engine.HEIGHT/2));
    }

    public void update() {
        Engine.timer++;

        if (Engine.currentState == GameState.Quit)
            System.exit(1);

        if (Engine.currentState != GameState.Pause && Engine.currentState != GameState.Game)
            resetGame();

        if (Engine.currentState == GameState.Game) {

            boolean endGameCondition = collision;

            if (endGameCondition) {
                Engine.gameOverState();
                enableHighScoreUpdate();
                resetGame();
            }

            /*************
             * Game Code *
             *************/

            for (int i = 0; i < gameObjects.size(); i++) {
                GameObject gameObject = gameObjects.get(i);
                gameObject.update();
            }

            checkCollision();
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

            for (int i = 0; i < gameObjects.size(); i++) {
                GameObject gameObject = gameObjects.get(i);
                gameObject.render(g);
            }
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

        Engine.gameState();
        initGame();
        collision = false;
    }

    public void keyPressed(int key) {
        /*************
         * Game Code *
         *************/

        if (key == KeyEvent.VK_SPACE) {
            player.jumping = true;
        }
    }

    public void keyReleased(int key) {
        /*************
         * Game Code *
         *************/

        if (key == KeyEvent.VK_SPACE) {
            player.jumping = false;
        }

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

    private void checkCollision() {
        Rectangle playerBounds = player.getBounds();
        for (int i = 0; i < gameObjects.size(); i++) {
            GameObject gameObject = gameObjects.get(i);
            if (gameObject.getId() == ObjectID.Block)
                if (gameObject.getBounds().intersects(playerBounds)) {
                    collision = true;
                }
        }
    }
}
