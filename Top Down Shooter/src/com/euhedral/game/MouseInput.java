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
        gameController.mousePressed(getMxPressed(), getMyPressed());
    }

    public void updateReleased() {
        gameController.mouseReleased(getMxReleased(), getMyReleased());
    }

    public void updateMoved() {
        gameController.mouseMoved(getMx(), getMy());
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
        gameController.mouseReleased(getMxPressed(), getMyPressed());
    }

    public void checkButtonReleased() {
        gameController.mouseReleased(getMxReleased(), getMyReleased());
    }
}
