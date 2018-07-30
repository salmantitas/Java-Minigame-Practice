import java.awt.*;

public class Button {
    private int x, y, size, width, height;
    private String text;
    private Font font;
    private GameState renderState, targetSate;
    private Color backColor, textColor;


    public Button(int x, int y, int size, String text, GameState renderState, GameState targetSate) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.text = text;
        this.renderState = renderState;
        this.targetSate = targetSate;
        font = new Font("arial", 1, size);
        backColor = Color.BLUE;
        textColor = Color.RED;
    }

    public Button(int x, int y, int size, String text, GameState renderState, GameState targetSate, Color backColor, Color textColor) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.text = text;
        this.renderState = renderState;
        this.targetSate = targetSate;
        font = new Font("arial", 1, size);
        this.backColor = backColor;
        this.textColor = textColor;
    }

    public Button(int x, int y, int size, String text, GameState renderState, GameState targetSate, Color backColor, Color textColor, Font font) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.text = text;
        this.renderState = renderState;
        this.targetSate = targetSate;
        this.font = font;
        this.backColor = backColor;
        this.textColor = textColor;
    }

    public void render(Graphics g) {
        g.setFont(font);
        width = Engine.perc((g.getFontMetrics().stringWidth(text)), 110);
        if (width / size < 3)
            height = Engine.perc(width, 50);
        else height = Engine.perc(width, 25);

        g.setColor(backColor);
        g.drawRect(x, y, width, height);

        g.setColor(textColor);
        g.drawString(text, x + Engine.perc(width, 5), y + Engine.perc(height, 75));
    }

    public boolean mouseOverlap(int mx, int my) {
        return (mx >= x && mx <= x + width && my >= y && my <= y + height);
    }

    public GameState getRenderState() {
        return renderState;
    }

    public GameState getTargetSate() {
        return targetSate;
    }

    public void setText(String text) {
        this.text = text;
    }
}
