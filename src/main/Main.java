package main;

import main.input.InputManager;
import main.views.MainView;
import main.views.View;
import rendering.Loader;
import rendering.Graphics;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import rendering.shaders.layered_color.LayeredColorShader;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Main {
    private MainView mainView;

    public void run() {
        Settings.loadSettings();

        Graphics.createWindow();
        InputManager.init(Graphics.window);
        initGL();
        Graphics.initShaders();

        mainView = new MainView(Settings.get(SettingType.RESOLUTION_WIDTH), Settings.get(SettingType.RESOLUTION_HEIGHT));
        LayeredColorShader shader = new LayeredColorShader();

        while (!glfwWindowShouldClose(Graphics.window)) {
            glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            InputManager.update(Graphics.getDelta());

            mainView.update(Graphics.getDelta());
            mainView.draw();

            //shader.start();
            glOrtho(0, mainView.getWidth(), 0, mainView.getHeight(), 0, 1);
            mainView.getMainFrameBuffer().draw(mainView.getWidth()/2, mainView.getHeight()/2);
            //shader.stop();

            Graphics.updateWindow();
        }

        Graphics.destroyWindow();
        Graphics.cleanUp();
        Loader.cleanUp();
        mainView.cleanUp();
    }

    private void initGL() {
        GL.createCapabilities();
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glViewport(0, 0,
                Settings.get(SettingType.RESOLUTION_WIDTH),
                Settings.get(SettingType.RESOLUTION_HEIGHT));
        GL11.glOrtho(0,
                Settings.get(SettingType.RESOLUTION_WIDTH),
                Settings.get(SettingType.RESOLUTION_HEIGHT), 0, 0, 1);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        glMatrixMode(GL_MODELVIEW);                                // Select The Modelview Matrix
        glLoadIdentity();                                          // Reset The Modelview Matrix


    }

    public static void main(String[] argv) {
        new Main().run();
    }
}