package com.euhedral.engine;/*
 * Do not modify
 * */

import com.euhedral.engine.Engine;
import com.euhedral.engine.GameState;

import java.awt.*;
import java.util.LinkedList;

public class ActButton {
    private int x, y, width, height;
    private int size;
    private String text;
    private Font font;
    private GameState renderState;
    private String action;
    private Color backColor, textColor;
    private boolean fill = false;
    private LinkedList<GameState> otherStates = new LinkedList<>();
    private float transparency = 1;

    public ActButton(int x, int y, int size, String text, GameState renderState, String action) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.text = text;
        this.renderState = renderState;
        this.action = action;
        font = new Font("arial", 1, size);
        backColor = Color.BLUE;
        textColor = Color.RED;
    }

    public ActButton(int x, int y, int size, String text, GameState renderState, String action, boolean fill) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.text = text;
        this.renderState = renderState;
        this.action = action;
        font = new Font("arial", 1, size);
        backColor = Color.BLUE;
        textColor = Color.RED;
        this.fill = fill;
    }

    public ActButton(int x, int y, int size, String text, GameState renderState, String action, Color backColor, Color textColor) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.text = text;
        this.renderState = renderState;
        this.action = action;
        font = new Font("arial", 1, size);
        this.backColor = backColor;
        this.textColor = textColor;
    }

    public ActButton(int x, int y, int size, String text, GameState renderState, String action, Color backColor, Color textColor, Font font) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.text = text;
        this.renderState = renderState;
        this.action = action;
        this.font = font;
        this.backColor = backColor;
        this.textColor = textColor;
    }

    public void render(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        if (transparency < 1) {
            g2d.setComposite(makeTransparent(transparency));
        }

        g.setFont(font);
        width = Engine.perc((g.getFontMetrics().stringWidth(text)), 110);
        if (width / size < 3)
            height = Engine.perc(width, 50);
        else height = Engine.perc(width, 25);

        g.setColor(backColor);
        if (fill)
            g.fill3DRect(x,y,width,height, true);
        else g.draw3DRect(x, y, width, height, true);

        g.setColor(textColor);
        g.drawString(text, x + Engine.perc(width, 5), y + Engine.perc(height, 75));

        if (transparency < 1) {
            g2d.setComposite(makeTransparent(1));
        }
    }

    public boolean mouseOverlap(int mx, int my) {
        return (mx >= x && mx <= x + width && my >= y && my <= y + height);
    }

    public void setBackColor(Color backColor) {
        this.backColor = backColor;
    }

    public void setTransparency(float transparency) {
        this.transparency = transparency;
    }

    public boolean stateIs(GameState state) {
        boolean temp;
        if (otherStates.isEmpty())
            temp = state == this.renderState;
        else {
            temp = state == this.renderState;
            for (GameState gs: otherStates) {
                temp = temp || (gs == state);
            }
        }
        return temp;
    }

    public void setFill() {
        this.fill = true;
    }

    public GameState getRenderState() {
        return renderState;
    }

    public String getAction() {
        return action;
    }

    public void addOtherState(GameState state) {
        otherStates.add(state);
    }

    public void setText(String text) {
        this.text = text;
    }

    // Not very sure what's happening here
    private AlphaComposite makeTransparent(float alpha) {
        int type = AlphaComposite.SRC_OVER;
        return(AlphaComposite.getInstance(type, alpha));
    }
}
