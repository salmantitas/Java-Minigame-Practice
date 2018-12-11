package com.euhedral.engine;

import java.awt.*;

public class Grid {

    protected int x, y, row, column, size;
    protected Color padding, tile;

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
                g.fillRect(x + i * size, y + j * size, size, size);

                g.setColor(padding);
                g.drawRect(x + i * size, y + j * size, size, size);
            }
        }
    }

    public boolean checkOverlap(int mx, int my) {
        boolean overlap = x < mx && mx < x + (column)*size &&
                y < my && my < y + (row)*size;
//        if (overlap)
//            System.out.println("The grid has been clicked");
        return overlap;
    }

    //Assuming this will not be called if the grid has not been clicked.
    public void gridClicked(int mx, int my) {
        int gridX = mouseToGrid(mx, x);
        int gridY = mouseToGrid(my, y);
        System.out.println("com.euhedral.engine.Grid [" + gridX + ", " + gridY + "]");
    }

    protected int mouseToGrid(int mouse, int pos) {
        int result = (mouse - pos)/size ;

        return result;
    }



}
