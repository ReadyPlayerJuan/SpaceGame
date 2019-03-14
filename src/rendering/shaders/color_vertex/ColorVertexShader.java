package rendering.shaders.color_vertex;

import rendering.shaders.ShaderProgram;

public class ColorVertexShader extends ShaderProgram {
    private static final String VERTEX_FILE = "src/rendering/shaders/color_vertex/colorVertexVertex.txt";
    private static final String FRAGMENT_FILE = "src/rendering/shaders/color_vertex/colorVertexFragment.txt";

    private int location_time;
    private int location_color;

    public ColorVertexShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void getAllUniformLocations() {
        location_color = super.getUniformLocation("color");
        location_time = super.getUniformLocation("time");
    }

    public void setTime(float time) {
        super.loadFloat(location_time, time);
    }

    public void setColor(float r, float g, float b) {
        super.loadVector(location_color, r, g, b);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "wobble");
    }

}
