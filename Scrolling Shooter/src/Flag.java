import java.awt.*;
import java.util.LinkedList;

public class Flag {

    protected int x, y;
    protected ID id;
    protected float velX, velY;
    protected int width, height;
    protected Color color;
    protected Camera cam;

    public Flag(int x, int y, ID id) {
        this.x = x;
        this.y = y;
        width = Engine.intAtWidth640(32);
        height = width;
        velY = Engine.floatAtWidth640(2)/4;
        color = Color.YELLOW;
        this.id = id;
        cam = GameController.getCamera();
    }

    public void update() {
        move();
        System.out.println("Flag at (" + x + ", " + y + ")");
    }

    public void render(Graphics g) {
        g.setColor(color);
        g.drawRect(x,y,width,height);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public int getY() {
        return y;
    }

    public void setVelX(int velX) {
        this.velX = velX;
    }

    public void setVelY(int velY) {
        this.velY =  velY;
    }

    public ID getID() {
        return id;
    }

    // Private Methods

    private void move() {
        y += velY;
    }

    public void reset() {
        x = Engine.WIDTH/2;
        y = -Engine.HEIGHT/2;
    }

}
