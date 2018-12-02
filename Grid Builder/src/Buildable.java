import java.awt.*;

public abstract class Buildable {

    protected int x, y, size;
    protected Color color;
    protected Tile.ID suitable;
    protected Pair cellOccupancy;
    protected ID id;

    public enum ID {
        DEMOLISH, WALL
    }

    public Buildable(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
    }

    public void update() {

    }

    public void render(Graphics g, int worldX, int worldY) {

    }

    public Pair getCellOccupancy() {
        return cellOccupancy;
    }
}
