package main.game.ships;

import main.Util;
import main.game.boards.Board;
import main.game.boards.BoardCamera;
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

public class TestShip extends Ship {
    private final double evadeTimerMax = 0.5;
    private double evadeTimer = 0, evadePct = 0, prevEvadePct = 0;
    private int evadeDirection, evadeStartX, evadeEndX;
    private final float evadeRotationMax = 0.2f;
    private float evadeRotation = 0;

    private ShipAction actionEvadeLeft, actionEvadeRight;
    private ShipSection sectionLeft, sectionRight;

    public TestShip(GameView parentView, Team team) {
        super(parentView, team);

        shipColWidth = 2;
        collisionHeight = 0.5f;

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
            evadeRotation = (float)(1.0 - (2 * Math.abs((evadeTimer / evadeTimerMax) - 0.5))) * evadeDirection * evadeRotationMax;

            if(evadePct > 0.5 && prevEvadePct <= 0.5) {
                colX += evadeDirection;
            }
            spriteColX = Util.mix(evadeStartX, evadeEndX, evadePct);
        }

        //set ship action states
        //for example: if in leftmost column, disable evade left
        int boardWidth = currentBoard.boardColumns;
        if(evadeTimer > 0) {
            actionEvadeLeft.setState(ShipActionState.ON_COOLDOWN);
        } else if(colX > 0) {
            actionEvadeLeft.setState(ShipActionState.AVAILABLE);
        } else {
            actionEvadeLeft.setState(ShipActionState.UNAVAILABLE);
        }
        if(evadeTimer > 0) {
            actionEvadeRight.setState(ShipActionState.ON_COOLDOWN);
        } else if(colX + shipColWidth < boardWidth) {
            actionEvadeRight.setState(ShipActionState.AVAILABLE);
        } else {
            actionEvadeRight.setState(ShipActionState.UNAVAILABLE);
        }


        //update world position, etc
        super.update(currentBoard, delta);
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

    public void draw(Board currentBoard, BoardCamera camera, int viewWidth, int viewHeight) {
        float viewRatio = (float)viewHeight / viewWidth;
        float cameraZoomRatio = currentBoard.boardHeight / camera.visibleHeight;

        float screenUnitX = cameraZoomRatio * currentBoard.boardColumnWidth * viewRatio * 2;
        float screenUnitY = screenUnitX / viewRatio;

        float screenX = (spriteWorldX - camera.centerX) * cameraZoomRatio * viewRatio * 2;
        float screenY = (spriteWorldY - camera.centerY) * cameraZoomRatio * 2;

        float screenShipTopY = screenUnitX * 0.8f;
        float screenShipBotY = screenUnitX * -0.5f;
        float screenShipCornerX = screenUnitX * 0.8f;
        float screenShipCornerY = screenUnitX * -0.66f;
        float screenShipBotCornerX = screenUnitX * 0.5f;
        float screenShipBotCornerY = screenUnitX * -0.9f;

        float innerSpriteScale = 0.90f;

        Graphics.drawQuad(
                0, 0 + screenShipTopY,
                0 + screenShipCornerX, 0 + screenShipCornerY,
                0 + screenShipBotCornerX, 0 + screenShipBotCornerY,
                0, 0 + screenShipBotY,

                screenX, screenY, -evadeRotation, 1.0f, spriteFlipY / viewRatio,
                screenX, screenY, evadeRotation, -1.0f, spriteFlipY / viewRatio,

                screenX, screenY, -evadeRotation, 1.0f * innerSpriteScale, spriteFlipY * innerSpriteScale / viewRatio,
                screenX, screenY, evadeRotation, -1.0f * innerSpriteScale, spriteFlipY * innerSpriteScale / viewRatio);
    }
}
