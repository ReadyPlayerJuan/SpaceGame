package main.game.weapons;

import main.game.boards.Board;
import main.game.collision.projectiles.TestProjectile1;
import main.game.enums.ShipActionType;
import main.game.enums.ShipActionState;
import main.game.enums.Team;
import main.game.ships.ShipAction;
import main.input.InputCode;

public class TestWeapon1 extends Weapon {
    private double cooldown = 0;
    private ShipAction actionFireWeapon;

    public TestWeapon1(Board board, ShipActionType fireAction, InputCode code) {
        super(board);

        actionFireWeapon = new ShipAction(fireAction, code);

        this.weaponActions = new ShipAction[] {
                actionFireWeapon,
        };
    }

    public void fire(Team team, double x, double y, int direction) {
        new TestProjectile1(board, team, x, y, direction);
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

        //actionFireWeapon.setFocused(shipSection.isFocused);
    }
}
