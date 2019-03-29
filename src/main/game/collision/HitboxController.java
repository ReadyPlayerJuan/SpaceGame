package main.game.collision;

import main.game.collision.projectiles.Projectile;

public interface HitboxController {
    public void collide(Projectile p);
}
