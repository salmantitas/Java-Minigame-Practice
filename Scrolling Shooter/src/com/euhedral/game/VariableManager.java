package com.euhedral.game;

import com.euhedral.engine.Engine;

import java.awt.*;

public class VariableManager {

    /****************************************
     * Common Game Variables                *
     * Comment Out Whichever is Unnecessary *
     ****************************************/

    // Vitality
//    private int lives = 3;

    private int healthX = Engine.percWidth(2.5);
    private int healthY = 5 * healthX;
    private final int healthDefault = 100;
    private int health = healthDefault;

    // Score
    private int score = 0;
    private int scoreX = Engine.percWidth(2.5);
    private int scoreY = Engine.percHeight(15);
    private int scoreSize = Engine.percWidth(2);

    // Power
    private int powerX = Engine.percWidth(24);
    private int powerY = scoreY;
    private int powerSize = scoreSize;
    private final int maxPower = 5;
    private int power = 1;

    public VariableManager() {

    }

    public void resetHealth() {
        health = healthDefault;
    }

    public void resetScore() {
        score = 0;
    }

    public void resetPower() {
        power = 1;
    }

    public void increaseHealth(int health) {
        this.health += health;
    }

    public void decreaseHealth(int health) {
        this.health -= health;
    }

    public void increaseScore(int score) {
        this.score += score;
    }

    public void decreaseScore(int score) {
        this.score -= score;
    }

    public void increasePower(int power) {
        this.power += power;
    }

    public void decreasePower(int power) {
        this.power -= power;
    }

    /*
    * Render
    * */

    public void renderHealth(Graphics g) {
        int width = Engine.intAtWidth640(2);
        int height = width * 6;
        Color backColor = Color.lightGray;
        Color healthColor = Color.GREEN;
        g.setColor(backColor);
        g.fillRect(healthX, healthY, healthDefault * width, height);
        g.setColor(healthColor);
        g.fillRect(healthX, healthY, health * width, height);
    }

    public void renderScore(Graphics g) {
        g.setFont(new Font("arial", 1, scoreSize));
        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, scoreX, scoreY);
    }

    public void renderPower(Graphics g) {
        g.setFont(new Font("arial", 1, powerSize));
        g.setColor(Color.WHITE);
        g.drawString("Power: " + power, powerX, powerY);
    }


    /**/

    public int getHealthX() {
        return healthX;
    }

    public void setHealthX(int healthX) {
        this.healthX = healthX;
    }

    public int getHealthY() {
        return healthY;
    }

    public void setHealthY(int healthY) {
        this.healthY = healthY;
    }

    public int getHealthDefault() {
        return healthDefault;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScoreX() {
        return scoreX;
    }

    public void setScoreX(int scoreX) {
        this.scoreX = scoreX;
    }

    public int getScoreY() {
        return scoreY;
    }

    public void setScoreY(int scoreY) {
        this.scoreY = scoreY;
    }

    public int getScoreSize() {
        return scoreSize;
    }

    public void setScoreSize(int scoreSize) {
        this.scoreSize = scoreSize;
    }

    public int getPowerX() {
        return powerX;
    }

    public void setPowerX(int powerX) {
        this.powerX = powerX;
    }

    public int getPowerY() {
        return powerY;
    }

    public void setPowerY(int powerY) {
        this.powerY = powerY;
    }

    public int getPowerSize() {
        return powerSize;
    }

    public void setPowerSize(int powerSize) {
        this.powerSize = powerSize;
    }

    public int getMaxPower() {
        return maxPower;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }
}
