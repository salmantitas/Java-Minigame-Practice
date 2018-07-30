import java.awt.*;
import java.util.LinkedList;

public class UIHandler {
    private LinkedList<MenuItem> menuItems = new LinkedList<>();
    private LinkedList<Button> buttons = new LinkedList<>();

    /******************
     * User variables *
     ******************/

    int titleY = Engine.percHeight(25);
    int playButtonY = Engine.percHeight(35);
    int highScoreButtonY = Engine.percHeight(80);
    int quitButtonY = Engine.percHeight(60);
    int buttonSize = Engine.percWidth(8);

    public UIHandler() {


        // Game Backdrop
        MenuItem sky = new MenuItem(0, 0, Engine.WIDTH, Engine.HEIGHT, GameState.Game, GameState.Pause, 1, Color.BLUE);
        MenuItem road = new MenuItem(0, Engine.percHeight(70), Engine.WIDTH, Engine.percHeight(30), GameState.Game, GameState.Pause,
                1, Color.ORANGE);
        sky.addOtherState(GameState.GameOver);
        road.addOtherState(GameState.GameOver);
        addPanel(sky);
        addPanel(road);

        // Main Menu

        addButton(Engine.percWidth(20), playButtonY, buttonSize, "Play", GameState.Menu, GameState.Game);
        addButton(Engine.percWidth(35), quitButtonY, buttonSize, "Quit", GameState.Menu, GameState.Quit);
        addButton(Engine.percWidth(50), playButtonY, buttonSize, "High Score", GameState.Menu, GameState.Highscore);

        // In-Game
        addButton(Engine.percWidth(80), Engine.percHeight(4), Engine.percWidth(5), "Menu", GameState.Pause, GameState.Menu, Color.BLACK, Color.RED);

        // Game Over Screen

        playButtonY = Engine.percHeight(15);

        addButton(Engine.percWidth(35), playButtonY, Engine.percWidth(10), "Play", GameState.GameOver, GameState.Game,
                Color.BLACK, Color.RED);
        addButton(Engine.percWidth(30), Engine.percHeight(60), Engine.percWidth(10), "Menu", GameState.GameOver, GameState.Menu,
                Color.BLACK, Color.RED);
        addButton(Engine.percWidth(90), Engine.percHeight(90), Engine.percWidth(2), "Quit", GameState.GameOver, GameState.Quit,
                Color.BLACK, Color.RED);

        // High Score Menu
        addButton(Engine.percWidth(35), playButtonY, Engine.percWidth(10), "Play", GameState.Highscore, GameState.Game);
        addButton(Engine.percWidth(30), Engine.percHeight(60), Engine.percWidth(10), "Menu", GameState.Highscore, GameState.Menu);
    }

//    public void update() {
//
//    }

    public void render(Graphics g) {
        if (Engine.currentState == GameState.Menu) {
            drawTitle(g);
        }

        for (MenuItem menuItem: menuItems) {
            if (menuItem.stateIs(Engine.currentState))
                menuItem.render(g);
        }

        for (Button button: buttons) {
            if (Engine.currentState == button.getRenderState())
                button.render(g);
        }
    }

    public void checkOverlap(int mx, int my) {
        for (Button button: buttons) {
            if (button.getRenderState() == Engine.currentState)
                if (button.mouseOverlap(mx, my))
                    Engine.setState(button.getTargetSate());
        }
    }

    private void drawTitle(Graphics g) {
        g.setFont(new Font("arial", 1, 200));
        g.setColor(Color.WHITE);
        g.drawString(Engine.TITLE, Engine.percWidth(2), titleY);
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

    public void addPanel(MenuItem menuItem) {
        menuItems.add(menuItem);
    }

    public void addPanel(int x, int y, int width, int height, GameState state) {
        menuItems.add(new MenuItem(x, y, width, height, state));
    }

    public void addPanel(int x, int y, int width, int height, GameState state, GameState other) {
        menuItems.add(new MenuItem(x, y, width, height, state, other));
    }

    public void addPanel(int x, int y, int width, int height, GameState state, float transparency, Color color) {
        menuItems.add(new MenuItem(x, y, width, height, state, transparency, color));
    }

    public void addPanel(int x, int y, int width, int height, GameState state, GameState other, float transparency, Color color) {
        menuItems.add(new MenuItem(x, y, width, height, state, other, transparency, color));
    }
}
