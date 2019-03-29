package main.game.boards;

public class BoardVector {
    private boolean refreshed = true;
    private double worldX, worldY;
    private float screenX, screenY;

    public BoardVector(double worldX, double worldY) {
        this.worldX = worldX;
        this.worldY = worldY;
    }

    public void setPosition(double x, double y) {
        worldX = x;
        worldY = y;
        refreshed = false;
    }

    public void setX(double x) {
        worldX = x;
        refreshed = false;
    }

    public void setY(double y) {
        worldY = y;
        refreshed = false;
    }

    public void refresh(BoardCamera camera) {
        if(!refreshed) {
            refreshed = true;

            float zoomRatio = camera.getZoomRatio();
            float viewRatio = camera.getViewRatio();
            screenX = (float)(worldX - camera.getCenterX()) * zoomRatio * viewRatio * 2;
            screenY = (float)(worldY - camera.getCenterY()) * zoomRatio * 2;
        }
    }

    public double getWorldX() {
        return worldX;
    }

    public double getWorldY() {
        return worldY;
    }

    public float getScreenX() {
        if(!refreshed)
            System.out.println("WARNING: GETTING SCREEN POSITION BEFORE REFRESHING!!!");
        return screenX;
    }

    public float getScreenY() {
        if(!refreshed)
            System.out.println("WARNING: GETTING SCREEN POSITION BEFORE REFRESHING!!!");
        return screenY;
    }

    public String toString() {

        return "(" + worldX + ", " + worldY + ")";
    }
}
