package main.game.ships;

import main.game.boards.Board;
import main.game.boards.BoardCamera;
import main.game.boards.BoardVector;
import main.game.enums.ShipActionState;
import main.game.enums.Team;
import main.input.InputCode;
import main.views.GameView;

import java.util.ArrayList;

public abstract class Ship {
    //protected GameView parentView;
    protected Board board;

    protected int shipWidth;
    protected BoardVector position;
    protected float spriteFlipY;
    protected Team team;
    protected ShipSection[] sections;
    protected int focusedSection;

    protected ArrayList<ShipAction> globalActions = new ArrayList<>();
    protected ArrayList<ShipAction> availableActions = new ArrayList<>();

    public Ship(Board board, Team team) {
        this.board = board;
        this.team = team;

        position = new BoardVector(0, 0);

        if(team == Team.PLAYER) {
            spriteFlipY = 1;
            position.setY(board.getPlayableHeight() / -2);
        } else {
            spriteFlipY = -1;
            position.setY(board.getPlayableHeight() / 2);
        }

        if(board.getNumColumns() % 2 == 1) {
            position.setX(0.5 * spriteFlipY);
        }
    }

    public abstract void update(double delta);

    protected void updateShipSections(double delta) {
        for(ShipSection s: sections) {
            s.update(delta);
        }
    }

    public void setPosition(int colX) {
        //this.shipColX = colX;
        //this.shipWorldX = colX;
    }

    public abstract void draw(BoardCamera camera);

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

    public BoardVector getPosition() {
        return position;
    }

    public ShipSection[] getSections() {
        return sections;
    }

    public abstract void processAction(ShipAction action);

    public void setTeam(Team team) {
        this.team = team;
    }
}
