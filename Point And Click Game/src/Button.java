import java.awt.*;

public class Button {
    private int x, y, size, width, height;
    private String text;

    private Game.BUTTON_TYPE type;

    public Button(int x, int y, int size, String text, Game.BUTTON_TYPE type) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.text = text;
        this.type = type;
    }

    public void update() {

    }

    public void render(Graphics g) {
        Font font = new Font("arial", 1, size);

        g.setFont(font);
        width = (g.getFontMetrics().stringWidth(text)) * 3 / 2;
        height = width / 2;

        g.setColor(Color.BLUE);
        g.drawRect(x, y, width, height);

        g.setColor(Color.RED);
        g.drawString(text, x + width / 6, y + height * 2 / 3);
    }

    public boolean mouseOverlap(int mx, int my) {
        return (mx >= x && mx <= x + width && my >= y && my <= y + height);
    }

    public Game.BUTTON_TYPE getType() {
        return type;
    }

    public void setText(String text) {
        this.text = text;
    }

    private void adjustPosition() {
        x = (x + width) / 2;
    }
}
