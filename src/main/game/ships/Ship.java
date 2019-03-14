package main.game.ships;

import main.game.boards.Board;
import main.game.boards.BoardCamera;
import main.game.enums.ShipActionState;
import main.game.enums.Team;
import main.input.InputCode;
import main.views.GameView;
import main.views.View;

import java.util.ArrayList;

public abstract class Ship {
    protected GameView parentView;

    protected int colX, shipColWidth;
    protected double spriteColX;

    protected float spriteFlipY;
    protected float spriteWorldX, spriteWorldY, collisionHeight;
    protected Team team;

    protected ShipSection[] sections;
    protected int focusedSection;

    protected ArrayList<ShipAction> globalActions = new ArrayList<>();
    protected ArrayList<ShipAction> availableActions = new ArrayList<>();

    public Ship(GameView parentView, Team team) {
        this.parentView = parentView;
        this.team = team;

        colX = 0;
        spriteColX = 0;

        if(team == Team.PLAYER) {
            spriteFlipY = 1;
        } else {
            spriteFlipY = -1;
        }
    }

    public void update(Board currentBoard, double delta) {
        spriteWorldX = (float)(currentBoard.boardColumnWidth * (spriteColX + shipColWidth/2.0f + currentBoard.boardColumns/-2.0f));
        if(team == Team.PLAYER) {
            spriteWorldY = currentBoard.boardHeight / -2.0f;
        } else {
            spriteWorldY = currentBoard.boardHeight / 2.0f;
        }
    }

    protected void updateShipSections(double delta) {
        for(ShipSection s: sections) {
            s.update(delta);
        }
    }

    public void setPosition(int colX) {
        this.colX = colX;
        this.spriteColX = colX;
    }

    public abstract void draw(Board currentBoard, BoardCamera camera, int viewWidth, int viewHeight);

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
