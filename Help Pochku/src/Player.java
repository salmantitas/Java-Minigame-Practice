import java.awt.*;

public class Player {
    private int x, y, vel, movement;
    private int width, height, eye;
    private boolean moveLeft, moveRight;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        vel = 0;
        movement = Engine.intAtWidth640(3);
        width = Engine.intAtWidth640(32);
        height = width*3/2;
        eye = width/5;
        moveLeft = false;
        moveRight = false;
    }

    public void update() {
        move();
    }

    public void render(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x,y,width,height);

        renderEyes(g);
    }

    public void move() {
        x += vel;
        x = Engine.clamp(x, 0, Engine.percWidth(100) - width);

        if (moveLeft && !moveRight) {
            vel = -movement;
        }
        else if (moveRight && !moveLeft) {
            vel = movement;
        }
        else if (!moveLeft && !moveRight || (moveLeft && moveRight))
            vel = 0;
    }

    public void moveLeft(boolean b) {
        moveLeft = b;
    }

    public void moveRight(boolean b) {
        moveRight = b;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    private void renderEyes(Graphics g) {
        g.setColor(Color.WHITE);
        if (moveLeft && !moveRight) {
            renderEye(g, 'l');
        }
        else if (moveRight && !moveLeft) {
            renderEye(g, 'r');
        }
        if (!moveLeft && !moveRight || (moveLeft && moveRight)) {
            renderEye(g, 'l');
            renderEye(g, 'r');
        }
    }

    private void renderEye(Graphics g, Character c) {
        if (c == 'l')
            g.fillOval(x,y,eye,eye);
        if (c == 'r')
            g.fillOval(x + width - eye, y, eye, eye);
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }
}
