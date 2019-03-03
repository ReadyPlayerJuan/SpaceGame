package main.game.weapons;

import main.game.enums.ShipActionType;
import main.game.enums.ShipActionState;
import main.game.ships.ShipAction;
import main.game.ships.ShipSection;

public class TestWeapon1 extends Weapon {
    private double cooldown = 0;
    private ShipAction actionFireWeapon;

    public TestWeapon1(ShipSection shipSection, ShipActionType fireAction) {
        super(shipSection);

        actionFireWeapon = new ShipAction(fireAction);

        this.weaponActions = new ShipAction[] {
                actionFireWeapon,
        };
    }

    public void update(double delta) {
        cooldown = Math.max(0, cooldown - delta);

        if(cooldown > 0) {
            actionFireWeapon.setState(ShipActionState.ON_COOLDOWN);
        } else if(shipSection.systemsBroken()) {
            actionFireWeapon.setState(ShipActionState.SECTION_BROKEN);
        } else {
            actionFireWeapon.setState(ShipActionState.AVAILABLE);
        }

        actionFireWeapon.setFocused(shipSection.isFocused);
    }
}
