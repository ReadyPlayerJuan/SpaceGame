package main.views;

import main.game.boards.Board;
import main.game.boards.BoardCamera;
import main.game.enums.Team;
import main.game.ships.Ship;
import main.game.ships.TestShip;
import main.input.InputCode;
import rendering.FrameBuffer;
import rendering.Graphics;
import rendering.colors.ColorRegion;
import rendering.colors.ColorTheme;

import static  org.lwjgl.opengl.GL11.*;

public class GameView extends View {
    private Board currentBoard;
    private BoardCamera camera;
    private Ship playerShip, enemyShip; //maybe make this a blueprint, and then create a new player ship for each board

    private FrameBuffer layerFrameBuffer;

    public GameView(int width, int height) {
        super(width, height);

        layerFrameBuffer = new FrameBuffer(width, height);

        currentBoard = new Board(this, 6);
        camera = new BoardCamera(currentBoard);
        playerShip = new TestShip(this, Team.PLAYER);
        playerShip.setPosition(currentBoard.boardColumns/2 - 1);
        enemyShip = new TestShip(this, Team.ENEMY);
    }

    public void updateSelf(double delta) {
        currentBoard.update(delta);
        playerShip.update(currentBoard, delta);
        enemyShip.update(currentBoard, delta);
    }

    public void processInput(InputCode code) {
        playerShip.processInput(code);
        //System.out.println("GAMEVIEW RECIEVED CODE " + code);
    }

    public void drawSelf() {
        layerFrameBuffer.bindFrameBuffer();
        Graphics.clear(0, 0, 0, 1);

        Graphics.startDrawingColorRegion(ColorRegion.GAME_BACKGROUND);
        currentBoard.draw(camera);
        Graphics.startDrawingColorRegion(ColorRegion.GAME_PLAYER_SHIP);
        playerShip.draw(currentBoard, camera, width, height);
        Graphics.startDrawingColorRegion(ColorRegion.GAME_ENEMY_SHIP);
        enemyShip.draw(currentBoard, camera, width, height);
        Graphics.finishDrawingColorRegion();

        layerFrameBuffer.unbindFrameBuffer();



        mainFrameBuffer.bindFrameBuffer();
        Graphics.drawWithLayerFilter(layerFrameBuffer, ColorTheme.GAME);
        mainFrameBuffer.unbindFrameBuffer();
    }

    public void cleanUp() {
        super.cleanUp();
    }
}
