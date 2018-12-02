import java.awt.*;
import java.util.HashMap;

public class Worldspace extends Grid {

    public Tile[][] tiles;

    public static HashMap<Tile.ID, Color> colorMap;

    public Worldspace(int x, int y, int row, int column, int size) {
        super(x, y, row, column, size);

        tiles = new Tile[column][row];

        colorMap =  new HashMap<>();
        colorMap.put(Tile.ID.GRASS, Color.GREEN);
        colorMap.put(Tile.ID.FLOOR, Color.darkGray);

        for (int i = 0; i < column; i++) {
            for (int j = 0; j < row; j++) {
                tiles[i][j] = new Tile(i,j,size);
            }
        }
    }

    public void update() {
        for (Tile[] tileArray: tiles) {
            for (Tile tile: tileArray) {

            }
        }
    }

    public void render(Graphics g) {
        super.render(g);

        for (Tile[] tileArray: tiles) {
            for (Tile tile: tileArray) {
                tile.render(g, x, y);
            }
        }
    }

    @Override
    public void gridClicked(int mx, int my) {
        super.gridClicked(mx, my);
        Tile tile = getTileAt(mx,my);
        if (tile.getId() == Tile.ID.FLOOR)
            tile.changeTile(Tile.ID.GRASS);
        else tile.changeTile(Tile.ID.FLOOR);
    }

    public Tile getTileAt(int mx, int my) {
        int tileX = mouseToGrid(mx, x);
        int tileY = mouseToGrid(my, y);
        return tiles[tileX][tileY];
    }


}
