package main.game.ships;

import main.game.enums.ShipActionType;
import main.game.enums.ShipActionState;
import main.input.InputCode;

public class ShipAction {
    private InputCode code; //the code that activates this action
    private ShipActionType action; //the action that the code corresponds to
    private ShipActionState state = ShipActionState.AVAILABLE; //the state of the action or system
    private boolean focused = false; //whether or not the system's ship section is selected

    public ShipAction(ShipActionType action, InputCode code) {
        this.action = action;
        this.code = code;
    }

    public ShipAction(ShipActionType action, InputCode code, ShipActionState state, boolean focused) {
        this.action = action;
        this.code = code;
        this.state = state;
        this.focused = focused;
    }

    public InputCode getCode() {
        return code;
    }

    public void setCode(InputCode code) {
        this.code = code;
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
