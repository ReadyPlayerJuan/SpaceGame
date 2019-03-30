package main.game.boards;

public class BoardCamera {
    private Board board;

    private float centerX, centerY;
    private float visibleHeight;

    private int viewWidth, viewHeight;
    private float viewRatio;
    private float zoomRatio;
    private float screenUnitX; //column width in screen size
    private float screenUnitY; //column width scaled by aspect ratio for verticality

    private final float baseZoom = 1.35f;

    public BoardCamera(Board board, int viewWidth, int viewHeight) {
        this.board = board;
        this.viewWidth = viewWidth;
        this.viewHeight = viewHeight;

        centerX = 0.0f;
        centerY = 0.0f;
        visibleHeight = board.getPlayableHeight() * baseZoom;
        viewRatio = (float)viewHeight / viewWidth;
        zoomRatio = 1.0f / visibleHeight;
        screenUnitX = zoomRatio * board.getColumnWidth() * viewRatio * 2;
        screenUnitY = screenUnitX / viewRatio;
    }

    public float getCenterX() {
        return centerX;
    }

    public float getCenterY() {
        return centerY;
    }

    public float getVisibleHeight() {
        return visibleHeight;
    }

    public float getViewRatio() {
        return viewRatio;
    }

    public float getZoomRatio() {
        return zoomRatio;
    }

    public float getScreenUnitX() {
        return screenUnitX;
    }

    public float getScreenUnitY() {
        return screenUnitY;
    }

    public int getViewWidth() {
        return viewWidth;
    }

    public int getViewHeight() {
        return viewHeight;
    }
}
