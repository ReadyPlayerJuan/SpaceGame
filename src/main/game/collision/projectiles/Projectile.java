package main.game.collision.projectiles;

import main.game.collision.Hitbox;
import main.game.enums.Team;

public abstract class Projectile {
    private Team team;

    public abstract void update(double delta);
    public abstract void draw();

    public abstract boolean isColliding(Hitbox h);
    public abstract void collide(Hitbox h);

    public abstract boolean shouldBeDestroyed();
}
