package main.game.ships;

import main.game.enums.ShipActionType;
import main.game.enums.ShipActionState;

public class ShipAction {
    //private ActionCode code;
    private ShipActionType action; //the action that the code corresponds to
    private ShipActionState state = ShipActionState.AVAILABLE; //the state of the action or system
    private boolean focused = false; //whether or not the system's ship section is selected

    public ShipAction(ShipActionType action) {
        this.action = action;
    }

    public ShipAction(ShipActionType action, ShipActionState state, boolean focused) {
        this.action = action;
        this.state = state;
        this.focused = focused;
    }

    public ShipActionType getAction() {
        return action;
    }

    public void setAction(ShipActionType action) {
        this.action = action;
    }

    public ShipActionState getState() {
        return state;
    }

    public void setState(ShipActionState state) {
        this.state = state;
    }

    public boolean isFocused() {
        return focused;
    }

    public void setFocused(boolean focused) {
        this.focused = focused;
    }
}
