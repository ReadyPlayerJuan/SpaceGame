package main.game.boards;

import main.views.GameView;
import rendering.Graphics;

import static org.lwjgl.opengl.GL11.*;

public class Board {
    private final float COLUMN_ASPECT_RATIO = 0.15f;
    private final float LINE_WIDTH_PIXELS = 3.0f;

    private GameView parentView;
    public int boardColumns;
    public float boardHeight = 1.0f;
    public float boardColumnWidth = COLUMN_ASPECT_RATIO;
    private float lineWidth;

    private int viewWidth, viewHeight;
    //private int columnWidth, columnHeight;

    public Board(GameView parentVew, int numColumns) {
        this.parentView = parentVew;
        this.boardColumns = numColumns;

        viewWidth = parentVew.getWidth();
        viewHeight = parentVew.getHeight();

        lineWidth = 2.0f * LINE_WIDTH_PIXELS / viewWidth;

        /*columnHeight = viewHeight;
        columnWidth = (int)(columnHeight * COLUMN_ASPECT_RATIO);
        if(columnWidth > viewWidth) {
            columnWidth = viewWidth;
            columnHeight = (int)(columnWidth / COLUMN_ASPECT_RATIO);
        }*/
    }

    public void update(double delta) {

    }

    public void draw(BoardCamera camera) {
        float viewRatio = (float)viewHeight / viewWidth;
        float cameraZoomRatio = (float)(this.boardHeight / camera.visibleHeight);
        float screenUnitX = cameraZoomRatio * boardColumnWidth * viewRatio * 2;
        //float screenUnitY = screenUnitX * viewRatio;

        Graphics.drawQuad(
                -1, -1,
                1, -1,
                -1, 1,
                1, 1);

        for(int i = 0; i < boardColumns+1; i++) {
            float colx = i - ((boardColumns) / 2.0f)  - (camera.centerX / boardColumnWidth);
            Graphics.drawQuad(
                    colx * screenUnitX - lineWidth/2, -1,
                    colx * screenUnitX - lineWidth/2, 1,
                    colx * screenUnitX + lineWidth/2, -1,
                    colx * screenUnitX + lineWidth/2, 1);
        }
    }
}
