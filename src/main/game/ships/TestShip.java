package main.game.ships;

import main.Util;
import main.game.boards.Board;
import main.game.boards.BoardCamera;
import main.game.enums.ShipActionType;
import main.game.enums.Team;
import main.game.enums.Direction;
import main.game.ships.systems.ShipEvasion;
import main.game.weapons.TestWeapon1;
import main.game.weapons.Weapon;
import main.input.InputCode;
import main.input.InputType;
import main.views.GameView;
import rendering.Graphics;

public class TestShip extends Ship {
    private final double evadeTimerMax = 0.5;
    private final float evadeRotationMax = 0.2f;

    private ShipEvasion evasionSystem;

    private ShipAction actionEvadeLeft, actionEvadeRight;
    private ShipSection sectionLeft, sectionRight;

    public TestShip(GameView parentView, Team team) {
        super(parentView, team);

        shipWidth = 2;
        collisionHeight = 0.5f;

        actionEvadeLeft = new ShipAction(ShipActionType.EVADE_LEFT,
                new InputCode(InputType.INPUT_SHORT, InputType.PAUSE_SHORT, InputType.INPUT_SHORT));
        actionEvadeRight = new ShipAction(ShipActionType.EVADE_RIGHT,
                new InputCode(InputType.INPUT_SHORT, InputType.PAUSE_SHORT, InputType.INPUT_SHORT, InputType.PAUSE_SHORT, InputType.INPUT_SHORT));

        evasionSystem = new ShipEvasion(evadeTimerMax, evadeRotationMax, actionEvadeLeft, actionEvadeRight);

        Weapon weaponLeft = new TestWeapon1(ShipActionType.FIRE_WEAPON_1,
                new InputCode(InputType.INPUT_LONG, InputType.PAUSE_SHORT, InputType.INPUT_SHORT));
        Weapon weaponRight = new TestWeapon1(ShipActionType.FIRE_WEAPON_2,
                new InputCode(InputType.INPUT_LONG, InputType.PAUSE_SHORT, InputType.INPUT_SHORT, InputType.PAUSE_SHORT, InputType.INPUT_SHORT));

        sectionLeft = new ShipSection(this, 0, 2, actionEvadeLeft);
        sectionRight = new ShipSection(this, 1, 2, actionEvadeRight);
        sectionLeft.setWeapons(new Weapon[] {
                weaponLeft,
        });
        sectionRight.setWeapons(new Weapon[] {
                weaponRight,
        });
        sectionLeft.setAdjacentSection(Direction.RIGHT, sectionRight);
        sectionRight.setAdjacentSection(Direction.LEFT, sectionLeft);
        this.sections = new ShipSection[] {sectionLeft, sectionRight};

        focusedSection = 0;

        initDraw();
    }

    public void update(Board currentBoard, double delta) {
        updateShipSections(delta);

        evasionSystem.update(delta, currentBoard.boardColumns, shipX, shipWidth);
        shipX += evasionSystem.shipShouldMove();
        spriteX = shipX + evasionSystem.getShipSpriteOffset();

        //update world position, etc
        super.update(currentBoard, delta);
    }

    public void processAction(ShipAction action) {
        if(evasionSystem.processInput(action)) return;

        switch(action.getAction()) {
            case FIRE_WEAPON_1:
                System.out.println("fire 1");
                break;
            case FIRE_WEAPON_2:
                System.out.println("fire 2");
                break;
        }
    }


    private float[] shipVertices;
    private int[] shipIndices;
    private float[] windowVertices;
    private int[] windowIndices;
    private float[] squareVertices;

    private final float shipInnerSpriteScale = 0.97f;
    private final float shipWobbleAmount = 0.035f;
    private final float windowWobbleAmount = 0.020f;
    private final float squareWobbleAmount = 0.020f;

    private void initDraw() {
        float screenShipTopY = 0.8f;
        float screenShipBotY = -0.5f;
        float screenShipCornerX = 0.8f;
        float screenShipCornerY = -0.66f;
        float screenShipBotCornerX = 0.5f;
        float screenShipBotCornerY = -0.9f;

        shipVertices = new float[] {
                0, screenShipTopY,
                screenShipCornerX, screenShipCornerY,
                screenShipBotCornerX, screenShipBotCornerY,
                0, screenShipBotY,
                -screenShipBotCornerX, screenShipBotCornerY,
                -screenShipCornerX, screenShipCornerY,
        };
        shipIndices = new int[] {
                0, 1, 2,
                0, 2, 3,
                0, 3, 4,
                0, 4, 5
        };

        float windowSize = 0.40f;
        float windowDY = screenShipTopY * 0.20f;
        float windowCXY = screenShipTopY * 0.11f;
        float windowTopY = screenShipTopY - windowDY;
        float windowCornerX = (screenShipCornerX - 0) * windowSize + 0;
        float windowCornerY = (screenShipCornerY - screenShipTopY) * windowSize + screenShipTopY - windowDY;
        float windowCornerBotX = windowCornerX - windowCXY;
        float windowCornerBotY = windowCornerY - windowCXY;

        windowVertices = new float[] {
                0, windowTopY,
                windowCornerX, windowCornerY,
                windowCornerBotX, windowCornerBotY,
                -windowCornerBotX, windowCornerBotY,
                -windowCornerX, windowCornerY,
        };
        windowIndices = new int[] {
                0, 1, 2,
                0, 2, 3,
                0, 3, 4
        };

        float squareUDX = (0 - screenShipCornerX) * 0.11f;
        float squareUDY = (screenShipTopY - screenShipCornerY) * 0.11f;
        float squareRDX = (screenShipCornerX - screenShipBotCornerX) * 0.20f;
        float squareRDY = (screenShipCornerY - screenShipBotCornerY) * 0.20f;

        float squareROff = 0.8f;
        float squareUOff1 = 0.5f;
        float squareUOff2 = 1.9f;

        float squareCornerX = screenShipCornerX + squareUDX*squareUOff1 - squareRDX*squareROff;
        float squareCornerY = screenShipCornerY + squareUDY*squareUOff1 - squareRDY*squareROff;

        float squareCornerX2 = screenShipCornerX + squareUDX*squareUOff2 - squareRDX*squareROff;
        float squareCornerY2 = screenShipCornerY + squareUDY*squareUOff2 - squareRDY*squareROff;

        squareVertices = new float[] {
                squareCornerX, squareCornerY,
                squareCornerX - squareRDX, squareCornerY - squareRDY,
                squareCornerX + squareUDX, squareCornerY + squareUDY,
                squareCornerX - squareRDX + squareUDX, squareCornerY - squareRDY + squareUDY,

                squareCornerX2, squareCornerY2,
                squareCornerX2 - squareRDX, squareCornerY2 - squareRDY,
                squareCornerX2 + squareUDX, squareCornerY2 + squareUDY,
                squareCornerX2 - squareRDX + squareUDX, squareCornerY2 - squareRDY + squareUDY
        };
    }

    public void draw(Board currentBoard, BoardCamera camera, int viewWidth, int viewHeight) {
        float viewRatio = (float)viewHeight / viewWidth;
        float cameraZoomRatio = currentBoard.boardHeight / camera.visibleHeight;

        float screenUnitX = cameraZoomRatio * currentBoard.boardColumnWidth * viewRatio * 2;
        //float screenUnitY = screenUnitX / viewRatio;

        float rotation = evasionSystem.getEvadeRotation();

        float screenX = (spriteWorldX - camera.centerX) * cameraZoomRatio * viewRatio * 2;
        float screenY = (spriteWorldY - camera.centerY) * cameraZoomRatio * 2;

        Graphics.drawTriangles(shipVertices, shipIndices,
                screenX, screenY,
                -rotation, 1.0f * screenUnitX, spriteFlipY * screenUnitX / viewRatio,
                screenUnitX * shipWobbleAmount, 0.3983f,

                screenX, screenY,
                -rotation, 1.0f * screenUnitX * shipInnerSpriteScale, spriteFlipY * screenUnitX * shipInnerSpriteScale / viewRatio,
                screenUnitX * shipWobbleAmount, 0.5216f);


        Graphics.drawTriangles(windowVertices, windowIndices,
                screenX, screenY,
                -rotation, 1.0f * screenUnitX, spriteFlipY * screenUnitX / viewRatio,
                windowWobbleAmount * screenUnitX, 0.7101f,

                screenX, screenY,
                -rotation, 1.0f * 0.90f * screenUnitX, spriteFlipY * 0.90f * screenUnitX / viewRatio,
                windowWobbleAmount * screenUnitX, 0.4139f);


        Graphics.drawQuads(squareVertices,
                screenX, screenY,
                -rotation, 1.0f * screenUnitX, spriteFlipY * screenUnitX / viewRatio,
                squareWobbleAmount * screenUnitX, 0.8296f,

                screenX, screenY,
                rotation, -1.0f * screenUnitX, spriteFlipY * screenUnitX / viewRatio,
                squareWobbleAmount * screenUnitX, 0.1296f
        );
    }
}
