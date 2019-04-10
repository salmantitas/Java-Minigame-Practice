package com.euhedral.game;

import com.euhedral.engine.Engine;
import com.euhedral.engine.GameState;
import com.euhedral.engine.MenuItem;
import com.euhedral.engine.Button;
import com.euhedral.engine.Panel;

import java.awt.*;
import java.util.LinkedList;

public class UIHandler {
    private LinkedList<MenuItem> menuItems = new LinkedList<>();
    private LinkedList<Button> buttons = new LinkedList<>();

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

    public UIHandler() {

        // Game Backdrop

        // Main Menu
        Panel mainMenu = new Panel(0, 0, Engine.percWidth(40), Engine.HEIGHT, GameState.Menu);
        addPanel(mainMenu);

        Button mainMenuPlay = new Button(mainMenuButtonX, playButtonY, buttonSize, "Play", GameState.Menu, GameState.Transition);
        mainMenuPlay.setFill();
        addButton(mainMenuPlay);

        Button mainMenuHighScore = new Button(mainMenuButtonX, highScoreButtonY, buttonSize, "High Score", GameState.Menu, GameState.Highscore);
        mainMenuHighScore.setFill();
        addButton(mainMenuHighScore);

        Button mainMenuQuit = new Button(mainMenuButtonX, quitButtonY, buttonSize, "Quit", GameState.Menu, GameState.Quit);
        mainMenuQuit.setFill();
        addButton(mainMenuQuit);

        // In-Game

        Button backToMenuFromPause = new Button(backToMenuX, lowestButtonY, buttonSize, "Main Menu", GameState.Pause, GameState.Menu);
        addButton(backToMenuFromPause);

        Button backToMenu = new Button(backToMenuX, lowestButtonY, buttonSize, "Main Menu", GameState.Highscore, GameState.Menu);
        backToMenu.setFill();
        backToMenu.addOtherState(GameState.GameOver);
        addButton(backToMenu);

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

        for (Button button: buttons) {
            if (button.stateIs(Engine.currentState))
                button.render(g);
        }

        if (Engine.currentState == GameState.Menu) {
            drawTitle(g);
        }
    }

    public void checkButtonAction(int mx, int my) {
        for (Button button: buttons) {
            if (button.stateIs(Engine.currentState))
                if (button.mouseOverlap(mx, my))
                    Engine.setState(button.getTargetSate());
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

    public void addButton(Button button) {
        buttons.add(button);
    }

    public void addButton(int x, int y, int size, String text, GameState renderState, GameState targetState) {
        buttons.add(new Button(x, y, size, text, renderState, targetState));
    }

    public void addButton(int x, int y, int size, String text, GameState renderState, GameState targetState, Color borderColor, Color textColor) {
        buttons.add(new Button(x, y, size, text, renderState, targetState, borderColor, textColor));
    }

    public void addButton(int x, int y, int size, String text, GameState renderState, GameState targetState, Color borderColor, Color textColor, Font font) {
        buttons.add(new Button(x, y, size, text, renderState, targetState, borderColor, textColor, font));
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

}
