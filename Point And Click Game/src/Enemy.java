import java.awt.*;

public abstract class Enemy {
    protected float x, y, velX, velY;
    protected int size, health;
    protected Color color;
    protected boolean dead = false;
    protected int scoreFactor = 1;
    protected int fadeFactor = 10;

    public Enemy(float x, float y) {
        this.x = x;
        this.y = y;
        System.out.println("An enemy has been spawned");
    }

    public abstract Rectangle getBounds();

    public abstract void update();

    public abstract void render(Graphics g);

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setVelX(float velX) {
        this.velX = velX;
    }

    public void setVelY(float velY) {
        this.velY = velY;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getVelX() {
        return velX;
    }

    public float getVelY() {
        return velY;
    }

    public boolean isDead() {
        return dead;
    }

    public abstract boolean mouseOverlap(int mx, int my);

    public abstract void damage();

    public void die() {
        dead = true;
        Game.killed++;
        Game.score += Game.MAX_SCORE / scoreFactor;
        System.out.println("Enemy is dead");
    }

    public abstract void fade();
}
