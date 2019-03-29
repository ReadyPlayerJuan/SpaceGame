package main.game.collision.projectiles;

import main.game.collision.Hitbox;

public class TestProjectile1 extends Projectile {
    private boolean alive = true;

    private int colX;
    private double worldX, worldY;

    private int direction = 1;
    private final double speed = 0.6;

    public void update(double delta) {
        worldY += speed * direction * delta;
    }

    public void draw() {

    }

    public boolean isColliding(Hitbox h) {
        return alive && false;// &&
                //colX >= h.getColX() && colX < h.getColX() + h.getWidth() &&
                //worldY > h.getWorldY() - h.getHeight() && worldY < h.getWorldY() + h.getHeight();
    }

    public void collide(Hitbox h) {
        alive = false;
    }

    public boolean shouldBeDestroyed() {
        return !alive;
    }
}
