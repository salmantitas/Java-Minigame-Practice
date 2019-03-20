package com.euhedral.game;

import com.euhedral.engine.Engine;

public class Camera {

    private float x,y;

    public Camera(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void update(GameObject gameObject) {

        // Game Code

        if (gameObject != null) {
            x = -gameObject.getX() + Engine.WIDTH / 2;
            y = -gameObject.getY() + Engine.HEIGHT / 2;
        }
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
