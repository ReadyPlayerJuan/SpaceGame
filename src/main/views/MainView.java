package main.views;

import main.input.InputCode;
import main.input.InputCodeBuilder;
import rendering.FrameBuffer;
import rendering.Graphics;
import rendering.fonts.TrueTypeFont;

import static org.lwjgl.opengl.GL11.*;

public class MainView extends View {
    public View gameView;

    private FrameBuffer layerFrameBuffer;

    public MainView(int width, int height) {
        super(width, height);

        layerFrameBuffer = new FrameBuffer(width, height);

        //mainFrameBuffer.setAutomaticOrtho(false);

        gameView = new GameView(width, height);
        addSubView(gameView);

        gameView.setFocused(true); //gameview recieves input
    }

    public void updateSelf(double delta) {

    }

    public void processInput(InputCode code) {

    }

    public void drawSelf() {
        mainFrameBuffer.bindFrameBuffer();
        //Graphics.setRenderTarget(mainFrameBuffer);
        glClearColor(0f, 0f, 1f, 1);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        //gameView.drawMainView(width/2, height/2, width, height);
        gameView.getMainFrameBuffer().draw(width/2, height/2);

        glColor3f(1f, 0f, 1f);
        Graphics.debugFont.drawText("FPS " + Graphics.getFps(), 0, height-24);
        Graphics.debugFont.drawText(InputCodeBuilder.debugString1, 0, height-24*2);
        Graphics.debugFont.drawText(InputCodeBuilder.debugString2, 0, height-24*3);

        mainFrameBuffer.unbindFrameBuffer();



        //mainFrameBuffer.bindFrameBuffer();

        //Graphics.layeredColorShader.drawFrameBuffer(layerFrameBuffer);

        //mainFrameBuffer.unbindFrameBuffer();
    }

    public void cleanUp() {
        super.cleanUp();
    }
}
