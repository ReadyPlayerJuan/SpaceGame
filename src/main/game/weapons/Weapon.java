package main.game.weapons;

import main.game.ships.ShipAction;
import main.game.ships.ShipSection;

public abstract class Weapon {
    protected ShipSection shipSection;
    protected ShipAction[] weaponActions;

    public Weapon() {
        this.shipSection = null;
    }

    public Weapon(ShipSection shipSection) {
        this.shipSection = shipSection;
    }

    public ShipAction[] getWeaponActions() {
        return weaponActions;
    }

    public abstract void update(double delta);
}
