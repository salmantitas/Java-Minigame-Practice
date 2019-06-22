package com.euhedral.game;

import com.euhedral.engine.BufferedImageLoader;
import com.euhedral.engine.Engine;
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

    // Manually set the com.euhedral.engine.Window information here
    private int gameWidth = 1024;
    private double gameRatio = 4/3;
    private int gameHeight = Engine.HEIGHT;
    private String gameTitle = "Scrolling Shooter";
    private Color gameBackground = Color.BLUE;

    // Common game variables
    private Texture texture;
    private SpriteSheet spriteSheet;
    private Sprite sprite;

    // Camera
    int offsetHorizontal;
    int offsetVertical;

    private int score = 0;
    private int scoreX = Engine.percWidth(2.5);
    private int powerX = Engine.percWidth(37);
    private int scoreY = Engine.percHeight(16);
    private int powerY = scoreY;
    private int scoreSize = Engine.percWidth(3);
    private int powerSize = scoreSize;
    private int lives = 3;
    private final int maxPower = 5;
    private int power = 1;

    private int healthX = Engine.percWidth(2.5);
    private int healthY = 5 * healthX;
    private final int healthDef = 100;
    private int health = healthDef;
    private LinkedList<Integer> highScore = new LinkedList<>();
    private int highScoreNumbers = 5;
    private boolean updateHighScore = false;
    private boolean loadMission = false;
    private static int STARTLEVEL = 1;
    private static int level;
    private final int MAXLEVEL = 2;

    /******************
     * User variables *
     ******************/

    private Player player = new Player(0,0, 0);
    private EnemyBoss boss;
    private Flag flag;

    private int healthBossDef, healthBoss;

    private boolean bossLives = false;

    private LinkedList<Enemy> enemies = new LinkedList<>();

    private int TRANSITION_TIMER = 60;
    private int transitionTimer = TRANSITION_TIMER;
    private int basicEnemyScore = 50;
    private int bossScore = 500;
    private int levelHeight;

    private boolean levelSpawned = false;
    private boolean ground = false;

    private BufferedImage level1 = null, level2 = null;
    private LevelGenerator levelGenerator;
    public static Camera cam;
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

        initialize();

    }

    private void initialize() {
        Engine.menuState();
        setupHighScore();
        BufferedImageLoader loader = new BufferedImageLoader();
        level1 = loader.loadImage("/level1.png");
        level2 = loader.loadImage("/level2.png");
        levelGenerator = new LevelGenerator(this);
        texture = new Texture("tex");
        spriteSheet = new SpriteSheet(new Texture("tex"), 32);
        sprite = new Sprite(spriteSheet, 1 ,1);
//        spawn();
    }

    public void update() {
//        System.out.println(Engine.currentState);
        Engine.timer++;

        if (Engine.currentState == GameState.Quit)
            System.exit(1);

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
                enableHighScoreUpdate();
                resetGame();
            }

            /*************
             * Game Code *
             *************/

            else {

                player.update();
                flag.update();

                for (Enemy enemy : enemies) {
                    enemy.update();
                }

                checkCollision();

                checkLevelStatus();
            }
        }
    }

    public void render(Graphics g) {

        if (Engine.currentState == GameState.Highscore) {
            drawHighScore(g);
        }

        if (Engine.currentState == GameState.Transition) {
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

                    Graphics2D g2d = (Graphics2D) g;

                    // Camera start
                    g2d.translate(cam.getX(), cam.getY());

                    for (Enemy enemy : enemies) {
                        enemy.render(g);
                    }

                    flag.render(g);
                    player.render(g);

                    // Camera end
                    g2d.translate(-cam.getX(), -cam.getY());

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

        if (mouse==MouseEvent.BUTTON1) {
            shootPlayer();
        }
    }

    public void mouseReleased(int mouse) {
        /*************
         * Game Code *
         *************/

        if (mouse==MouseEvent.BUTTON1) {
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
            if (key == (KeyEvent.VK_LEFT ) || key == (KeyEvent.VK_A))
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

        if (key==(KeyEvent.VK_RIGHT)|| key==(KeyEvent.VK_D))
            stopMovePlayer('r');

        if (key==(KeyEvent.VK_UP) || key==(KeyEvent.VK_W))
            stopMovePlayer('u');

        if (key==(KeyEvent.VK_DOWN) || key==(KeyEvent.VK_S))
            stopMovePlayer('d');

        if (key==(KeyEvent.VK_SPACE) || key==(KeyEvent.VK_NUMPAD0))
            stopShootPlayer();

    }

    private void setupHighScore() {
        for (int i = 0; i < highScoreNumbers; i++) {
            highScore.add(0);
        }
    }

    public void resetGame() {

        level = STARTLEVEL;
        levelSpawned = false;
        Engine.timer = 0;

        /*************
         * Game Code *
         *************/

        updateHighScore();
        score = 0;
        power = 1;
        health = healthDef;
        enemies.clear();
        levelSpawned = false;
        uiHandler.ground = false;
    }

    public void checkButtonAction(int mx, int my) {
        uiHandler.checkButtonAction(mx, my);
        performAction();
    }

    private void enableHighScoreUpdate() {
        updateHighScore = true;
    }

    private void updateHighScore() {
            int toAddIndex = 0;
            for (int hs: highScore) {
                if (hs > score) {
                    toAddIndex++;
                }
                else break;
            }
            highScore.add(toAddIndex, score);
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

    // Shop Functions

    private void buyHealth() {
        int cost = 500;

        if (score >= cost) {
            if (health < healthDef) {
                health += 25;
                score -= cost;
                if (health > healthDef)
                    health = healthDef;
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


    /***************************
     * Render Helper Functions *
     ***************************/

    private void drawScore(Graphics g) {
        g.setFont(new Font("arial", 1, scoreSize));
        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, scoreX, scoreY);
    }

    private void drawPower(Graphics g) {
        g.setFont(new Font("arial", 1, powerSize));
        g.setColor(Color.WHITE);
        g.drawString("Power: " + power, powerX, powerY);
    }

    private void drawLives(Graphics g) {
        int x = Engine.intAtWidth640(10);
        int y = x/2;
        int sep = x*2;
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
        int width = Engine.intAtWidth640(2);
        int height = width*6;
        Color backColor = Color.lightGray;
        Color healthColor = Color.GREEN;
        g.setColor(backColor);
        g.fillRect(healthX, healthY, healthDef * width, height);
        g.setColor(healthColor);
        g.fillRect(healthX, healthY, health * width, height);
    }

    public void drawHighScore(Graphics g) {
        g.setFont(new Font("arial", 1, 20));
        g.setColor(Color.WHITE);
        for (int i = 0; i < highScoreNumbers; i++) {
            g.drawString("Score " + (i+1) + ": " + highScore.get(i), Engine.percWidth(40), Engine.percHeight( 10 * i + 10));
        }
    }

    /******************
     * User functions *
     ******************/

    protected void drawBossHealth(Graphics g) {
        int startX = Engine.percWidth(35);
        int endX = Engine.percWidth(65);
        int diffX = endX - startX;

        int y = Engine.percHeight(14);
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
        // com.euhedral.game.Player vs enemy collision
        for (Enemy enemy: enemies) {
            if (enemy.getID() == ID.Air)
                if (enemy.inscreen && enemy.getBounds().intersects(player.getBounds())) {
                    score += basicEnemyScore;
                    health -= 30;
                    destroy(enemy);
            }
            else if (enemy.getID() == ID.Boss) {
                health = -10;
                }
        }

        // com.euhedral.game.Player vs enemy bullet collision
        for (Enemy enemy: enemies) {
            Bullet b = enemy.checkCollision(player);
            if (b != null) {
                health -= 10;
                destroy(b);
            }
        }

        // com.euhedral.game.Enemy vs player bullet collision
        for (Enemy enemy: enemies) {
            if (enemy.inscreen) {
                Bullet b = player.checkCollision(enemy);
                if (b != null) {
                    if (enemy.getID() == ID.Boss) {
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
                            score += basicEnemyScore;
                        }
                    }
                    destroy(b);
                }
            }
        }
    }

    private void destroy(Enemy enemy) {
//        Iterator<com.euhedral.game.Enemy> it = enemies.iterator();
//        while (it.hasNext()) {
//            com.euhedral.game.Enemy e = it.next();
//            if (e == enemy) {
//                it.remove();
//            }
//        }
        enemy.setX(+ 300);
        enemy.setY(player.getY() + 1000);
        enemy.setVelX(0);
        enemy.setVelY(0);
    }

    private void destroy(Bullet bullet) {
        bullet.setX(+ 100);
        bullet.setY(Engine.HEIGHT  + 100);
        bullet.setVel(0);
    }

    private void spawn() {
        transitionTimer = TRANSITION_TIMER;
        levelSpawned = !levelSpawned;
        Engine.gameState();

        if (level == 1)
            levelGenerator.loadImageLevel(level1);

        if (level == 2)
            levelGenerator.loadImageLevel(level2);
    }

    public static Camera getCamera() {
        return cam;
    }

    public void spawnPlayer(int width, int height, int levelHeight) {
        offsetHorizontal = - gameWidth/2 + 32;
        offsetVertical = gameHeight - 160;
        player = new Player(width,height, levelHeight);
        player.setPower(power);
        player.setGround(ground);
        // sets the camera's width to center the player horizontally, essentially to 0, and
        // adjust the height so that player is at the bottom of the screen
        cam = new Camera(player.getX() + offsetHorizontal,-player.getY() + offsetVertical);
        cam.setMarker(player.getY());
    }

    public void spawnCamera(int width, int height) {
        cam = new Camera(width,-750); // -700 = 2 fps;
    }

    public void spawnEnemy(int width, int height, ID id) {
        if (id == ID.Air)
            enemies.add(new EnemyAirBasic(width, height, 1));
        else enemies.add(new EnemyGroundBasic(width,height));
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
        flag = new Flag(Engine.WIDTH/2,-Engine.HEIGHT/2, ID.Air);
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
            }
            else {
                Engine.transitionState();
//                spawn();
            }
        }
    }

    public void setLevelHeight(int h) {
        levelHeight = h;
    }

}
