package game.gameView;

import game.View;
import org.lwjgl.opengl.GL11;

public class GameView extends View {
    public GameView(int width, int height) {
        super(width, height);
    }

    public void updateSelf(double delta) {

    }

    public void drawSelf() {
        mainFrameBuffer.bindFrameBuffer();
        GL11.glClearColor(0, 0, 0, 1);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        mainFrameBuffer.unbindFrameBuffer();
    }
}
