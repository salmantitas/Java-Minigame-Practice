import java.awt.*;

public class EnemyDifficult extends Enemy {
    private boolean teleport = true;
    private boolean escape = true;

    public EnemyDifficult(int x, int y) {
        super(x,y);
        size = 16 * Game.SCREEN_MULT;
        health = 1;
        color = Color.GREEN;
        scoreFactor = 1;
        fadeFactor = 10;
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, size, size);
    }

    public void update() {
        x += velX;
        y += velY;

        if (Game.spawnTimer % 100 == 0)
            fadeFactor--;
        if (fadeFactor == 5 && teleport) {
            teleport = false;
            teleport();
        }
        if (fadeFactor == 9 && escape) {
            escape = false;
            escape();
        }
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
//        Game.score += Game.MAX_SCORE / scoreFactor;
//        System.out.println("Enemy is dead");
//    }

    public void fade() {
        dead = true;
        System.out.println("Enemy faded");
    }

    public void teleport() {
        setX((float) Game.randRange(Game.minX, Game.maxX));
        setY((float) Game.randRange(Game.minY, Game.maxY));
        System.out.println("Enemy teleported");
    }

    public void escape() {
        setVelX((float) Game.randRange(-3, 3));
        setVelY((float) Game.randRange(-3, 3));
    }
}
