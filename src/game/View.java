package game;

import rendering.FrameBuffer;

import java.util.LinkedList;

public abstract class View {
    public int draw_priority = 0;
    protected int width, height;
    protected boolean shouldBeDestroyed = false;
    protected boolean shouldSortSubViews = false;

    protected FrameBuffer mainFrameBuffer;
    protected LinkedList<View> subViews;

    public View(int width, int height) {
        this.width = width;
        this.height = height;
        mainFrameBuffer = new FrameBuffer(width, height, FrameBuffer.NONE);
        subViews = new LinkedList<View>();
    }

    public void update(double delta) {
        updateSubViews(delta);
        updateSelf(delta);
    }

    public void draw() {
        drawSubViews();
        drawSelf();
    }

    private void updateSubViews(double delta) {
        if(shouldSortSubViews) { sortSubViews(); }
        for(View v: subViews) {
            v.update(delta);
        }
    }

    private void drawSubViews() {
        if(shouldSortSubViews) { sortSubViews(); }
        for(View v: subViews) {
            v.draw();
        }
    }

    public abstract void updateSelf(double delta);
    public abstract void drawSelf();

    public void drawMainView(int x, int y, double scale) {
        mainFrameBuffer.draw(x, y, scale);
    }

    private void sortSubViews() {
        for(int i = 0; i < subViews.size()-1; i++) {
            for(int j = 0; j < i; j++) {
                if(subViews.get(j).draw_priority < subViews.get(j+1).draw_priority) {
                    subViews.add(j, subViews.remove(j+1));
                }
            }
        }
    }

    public boolean shouldBeDestroyed() {
        return shouldBeDestroyed;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void cleanUp() {
        mainFrameBuffer.cleanUp();
        for(View v: subViews) {
            v.cleanUp();
        }
    }
}
