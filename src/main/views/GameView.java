package main.views;

import main.game.boards.Board;
import main.game.enums.Team;
import main.game.ships.Ship;
import main.game.ships.TestShip;
import main.input.InputCode;
import org.lwjgl.opengl.GL11;

public class GameView extends View {
    private Board currentBoard;
    private Ship playerShip; //maybe make this a blueprint, and then create a new player ship for each board

    public GameView(int width, int height) {
        super(width, height);

        currentBoard = new Board(this, 4);
        playerShip = new TestShip(this, Team.PLAYER);
    }

    public void updateSelf(double delta) {
        currentBoard.update(delta);
        playerShip.update(currentBoard, delta);
    }

    public void processInput(InputCode code) {
        playerShip.processInput(code);
        System.out.println("GAMEVIEW RECIEVED CODE " + code);
    }

    public void drawSelf() {
        mainFrameBuffer.bindFrameBuffer();
        GL11.glClearColor(0, 0, 0, 1);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        currentBoard.draw();
        playerShip.draw(currentBoard, width, height);
        mainFrameBuffer.unbindFrameBuffer();
    }
}
