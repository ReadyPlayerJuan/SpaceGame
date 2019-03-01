package game;

import rendering.FrameBuffer;

import java.util.LinkedList;

public abstract class View {
    protected int width, height;
    protected FrameBuffer mainFrameBuffer;
    protected LinkedList<View> subViews;

    public View(int width, int height) {
        this.width = width;
        this.height = height;
        mainFrameBuffer = new FrameBuffer(width, height, FrameBuffer.NONE);
        subViews = new LinkedList<View>();
    }

    public abstract void update(double delta);
    public abstract void draw();
}
