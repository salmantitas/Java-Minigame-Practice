import com.euhedral.engine.BufferedImageLoader;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Random;

public class GameController {
    private UIHandler uiHandler;
    private Random r = new Random();

    // Manually set the Window information here
    private int gameWidth = 1280;
    private int gameHeight = Engine.HEIGHT;
    private String gameTitle = "Scrolling Shooter";
    private Color gameBackground = Color.BLUE;

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

    /******************
     * User variables *
     ******************/

    private Player player;
    private LinkedList<Enemy> enemies = new LinkedList<>();

    private int basicEnemyScore = 100;
//    private int basicEnemySpawnTime = 400;
//    private int basicEnemyIncreaseTime = 2000;

    int level = 1;
    private boolean levelSpawned = false;

    private BufferedImage level1 = null;
    private LevelGenerator levelGenerator;
    public static Camera cam;

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
        Engine.menuState();
        setupHighScore();
        BufferedImageLoader loader = new BufferedImageLoader();
        level1 = loader.loadImage("/level1.png");
        levelGenerator = new LevelGenerator(this);
        spawn();
    }

    public void update() {
        Engine.timer++;

        if (Engine.currentState == GameState.Quit)
            System.exit(1);

        if (Engine.currentState != GameState.Pause && Engine.currentState != GameState.Game)
            resetGame();

        if (Engine.currentState == GameState.Game) {

            boolean endGameCondition = health <= 0;

            if (endGameCondition) {
                Engine.gameOverState();
                enableHighScoreUpdate();
                resetGame();
            }

            /*************
             * Game Code *
             *************/

            if (!levelSpawned)
                spawn();

            player.update();

            for (Enemy enemy: enemies) {
                enemy.update();
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

            if (Engine.currentState == GameState.Game || Engine.currentState == GameState.Pause ) {

                Graphics2D g2d = (Graphics2D) g;

                // Camera start
                g2d.translate(cam.getX(), cam.getY());

                for (Enemy enemy: enemies) {
                    enemy.render(g);
                }

                player.render(g);

                // Camera end
                g2d.translate(-cam.getX(), -cam.getY());

                drawHealth(g);
                drawScore(g);
            }
        }

        /***************
         * Engine Code *
         ***************/

        uiHandler.render(g);
    }

    public void keyPressed(int key) {
        /*************
         * Game Code *
         *************/

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
        }

        if (key == KeyEvent.VK_CONTROL)
            player.switchBullet();

        if (key == (KeyEvent.VK_ESCAPE)) {
            if (Engine.currentState == GameState.Menu) {
                System.exit(1);
            }
            else {
                if (Engine.currentState == GameState.Game)
                    Engine.setState(GameState.Pause);
                else if (Engine.currentState == GameState.Pause)
                    Engine.setState(GameState.Game);
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

        Engine.timer = 0;

        /*************
         * Game Code *
         *************/

        updateHighScore();
        score = 0;
        health = 100;
        enemies.clear();
        levelSpawned = false;

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
        g.setFont(new Font("arial", 1, scoreSize));
        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, scoreX, scoreY);
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
            g.drawString("Score " + (i+1) + ": " + highScore.get(i), Engine.percWidth(40), Engine.percHeight( 10 * i + 10));
        }
    }

    /******************
     * User functions *
     ******************/

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

    public void shootPlayer() {
        player.canShoot(true);
    }

    public void stopShootPlayer() {
        player.canShoot(false);
    }

    public void checkCollision() {
        // Player vs enemy collision
        for (Enemy enemy: enemies) {
            if (enemy.getID() == ID.Air)
                if (enemy.getBounds().intersects(player.getBounds())) {
                    score += basicEnemyScore;
                    health -= 30;
                    destroy(enemy);
            }
        }

        // Player vs enemy bullet collision
        for (Enemy enemy: enemies) {
            Bullet b = enemy.checkCollision(player);
            if (b != null) {
                health -= 10;
                destroy(b);
            }
        }

        // EnemyAir vs player bullet collision
        for (Enemy enemy: enemies) {
            Bullet b = player.checkCollision(enemy);
            if (b != null) {
                score += basicEnemyScore;
                destroy(enemy);
                destroy(b);
            }
        }

        // enemy vs enemy collision
        for (int i = 0; i< enemies.size() - 1; i++) {
            Enemy enemy1 = enemies.get(i);
            Enemy enemy2 = enemies.get(i + 1);
            if (enemy1.getBounds().intersects(enemy2.getBounds())) {
                enemy2.setX(r.nextInt(Engine.WIDTH - 300) + 150);
            }
        }
    }

    private void destroy(Enemy enemy) {
//        Iterator<Enemy> it = enemies.iterator();
//        while (it.hasNext()) {
//            Enemy e = it.next();
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
        levelSpawned = !levelSpawned;

        if (level == 1)
            levelGenerator.loadImageLevel(level1);

//        int variance = 0;
//
//        // Randomize spawn after score becomes greater than a value
//        if (score > basicEnemyIncreaseTime) {
//            variance = r.nextInt(250);
//        }
//        System.out.println(variance);
//        if (Engine.timer % (basicEnemySpawnTime - variance)== 0) {
//            enemies.add(new EnemyAirBasic(r.nextInt(Engine.WIDTH - 300) + 150, -300));
//        }
//        if (Engine.timer % (basicEnemySpawnTime - variance)*2 ==0)
//            enemies.add(new EnemyGroundBasic(r.nextInt(Engine.WIDTH-300)+ 150, - 300));
    }

    public static Camera getCamera() {
        return cam;
    }

    public void spawnPlayer(int width, int height, int levelHeight) {
        player = new Player(width,height);
        // sets the camera's width to center the player horizontally, essentially to 0, and
        // adjust the height so that player is at the bottom of the screen
        cam = new Camera(player.getX()-gameWidth/2,-player.getY() + gameHeight - 256);
        cam.setMarker(player.getY());
    }

    public void spawnCamera(int width, int height) {
        cam = new Camera(width,-750); // -700 = 2 fps;
    }

    public void spawnEnemy(int width, int height, ID id) {
        if (id == ID.Air)
            enemies.add(new EnemyAirBasic(width, height));
        else enemies.add(new EnemyGroundBasic(width,height));
    }


}
