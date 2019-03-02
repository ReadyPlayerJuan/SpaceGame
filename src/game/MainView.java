package game;

import game.gameView.GameView;
import org.lwjgl.opengl.GL11;
import rendering.WindowManager;
import rendering.fonts.TrueTypeFont;

public class MainView extends View {
    private View gameView;

    private TrueTypeFont debugFont;

    public MainView(int width, int height) {
        super(width, height);

        try {
            debugFont = new TrueTypeFont("monofonto.ttf", 64);
            debugFont.drawFontTexture(0, 0);
        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }

        gameView = new GameView(width/2, height/2);
        subViews.add(gameView);
    }

    public void updateSelf(double delta) {

    }

    public void drawSelf() {
        mainFrameBuffer.bindFrameBuffer();
        GL11.glClearColor(1, 1, 1, 1);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        gameView.drawMainView(width/2, height/2, 1);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor3f(0.5f, 0.5f, 0.5f);
        debugFont.drawText("FPS: " + WindowManager.getFps(), 10, 10);
        GL11.glColor3f(1f, 1f, 1f);

        mainFrameBuffer.unbindFrameBuffer();
    }
}
