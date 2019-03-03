package main.views;

import main.game.boards.Board;
import main.game.ships.Ship;
import org.lwjgl.opengl.GL11;

public class GameView extends View {
    public Board currentBoard;
    private Ship playerShip; //maybe make this a blueprint, and then create a new player ship for each board

    public GameView(int width, int height) {
        super(width, height);

        currentBoard = new Board(this, 4);
    }

    public void updateSelf(double delta) {
        currentBoard.update(delta);
    }

    public void drawSelf() {
        mainFrameBuffer.bindFrameBuffer();
        GL11.glClearColor(0, 0, 0, 1);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        currentBoard.draw();
        mainFrameBuffer.unbindFrameBuffer();
    }
}
