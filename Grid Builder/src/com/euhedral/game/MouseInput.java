package com.euhedral.game;

import com.euhedral.engine.EngineMouse;
import com.euhedral.game.GameController;

import java.awt.event.MouseAdapter;

public class MouseInput extends MouseAdapter {
    private GameController gameController;
    private EngineMouse mouse;

    public MouseInput(EngineMouse engineMouse, GameController gameController) {
        this.gameController = gameController;
        mouse = engineMouse;
    }

    public void updatePressed() {
        boolean isGridClicked = gameController.worldspace.checkOverlap(getMxPressed(), getMyPressed());

//        if (isGridClicked) {
////            System.out.println(getMx() + " " + getMy());
//            gameController.worldspace.displayOverlay(getMxPressed(), getMyPressed(), getMx(), getMy());
//        }

    }

    public void updateReleased() {
        boolean isGridClicked = (gameController.worldspace.checkOverlap(getMxReleased(), getMyReleased()) ||
                gameController.worldspace.checkOverlap(getMxPressed(), getMyPressed()));

        if (isGridClicked) {
            gameController.worldspace.gridClicked(getMxPressed(), getMyPressed(), getMxReleased(), getMyReleased());
        }
    }

    public void updateMoved() {
//        System.out.println("Mouse at (" + getMx() + ", " + getMy() + ")");

        boolean isOnGrid = gameController.worldspace.checkOverlap(getMx(), getMy());

        if (isOnGrid) {
            gameController.worldspace.onGrid(getMx(), getMy());
        }
    }

    public int getMx() {
        return mouse.getMx();
    }

    public int getMy() {
        return mouse.getMy();
    }

    public int getMxPressed() {
        return mouse.getMxPressed();
    }

    public int getMyPressed() {
        return mouse.getMyPressed();
    }

    public int getMxReleased() {
        return mouse.getMxReleased();
    }

    public int getMyReleased() {
        return mouse.getMyReleased();
    }

    public void checkButtonPressed() {
        gameController.checkButtonAction(getMxPressed(), getMyPressed());
    }

    public void checkButtonReleased() {
        gameController.checkButtonAction(getMxReleased(), getMyReleased());
    }
}
