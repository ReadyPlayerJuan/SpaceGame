package main.game.ships;

import main.game.boards.Board;
import main.game.enums.ShipActionState;
import main.game.enums.Team;
import main.input.InputCode;
import main.views.GameView;

import java.util.ArrayList;

public abstract class Ship {
    protected GameView parentView;

    protected int colX, shipWidth;
    protected double spriteColX;
    protected Team team;

    protected ShipSection[] sections;
    protected int focusedSection;

    protected ArrayList<ShipAction> globalActions = new ArrayList<>();
    protected ArrayList<ShipAction> availableActions = new ArrayList<>();

    public void update(Board currentBoard, double delta) {
    }

    protected void updateShipSections(double delta) {
        for(ShipSection s: sections) {
            s.update(delta);
        }
    }

    public abstract void draw(Board currentBoard, int viewWidth, int viewHeight);

    public void processInput(InputCode code) {
        updateAvailableActions();

        for(ShipAction a: availableActions) {
            if(code.equals(a.getCode())) {
                processAction(a);
                break;
            }
        }
    }

    private void updateAvailableActions() {
        availableActions.clear();
        for(ShipAction a: globalActions) {
            if(a.getState() == ShipActionState.AVAILABLE)
                availableActions.add(a);
        }
        for(ShipSection section: sections) {
            for(ShipAction a: section.getActions()) {
                if(a.getState() == ShipActionState.AVAILABLE)
                    availableActions.add(a);
            }
        }
    }

    public abstract void processAction(ShipAction action);

    public void setTeam(Team team) {
        this.team = team;
    }
}
