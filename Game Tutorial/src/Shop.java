import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Shop extends MouseAdapter{

    Handler handler;
    HUD hud;
    private int B1 = 1000;
    private int B2 = 1000;
    private int B3 = 1000;

    public Shop(Handler handler, HUD hud) {
        this.handler = handler;
        this.hud = hud;
    }

    public void render(Graphics g) {
        g.setColor(Color.white);
        g.setFont(new Font("arial", 0, 48));
        g.drawString("Shop", Game.WIDTH/2 - 100, 50);

        g.setFont(new Font("arial", 0,12));

        // Box 1
        g.drawString("Upgrade Health", 110, 120);
        g.drawString("Cost: " + B1, 110, 140 );
        g.drawRect(100, 100, 100, 80);

        // Box 2
        g.drawString("Upgrade Speed", 260, 120);
        g.drawString("Cost: " + B2, 260, 140 );
        g.drawRect(250, 100, 100, 80);

        // Box 3
        g.drawString("Refill Health", 410, 120);
        g.drawString("Cost: " + B3, 410, 140 );
        g.drawRect(400, 100, 100, 80);

        g.drawString("SCORE: "+ hud.getScore(), Game.WIDTH/2-50, 300);
        g.drawString("Press space to go back", Game.WIDTH/2-50, 330);
    }

    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        // Box 1
        if (mouseOver(mx,my,100,100, 100, 80)) {
            if (hud.getScore() >= B1) {
                hud.setScore(hud.getScore() - B1);
                B1 += 1000;
                hud.bounds += 20;
                hud.HEALTH = (100 + (hud.bounds/2));
            }
        }

        // Box 2
        if (mouseOver(mx,my,250,100, 100, 80)) {
            if (hud.getScore() >= B2) {
                hud.setScore(hud.getScore() - B2);
                B2 += 1000;
                handler.speed++;
            }
        }

        // Box 3
        if (mouseOver(mx,my,400,100, 100, 80)) {
            if (hud.getScore() >= B3) {
                hud.setScore(hud.getScore() - B3);
                hud.HEALTH = (100 + hud.bounds/2);
            }
        }
    }

    private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
        if (mx > x && mx < x + width)
            if (my > y && my < y + height)
                return true;
            else return false;
        else return false;
    }
}
