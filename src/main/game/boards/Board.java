package main.game.boards;

import main.views.GameView;
import static org.lwjgl.opengl.GL11.*;

public class Board {
    private GameView parentView;
    public int numColumns;

    private final double COLUMN_ASPECT_RATIO = 0.15;
    private final float LINE_WIDTH = 3.0f;
    private int viewWidth, viewHeight;
    public int columnWidth, columnHeight;

    public Board(GameView parentVew, int numColumns) {
        this.parentView = parentVew;
        this.numColumns = numColumns;

        viewWidth = parentVew.getWidth();
        viewHeight = parentVew.getHeight();

        columnHeight = viewHeight;
        columnWidth = (int)(columnHeight * COLUMN_ASPECT_RATIO);
        if(columnWidth > viewWidth) {
            columnWidth = viewWidth;
            columnHeight = (int)(columnWidth / COLUMN_ASPECT_RATIO);
        }
    }

    public void update(double delta) {

    }

    public void draw() {
        glBegin(GL_QUADS);
        glColor4f(1, 1, 1, 1);
        for(int i = 0; i < numColumns+1; i++) {
            float colx = i - ((numColumns) / 2.0f);
            glVertex2f(viewWidth/2 + colx * columnWidth - LINE_WIDTH/2, 0);
            glVertex2f(viewWidth/2 + colx * columnWidth - LINE_WIDTH/2, columnHeight);
            glVertex2f(viewWidth/2 + colx * columnWidth + LINE_WIDTH/2, columnHeight);
            glVertex2f(viewWidth/2 + colx * columnWidth + LINE_WIDTH/2, 0);
        }
        glEnd();
    }
}
