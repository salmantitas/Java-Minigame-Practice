import java.awt.*;

public class Wall extends Buildable {
//    private int x, y;
//    private Color color = Color.BLUE;
//    private Tile.ID suitable = Tile.ID.FLOOR;

    public Wall(int x, int y, int size) {
        super(x,y,size);
        id = ID.WALL;
        color = Color.BLUE;
        suitable = Tile.ID.FLOOR;
        cellOccupancy = new Pair(1,1);

        System.out.println("New wall created");
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void render(Graphics g, int worldX, int worldY) {
        g.setColor(color);
        for (int i = 0; i < cellOccupancy.x; i++) {
            for (int j = 0; j < cellOccupancy.y; j++) {
                g.fillRect(size * i + worldX + x * size, size * j + worldY + y * size, size, size);
            }
        }
    }
}
