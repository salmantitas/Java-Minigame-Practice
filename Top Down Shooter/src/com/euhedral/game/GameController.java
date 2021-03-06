package com.euhedral.game;

import com.euhedral.engine.BufferedImageLoader;
import com.euhedral.engine.Engine;
import com.euhedral.engine.GameState;
import com.euhedral.engine.SpriteSheet;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Random;

public class GameController {
    private UIHandler uiHandler;
    private Random r = new Random();

    // Manually set the com.euhedral.engine.Window information here
    private int gameWidth = 800;
    private int gameHeight = Engine.HEIGHT;
    private String gameTitle = Engine.TITLE;
    private Color gameBackground = Color.red;

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
    private Camera camera;
    private LinkedList<GameObject> gameObjects = new LinkedList<>();

    private BufferedImageLoader loader;

    // Level Generation

    private LevelGenerator levelGenerator;

    // Levels

    private BufferedImage level1;

    /******************
     * User variables *
     ******************/

    int posX, posY;
    private LinkedList<Block> blocks = new LinkedList<>();
    private LinkedList<Enemy> enemies = new LinkedList<>();
    private LinkedList<Bullet> playerBullets = new LinkedList<>();
    private LinkedList<Bullet> enemyBullets = new LinkedList<>();
    int ammo = 5;

    // Graphics
    private BufferedImage sprite_sheet = null;
    private SpriteSheet ss;
    private BufferedImage floor, block, crate;

    // Animations

    private BufferedImage[] player_image = new BufferedImage[3];
    private BufferedImage[] enemy_image = new BufferedImage[3];

    public GameController() {

        /******************
         * Window Setting *
         ******************/
        Engine.setTITLE(gameTitle);
        Engine.setWIDTH(gameWidth);
        Engine.setBACKGROUND_COLOR(gameBackground);

        gameHeight = Engine.HEIGHT;

        uiHandler = new UIHandler();

        initializeGame();
        initializeGraphics();
        initializeAnimations();
        initializeLevel();
    }

    private void initializeGame() {
        /*************
         * Game Code *
         *************/

        camera = new Camera(0,0);
        loader = new BufferedImageLoader();
        level1 = loader.loadImage("/level1.png");
        levelGenerator = new LevelGenerator(this);
    }

    private void initializeGraphics() {
        /*************
         * Game Code *
         *************/

        sprite_sheet = loader.loadImage("/sprite_sheet.png");
        ss = new SpriteSheet(sprite_sheet);
        floor = ss.grabImage(4,2,32,32);
        block = ss.grabImage(5,2,32,32);
        crate = ss.grabImage(6,2,32,32);
    }

    private void initializeAnimations() {
        player_image[0] = ss.grabImage(1,1,32,48);
        player_image[1] = ss.grabImage(2,1,32,48);
        player_image[2] = ss.grabImage(3,1,32,48);

        enemy_image[0] = ss.grabImage(4,1,32,32);
        enemy_image[1] = ss.grabImage(5,1,32,32);
        enemy_image[2] = ss.grabImage(6,1,32,32);
    }

    private void initializeLevel() {
        /*************
         * Game Code *
         *************/

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
            updateEnemies();
            camera.update(player);
            checkCollisions();
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

            drawFloor(g);
            renderObjects(g);

            g2d.translate(camera.getX(), camera.getY());

            drawHealth(g);
            drawAmmo(g);

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

        if (ammo > 0)
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
        if (object.getId() == ObjectID.Block) {
            Block block = (Block) object;
            blocks.add(block);
        }
        if (object.getId() == ObjectID.Enemy) {
            Enemy enemy = (Enemy) object;
            enemies.add(enemy);
        }
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
        int y = 5*x/2;
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

    public void spawn(int w, int h, ObjectID ID) {
        /*************
         * Game Code *
         *************/

        if (ID == ObjectID.Block) {
//            Block block = new Block(w,h);
            addObject(new Block(w, h, block));
        }
        else if (ID == ObjectID.Enemy) {
            addObject(new Enemy(w, h, enemy_image));
        }
        else if (ID == ObjectID.Crate) {
            addObject(new Crate(w,h, crate));
        }

    }

    public void spawnPlayer(int w, int h) {
        player = new Player(w,h, player_image);
        addObject(player);
    }

    private void checkCollisions() {

        // collision of crate and player

        for (int i = 0; i < gameObjects.size(); i++) {
            GameObject crate = gameObjects.get(i);
            if (crate.getId() == ObjectID.Crate) {
                if (player.getBounds().intersects(crate.getBounds())) {
                    ammo += 10;
                    gameObjects.remove(crate);
                }
            }
        }

        //  collision of box and player

        for (int i = 0; i < blocks.size(); i++) {
            Block block = blocks.get(i);
            if (player.getBounds().intersects(block.getBounds())) {
                player.collision(block);
            }
        }

        //  collision of enemy and player

        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            if (player.getBounds().intersects(enemy.getBounds())) {
                health -= 10;
            }
        }


        // check collision with bullets

        for (int i = 0; i < blocks.size(); i++) {
            for (int j = 0; j < playerBullets.size(); j++) {
                Bullet bullet = playerBullets.get(j);
                if (playerBullets.get(j).getBounds().intersects(blocks.get(i).getBounds())) {
                    gameObjects.remove(bullet);
                    playerBullets.remove(bullet);

//                    System.out.println("Bullet collision");
                }
            }
        }

        // check enemy to wall collision

        for (int i = 0; i < blocks.size(); i++) {
            for (int j = 0; j < enemies.size(); j++) {
                Enemy enemy = enemies.get(j);
                if (enemy.getBoundsBig().intersects(blocks.get(j).getBounds())) {
                    enemy.collision(blocks.get(i));

                }
            }
        }

        // check enemy to bullet collision

        for (int i = 0; i < playerBullets.size(); i++) {
            Bullet bullet = playerBullets.get(i);
            for (int j = 0; j < enemies.size(); j++) {
                Enemy enemy = enemies.get(j);

                if (enemy.getBounds().intersects(bullet.getBounds())) {
                    playerBullets.remove(bullet);
                    gameObjects.remove(bullet);
                    enemy.hp -= 50;
                }
            }
        }
    }

    private void playerShoot(int mx, int my) {
        posX = (int) (mx + camera.getX());
        posY = (int) (my + camera.getY());

        Bullet bullet = new Bullet(player.getX(), player.getY(), posX, posY);
        addObject(bullet);
        playerBullets.add(bullet);
        ammo--;
//        player.canShoot(true);
    }

    private void updateEnemies() {
        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            if (enemy.hp <= 0) {
                enemies.remove(enemy);
                gameObjects.remove(enemy);
            }
        }
    }

    private void drawAmmo(Graphics g) {
        int posX = Engine.percWidth(1);
        int posY = Engine.percHeight(5);

        g.setFont(new Font("arial", 1, 20));
        g.setColor(Color.WHITE);
        g.drawString("Ammo: " + ammo, posX, posY);
    }

    private void drawFloor(Graphics g) {
        int width = 32*72, height = width;
        for (int i = 0; i < width; i += 32) {
            for (int j = 0; j < height; j += 32) {
                g.drawImage(floor, i, j, null);
            }
        }
    }
}
