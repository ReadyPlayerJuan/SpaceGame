package main.game.ships;

import main.game.enums.Direction;
import main.game.weapons.Weapon;

import java.util.ArrayList;

public class ShipSection {
    private Ship ship;

    private int columnOffset;
    private int health, maxHealth;
    public boolean isFocused = false;

    private ShipSection[] adjacentSections = {null, null, null, null};
    private Weapon[] weapons;

    private ArrayList<ShipAction> actionInfo = new ArrayList<ShipAction>();

    public ShipSection(Ship ship, int columnOffset, int maxHealth, ShipAction[] baseActions) {
        this.ship = ship;
        this.columnOffset = columnOffset;
        this.maxHealth = maxHealth;
        this.health = maxHealth;

        for(ShipAction a: baseActions) {
            actionInfo.add(a);
        }
    }

    public void setWeapons(Weapon[] weapons) {
        this.weapons = weapons;

        for(Weapon w: weapons) {
            for(ShipAction wa: w.getWeaponActions()) {
                actionInfo.add(wa);
            }
        }
    }

    public void update(double delta) {
        for(Weapon w: weapons) {
            w.update(delta);
        }

        for(ShipAction a: actionInfo) {
            a.setFocused(isFocused);
        }
    }

    public boolean hasAdjacentSection(Direction dir) {
        return adjacentSections[dir.i] != null;
    }

    public ShipSection getAdjacentShipSection(Direction dir) {
        return adjacentSections[dir.i];
    }

    public void setAdjacentSection(Direction dir, ShipSection section) {
        adjacentSections[dir.i] = section;
    }

    public boolean systemsBroken() {
        return health > 1;
    }


}
