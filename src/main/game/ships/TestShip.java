package main.game.ships;

import main.Util;
import main.game.boards.Board;
import main.game.enums.ShipActionState;
import main.game.enums.ShipActionType;
import main.game.enums.Team;
import main.game.enums.Direction;
import main.game.weapons.TestWeapon1;
import main.game.weapons.Weapon;
import main.input.InputCode;
import main.input.InputType;
import main.views.GameView;
import rendering.Graphics;
import rendering.WindowManager;

import static org.lwjgl.opengl.GL11.*;

public class TestShip extends Ship {
    private final double evadeTimerMax = 0.5;
    private double evadeTimer = 0, evadePct = 0, prevEvadePct = 0;
    private int evadeDirection, evadeStartX, evadeEndX;

    private ShipAction actionEvadeLeft, actionEvadeRight;
    private ShipSection sectionLeft, sectionRight;

    public TestShip(GameView parentView, Team team) {
        this.parentView = parentView;
        this.team = team;

        colX = 0;
        spriteColX = 0;
        shipWidth = 2;

        actionEvadeLeft = new ShipAction(ShipActionType.EVADE_LEFT,
                new InputCode(InputType.INPUT_SHORT, InputType.PAUSE_SHORT, InputType.INPUT_SHORT));
        actionEvadeRight = new ShipAction(ShipActionType.EVADE_RIGHT,
                new InputCode(InputType.INPUT_SHORT, InputType.PAUSE_SHORT, InputType.INPUT_SHORT, InputType.PAUSE_SHORT, InputType.INPUT_SHORT));

        sectionLeft = new ShipSection(this, 0, 2, actionEvadeLeft);
        sectionRight = new ShipSection(this, 1, 2, actionEvadeRight);
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

    public void update(Board currentBoard, double delta) {
        updateShipSections(delta);

        //update systems and timers
        if(evadeTimer > 0) {
            evadeTimer = Math.max(0, evadeTimer - delta);
            prevEvadePct = evadePct;
            evadePct = Util.skewPctPow(1 - (evadeTimer / evadeTimerMax), 2.0);

            if(evadePct > 0.5 && prevEvadePct <= 0.5) {
                colX += evadeDirection;
            }
            spriteColX = Util.mix(evadeStartX, evadeEndX, evadePct);
        }

        //set ship action states
        //for example: if in leftmost column, disable evade left
        int boardWidth = currentBoard.numColumns;
        if(evadeTimer > 0) {
            actionEvadeLeft.setState(ShipActionState.ON_COOLDOWN);
        } else if(colX > 0) {
            actionEvadeLeft.setState(ShipActionState.AVAILABLE);
        } else {
            actionEvadeLeft.setState(ShipActionState.UNAVAILABLE);
        }
        if(evadeTimer > 0) {
            actionEvadeRight.setState(ShipActionState.ON_COOLDOWN);
        } else if(colX + shipWidth < boardWidth) {
            actionEvadeRight.setState(ShipActionState.AVAILABLE);
        } else {
            actionEvadeRight.setState(ShipActionState.UNAVAILABLE);
        }
    }

    public void processAction(ShipAction action) {
        switch(action.getAction()) {
            case EVADE_LEFT:
                evadeTimer = evadeTimerMax;
                evadeDirection = -1;
                evadeStartX = colX;
                evadeEndX = evadeStartX + evadeDirection;
                evadePct = 0;
                break;
            case EVADE_RIGHT:
                evadeTimer = evadeTimerMax;
                evadeDirection = 1;
                evadeStartX = colX;
                evadeEndX = evadeStartX + evadeDirection;
                evadePct = 0;
                break;
        }
    }

    public void draw(Board currentBoard, int viewWidth, int viewHeight) {
        //glBegin(GL_QUADS);
        glColor4f(1, 0, 0, 1);
        float shipX = viewWidth/2f + currentBoard.columnWidth * ((float)spriteColX + shipWidth/2.0f + currentBoard.numColumns/-2.0f);
        float shipY = viewHeight/4f;
        float shipW = currentBoard.columnWidth * shipWidth * 0.8f;
        float shipH = shipW * 0.75f;
        Graphics.drawQuad(
                shipX - shipW/2, shipY - shipH/2,
                shipX + shipW/2, shipY - shipH/2,
                shipX + shipW/2, shipY + shipH/2,
                shipX - shipW/2, shipY + shipH/2);
        Graphics.drawQuad(
                shipX - shipW/2+20, shipY - shipH/2+20,
                shipX + shipW/2+20, shipY - shipH/2+20,
                shipX + shipW/2+20, shipY + shipH/2+20,
                shipX - shipW/2+20, shipY + shipH/2+20);
        /*glVertex2d(shipX - shipW/2, shipY - shipH/2);
        glVertex2d(shipX + shipW/2, shipY - shipH/2);
        glVertex2d(shipX + shipW/2, shipY + shipH/2);
        glVertex2d(shipX - shipW/2, shipY + shipH/2);
        glEnd();*/
        //WindowManager.debugFont.drawText("TEST", (int)shipX, (int)shipY);
    }
}
