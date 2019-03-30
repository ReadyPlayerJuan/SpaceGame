package main.game.ships.systems;

import main.Util;
import main.game.enums.ShipActionState;
import main.game.ships.ShipAction;

public class ShipEvasion {
    private double startColX = 0, endColX = 0, currentColX = 0;
    private boolean finishedEvading = false;

    private double evadeTimer = 0, evadeTimerMax, evadePct = 0;
    private int evadeDirection;
    private float evadeRotation = 0, evadeRotationMax;

    private ShipAction actionEvadeLeft, actionEvadeRight;

    public ShipEvasion(double evadeTimerMax, float evadeRotationMax, ShipAction actionEvadeLeft, ShipAction actionEvadeRight) {
        this.evadeTimerMax = evadeTimerMax;
        this.evadeRotationMax = evadeRotationMax;
        this.actionEvadeLeft = actionEvadeLeft;
        this.actionEvadeRight = actionEvadeRight;
    }

    public boolean processInput(ShipAction action, double shipColX) {
        switch(action.getAction()) {
            case EVADE_LEFT:
                evadeTimer = evadeTimerMax;
                evadeDirection = -1;
                evadePct = 0;
                startColX = shipColX;
                currentColX = startColX;
                endColX = startColX + evadeDirection;
                return true;
            case EVADE_RIGHT:
                evadeTimer = evadeTimerMax;
                evadeDirection = 1;
                evadePct = 0;
                startColX = shipColX;
                currentColX = startColX;
                endColX = startColX + evadeDirection;
                return true;
        }
        return false;
    }

    public void update(double delta, int numCols, double shipX, int shipWidth) {
        currentColX = -999;
        finishedEvading = false;

        //update systems and timers
        if(evadeTimer > 0) {
            evadeTimer = Math.max(0, evadeTimer - delta);
            //double prevEvadePct = evadePct;
            evadePct = Util.skewPctPow(1 - (evadeTimer / evadeTimerMax), 2.0);
            evadeRotation = (float)(1.0 - (2 * Math.abs((evadeTimer / evadeTimerMax) - 0.5))) * evadeDirection * evadeRotationMax;

            currentColX = startColX + evadePct * evadeDirection;

            if(evadeTimer == 0) {
                finishedEvading = true;
                currentColX = endColX;
            }
        }

        //set ship action states
        //for example: if in leftmost column, disable evade left
        if(evadeTimer > 0) {
            actionEvadeLeft.setState(ShipActionState.ON_COOLDOWN);
        } else if(shipX - (shipWidth / 2.0) > numCols / -2.0 + 1) {
            actionEvadeLeft.setState(ShipActionState.AVAILABLE);
        } else {
            actionEvadeLeft.setState(ShipActionState.UNAVAILABLE);
        }
        if(evadeTimer > 0) {
            actionEvadeRight.setState(ShipActionState.ON_COOLDOWN);
        } else if(shipX + (shipWidth / 2.0) < numCols / 2.0 - 1) {
            actionEvadeRight.setState(ShipActionState.AVAILABLE);
        } else {
            actionEvadeRight.setState(ShipActionState.UNAVAILABLE);
        }
    }

    public boolean isEvading() {
        return currentColX != -999;
    }

    public boolean isFinishedEvading() {
        return finishedEvading;
    }

    public double getEvasionPosition() {
        return currentColX;
    }

    /*public float getShipSpriteOffset() {
        return shipSpriteOffset;
    }*/

    public float getEvadeRotation() {
        return evadeRotation;
    }
}
