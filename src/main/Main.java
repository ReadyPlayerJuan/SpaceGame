package main;

import game.View;
import game.MainView;
import game.SettingType;
import game.Settings;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;
import rendering.WindowManager;

import java.awt.*;
import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Main {
    private View mainView;

    public void run() {
        Settings.loadSettings();

        WindowManager.createWindow();

        mainView = new MainView(Settings.get(SettingType.RESOLUTION_WIDTH), Settings.get(SettingType.RESOLUTION_HEIGHT));
        glClearColor(1.0f, 0.0f, 0.0f, 0.0f);

        while (!glfwWindowShouldClose(WindowManager.window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            mainView.update(WindowManager.getDelta());
            mainView.draw();

            WindowManager.updateWindow();
        }

        WindowManager.destroyWindow();
    }

    public static void main(String[] argv) {
        new Main().run();
    }
}