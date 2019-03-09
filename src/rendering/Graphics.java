package rendering;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import rendering.colors.ColorRegion;
import rendering.colors.ColorTheme;
import rendering.colors.ColorThemeManager;
import rendering.shaders.layered_color.LayeredColorShader;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;

public class Graphics {
    public static LayeredColorShader layeredColorShader;

    public static final int NUM_COLOR_REGIONS = 5;
    public static final int NUM_COLORS_PER_REGION = 5;
    private static boolean drawingColorRegions = false;
    private static int colorShadeFactor = 1;
    private static float currentRegionShade = 0;

    private static final int MAX_QUADS = 100;
    private static final FloatBuffer buffer = BufferUtils.createFloatBuffer(MAX_QUADS * 8);
    private static float[] quadVboData;
    private static int arrayIndex = 0;
    private static int vao;
    private static int vbo;

    public static void init() {
        layeredColorShader = new LayeredColorShader();
        layeredColorShader.start();
        layeredColorShader.loadColorTheme(ColorThemeManager.getColorTheme(ColorTheme.GAME));
        layeredColorShader.stop();

        /*float n = 99;
        float[] data = new float[] {-n, -n, n, -n, n, n, -n, n};

        vao = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vao);

        vbo = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, data.length * 4, GL15.GL_STREAM_DRAW);
        GL20.glVertexAttribPointer(0, 2, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL30.glBindVertexArray(0);


        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer.capacity() * 4, GL15.GL_STREAM_DRAW);
        GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, 0, buffer);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);*/



        vao = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vao);

        vbo = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, MAX_QUADS * 8 * 4, GL15.GL_STREAM_DRAW);
        GL20.glVertexAttribPointer(0, 2, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL30.glBindVertexArray(0);
    }

    public static void drawWithLayerFilter(FrameBuffer frameBuffer, ColorTheme theme) {
        layeredColorShader.start();
        layeredColorShader.loadColorTheme(ColorThemeManager.getColorTheme(theme));

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(-1, 1, -1, 1, 0, 1);
        frameBuffer.draw(0, 0, 2, 2);

        layeredColorShader.stop();
    }

    public static void startDrawingColorRegion(ColorRegion region) {
        if(drawingColorRegions) finishDrawingColorRegion();

        currentRegionShade = (float)region.i / NUM_COLORS_PER_REGION;
        drawingColorRegions = true;

        arrayIndex = 0;
        quadVboData = new float[MAX_QUADS * 8];
    }

    public static void finishDrawingColorRegion() {
        drawingColorRegions = false;

        buffer.clear();
        buffer.put(quadVboData);
        buffer.flip();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer.capacity() * 4, GL15.GL_STREAM_DRAW);
        GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, 0, buffer);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);


        GL30.glBindVertexArray(vao);
        GL20.glEnableVertexAttribArray(0);

        glColorMask(true, true, false, false);
        glBlendFunc(GL_ONE, GL_ZERO);
        glColor3f(0, currentRegionShade, 0);
        GL11.glDrawArrays(GL11.GL_QUADS, 0, arrayIndex);

        glColorMask(true, false, false, false);
        glBlendFunc(GL_ONE, GL_ONE);
        glColor3f((float)1.0f / NUM_COLORS_PER_REGION, 0, 0);
        GL11.glDrawArrays(GL11.GL_QUADS, 0, arrayIndex);

        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);



        glColorMask(true, true, true, true);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    public static void setColorShadeFactor(int factor) {
        colorShadeFactor = factor;
    }

    public static void resetColorShadeFactor() {
        colorShadeFactor = 1;
    }

    public static void drawQuad(float... positions) {
        if(drawingColorRegions) {
            glEnable(GL_BLEND);

            /*glColorMask(false, true, false, false);
            glBlendFunc(GL_ONE, GL_ZERO);
            glColor3f(0, currentRegionShade, 0);
            glBegin(GL_QUADS);
            drawVertices(positions);
            glEnd();*/

            /*glColorMask(true, false, false, false);
            glBlendFunc(GL_ONE, GL_ONE);
            glColor3f((float)colorShadeFactor / NUM_COLORS_PER_REGION, 0, 0);
            glBegin(GL_QUADS);
            drawVertices(positions);
            glEnd();*/

            for(int i = 0; i < 8; i++) {
                quadVboData[arrayIndex++] = positions[i];
            }

            glColorMask(true, true, true, true);
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        } else {
            glBegin(GL_QUADS);
            drawVertices(positions);
            glEnd();
        }
    }

    private static void drawVertices(float... positions) {
        for(int i = 0; i < positions.length; i += 2) {
            glVertex2d(positions[i], positions[i+1]);
        }
    }

    public static void cleanUp() {
        layeredColorShader.cleanUp();
        GL30.glDeleteVertexArrays(vao);
        GL30.glDeleteBuffers(vbo);
    }
}
