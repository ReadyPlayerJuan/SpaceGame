package rendering;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;
import rendering.colors.ColorRegion;
import rendering.colors.ColorTheme;
import rendering.colors.ColorThemeManager;
import rendering.shaders.color_vertex.ColorVertexShader;
import rendering.shaders.layered_color.LayeredColorShader;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;

public class Graphics {
    public static LayeredColorShader layeredColorShader;
    public static ColorVertexShader colorVertexShader;

    public static final int NUM_COLOR_REGIONS = 5;
    public static final int NUM_COLORS_PER_REGION = 5;
    private static boolean drawingColorRegions = false;
    private static float currentRegionShade = 0;

    private static final int MAX_QUADS = 100;
    private static final FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(MAX_QUADS * 8);
    private static final FloatBuffer wobbleBuffer = BufferUtils.createFloatBuffer(MAX_QUADS * 4);
    private static float[] quadVboData;
    private static float[] wobbleVboData;
    private static int quadArrayIndex = 0;
    private static int wobbleArrayIndex = 0;
    private static int vao;
    private static int vertexVbo, wobbleVbo;

    public static void init() {
        layeredColorShader = new LayeredColorShader();
        layeredColorShader.start();
        layeredColorShader.loadColorTheme(ColorThemeManager.getColorTheme(ColorTheme.GAME));
        layeredColorShader.stop();

        colorVertexShader = new ColorVertexShader();


        vao = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vao);

        vertexVbo = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertexVbo);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, MAX_QUADS * 8 * 4, GL15.GL_STREAM_DRAW);
        GL20.glVertexAttribPointer(0, 2, GL11.GL_FLOAT, false, 0, 0);

        wobbleVbo = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, wobbleVbo);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, MAX_QUADS * 4 * 4, GL15.GL_STREAM_DRAW);
        GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 0, 0);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL30.glBindVertexArray(0);
    }

    public static void clear(float r, float g, float b, float a) {
        glClearColor(r, g, b, a);
        glClear(GL_COLOR_BUFFER_BIT);
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

        quadArrayIndex = 0;
        wobbleArrayIndex = 0;
        quadVboData = new float[MAX_QUADS * 8];
        wobbleVboData = new float[MAX_QUADS * 4];
    }

    public static void finishDrawingColorRegion() {
        drawingColorRegions = false;

        vertexBuffer.clear();
        vertexBuffer.put(quadVboData);
        vertexBuffer.flip();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertexVbo);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexBuffer.capacity() * 4, GL15.GL_STREAM_DRAW);
        GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, 0, vertexBuffer);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

        wobbleBuffer.clear();
        wobbleBuffer.put(wobbleVboData);
        wobbleBuffer.flip();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, wobbleVbo);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, wobbleBuffer.capacity() * 4, GL15.GL_STREAM_DRAW);
        GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, 0, wobbleBuffer);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);


        GL30.glBindVertexArray(vao);
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        colorVertexShader.start();
        colorVertexShader.setTime((float)WindowManager.getTime());

        glColorMask(true, true, false, false);
        glBlendFunc(GL_ONE, GL_ZERO);
        //glColor3f(0, currentRegionShade, 0);
        colorVertexShader.setColor(0, currentRegionShade, 0);
        GL11.glDrawArrays(GL11.GL_QUADS, 0, quadArrayIndex);

        glColorMask(true, false, false, false);
        glBlendFunc(GL_ONE, GL_ONE);
        //glColor3f(1.0f / NUM_COLORS_PER_REGION, 0, 0);
        colorVertexShader.setColor(1.0f / NUM_COLORS_PER_REGION, 0, 0);
        GL11.glDrawArrays(GL11.GL_QUADS, 0, quadArrayIndex);

        colorVertexShader.stop();
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);



        glColorMask(true, true, true, true);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    public static void drawQuad(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) {
        if(drawingColorRegions) {
            quadVboData[quadArrayIndex++] = x1;
            quadVboData[quadArrayIndex++] = y1;
            quadVboData[quadArrayIndex++] = x2;
            quadVboData[quadArrayIndex++] = y2;
            quadVboData[quadArrayIndex++] = x3;
            quadVboData[quadArrayIndex++] = y3;
            quadVboData[quadArrayIndex++] = x4;
            quadVboData[quadArrayIndex++] = y4;

            wobbleVboData[wobbleArrayIndex++] = wobbleArrayIndex;
            wobbleVboData[wobbleArrayIndex++] = wobbleArrayIndex;
            wobbleVboData[wobbleArrayIndex++] = wobbleArrayIndex;
            wobbleVboData[wobbleArrayIndex++] = wobbleArrayIndex;
        } else {
            glBegin(GL_QUADS);
            drawVertices(x1, y1, x2, y2, x3, y3, x4, y4);
            glEnd();
        }
    }

    public static void drawQuad(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4, float... data) {
        for(int i = 0; i < data.length / 5; i++) {
            float cenx = data[i*5];
            float ceny = data[i*5 + 1];
            float rotation = data[i*5 + 2];
            float xscale = data[i*5 + 3];
            float yscale = data[i*5 + 4];
            float cosr = (float)Math.cos(rotation);
            float sinr = (float)Math.sin(rotation);
            drawQuad(
                    cenx + xscale * (x1*cosr - y1*sinr),
                    ceny + yscale * (x1*sinr + y1*cosr),
                    cenx + xscale * (x2*cosr - y2*sinr),
                    ceny + yscale * (x2*sinr + y2*cosr),
                    cenx + xscale * (x3*cosr - y3*sinr),
                    ceny + yscale * (x3*sinr + y3*cosr),
                    cenx + xscale * (x4*cosr - y4*sinr),
                    ceny + yscale * (x4*sinr + y4*cosr)
            );
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
        GL30.glDeleteBuffers(vertexVbo);
        GL30.glDeleteBuffers(wobbleVbo);
    }
}
