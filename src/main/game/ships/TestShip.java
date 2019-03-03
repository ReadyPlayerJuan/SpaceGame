package main.game.ships;

import main.game.enums.ShipActionState;
import main.game.enums.ShipActionType;
import main.game.enums.Team;
import main.game.enums.Direction;
import main.game.weapons.TestWeapon1;
import main.game.weapons.Weapon;
import main.views.GameView;

public class TestShip extends Ship {
    private ShipAction actionEvadeLeft, actionEvadeRight;
    private ShipSection sectionLeft, sectionRight;

    public TestShip(GameView parentView, Team team) {
        this.parentView = parentView;

        columnPosition = 0;
        shipWidth = 2;
        this.team = team;

        actionEvadeLeft = new ShipAction(ShipActionType.EVADE_LEFT);
        actionEvadeRight = new ShipAction(ShipActionType.EVADE_RIGHT);

        sectionLeft = new ShipSection(this, 0, 2, new ShipAction[] {actionEvadeLeft});
        sectionRight = new ShipSection(this, 1, 2, new ShipAction[] {actionEvadeRight});
        sectionLeft.setWeapons(new Weapon[] {
                new TestWeapon1(sectionLeft, ShipActionType.FIRE_WEAPON_1),
        });
        sectionRight.setWeapons(new Weapon[] {
                new TestWeapon1(sectionRight, ShipActionType.FIRE_WEAPON_2),
        });
        sectionLeft.setAdjacentSection(Direction.RIGHT, sectionRight);
        sectionRight.setAdjacentSection(Direction.LEFT, sectionLeft);
        this.sections = new ShipSection[] {sectionLeft, sectionRight};

        focusedSection = 0;
    }

    public void update(double delta) {
        super.update(delta); //update sections

        int boardWidth = parentView.currentBoard.numColumns;
        if(columnPosition > 0) {
            actionEvadeLeft.setState(ShipActionState.AVAILABLE);
        } else {
            actionEvadeLeft.setState(ShipActionState.UNAVAILABLE);
        }
        if(columnPosition + shipWidth < boardWidth) {
            actionEvadeRight.setState(ShipActionState.AVAILABLE);
        } else {
            actionEvadeRight.setState(ShipActionState.UNAVAILABLE);
        }
    }
}
