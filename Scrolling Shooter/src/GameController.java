import java.awt.*;
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
    private LinkedList<AirEnemy> enemies = new LinkedList<>();

    private int basicEnemyScore = 100;
    private int basicEnemySpawnTime = 400;
    private int basicEnemyIncreaseTime = 2000;

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

        Engine.menuState();
        setupHighScore();

        player = new Player(gameWidth/2, gameHeight/2);


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

            player.update();

            for (AirEnemy enemy: enemies) {
                enemy.update();
            }

            checkCollision();
            spawn();


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
                player.render(g);

                for (AirEnemy enemy: enemies) {
                    enemy.render(g);
                }

                drawHealth(g);
                drawScore(g);
            }
        }

        /***************
         * Engine Code *
         ***************/

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

        updateHighScore();
        score = 0;
        health = 100;
        enemies.clear();

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
        for (AirEnemy enemy: enemies) {
            if (enemy.getBounds().intersects(player.getBounds())) {
                score += basicEnemyScore;
                health -= 30;
                destroy(enemy);
            }
        }

        // Player vs enemy bullet collision
        for (AirEnemy enemy: enemies) {
            Bullet b = enemy.checkCollision(player);
            if (b != null) {
                health -= 10;
                destroy(b);
            }
        }

        // AirEnemy vs player bullet collision
        for (AirEnemy enemy: enemies) {
            Bullet b = player.checkCollision(enemy);
            if (b != null) {
                score += basicEnemyScore;
                destroy(enemy);
                destroy(b);
            }
        }

        // AirEnemy vs enemy collision
        for (int i = 0; i< enemies.size() - 1; i++) {
            AirEnemy enemy1 = enemies.get(i);
            AirEnemy enemy2 = enemies.get(i + 1);
            if (enemy1.getBounds().intersects(enemy2.getBounds())) {
                enemy2.setX(r.nextInt(Engine.WIDTH - 300) + 150);
            }
        }
    }

    private void destroy(AirEnemy enemy) {
        enemy.setX(+ 300);
        enemy.setY(Engine.HEIGHT + 300);
        enemy.setVelX(0);
        enemy.setVelY(0);
    }

    private void destroy(Bullet bullet) {
        bullet.setX(+ 100);
        bullet.setY(Engine.HEIGHT  + 100);
        bullet.setVel(0);
    }

    private void spawn() {
        int variance = 0;

        // Randomize spawn after score becomes greater than a value
        if (score > basicEnemyIncreaseTime) {
            variance = r.nextInt(250);
        }
        System.out.println(variance);
        if (Engine.timer % (basicEnemySpawnTime - variance)== 0) {
            enemies.add(new BasicAirEnemy(r.nextInt(Engine.WIDTH - 300) + 150, -300));
        }
    }


}
