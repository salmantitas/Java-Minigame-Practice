package com.euhedral.engine;

import com.euhedral.game.ActionTag;

import java.awt.*;
import java.util.LinkedList;

public class Button {
    protected int x, y, width, height;
    protected int size;
    protected String text;
    protected Font font;
    protected GameState renderState;
    protected ActionTag action;
    protected Color backColor, textColor;
    protected boolean fill = false;
    protected LinkedList<GameState> otherStates = new LinkedList<>();
    protected float transparency = 1;
}
