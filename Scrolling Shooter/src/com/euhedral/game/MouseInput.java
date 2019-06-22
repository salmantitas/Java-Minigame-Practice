package com.euhedral.game;

import com.euhedral.engine.EngineMouse;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter {
    private GameController gameController;
    private EngineMouse mouse;

    public MouseInput(EngineMouse engineMouse, GameController gameController) {
        this.gameController = gameController;
        mouse = engineMouse;
    }

    public void updatePressed() {
        if (mouse.buttonIsPressed(MouseEvent.BUTTON1)) {
            gameController.shootPlayer();
        }
//        gameController.giveDestination(getMxPressed(), getMyPressed());
    }

    public void updateReleased() {
        if (mouse.buttonIsReleased(MouseEvent.BUTTON1))
            gameController.stopShootPlayer();
        gameController.checkButtonAction(getMxReleased(), getMyReleased());
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
}
