package com.euhedral.game;

import com.euhedral.engine.Entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class EntityManager {
    private VariableManager variableManager;
    public LinkedList<EntityActionTag> actions = new LinkedList<>();

    private LinkedList<Entity> entities;

    private Player player = new Player(0, 0, 0);
    private LinkedList<Enemy> enemies = new LinkedList<>();
    private LinkedList<Bullet> bullets = new LinkedList<>();

    EntityManager(VariableManager variableManager) {
        this.variableManager = variableManager;
    }

    public void initializeGraphics() {
        /*************
         * Game Code *
         *************/
//        playerSpriteSheet = new SpriteSheet("/player.png");
//        playerImage = new BufferedImage[2];
//        playerImage[0] = playerSpriteSheet.grabImage(1,1,32,32);
//        playerImage[1] = playerSpriteSheet.grabImage(2,1,32,32);
    }

    public void initializeAnimations() {
        /*************
         * Game Code *
         *************/
    }

    public void update() {

    }

    public void render(Graphics g) {

    }

    /********************
     * Player Functions *
     ********************/

    public void updatePlayer() {
        player.update();
    }

    public void renderPlayer(Graphics g) {
        player.render(g);
    }

    public void movePlayer(char c) {
        if (c == 'l')
            player.moveLeft(true);
        else if (c == 'r')
            player.moveRight(true);

        if (c == 'u')
            player.moveUp(true);
        else if (c == 'd')
            player.moveDown(true);
    }

    public void stopMovePlayer(char c) {
        if (c == 'l')
            player.moveLeft(false);
        else if (c == 'r')
            player.moveRight(false);

        if (c == 'u')
            player.moveUp(false);
        else if (c == 'd')
            player.moveDown(false);
    }

    public void giveDestination(int mx, int my) {
        player.giveDestination(mx, my);
    }

    public boolean canUpdateDestination(int mx, int my) {
        return !(player.getMx() == mx && player.getMy() == my);
    }

    public void switchPlayerBullet() {
        player.switchBullet();
    }

    public int getPlayerPower() {
        return player.getPower();
    }

    public void playerCanShoot() {
        player.canShoot(true);
    }
    public void playerCannotShoot() {
        player.canShoot(false);
    }

    public void spawnPlayer(int width, int height, int levelHeight, BufferedImage sprite, int power, boolean ground) {
        player = new Player(width, height, levelHeight, sprite);
        player.setGround(ground);
        player.setPower(power);
    }

    public Bullet checkPlayerCollision(Enemy enemy) {
        return player.checkCollision(enemy);
    }

    // Temp Functions
    public Rectangle getPlayerBounds() {
        return player.getBounds();
    }

    public int getPlayerY() {
        return player.getY();
    }

    /********************
     * Bullet Functions *
     ********************/

    public void updateBullets(EnemyBoss boss) {
        for (Bullet bullet : bullets) {
            if (bullet.isActive())
                bullet.update();
        }

        bullets.addAll(boss.getBullets());
    }
    public void renderBullets(Graphics g) {
        for (Bullet bullet: bullets) {
            if (bullet.isActive())
                bullet.render(g);
        }
    }

    public void clearBullets() {
        bullets.clear();
    }

    public void addToBullets(Enemy enemy) {
        bullets.addAll(enemy.getBullets());
    }

    private void destroy(Bullet bullet) {
        bullet.disable();
    }

    /***********************
     * Collision Functions *
     ***********************/

    public void playerVsEnemyBulletCollision() {
        for (Bullet bullet: bullets) {
            if (bullet.isActive() && bullet.getBounds().intersects(player.getBounds())) {
                variableManager.decreaseHealth(10);
                destroy(bullet);
            }
        }
    }

    /*******************************
     * Entity Management Functions *
     ****************-**************/

    public void addEntity(Entity entity) {
        entities.add(entity);

        /*************
         * Game Code *
         *************/
    }

    public void removeEntity(Entity entity) {
        entities.remove(entity);

        /*************
         * Game Code *
         *************/
    }

    private void updateEntities() {
        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            entity.update();
        }
    }

    private void updateActiveEntities(LinkedList<Entity> list) {
        for (int i = 0; i < list.size(); i++) {
            Entity entity = list.get(i);
            if (entity.isActive())
                entity.update();
        }
    }

    private void renderEntities(Graphics g) {
        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            entity.render(g);
        }
    }
}
