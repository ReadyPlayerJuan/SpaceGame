package main.game.ships.test_ship;

import main.game.boards.Board;
import main.game.boards.BoardCamera;
import main.game.collision.Hitbox;
import main.game.collision.projectiles.Projectile;
import main.game.enums.ShipActionType;
import main.game.enums.Team;
import main.game.ships.Ship;
import main.game.ships.ShipAction;
import main.game.ships.ShipSection;
import main.game.ships.systems.ShipEvasion;
import main.game.weapons.TestWeapon1;
import main.game.weapons.Weapon;
import main.input.InputCode;
import main.input.InputType;
import rendering.Graphics;

public class TestShip extends Ship {
    private final int SHIP_WIDTH = 2;
    private final double EVADE_SPEED = 0.5;
    private final float EVADE_ROTATION = 0.2f;
    private final float COLLISION_HEIGHT = 0.5f;

    private ShipEvasion evasionSystem;
    private double approxColX = 0;

    private TestWeapon1 weaponLeft, weaponRight;
    private ShipAction actionEvadeLeft, actionEvadeRight;
    private ShipSection sectionLeft, sectionRight;

    public TestShip(Board board, Team team) {
        super(board, team);

        actionEvadeLeft = new ShipAction(ShipActionType.EVADE_LEFT,
                new InputCode(InputType.INPUT_SHORT, InputType.PAUSE_SHORT, InputType.INPUT_SHORT));
        actionEvadeRight = new ShipAction(ShipActionType.EVADE_RIGHT,
                new InputCode(InputType.INPUT_SHORT, InputType.PAUSE_SHORT, InputType.INPUT_SHORT, InputType.PAUSE_SHORT, InputType.INPUT_SHORT));

        evasionSystem = new ShipEvasion(EVADE_SPEED, EVADE_ROTATION, actionEvadeLeft, actionEvadeRight);

        weaponLeft = new TestWeapon1(ShipActionType.FIRE_WEAPON_1,
                new InputCode(InputType.INPUT_LONG, InputType.PAUSE_SHORT, InputType.INPUT_SHORT));
        weaponRight = new TestWeapon1(ShipActionType.FIRE_WEAPON_2,
                new InputCode(InputType.INPUT_LONG, InputType.PAUSE_SHORT, InputType.INPUT_SHORT, InputType.PAUSE_SHORT, InputType.INPUT_SHORT));

        sectionLeft = new ShipSection(this, board,
                new Hitbox(null, team, 0, 0, 1, 1),
                2, actionEvadeLeft) {

            @Override
            public void collide(Projectile p) {
                System.out.println("COLLISION LEFT!!!!");
            }
        };
        sectionRight = new ShipSection(this, board,
                new Hitbox(null, team, 0, 0, 1, 1),
                2, actionEvadeRight) {

            @Override
            public void collide(Projectile p) {
                System.out.println("COLLISION RIGHT!!!!");
            }
        };
        sectionLeft.setWeapons(new Weapon[] {
                weaponLeft,
        });
        sectionRight.setWeapons(new Weapon[] {
                weaponRight,
        });
        //sectionLeft.setAdjacentSection(Direction.RIGHT, sectionRight);
        //sectionRight.setAdjacentSection(Direction.LEFT, sectionLeft);
        sections = new ShipSection[] {sectionLeft, sectionRight};

        focusedSection = 0;
        sectionLeft.setFocused(true);
        sectionRight.setFocused(true);

        initDraw();
    }

    @Override
    public void update(double delta) {
        approxColX = Math.round((position.getWorldX() / board.getColumnWidth()) * 2.0) / 2.0;
        System.out.println(position + "   " + approxColX);

        //update evasion and let evasion system control position if evading
        evasionSystem.update(delta, board.getNumColumns(), approxColX, shipWidth);
        if(evasionSystem.isEvading())
            position.setX(evasionSystem.getEvasionPosition());

        //when done evading, slot into the board columns
        if(evasionSystem.isFinishedEvading())
            position.setX(Math.round((position.getWorldX() / board.getColumnWidth()) * 2.0) / 2.0);

        //update sections, hitboxes, etc
        updateShipSections(delta);
    }

    public void processAction(ShipAction action) {
        if(evasionSystem.processInput(action, approxColX)) return;

        switch(action.getAction()) {
            case FIRE_WEAPON_1:
                System.out.println("fire 1");
                //weaponLeft.fire();
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

    public void draw(BoardCamera camera) {
        float viewRatio = camera.getViewRatio();
        float screenUnitX = camera.getScreenColumnWidth();

        float rotation = evasionSystem.getEvadeRotation();

        position.refresh(camera);
        float screenX = position.getScreenX();
        float screenY = position.getScreenY();

        Graphics.drawTriangles(shipVertices, shipIndices,
                screenX, screenY,
                -rotation, 1.0f * screenUnitX, spriteFlipY * screenUnitX / viewRatio,
                screenUnitX * shipWobbleAmount, 0.3983f,

                screenX, screenY,
                -rotation, 1.0f * screenUnitX * shipInnerSpriteScale, spriteFlipY * screenUnitX * shipInnerSpriteScale / viewRatio,
                screenUnitX * shipWobbleAmount, 0.5216f
        );

        Graphics.drawTriangles(windowVertices, windowIndices,
                screenX, screenY,
                -rotation, 1.0f * screenUnitX, spriteFlipY * screenUnitX / viewRatio,
                windowWobbleAmount * screenUnitX, 0.7101f,

                screenX, screenY,
                -rotation, 1.0f * 0.90f * screenUnitX, spriteFlipY * 0.90f * screenUnitX / viewRatio,
                windowWobbleAmount * screenUnitX, 0.4139f
        );

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
