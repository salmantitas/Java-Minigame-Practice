package com.euhedral.game;

import com.euhedral.engine.Pair;

import java.awt.*;

public class Wall extends Buildable {
//    private int x, y;
//    private Color color = Color.BLUE;
//    private com.euhedral.game.Tile.ID suitable = com.euhedral.game.Tile.ID.FLOOR;

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

        g.setColor(Color.RED);
        g.drawRect(worldX + x * size,  worldY + y * size, cellOccupancy.x*size, cellOccupancy.y*size);

    }
}
