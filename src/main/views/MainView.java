package main.views;

import main.input.InputCode;
import main.input.InputCodeBuilder;
import main.input.InputType;
import org.lwjgl.opengl.GL11;
import rendering.WindowManager;
import rendering.fonts.TrueTypeFont;

public class MainView extends View {
    private View gameView;

    private TrueTypeFont debugFont;

    public MainView(int width, int height) {
        super(width, height);

        try {
            debugFont = new TrueTypeFont("monofonto.ttf", 24);
            debugFont.drawFontTexture(0, 0);
        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }

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
        GL11.glClearColor(0f, 0f, 0f, 1);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        gameView.drawMainView(width/2, height/2, 1);

        GL11.glColor3f(1f, 1f, 1f);
        debugFont.drawText("FPS " + WindowManager.getFps(), 0, height-24);


        debugFont.drawText(InputCodeBuilder.debugString1, 0, height-24*2);
        debugFont.drawText(InputCodeBuilder.debugString2, 0, height-24*3);

        mainFrameBuffer.unbindFrameBuffer();
    }
}
