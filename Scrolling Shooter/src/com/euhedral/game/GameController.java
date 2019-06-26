package com.euhedral.game;

import com.euhedral.engine.BufferedImageLoader;
import com.euhedral.engine.Engine;
import com.euhedral.engine.Entity;
import com.euhedral.engine.GameState;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Random;

public class GameController {
    private UIHandler uiHandler;
    private Random r = new Random();

    /********************************************
     * Window Settings - Manually Configurable *
     *******************************************/

    private int gameWidth = 1024;
    private double gameRatio = 4 / 3;
    private int gameHeight = Engine.HEIGHT;
    private String gameTitle = "Aerial Predator";
    private Color gameBackground = Color.BLUE;

    /****************************************
     * Common Game Variables                *
     * Comment Out Whichever is Unnecessary *
     ****************************************/

    // Score
    private int score = 0;
    private int scoreX = Engine.percWidth(2.5);
    private int scoreY = Engine.percHeight(16);
    private int scoreSize = Engine.percWidth(3);

    // Vitality
//    private int lives = 3;

    private int healthX = Engine.percWidth(2.5);
    private int healthY = 5 * healthX;
    private final int healthDefault = 100;
    private int health = healthDefault;

    // Power
    private int powerX = Engine.percWidth(37);
    private int powerY = scoreY;
    private int powerSize = scoreSize;
    private final int maxPower = 5;
    private int power = 1;

    // High Score
    private LinkedList<Integer> highScore = new LinkedList<>();
    private int highScoreNumbers = 5;
    private boolean updateHighScore = false;

    // Objects

    private LinkedList<Entity> entities;
    private Player player = new Player(0, 0, 0);

    // Camera
    public static Camera camera;
    int offsetHorizontal;
    int offsetVertical;

    // Graphics
    private BufferedImageLoader loader;

    // Level Generation
    private LevelGenerator levelGenerator;

    // Levels
    private int levelHeight;
    private boolean loadMission = false;


    /******************
     * User variables *
     ******************/

    private static int STARTLEVEL = 1;
    private static int level;
    private final int MAXLEVEL = 2;

    private EnemyBoss boss;
    private Flag flag;

    private int healthBossDef, healthBoss;

    private boolean bossLives = false;

    private LinkedList<Enemy> enemies = new LinkedList<>();

    private int bossScore = 500;

    private boolean levelSpawned = false;
    private boolean ground = false;

    private BufferedImage level1 = null, level2 = null;
    private boolean keyboardControl = true; // false means mouse Control

    public GameController() {

        /******************
         * Window Setting *
         ******************/
        Engine.setTITLE(gameTitle);
//        Engine.setRatio(gameRatio);
        Engine.setWIDTH(gameWidth);
        Engine.setBACKGROUND_COLOR(gameBackground);
        gameHeight = Engine.HEIGHT;
        uiHandler = new UIHandler();
        initializeGame();
        initializeGraphics();
        initializeAnimations();
        initializeLevel();
    }

    /*************************
     * Initializer Functions *
     *************************/

    private void initializeGame() {
        /*************
         * Game Code *
         *************/

        Engine.menuState();
    }

    private void initializeGraphics() {
        /*************
         * Game Code *
         *************/

        loader = new BufferedImageLoader();
        level1 = loader.loadImage("/level1.png");
        level2 = loader.loadImage("/level2.png");
    }

    private void initializeAnimations() {
        /*************
         * Game Code *
         *************/
    }

    private void initializeLevel() {
        /*************
         * Game Code *
         *************/

        levelGenerator = new LevelGenerator(this);
    }

    public void update() {
//        System.out.println(Engine.currentState);
        Engine.timer++;

        if (Engine.currentState == GameState.Quit)
            Engine.stop();

        if (Engine.currentState != GameState.Pause && Engine.currentState != GameState.Game && Engine.currentState != GameState.Transition)
            resetGame();

        if (Engine.currentState == GameState.Transition) {
            /*************
             * Game Code *
             *************/

            if (loadMission) {
                if (!levelSpawned)
                    spawn();
            }
        }

        if (Engine.currentState == GameState.Game) {
            loadMission = false;
            boolean endGameCondition = health <= 0;

            if (endGameCondition) {
                Engine.gameOverState();
                resetGame();
            }

            /*************
             * Game Code *
             *************/

            else {

                player.update();
                flag.update();

                for (Enemy enemy : enemies) {
                    if(enemy.isActive()) {
                        enemy.update();
                    }
                }

                checkCollision();

                checkLevelStatus();
            }
        }
    }

    public void render(Graphics g) {

        if (Engine.currentState == GameState.Highscore) {
//            drawHighScore(g);
        }

        if (Engine.currentState == GameState.Transition) {
            /*************
             * Game Code *
             *************/

            g.setFont(new Font("arial", 1, Engine.percWidth(5)));
            g.setColor(Color.WHITE);
            g.drawString("Level " + level, Engine.percWidth(40), Engine.percHeight(45));
            drawHealth(g);
            drawScore(g);
            drawPower(g);
        }

        if (Engine.currentState == GameState.Game || Engine.currentState == GameState.Pause || Engine.currentState == GameState.GameOver) {


            /*************
             * Game Code *
             *************/

            if (Engine.currentState == GameState.Game || Engine.currentState == GameState.Pause ) {

                renderInCamera(g);
                drawHealth(g);

                if (boss.isInscreen() && boss.isAlive())
                    drawBossHealth(g);

                drawScore(g);
                drawPower(g);

            }
        }

        /***************
         * Engine Code *
         ***************/

        uiHandler.render(g);
    }

    private void renderInCamera(Graphics g) {
        /*****************
         * Engine Conde *
         *****************/

        Graphics2D g2d = (Graphics2D) g;

        // Camera start
        g2d.translate(camera.getX(), camera.getY());

        /*************
         * Game Code *
         *************/

        for (Enemy enemy : enemies) {
            if (enemy.isActive()) {
                enemy.render(g);
            }
        }

        flag.render(g);
        player.render(g);

        /*****************
         * Engine Code *
         *****************/

        // Camera end
        g2d.translate(-camera.getX(), -camera.getY());
    }

    /************************
     * User Input Functions *
     ************************/

    public void mouseMoved(int mx, int my) {
        /*************
         * Game Code *
         *************/

        uiHandler.checkHover(mx, my);
        giveDestination(mx, my);
    }

    public void mouseDragged(int mx, int my) {
        /*************
         * Game Code *
         *************/

        giveDestination(mx, my);
    }

    public void mousePressed(int mouse) {
        /*************
         * Game Code *
         *************/

        if (mouse == MouseEvent.BUTTON1) {
            shootPlayer();
        }
    }

    public void mouseReleased(int mouse) {
        /*************
         * Game Code *
         *************/

        if (mouse == MouseEvent.BUTTON1) {
            stopShootPlayer();
        }
    }

    public void keyPressed(int key) {
        /*************
         * Game Code *
         *************/

        if (Engine.currentState != GameState.Game) {
            // Keyboard to Navigate buttons

            // Enter/Spacebar to select selected
            if (key == KeyEvent.VK_ENTER || key == KeyEvent.VK_SPACE) {
                uiHandler.chooseSelected();
            }

            if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
//                uiHandler.keyboardSelection();
            }
        }

        if (Engine.currentState != GameState.Pause) {
            if (key == (KeyEvent.VK_LEFT) || key == (KeyEvent.VK_A))
                movePlayer('l');

            if (key == (KeyEvent.VK_RIGHT) || key == (KeyEvent.VK_D))
                movePlayer('r');

            if (key == (KeyEvent.VK_UP) || key == (KeyEvent.VK_W))
                movePlayer('u');

            if (key == (KeyEvent.VK_DOWN) || key == (KeyEvent.VK_S))
                movePlayer('d');

            if (key == (KeyEvent.VK_SPACE) || key == (KeyEvent.VK_NUMPAD0))
                shootPlayer();

            if (key == KeyEvent.VK_CONTROL)
                player.switchBullet();

            if (Engine.currentState == GameState.Game) {
                if (key == KeyEvent.VK_P || key == KeyEvent.VK_ESCAPE) {
                    Engine.pauseState();
                }
            }

        } else {

            if (Engine.currentState == GameState.Pause) {
                if (key == KeyEvent.VK_P || key == KeyEvent.VK_ESCAPE) {
                    Engine.gameState();
                }
            }

            if (key == (KeyEvent.VK_ESCAPE)) {
                if (Engine.currentState == GameState.Menu) {
                    System.exit(1);
                }
            }
        }

    }

    public void keyReleased(int key) {
        /*************
         * Game Code *
         *************/

        if (key == (KeyEvent.VK_LEFT) || key == (KeyEvent.VK_A))
            stopMovePlayer('l');

        if (key == (KeyEvent.VK_RIGHT) || key == (KeyEvent.VK_D))
            stopMovePlayer('r');

        if (key == (KeyEvent.VK_UP) || key == (KeyEvent.VK_W))
            stopMovePlayer('u');

        if (key == (KeyEvent.VK_DOWN) || key == (KeyEvent.VK_S))
            stopMovePlayer('d');

        if (key == (KeyEvent.VK_SPACE) || key == (KeyEvent.VK_NUMPAD0))
            stopShootPlayer();

    }

    private void setupHighScore() {
        for (int i = 0; i < highScoreNumbers; i++) {
            highScore.add(0);
        }
    }

    /***************************
     * Game Managing Functions *
     ***************************/

    public void resetGame() {

        Engine.timer = 0;
        score = 0;
        power = 1;
        health = healthDefault;

        /*************
         * Game Code *
         *************/

        level = STARTLEVEL;
        levelSpawned = false;
        enemies.clear();
        levelSpawned = false;
        uiHandler.ground = false;
    }

    public void checkButtonAction(int mx, int my) {
        uiHandler.checkButtonAction(mx, my);
        performAction();
    }

    private void performAction() {
        ActionTag action = uiHandler.getAction();
        if (action != null) {
            if (action == ActionTag.go) {
                loadMission = true;
            } else if (action == ActionTag.control) {
                keyboardControl = !keyboardControl;
            } else if (action == ActionTag.health) {
                buyHealth();
            } else if (action == ActionTag.power) {
                buyPower();
            } else if (action == ActionTag.ground) {
                buyGround();
            }
            uiHandler.endAction();
        }
    }

    /*******************************
     * Entity Management Functions *
     ****************-**************/

    public void addEntity(Entity entity) {
        entities.add(entity);

        /*************
         * Game Code *
         *************/
    }

    public void removeEntity(Entity entity) {
        entities.remove(entity);

        /*************
         * Game Code *
         *************/
    }

    private void updateEntities() {
        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            entity.update();
        }
    }

    private void renderEntities(Graphics g) {
        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            entity.render(g);
        }
    }

    /***************************
     * Render Helper Functions *
     ***************************/

    private void drawScore(Graphics g) {
        g.setFont(new Font("arial", 1, scoreSize));
        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, scoreX, scoreY);
    }

    private void drawHealth(Graphics g) {
        int width = Engine.intAtWidth640(2);
        int height = width * 6;
        Color backColor = Color.lightGray;
        Color healthColor = Color.GREEN;
        g.setColor(backColor);
        g.fillRect(healthX, healthY, healthDefault * width, height);
        g.setColor(healthColor);
        g.fillRect(healthX, healthY, health * width, height);
    }

    private void drawPower(Graphics g) {
        g.setFont(new Font("arial", 1, powerSize));
        g.setColor(Color.WHITE);
        g.drawString("Power: " + power, powerX, powerY);
    }

    /******************
     * User functions *
     ******************/

    // Shop Functions

    private void buyHealth() {
        int cost = 500;

        if (score >= cost) {
            if (health < healthDefault) {
                health += 25;
                score -= cost;
                if (health > healthDefault)
                    health = healthDefault;
            } else {
                System.out.println("Health is full");
            }
        } else {
            System.out.println("Not enough score");
        }
    }

    private void buyPower() {
        int cost = 1000;

        if (score >= cost) {
            if (player.getPower() < maxPower) {
                power++;
                score -= cost;
                if (power > maxPower)
                    power--;
            } else {
                System.out.println("Max power is reached");
            }
        } else {
            System.out.println("Not enough score");
        }
    }

    private void buyGround() {
        int cost = 2000;
        if (score >= cost) {
            if (!ground) {
                score -= cost;
                ground = true;
                uiHandler.ground = true;
            }
        } else {
            System.out.println("Not enough score");
        }
    }

    protected void drawBossHealth(Graphics g) {
        int startX = Engine.percWidth(35);
        int endX = Engine.percWidth(65);
        int diffX = endX - startX;

        int y = Engine.percHeight(28);
        int width = diffX / healthBossDef;
        int height = width;
        Color backColor = Color.lightGray;
        Color healthColor = Color.RED;
        g.setColor(backColor);
        g.fillRect(startX, y, healthBossDef * width, height);
        g.setColor(healthColor);
        g.fillRect(startX, y, healthBoss * width, height);
    }

    public void movePlayer(char c) {
        if (c == 'l')
            player.moveLeft(true);
        else if (c == 'r')
            player.moveRight(true);

        if (c == 'u')
            player.moveUp(true);
        else if (c == 'd')
            player.moveDown(true);
    }

    public void stopMovePlayer(char c) {
        if (c == 'l')
            player.moveLeft(false);
        else if (c == 'r')
            player.moveRight(false);

        if (c == 'u')
            player.moveUp(false);
        else if (c == 'd')
            player.moveDown(false);
    }

    private void giveDestination(int mx, int my) {
        if (!keyboardControl)
            player.giveDestination(mx, my);
    }

    public boolean canUpdateDestination(int mx, int my) {
        return !(player.getMx() == mx && player.getMy() == my);
    }

    public void shootPlayer() {
        player.canShoot(true);
    }

    public void stopShootPlayer() {
        player.canShoot(false);
    }

    public void checkCollision() {
        // Player vs enemy collision
        for (Enemy enemy : enemies) {
            if (enemy.getID() == ContactID.Air)
                if (enemy.inscreen && enemy.getBounds().intersects(player.getBounds())) {
                    score += enemy.getScore();
                    health -= 30;
                    destroy(enemy);
                } else if (enemy.getID() == ContactID.Boss) {
                    health = -10;
                }
        }

        // Player vs enemy bullet collision
        for (Enemy enemy : enemies) {
            Bullet b = enemy.checkCollision(player);
            if (b != null) {
                health -= 10;
                destroy(b);
            }
        }

        // Enemy vs player bullet collision
        for (Enemy enemy : enemies) {
            if (enemy.inscreen && enemy.isActive()) {
                Bullet b = player.checkCollision(enemy);
                if (b != null) {
                    if (enemy.getID() == ContactID.Boss) {
                        boss.damage();
                        healthBoss = boss.getHealth();
                        if (boss.getHealth() <= 0) {
                            destroy(boss);
                            score += bossScore;
                        }
                    } else {
                        enemy.damage();
                        if (enemy.getHealth() <= 0) {
                            destroy(enemy);
                            score += enemy.getScore();
                        }
                    }
                    destroy(b);
                }
            }
        }
    }

    private void destroy(Enemy enemy) {
        enemy.setActive(false);
//        Iterator<com.euhedral.game.Enemy> it = enemies.iterator();
//        while (it.hasNext()) {
//            com.euhedral.game.Enemy e = it.next();
//            if (e == enemy) {
//                it.remove();
//            }
//        }
//        enemy.setX(+300);
//        enemy.setY(player.getY() + 1000);
//        enemy.setVelX(0);
//        enemy.setVelY(0);
    }

    private void destroy(Bullet bullet) {
        bullet.setX(+100);
        bullet.setY(Engine.HEIGHT + 100);
        bullet.setVel(0);
    }

    private void spawn() {
        levelSpawned = !levelSpawned;
        Engine.gameState();

        if (level == 1)
            levelGenerator.loadImageLevel(level1);

        if (level == 2)
            levelGenerator.loadImageLevel(level2);
    }

    public static Camera getCamera() {
        return camera;
    }

    public void spawnPlayer(int width, int height, int levelHeight) {
        offsetHorizontal = -gameWidth / 2 + 32;
        offsetVertical = gameHeight - 160;
        player = new Player(width, height, levelHeight);
        player.setPower(power);
        player.setGround(ground);
        // sets the camera's width to center the player horizontally, essentially to 0, and
        // adjust the height so that player is at the bottom of the screen
//        camera = new Camera(player.getX() + offsetHorizontal, -player.getY() + offsetVertical);
        camera = new Camera(0, -player.getY() + offsetVertical);
        camera.setMarker(player.getY());
    }

    public void spawnCamera(int width, int height) {
        camera = new Camera(width, -750); // -700 = 2 fps;
    }

    // Spawn Air Enemy Basic
    public void spawnEnemy(int x, int y, ContactID contactId) {
        if (contactId == ContactID.Air)
            enemies.add(new EnemyBasic(x, y, EnemyID.Basic, contactId));
        else enemies.add(new EnemyGroundBasic(x, y));
    }

    // Spawn Air Enemy Basic
    public void spawnEnemy(int x, int y, EnemyID enemyID, ContactID contactId, Color color) {
        if (contactId == ContactID.Air)
            enemies.add(new EnemyBasic(x, y, EnemyID.Fast, contactId, color));
    }

    public void spawnBoss(int width, int height) {
        bossLives = true;
        if (level == 1)
            boss = new EnemyBoss1(width, height);
        enemies.add(boss);
        healthBossDef = boss.getHealth();
        healthBoss = healthBossDef;
//        else enemies.add(new com.euhedral.game.EnemyGroundBasic(width,height));
    }

    public void spawnFlag() {
        flag = new Flag(Engine.WIDTH / 2, -Engine.HEIGHT / 2, ContactID.Air);
    }

    public void respawnFlag() {
        flag.reset();
    }

    // if the flag crosses the screen, advance level and if no levels remain, end game
    public void checkLevelStatus() {
        // If the boss is killed, updates the boolean variable and adds the bossScore
        if (bossLives != boss.isAlive()) {
            bossLives = boss.isAlive();
            score += bossScore;
        }

        if (flag.getY() > levelHeight && !bossLives) {
            level++;
            levelSpawned = false;
            bossLives = false;

            if (level > MAXLEVEL) {
                Engine.menuState(); // stub
                resetGame();
            } else {
                Engine.transitionState();
//                spawn();
            }
        }
    }

    public void setLevelHeight(int h) {
        levelHeight = h;
    }

}
