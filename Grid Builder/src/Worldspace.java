import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Worldspace extends Grid {

    public Tile[][] tiles;
    public ArrayList<Buildable> buildables;
//    public ArrayList<Placeable> placeables;

    public HashMap<Tile.ID, Color> colorMap;
    public HashMap<Buildable.ID, Buildable> buildableMap;

    private Tile.ID flooringState = Tile.ID.GRASS;
    private Buildable.ID buildingState = Buildable.ID.WALL;
    private boolean flooring = true;
    private boolean building = false, placing = false;

    // Instances of Buildables

    public Worldspace(int x, int y, int row, int column, int size) {
        super(x, y, row, column, size);

        tiles = new Tile[column][row];
        buildables = new ArrayList<>();

        colorMap =  new HashMap<>();
        colorMap.put(Tile.ID.GRASS, Color.GREEN);
        colorMap.put(Tile.ID.FLOOR, Color.darkGray);

        buildableMap = new HashMap<>();
        buildableMap.put(Buildable.ID.WALL, new Wall(0,0,size));

        for (int i = 0; i < column; i++) {
            for (int j = 0; j < row; j++) {
                tiles[i][j] = new Tile(this, i,j,size);
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

        for (Buildable buildable: buildables)
        {
            buildable.render(g,x,y);
        }
    }

    public void onGrid(int mx, int my) {
        int startCellX = mouseToGrid(mx, x);
        int startCellY = mouseToGrid(my, y);

        int i = startCellX;
        int j = startCellY;

        for (Tile[] tileArray: tiles) {
            for (Tile tile: tileArray) {
                if (tile.getY() != j || tile.getX() != i)
                    tile.setOverlay(false);
            }
        }

        Tile tile = tiles[i][j];
        tile.setOverlay(true);
    }

//    public void displayOverlay(int mxPressed, int myPressed, int mx, int my) {
//        int startCellX = mouseToGrid(mxPressed, x);
//        int startCellY = mouseToGrid(myPressed, y);
//        int endCellX = mouseToGrid(mx, x);
//        int endCellY = mouseToGrid(my, y);
//
//        // Bind values inside the grid
//        startCellX = Engine.clamp(startCellX, 0, column - 1);
//        startCellY = Engine.clamp(startCellY, 0, row - 1);
//        endCellX = Engine.clamp(endCellX, 0, column - 1);
//        endCellY = Engine.clamp(endCellY, 0, row - 1);
//
//
//        // Switch cells if in reverse order (ie, startcell larger than endcell)
//        if (startCellX > endCellX)
//        {
//            int temp = startCellX;
//            startCellX = endCellX;
//            endCellX = temp;
//        }
//        if (startCellY > endCellY)
//        {
//            int temp = startCellY;
//            startCellY = endCellY;
//            endCellY = temp;
//        }
//
//        for (int i = startCellX; i <= endCellX; i++) {
//            for (int j = startCellY; j <= endCellY; j++) {
//                tiles[i][j].setOverlay(true);
//            }
//        }
//    }

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

        if (flooring) {
            for (int i = startCellX; i <= endCellX; i++) {
                for (int j = startCellY; j <= endCellY; j++) {
                    Tile tile = tiles[i][j];
                    if (!tile.isOccupied())
                        changeTile(tiles[i][j]);
                }
            }
        }

        if (building) {
//            if (buildingState == Buildable.ID.WALL) {

            Buildable buildable = buildableMap.get(buildingState);

            boolean tileOccupied = false;
            boolean tileSuitability = true;

            for (int i = startCellX; i < startCellX + buildable.cellOccupancy.x; i++) {
                for (int j = startCellY; j < startCellY + buildable.cellOccupancy.y; j++) {
                    tileOccupied = tileOccupied || tiles[i][j].isOccupied();
                    tileSuitability = tileSuitability && tiles[i][j].getId() == buildable.suitable;
                }
            }

            if (!tileOccupied && tileSuitability) {
                buildables.add(new Wall(startCellX, startCellY, size));
                for (int i = startCellX; i < startCellX + buildable.cellOccupancy.x; i++) {
                    for (int j = startCellY; j < startCellY + buildable.cellOccupancy.y; j++) {
                        tiles[i][j].setOccupied(true);
                    }
                }
            } else if (tileOccupied) {
                System.out.println("Tile is occupied");
            } else {
                System.out.println("Tile is not suitable");
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
        if (tile.getId() != flooringState)
            tile.changeTile(flooringState);
    }

    public void buildStateGrass() {
        setFlooringState(Tile.ID.GRASS);
    }

    public void buildStateFloor() {
        setFlooringState(Tile.ID.FLOOR);
    }

    public void demolish() {
        setBuildingState(Buildable.ID.DEMOLISH);
    }

    public void buildWall() {
        setBuildingState(Buildable.ID.WALL);
    }

    private void setFlooringState(Tile.ID flooringState) {
        flooring = true;
        building = false;
        placing = false;
        this.flooringState = flooringState;
    }

    private void setBuildingState(Buildable.ID buildingState) {
        flooring = false;
        building = true;
        placing = false;
        this.buildingState = buildingState;
    }

//    private void setPlacingState(Placeable.ID placingState) {
//        flooring = false;
//        building = false;
//        placing = true;
//        this.placingState = placingState;
//    }
}
