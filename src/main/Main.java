package main;

import main.views.MainView;
import main.views.View;
import rendering.FrameBuffer;
import rendering.Loader;
import rendering.WindowManager;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Main {
    private View mainView;

    public void run() {
        Settings.loadSettings();

        WindowManager.createWindow();
        initGL();

        mainView = new MainView(Settings.get(SettingType.RESOLUTION_WIDTH), Settings.get(SettingType.RESOLUTION_HEIGHT));
        //FrameBuffer testFbo1 = new FrameBuffer(400, 400, FrameBuffer.NONE);
        //FrameBuffer testFbo2 = new FrameBuffer(200, 200, FrameBuffer.NONE);

        while (!glfwWindowShouldClose(WindowManager.window)) {
            glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            mainView.update(WindowManager.getDelta());
            mainView.draw();
            mainView.drawMainView(mainView.getWidth()/2, mainView.getHeight()/2, 1.0);


            /*fbo.begin();

            GL11.glClearColor(0, 0, 0, 1);
            GL11.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            //GL11.glBlendFunc(GL_ONE, GL_ZERO);
            //GL11.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
            GL11.glBegin(GL11.GL_QUADS);
            GL11.glColor4f(0, 1, 0, 1);
            GL11.glVertex2f(0, 0);
            GL11.glVertex2f(fbowidth/2, 0);
            GL11.glVertex2f(fbowidth/2, 200);
            GL11.glVertex2f(0, 200);
            GL11.glEnd();

            GL11.glBegin(GL11.GL_QUADS);
            GL11.glColor4f(1, 0, 0, 1f);
            GL11.glVertex2f(50, 50);
            GL11.glVertex2f(250, 50);
            GL11.glVertex2f(250, 250);
            GL11.glVertex2f(50, 250);
            GL11.glEnd();

            fbo.end();



            //on screen

            glMatrixMode(GL_PROJECTION);
            glLoadIdentity();
            glViewport(0, 0,
                    Settings.get(SettingType.RESOLUTION_WIDTH),
                    Settings.get(SettingType.RESOLUTION_HEIGHT));
            glOrtho(0, Settings.get(SettingType.RESOLUTION_WIDTH),
                    Settings.get(SettingType.RESOLUTION_HEIGHT), 0, 0, 1);

            glClearColor (0.0f, 0.0f, 1.0f, 1.0f);
            glClear (GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            glEnable(GL_TEXTURE_2D);
            glBindTexture(GL_TEXTURE_2D, fbo.getTexture());

            glBegin(GL_QUADS);
            GL11.glColor4f(1, 1, 1, 1f);
            glTexCoord2f(0, 0); glVertex2f(50, 50);
            glTexCoord2f(1, 0); glVertex2f(50 + fbowidth, 50);
            glTexCoord2f(1, 1); glVertex2f(50 + fbowidth, 50 + fboheight);
            glTexCoord2f(0, 1); glVertex2f(50, 50 + fboheight);
            glEnd();

            glBindTexture(GL_TEXTURE_2D, 0);

            GL11.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
            //GL11.glBlendFunc(GL_ONE, GL_ZERO);

            //testFbo1.unbindFrameBuffer();

            //testFbo1.draw(250, 250, 1.0);

            GL11.glBegin(GL11.GL_QUADS);
            GL11.glColor4f(0, 0, 0, 0.5f);
            GL11.glVertex2f(0, 0);
            GL11.glVertex2f(200, 0);
            GL11.glVertex2f(200, 200);
            GL11.glVertex2f(0, 200);
            GL11.glEnd();

            glDisable(GL_TEXTURE_2D);*/


            WindowManager.updateWindow();
        }

        WindowManager.destroyWindow();
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

        glMatrixMode (GL_MODELVIEW);                                // Select The Modelview Matrix
        glLoadIdentity ();                                          // Reset The Modelview Matrix


    }

    public static void main(String[] argv) {
        new Main().run();
    }
}