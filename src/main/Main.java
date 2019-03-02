package main;

import game.View;
import game.MainView;
import game.SettingType;
import game.Settings;
import rendering.WindowManager;

import java.awt.*;
import java.io.File;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Main {
    private View mainView;

    public void run() {



        Settings.loadSettings();

        WindowManager.createWindow();

        mainView = new MainView(Settings.get(SettingType.RESOLUTION_WIDTH), Settings.get(SettingType.RESOLUTION_HEIGHT));


        while (!glfwWindowShouldClose(WindowManager.window)) {
            glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            mainView.update(WindowManager.getDelta());
            mainView.draw();
            mainView.drawMainView(mainView.getWidth()/2, mainView.getHeight()/2, 1);

            WindowManager.updateWindow();
        }

        WindowManager.destroyWindow();
        mainView.cleanUp();
    }

    public static void main(String[] argv) {
        new Main().run();
    }
}