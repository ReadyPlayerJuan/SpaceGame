package rendering.shaders.layered_color;

import rendering.shaders.ShaderProgram;

public class LayeredColorShader extends ShaderProgram {
    private static final String VERTEX_FILE = "src/rendering/shaders/layered_color/layeredColorVertex.txt";
    private static final String FRAGMENT_FILE = "src/rendering/shaders/layered_color/layeredColorFragment.txt";

    public LayeredColorShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void getAllUniformLocations() {
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }

}
