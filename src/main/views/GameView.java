package main.views;

import main.game.boards.Board;
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
    private Ship playerShip; //maybe make this a blueprint, and then create a new player ship for each board

    private FrameBuffer layerFrameBuffer;

    public GameView(int width, int height) {
        super(width, height);

        layerFrameBuffer = new FrameBuffer(width, height);

        currentBoard = new Board(this, 4);
        playerShip = new TestShip(this, Team.PLAYER);
    }

    public void updateSelf(double delta) {
        currentBoard.update(delta);
        playerShip.update(currentBoard, delta);
    }

    public void processInput(InputCode code) {
        playerShip.processInput(code);
        //System.out.println("GAMEVIEW RECIEVED CODE " + code);
    }

    public void drawSelf() {
        layerFrameBuffer.bindFrameBuffer();
        //WindowManager.setRenderTarget(mainFrameBuffer);
        glClearColor(0, 0, 0, 1);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        Graphics.startDrawingColorRegion(ColorRegion.GAME_BACKGROUND);
        currentBoard.draw();
        Graphics.startDrawingColorRegion(ColorRegion.GAME_PLAYER_SHIP);
        playerShip.draw(currentBoard, width, height);
        Graphics.finishDrawingColorRegion();

        layerFrameBuffer.unbindFrameBuffer();



        mainFrameBuffer.bindFrameBuffer();

        //layerFrameBuffer.draw(0, 0, 2, 2);
        Graphics.drawWithLayerFilter(layerFrameBuffer, ColorTheme.GAME);

        mainFrameBuffer.unbindFrameBuffer();
    }

    public void cleanUp() {
        super.cleanUp();
    }
}
