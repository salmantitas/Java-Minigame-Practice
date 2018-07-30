import java.awt.*;
import java.util.LinkedList;

public class MenuItem {

    int x, y, width, height;
    float transparency;
    Color color;
    GameState state;
    LinkedList<GameState> otherStates = new LinkedList<>();

    public MenuItem(int x, int y, int width, int height, GameState state) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.state = state;
        transparency = 0.7f;
        color = Color.GRAY;
    }

    public MenuItem(int x, int y, int width, int height, GameState state, GameState other) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.state = state;
        transparency = 0.7f;
        color = Color.GRAY;
        addOtherState(other);
    }

    public MenuItem(int x, int y, int width, int height, GameState state, float transparency, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.state = state;
        this.transparency = transparency;
        this.color = color;
    }

    public MenuItem(int x, int y, int width, int height, GameState state, GameState other, float transparency, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.state = state;
        this.transparency = transparency;
        this.color = color;
        addOtherState(other);
    }

//    private void addPausetoStates() {
//        otherStates.add(GameState.Pause);
//    }

    public void render(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setComposite(makeTransparent(transparency));

            g.setColor(color);
            g.fillRect(x, y, width, height);

            // Ensure that nothing on top of the transparent panel is rendered transparent
            g2d.setComposite(makeTransparent(1));
    }

    public boolean stateIs(GameState state) {
        boolean temp;
        if (otherStates.isEmpty())
            temp = state == this.state;
        else {
            temp = state == this.state;
            for (GameState gs: otherStates) {
                temp = temp || (gs == state);
            }
        }
        return temp;
    }
    public void addOtherState(GameState state) {
        otherStates.add(state);
    }

    // Not very sure what's happening here
    private AlphaComposite makeTransparent(float alpha) {
        int type = AlphaComposite.SRC_OVER;
        return(AlphaComposite.getInstance(type, alpha));
    }

}
