package game;

import org.lwjgl.opengl.GL11;

public class MainView extends View {
    public MainView(int width, int height) {
        super(width, height);
    }

    public void update(double delta) {

    }

    public void draw() {
        //mainFrameBuffer.bindFrameBuffer();
        GL11.glClearColor(1, 1, 1, 1);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        //mainFrameBuffer.unbindFrameBuffer();
    }
}
