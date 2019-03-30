package main.game.collision.projectiles;

import main.game.boards.Board;
import main.game.boards.BoardCamera;
import main.game.boards.BoardVector;
import main.game.collision.Hitbox;
import main.game.enums.Team;
import rendering.Graphics;

public class TestProjectile1 extends Projectile {
    private boolean alive = true;

    private BoardVector position;

    private int direction;
    private final double speed = 3.0;

    public TestProjectile1(Board board, Team team, double x, double y, int direction) {
        super(board, team);

        position = new BoardVector(x, y);
        this.direction = direction;
    }

    public void update(double delta) {
        position.setY(position.getWorldY() + speed * direction * delta);
    }

    public void draw(BoardCamera camera) {
        position.refresh(camera);
        float screenX = position.getScreenX();
        float screenY = position.getScreenY();

        float screenUnitX = camera.getScreenUnitX();
        float screenUnitY = camera.getScreenUnitY();
        Graphics.drawQuad(
                screenX - 0.1f * screenUnitX, screenY - 0.1f * screenUnitY,
                screenX + 0.1f * screenUnitX, screenY - 0.1f * screenUnitY,
                screenX - 0.1f * screenUnitX, screenY + 0.1f * screenUnitY,
                screenX + 0.1f * screenUnitX, screenY + 0.1f * screenUnitY);
    }

    public boolean isColliding(Hitbox h) {
        double x = position.getWorldX();
        double y = position.getWorldY();
        return alive && team.collidesWidth(h.getTeam()) &&
                x >= h.getWorldX() - h.getWidth()/2 && x <= h.getWorldX() + h.getWidth()/2 &&
                y >= h.getWorldY() - h.getHeight()/2 && y <= h.getWorldY() + h.getHeight()/2;
    }

    public void setPosition(double x, double y) {
        position.setPosition(x, y);
    }

    public void collide(Hitbox h) {
        alive = false;
    }

    public boolean shouldBeDestroyed() {
        return !alive;
    }
}
