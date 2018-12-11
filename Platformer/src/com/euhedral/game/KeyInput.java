package com.euhedral.game;

import com.euhedral.engine.EngineKeyboard;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter{

    private EngineKeyboard keyboard;
    private GameController gameController;

    public KeyInput(EngineKeyboard engineKeyboard, GameController gameController) {
        this.gameController = gameController;
        keyboard = engineKeyboard;
    }

    public void updatePressed() {
        /*************
         * Game Code *
         *************/

        Player player = gameController.player;

        if (keyIsPressed(KeyEvent.VK_ESCAPE))
            System.exit(1);

        if (keyIsPressed(KeyEvent.VK_D))
            player.setVelX(player.getSpeed());

        if (keyIsPressed(KeyEvent.VK_A))
            player.setVelX(-player.getSpeed());

        if (keyIsPressed(KeyEvent.VK_W) && !player.isJumping()) {
                player.setVelY(- player.getJumpSpeed());
                player.setJumping(true);
            }

    }

    public void updateReleased() {
        /*************
         * Game Code *
         *************/

        if (keyIsReleased(KeyEvent.VK_D))
            gameController.player.setVelX(0);

        if (keyIsReleased(KeyEvent.VK_A))
            gameController.player.setVelX(0);

//        if (keyIsReleased(KeyEvent.VK_W))
//            gameController.player.setJumping(false);

    }

    private boolean keyIsPressed(int key) {
        return keyboard.keyIsPressed(key);
    }

    private boolean keyIsReleased(int key) {
        return keyboard.keyIsReleased(key);
    }

    /******************
     * User functions *
     ******************/
}
