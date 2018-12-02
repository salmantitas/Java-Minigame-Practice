import java.awt.*;
import java.util.HashMap;

public class Worldspace extends Grid {

    public Tile[][] tiles;

    public static HashMap<Tile.ID, Color> colorMap;

    private Tile.ID buildState = Tile.ID.GRASS;

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

    //@Override
    public void gridClicked(int mxPressed, int myPressed, int mxReleased, int myReleased) {
        int startCellX = mouseToGrid(mxPressed, x);
        int startCellY = mouseToGrid(myPressed, y);
        int endCellX = mouseToGrid(mxReleased, x);
        int endCellY = mouseToGrid(myReleased, y);

        // Bind values inside the grid
        startCellX = Engine.clamp(startCellX, 0, column - 1);
        startCellY = Engine.clamp(startCellY, 0, row - 1);
        endCellX = Engine.clamp(endCellX, 0, column - 1);
        endCellY = Engine.clamp(endCellY, 0, row - 1);

        // Switch cells if in reverse order (ie, startcell larger than endcell)
        if (startCellX > endCellX)
        {
            int temp = startCellX;
            startCellX = endCellX;
            endCellX = temp;
        }
        if (startCellY > endCellY)
        {
            int temp = startCellY;
            startCellY = endCellY;
            endCellY = temp;
        }

        for (int i = startCellX; i <= endCellX; i++) {
            for (int j = startCellY; j <= endCellY; j++) {
                changeTile(tiles[i][j]);
            }
        }
    }

    public Tile getTileAt(int mx, int my) {
        int tileX = mouseToGrid(mx, x);
        int tileY = mouseToGrid(my, y);
        System.out.println("Grid [" + tileX + ", " + tileY + "]");
        return tiles[tileX][tileY];
    }

    private void changeTile(Tile tile) {
//        if (tile.getId() == Tile.ID.FLOOR)
//            tile.changeTile(Tile.ID.GRASS);
//        else tile.changeTile(Tile.ID.FLOOR);
        if (tile.getId() != buildState)
            tile.changeTile(buildState);
    }

    public void buildStateGrass() {
        setBuildState(Tile.ID.GRASS);
    }

    public void buildStateFloor() {
        setBuildState(Tile.ID.FLOOR);
    }

    private void setBuildState(Tile.ID buildState) {
        this.buildState = buildState;
    }
}
