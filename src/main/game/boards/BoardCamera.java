package main.game.boards;

public class BoardCamera {
    private Board board;
    public float centerX, centerY;
    public float visibleHeight;

    public BoardCamera(Board board) {
        this.board = board;

        centerX = 0.0f;
        centerY = 0.0f;
        visibleHeight = board.boardHeight * 1.5f;
    }
}
