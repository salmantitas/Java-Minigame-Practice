import java.awt.*;

public class EnemyBasic extends Enemy {

    public EnemyBasic(int x, int y) {
        super(x,y);
        size = 16 * Game.SCREEN_MULT;
        health = 1;
        color = Color.RED;
        scoreFactor = 100;
        fadeFactor = 20;
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, size, size);
    }

    public void update() {
        if (Game.spawnTimer % 100 == 0)
            fadeFactor--;
        if (fadeFactor <= 0)
            fade();

    }

    public void render(Graphics g) {
        g.setColor(color);
        g.fillRect((int) x, (int) y, size, size);
    }

    public boolean mouseOverlap(int mx, int my) {
        return (mx >= x && mx <= x + size && my >= y && my <= y + size);
    }

    public void damage() {
        health--;
        if (health <= 0)
            die();
    }

//    public void die() {
//        dead = true;
//        Game.killed++;
//        Game.score += Game.MAX_SCORE / scoreFactor;
//        System.out.println("Enemy is dead");
//    }

    public void fade() {
        dead = true;
        System.out.println("Enemy faded");
    }
}
