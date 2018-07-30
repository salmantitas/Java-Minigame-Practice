import javafx.scene.control.RadioMenuItem;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Player extends GameObject {

    Random r = new Random();
    Handler handler;

    private BufferedImage player_image;

    public Player(int x, int y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;

        SpriteSheet ss = new SpriteSheet(Game.sprite_sheet);
        player_image = ss.grabImage(1,1,32,32);
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y,32,32);
    }

    public void tick() {
        x += velX;
        y += velY;

        x = Game.clamp(x, 0, Game.WIDTH - Game.OFFSET);
        y = Game.clamp(y, 0, Game.HEIGHT - 2 * Game.OFFSET);
//        handler.addObject(new Trail(x,y, ID.Trail, Color.white, 32,32, 0.02f, handler));

        collision();
    }

    public void collision() {
        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            if (tempObject.getID() == ID.BasicEnemy || tempObject.getID() == ID.FastEnemy || tempObject.getID() == ID.SmartEnemy)
                if (getBounds().intersects(tempObject.getBounds())) {
                    // collision  code
                    HUD.HEALTH -= 2;
                }
        }
    }

    public void render(Graphics g) {
        g.drawImage(player_image, (int) x, (int) y, null);

//        g.setColor(Color.WHITE);
//        g.fillRect((int) x, (int) y,32,32);
    }
}
