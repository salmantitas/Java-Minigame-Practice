import java.awt.*;
import java.util.HashMap;

public class Tile {

    public enum ID {
        GRASS, FLOOR
    }

    private int x, y, size;

    public ID getId() {
        return id;
    }

    private ID id = ID.GRASS;
    private Color color = Color.GREEN;

    public Tile(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;

    }

    public void update() {

    }

    public void changeTile(ID id) {
        this.id = id;
        this.color = Worldspace.colorMap.get(id);
        System.out.println("Tile changed");
    }

    public void render(Graphics g, int worldX, int worldY) {
        g.setColor(color);
        g.fillRect(worldX + x * size, worldY + y * size , size, size);
    }

}