import java.awt.*;
import java.util.LinkedList;

public class GameController {
    private LinkedList<Enemy> enemies = new LinkedList();

    public GameController() {
        System.out.println("Game Controller is created");
    }

    public void update() {
        for (Enemy enemy: enemies) {
            if (enemy.isDead()) {
                enemies.remove(enemy);
//                Game.killed++;
                break;
            }
            enemy.update();
        }
    }

    public void render(Graphics g) {
        for (Enemy enemy: enemies) {
            enemy.render(g);
        }
    }

    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }

    public void clearEnemies() {
        if (enemies.size() > 0)
            enemies.clear();
    }

    public void checkOverlap(int mx, int my) {
        boolean overlap = false;

        for (Enemy enemy: enemies) {
            overlap = enemy.mouseOverlap(mx, my);

            if (overlap) {
                enemy.damage();
                break;
            }
        }
    }
}
