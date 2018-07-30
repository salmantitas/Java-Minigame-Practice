import java.awt.*;
import java.util.Random;

public class Spawner {

    GameController gameController;
    int basicInterval = 200;
    int intermediateInterval = basicInterval * 2;
    int difficultInterval = basicInterval * 6;

    public Spawner(GameController gameController) {
        this.gameController = gameController;
    }

    public void update() {

        Game.spawnTimer++;
        if (Game.spawnTimer == 50)
            spawnEnemy(new EnemyBasic(Game.randRange(Game.minX, Game.maxX), Game.randRange(Game.minY, Game.maxY)));
        if (Game.spawnTimer % basicInterval == 0) {
            spawnEnemy(new EnemyBasic(Game.randRange(Game.minX, Game.maxX), Game.randRange(Game.minY, Game.maxY)));
        }
        if (Game.spawnTimer % intermediateInterval == 0) {
            spawnEnemy(new EnemyIntermediate(Game.randRange(Game.minX, Game.maxX), Game.randRange(Game.minY, Game.maxY)));
        }
        if (Game.spawnTimer % difficultInterval == 0)
            spawnEnemy(new EnemyDifficult(Game.randRange(Game.minX, Game.maxX), Game.randRange(Game.minY, Game.maxY)));
//        basicInterval++;
//        intermediateInterval++;
    }

    public void render(Graphics g) {
    }

    public void reset() {
        gameController.clearEnemies();
    }

    private void spawnEnemy(Enemy enemy) {
//        if (spawn)
            gameController.addEnemy(enemy);
//        spawn = false;
    }


}
