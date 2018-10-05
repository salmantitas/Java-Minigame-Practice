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

        // In-Game

        // Game Over Screen

        // High Score Menu

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
            if (button.getRenderState() == Engine.currentState)
                if (button.mouseOverlap(mx, my))
                    Engine.setState(button.getTargetSate());
        }
    }

    /***************************
     * Render Helper Functions *
     ***************************/

    private void drawTitle(Graphics g) {
        g.setFont(new Font("arial", 1, 200));
        g.setColor(Color.WHITE);
        g.drawString(Engine.TITLE, Engine.percWidth(2), titleY);
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
