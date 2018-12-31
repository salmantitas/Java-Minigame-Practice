package com.euhedral.game;

import com.euhedral.engine.EngineKeyboard;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class KeyInput extends KeyAdapter{

    private EngineKeyboard keyboard;
    private GameController gameController;
    private ArrayList<Integer> legalKeysPress;
    private ArrayList<Integer> legalKeysRelease;

    public KeyInput(EngineKeyboard engineKeyboard, GameController gameController) {
        this.gameController = gameController;
        keyboard = engineKeyboard;

        /*************
         * Game Code *
         *************/

        legalKeysPress = new ArrayList<>();
        legalKeysPress.add(KeyEvent.VK_D);
        legalKeysPress.add(KeyEvent.VK_A);
        legalKeysPress.add(KeyEvent.VK_W);

        legalKeysRelease = new ArrayList<>();
        legalKeysRelease.add(KeyEvent.VK_D);
        legalKeysRelease.add(KeyEvent.VK_A);
    }

    public void updatePressed() {
        /***************
         * Engine Code *
         ***************/

        for (int lk: legalKeysPress)
            if (keyIsPressed(lk))
                notifyKeyPress(lk);

        /*************
         * Game Code *
         *************/

        if (keyIsPressed(KeyEvent.VK_ESCAPE))
            System.exit(1);


    }

    public void updateReleased() {
        /***************
         * Engine Code *
         ***************/

        for (int lk: legalKeysRelease)
            if (keyIsReleased(lk))
                notifyKeyRelease(lk);

        /*************
         * Game Code *
         *************/

    }

    private boolean keyIsPressed(int key) {
        return keyboard.keyIsPressed(key);
    }

    private boolean keyIsReleased(int key) {
        return keyboard.keyIsReleased(key);
    }

    private void notifyKeyPress(int key) {
        gameController.keyPressed(key);
    }

    private void notifyKeyRelease(int key) {
        gameController.keyReleased(key);
    }

    /******************
     * User functions *
     ******************/
}
