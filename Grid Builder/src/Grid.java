import java.awt.*;

public class Grid {

    int x, y, row, column, size;
    Color padding, tile;

    public Grid(int x, int y, int row, int column, int size) {
        this.x = y;
        this.y = y;
        this.row = row;
        this.column = column;
        this.size = size;
        padding = Color.DARK_GRAY;
        tile = Color.GRAY;
        System.out.println("A grid has been created at " + x + ", " + y + " with " + row + " rows and " + column + " columns.");
    }

    public void update() {

    }

    public void render(Graphics g) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                g.setColor(tile);
                g.fillRect(i*size, j*size, size, size);

                g.setColor(padding);
                g.drawRect(i*size, j*size, size, size);
            }
        }
    }

}
