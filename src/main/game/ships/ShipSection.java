package main.game.ships;

import main.game.boards.Board;
import main.game.collision.Hitbox;
import main.game.collision.HitboxController;
import main.game.collision.projectiles.Projectile;
import main.game.enums.Direction;
import main.game.weapons.Weapon;

import java.util.ArrayList;

public abstract class ShipSection implements HitboxController {
    protected Board board;
    protected Ship ship;
    protected Hitbox hitbox;

    protected double worldX, worldY;
    protected int health, maxHealth;
    protected boolean isFocused = false;

    protected Weapon[] weapons;

    protected ShipAction[] baseActions;
    protected ArrayList<ShipAction> actions = new ArrayList<ShipAction>();

    public ShipSection(Ship ship, Board board, Hitbox hitbox, int maxHealth, ShipAction... baseActions) {
        this.ship = ship;
        this.board = board;
        this.hitbox = hitbox;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.baseActions = baseActions;

        hitbox.setOwner(this);

        setWeapons(new Weapon[] {});
    }

    public void setPosition(double worldX, double worldY) {
        this.worldX = worldX;
        this.worldY = worldY;
        hitbox.setPosition(worldX, worldY);
    }

    public void setWeapons(Weapon[] weapons) {
        this.weapons = weapons;

        actions.clear();

        for(ShipAction a: baseActions) {
            actions.add(a);
        }

        for(Weapon w: weapons) {
            w.setShipSection(this);
            for(ShipAction wa: w.getWeaponActions()) {
                actions.add(wa);
            }
        }
    }

    public abstract void collide(Projectile p);

    public void update(double delta) {
        for(Weapon w: weapons) {
            w.update(delta);
        }

        for(ShipAction a: actions) {
            a.setFocused(isFocused);
        }
    }

    public void setFocused(boolean focused) {
        this.isFocused = focused;
    }

    public boolean isFocused() {
        return isFocused;
    }

    public boolean systemsBroken() {
        return health < 1;
    }

    public ArrayList<ShipAction> getActions() {
        return actions;
    }

    public Hitbox getHitbox() {
        return hitbox;
    }
}
