package main.game.weapons;

import main.game.boards.Board;
import main.game.ships.ShipAction;
import main.game.ships.ShipSection;

public abstract class Weapon {
    protected Board board;
    protected ShipSection shipSection;
    protected ShipAction[] weaponActions;

    public Weapon(Board board) {
        this.board = board;
        this.shipSection = null;
    }

    public void setShipSection(ShipSection shipSection) {
        this.shipSection = shipSection;
    }

    public ShipAction[] getWeaponActions() {
        return weaponActions;
    }

    public abstract void update(double delta);
}
