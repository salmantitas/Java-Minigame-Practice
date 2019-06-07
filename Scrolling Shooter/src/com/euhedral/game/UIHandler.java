package com.euhedral.game;

import com.euhedral.engine.*;
import com.euhedral.engine.MenuItem;
import com.euhedral.engine.Panel;

import java.awt.*;
import java.util.LinkedList;

public class UIHandler {
    private LinkedList<MenuItem> menuItems = new LinkedList<>();
    private LinkedList<NavButton> navButtons = new LinkedList<>();
    private LinkedList<ActButton> actButtons = new LinkedList<>();

    // Common game variables

    int titleX = Engine.percWidth(2);
    int titleY = Engine.percHeight(20);
    int titleSize = Engine.percWidth(11.5);
    Color titleColor = Color.BLACK;
    int buttonSize = Engine.percWidth(5);
    int mainMenuButtonX = Engine.percWidth(5);
    int backToMenuX = Engine.percWidth(38);
    int playButtonY = Engine.percHeight(30);
    int highScoreButtonY = Engine.percHeight(50);
    int quitButtonY = Engine.percHeight(70);
    int lowestButtonY = Engine.percHeight(80);

    String action = null;
    public boolean ground = false;

    public UIHandler() {

        // Game Backdrop

        // Main Menu
        Panel mainMenu = new Panel(0, 0, Engine.percWidth(40), Engine.HEIGHT, GameState.Menu);
        addPanel(mainMenu);

        NavButton mainMenuPlay = new NavButton(mainMenuButtonX, playButtonY, buttonSize, "Play", GameState.Menu, GameState.Transition);
        mainMenuPlay.addOtherState(GameState.GameOver);
        mainMenuPlay.setFill();
        addButton(mainMenuPlay);

        NavButton mainMenuQuit = new NavButton(mainMenuButtonX, quitButtonY, buttonSize, "Quit", GameState.Menu, GameState.Quit);
        mainMenuQuit.setFill();
        mainMenuQuit.addOtherState(GameState.Transition);
        mainMenuQuit.addOtherState(GameState.Pause);
        mainMenuQuit.addOtherState(GameState.GameOver);
        addButton(mainMenuQuit);

        // In-Game

        NavButton backToMenuFromPause = new NavButton(backToMenuX, lowestButtonY/2, buttonSize, "Main Menu", GameState.Pause, GameState.Menu);
        backToMenuFromPause.addOtherState(GameState.GameOver);
        addButton(backToMenuFromPause);

//        NavButton backToMenu = new NavButton(backToMenuX, lowestButtonY, buttonSize, "Main Menu", GameState.Highscore, GameState.Menu);
//        backToMenu.setFill();
//        backToMenu.addOtherState(GameState.GameOver);
//        addButton(backToMenu);

        // Shop

        int healthY = 100, powerY = 200, groundY = 300;

        ActButton go = new ActButton(600, highScoreButtonY, buttonSize, "Go!", GameState.Transition, "go");
        addButton(go);

        ActButton health = new ActButton(mainMenuButtonX, healthY, buttonSize/2, "Buy Health", GameState.Transition, "health");
        addButton(health);

        ActButton power = new ActButton(mainMenuButtonX, powerY, buttonSize/2, "Upgrade Power", GameState.Transition, "power");
        addButton(power);

        ActButton ground = new ActButton(mainMenuButtonX, groundY, buttonSize/2, "Ground Bullets", GameState.Transition, "ground");
        addButton(ground);

        // Game Over Screen -- High Score Menu

        Panel highScoreMenu = new Panel(0, Engine.percHeight(60), Engine.WIDTH, Engine.HEIGHT, GameState.Highscore);
        highScoreMenu.addOtherState(GameState.GameOver);
        addPanel(highScoreMenu);
    }

//    public void update() {
//
//    }

    public void render(Graphics g) {
        if (Engine.currentState == GameState.Pause) {
            drawPause(g);
        }

        if (Engine.currentState == GameState.GameOver) {
            drawGameOverScreen(g);
        }

        for (MenuItem menuItem: menuItems) {
            if (menuItem.stateIs(Engine.currentState))
                menuItem.render(g);
        }

        for (NavButton navButton : navButtons) {
            if (navButton.stateIs(Engine.currentState))
                navButton.render(g);
        }

        for (ActButton actButton : actButtons) {
            if (actButton.stateIs(Engine.currentState)) {
                if (!ground && actButton.getAction() == "ground") {
                    actButton.render(g);
                } else
                    actButton.render(g);
            }
        }

        if (Engine.currentState == GameState.Menu) {
            drawTitle(g);
        }
    }

    public void checkButtonAction(int mx, int my) {
        for (NavButton navButton : navButtons) {
            if (navButton.stateIs(Engine.currentState))
                if (navButton.mouseOverlap(mx, my))
                    Engine.setState(navButton.getTargetSate());
        }

        for (ActButton actButton : actButtons) {
            if (actButton.stateIs(Engine.currentState)) {
                if (actButton.mouseOverlap(mx, my)) {
                    this.action = actButton.getAction();
                }
            }
        }
    }

    /***************************
     * Render Helper Functions *
     ***************************/

    private void drawTitle(Graphics g) {
        g.setFont(new Font("arial", 1, titleSize));
        g.setColor(titleColor);
        g.drawString(Engine.TITLE, titleX, titleY);
    }

    public void drawPause(Graphics g) {
        g.setFont(new Font("arial", 1, Engine.percWidth(20)));
        g.setColor(Color.WHITE);
        g.drawString("PAUSE", Engine.percWidth(16), Engine.percHeight(25));
    }

    public void drawGameOverScreen(Graphics g) {
        g.setFont(new Font("arial", 1, 200));
        g.setColor(Color.WHITE);
        g.drawString("GAME OVER", Engine.percWidth(2), Engine.HEIGHT/2);
    }

    /****************
     * UI Functions *
     ****************/

    public void addButton(NavButton navButton) {
        navButtons.add(navButton);
    }

    public void addButton(ActButton actButton) {
        actButtons.add(actButton);
    }

    public void addButton(int x, int y, int size, String text, GameState renderState, GameState targetState) {
        navButtons.add(new NavButton(x, y, size, text, renderState, targetState));
    }

    public void addButton(int x, int y, int size, String text, GameState renderState, GameState targetState, Color borderColor, Color textColor) {
        navButtons.add(new NavButton(x, y, size, text, renderState, targetState, borderColor, textColor));
    }

    public void addButton(int x, int y, int size, String text, GameState renderState, GameState targetState, Color borderColor, Color textColor, Font font) {
        navButtons.add(new NavButton(x, y, size, text, renderState, targetState, borderColor, textColor, font));
    }

    public void addPanel(Panel panel) {
        menuItems.add(panel);
    }

    public void addPanel(int x, int y, int width, int height, GameState state) {
        menuItems.add(new Panel(x, y, width, height, state));
    }


    public void addPanel(int x, int y, int width, int height, GameState state, float transparency, Color color) {
        menuItems.add(new Panel(x, y, width, height, state, transparency, color));
    }

    public String getAction() {
        return action;
    }

    public void endAction() {
        action = null;
    }

}
