package main.game.ships.systems;

import main.Util;
import main.game.enums.ShipActionState;
import main.game.ships.ShipAction;

import main.game.enums.ShipActionType;

public class ShipEvasion {//extends ShipSystem {
    private double evadeTimerMax = 0.5;
    private float evadeRotationMax = 0.2f;
    private double evadeTimer = 0, evadePct = 0, prevEvadePct = 0;
    private int evadeDirection;//, evadeStartX, evadeEndX;
    private float evadeRotation = 0;

    private int shipShouldMove = 0;
    private float shipSpriteOffset = 0;

    private ShipAction actionEvadeLeft, actionEvadeRight;

    public ShipEvasion(double evadeTimerMax, float evadeRotationMax, ShipAction actionEvadeLeft, ShipAction actionEvadeRight) {
        this.evadeTimerMax = evadeTimerMax;
        this.evadeRotationMax = evadeRotationMax;
        this.actionEvadeLeft = actionEvadeLeft;
        this.actionEvadeRight = actionEvadeRight;
    }

    public boolean processInput(ShipAction action) {
        switch(action.getAction()) {
            case EVADE_LEFT:
                evadeTimer = evadeTimerMax;
                evadeDirection = -1;
                evadePct = 0;
                return true;
            case EVADE_RIGHT:
                evadeTimer = evadeTimerMax;
                evadeDirection = 1;
                evadePct = 0;
                return true;
        }
        return false;
    }

    public void update(double delta, int boardWidth, int shipX, int shipWidth) {
        shipShouldMove = 0;
        shipSpriteOffset = 0;

        //update systems and timers
        if(evadeTimer > 0) {
            evadeTimer = Math.max(0, evadeTimer - delta);
            prevEvadePct = evadePct;
            evadePct = Util.skewPctPow(1 - (evadeTimer / evadeTimerMax), 2.0);
            evadeRotation = (float)(1.0 - (2 * Math.abs((evadeTimer / evadeTimerMax) - 0.5))) * evadeDirection * evadeRotationMax;

            if(evadePct > 0.5 && prevEvadePct <= 0.5) {
                shipShouldMove = evadeDirection;
            }
            if(evadePct > 0.5) {
                shipSpriteOffset = (float)(evadePct - 1.0) * evadeDirection;
            } else {
                shipSpriteOffset = (float)evadePct * evadeDirection;
            }
        }

        //set ship action states
        //for example: if in leftmost column, disable evade left
        if(evadeTimer > 0) {
            actionEvadeLeft.setState(ShipActionState.ON_COOLDOWN);
        } else if(shipX > 0) {
            actionEvadeLeft.setState(ShipActionState.AVAILABLE);
        } else {
            actionEvadeLeft.setState(ShipActionState.UNAVAILABLE);
        }
        if(evadeTimer > 0) {
            actionEvadeRight.setState(ShipActionState.ON_COOLDOWN);
        } else if(shipX + shipWidth < boardWidth) {
            actionEvadeRight.setState(ShipActionState.AVAILABLE);
        } else {
            actionEvadeRight.setState(ShipActionState.UNAVAILABLE);
        }
    }

    public int shipShouldMove() {
        return shipShouldMove;
    }

    public float getShipSpriteOffset() {
        return shipSpriteOffset;
    }

    public float getEvadeRotation() {
        return evadeRotation;
    }
}
