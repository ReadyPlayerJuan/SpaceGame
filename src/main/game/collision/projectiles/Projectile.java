package main.game.collision.projectiles;

import main.game.boards.Board;
import main.game.boards.BoardCamera;
import main.game.collision.Hitbox;
import main.game.enums.Team;

public abstract class Projectile {
    protected Board board;
    protected Team team;

    public Projectile(Board board, Team team) {
        this.board = board;
        this.team = team;
        board.addProjectile(this);
    }

    public abstract void update(double delta);
    public abstract void draw(BoardCamera camera);

    public abstract boolean isColliding(Hitbox h);
    public abstract void collide(Hitbox h);

    public abstract boolean shouldBeDestroyed();
}
