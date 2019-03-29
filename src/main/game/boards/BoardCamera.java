package main.game.boards;

public class BoardCamera {
    private Board board;

    private float centerX, centerY;
    private float visibleHeight;

    private int viewWidth, viewHeight;
    private float viewRatio;
    private float zoomRatio;
    private float screenColumnWidth;

    public BoardCamera(Board board, int viewWidth, int viewHeight) {
        this.board = board;
        this.viewWidth = viewWidth;
        this.viewHeight = viewHeight;

        centerX = 0.0f;
        centerY = 0.0f;
        visibleHeight = board.getPlayableHeight() * 1.5f;
        viewRatio = (float)viewHeight / viewWidth;
        zoomRatio = 1.0f / visibleHeight;
        screenColumnWidth = zoomRatio * board.getColumnWidth() * viewRatio * 2;
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

    public float getScreenColumnWidth() {
        return screenColumnWidth;
    }

    public int getViewWidth() {
        return viewWidth;
    }

    public int getViewHeight() {
        return viewHeight;
    }
}
