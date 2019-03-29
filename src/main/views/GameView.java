package main.views;

import main.game.boards.Board;
import main.game.boards.BoardCamera;
import main.game.enums.Team;
import main.game.ships.Ship;
import main.game.ships.test_ship.TestShip;
import main.input.InputCode;
import rendering.FrameBuffer;
import rendering.Graphics;
import rendering.colors.ColorRegion;
import rendering.colors.ColorTheme;

public class GameView extends View {
    private Board board;
    private BoardCamera camera;
    private Ship playerShip, enemyShip; //maybe make this a blueprint, and then create a new player ship for each board

    private FrameBuffer layerFrameBuffer;

    public GameView(int width, int height) {
        super(width, height);

        layerFrameBuffer = new FrameBuffer(width, height);

        board = new Board(this, 6, 6.0f);
        camera = new BoardCamera(board, width, height);

        playerShip = new TestShip(board, Team.PLAYER);
        //playerShip.setPosition(board.getBoardWidth()/2 - 1);
        board.addShip(playerShip);

        enemyShip = new TestShip(board, Team.ENEMY);
        board.addShip(enemyShip);
    }

    public void updateSelf(double delta) {
        playerShip.update(delta);
        enemyShip.update(delta);
        board.update(delta);
    }

    public void processInput(InputCode code) {
        playerShip.processInput(code);
        //System.out.println("GAMEVIEW RECIEVED CODE " + code);
    }

    public void drawSelf() {
        layerFrameBuffer.bindFrameBuffer();
        Graphics.clear(0, 0, 0, 1);

        Graphics.startDrawingColorRegion(ColorRegion.GAME_BACKGROUND);
        board.draw(camera);
        Graphics.startDrawingColorRegion(ColorRegion.GAME_PLAYER_SHIP);
        playerShip.draw(camera);
        Graphics.startDrawingColorRegion(ColorRegion.GAME_ENEMY_SHIP);
        enemyShip.draw(camera);
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
