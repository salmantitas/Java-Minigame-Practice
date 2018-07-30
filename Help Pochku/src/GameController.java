import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

public class GameController {
    private UIHandler uiHandler;
    private Random r = new Random();

    // Manually set the Window information here
    private int gameWidth = 640 * 2;
    private int gameHeight;
    private String gameTitle = "Help Pochku";
    private Color gameBackground = Color.BLACK;

    // Common game variables
    private int score = 0;
    private int lives = 3;
    private int health = 100;
    private LinkedList<Integer> highScore = new LinkedList<>();
    private int highScoreNumbers = 5;
    private boolean updateHighScore = false;

    /******************
     * User variables *
     ******************/

    private Player player;
    private LinkedList<Projectile> projectiles = new LinkedList<>();
    private final int rockSpawnDef = 120;
    private int rockSpawn = rockSpawnDef;
    private int rockSpawnIncreaseDef = 1000;
    private int rockSpawnIncrease = rockSpawnIncreaseDef;
    private int decreaseRate = 10;
    private int increaseRate = 100;
    private int lifeSpawn = 1000;
    private int minSpawnTime = 40;

    enum ID {
        Projectile, Life
    }

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

        player = new Player(gameWidth/2, Engine.percHeight(80));
    }

    public void update() {
        Engine.timer++;

        if (Engine.currentState == GameState.Quit)
            System.exit(1);

        if (Engine.currentState != GameState.Pause && Engine.currentState != GameState.Game)
            resetGame();

        if (Engine.currentState == GameState.Game) {

            /*************
             * Game Code *
             *************/

            boolean endGameCondition = lives <= 0;

            if (endGameCondition) {
                Engine.currentState = GameState.GameOver;
                enableHighScoreUpdate();
            }

            player.update();
            score++;

            for (Projectile projectile: projectiles) {
                projectile.update();
                checkCollision(projectile);
                if (projectile.x < -projectile.width || projectile.x > gameWidth)
                    removeProjectile(projectile);
            }

            spawnRock();
        }
    }

    public void render(Graphics g) {
        uiHandler.render(g);

        if (Engine.currentState == GameState.Pause) {
            drawPause(g);
        }

        if (Engine.currentState == GameState.GameOver) {
            drawGameOverScreen(g);
        }

        if (Engine.currentState == GameState.Highscore) {
            drawHighScore(g);
        }

        if (Engine.currentState == GameState.Game || Engine.currentState == GameState.Pause || Engine.currentState == GameState.GameOver) {

            /*************
             * Game Code *
             *************/

            if (Engine.currentState == GameState.Game || Engine.currentState == GameState.Pause) {
                drawLives(g);
                drawScore(g);
                player.render(g);
            }

            for (Projectile projectile: projectiles) {
                projectile.render(g);
            }
        }

    }

    private void setupHighScore() {
        for (int i = 0; i < highScoreNumbers; i++) {
            highScore.add(0);
        }
    }

    public void resetGame() {

        /*************
         * Game Code *
         *************/

        clearProjectiles();
        resetPlayer();
        Engine.timer = 0;
        rockSpawn = rockSpawnDef;
        rockSpawnIncrease = rockSpawnIncreaseDef;
        updateHighScore();
        score = 0;
        lives = 3;
    }

    public void checkOverlap(int mx, int my) {
        uiHandler.checkOverlap(mx, my);
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
        g.setFont(new Font("arial", 1, Engine.percWidth(4)));
        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, Engine.percWidth(40), Engine.percHeight(10));
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

    public void drawPause(Graphics g) {
        g.setFont(new Font("arial", 1, 200));
        g.setColor(Color.WHITE);
        g.drawString("PAUSE", Engine.percWidth(20), Engine.HEIGHT/2);
    }

    public void drawHighScore(Graphics g) {
        g.setFont(new Font("arial", 1, 20));
        g.setColor(Color.WHITE);
        for (int i = 0; i < highScoreNumbers; i++) {
            g.drawString("Score " + (i+1) + ": " + highScore.get(i), Engine.percWidth(75), Engine.percHeight( 10 * i + 10));
        }
    }

    public void drawGameOverScreen(Graphics g) {
        g.setFont(new Font("arial", 1, 200));
        g.setColor(Color.WHITE);
        g.drawString("GAME OVER", Engine.percWidth(2), Engine.HEIGHT/2);
    }

    /******************
     * User functions *
     ******************/
    public void movePlayer(char c) {
        if (c == 'l')
            player.moveLeft(true);
        else if (c == 'r')
            player.moveRight(true);
    }

    public void stopMovePlayer(char c) {
        if (c == 'l')
            player.moveLeft(false);
        else if (c == 'r')
            player.moveRight(false);
    }

    private void spawnRock() {
        if (Engine.timer < 250) {}
        else if (Engine.timer % rockSpawn == 0)
            projectiles.add(new Rock(r.nextInt(gameWidth), 0, player.getX(), player.getY(), player.getWidth()));
        if (Engine.timer % rockSpawnIncrease == 0) {
            if (rockSpawn > minSpawnTime) {
                rockSpawn -= decreaseRate;
                rockSpawnIncreaseDef += increaseRate;
                System.out.println(rockSpawn);
            }
        }
        if (Engine.timer > 2000) {
            if (Engine.timer % lifeSpawn == 0)
                projectiles.add(new Life(r.nextInt(gameWidth)));
        }
    }

    private void clearProjectiles() {
        projectiles.clear();
    }

    private void resetPlayer() {
        player.setX(gameWidth/2);
    }

    private void checkCollision(Projectile projectile) {
        if (player.getBounds().intersects(projectile.getBounds())) {
            GameController.ID tempID = projectile.getId();
            removeProjectile(projectile);
            if (tempID == ID.Life) {
                lives++;
                score += 200;
            }
            if (tempID == ID.Projectile) {
                lives--;
                score += r.nextInt(1000);
            }
        }
    }

    private void removeProjectile(Projectile projectile) {
        projectile.x = -200;
        projectile.y = - 200;
        projectile.velX = 0;
        projectile.velY = 0;

        // stub ^^^
        //projectiles.remove(projectile);
    }
}
