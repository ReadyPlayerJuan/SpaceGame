package main.input;

import main.views.View;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;

public class InputManager {
    private static View focusedView = null;
    private static ArrayList<Double[]> pressedActionKeys, releasedActionKeys;

    public static void init(long w) {
        pressedActionKeys = new ArrayList<Double[]>();
        releasedActionKeys = new ArrayList<Double[]>();


        glfwSetKeyCallback(w, (window, key, scancode, action, mods) -> {
            if(isActionKey(key)) {
                if (action == GLFW_PRESS) {
                    //add to pressed keys list
                    pressedActionKeys.add(new Double[]{(double) key, 0.0});
                } else if (action == GLFW_RELEASE) {
                    //remove from pressed keys list
                    for (int i = 0; i < pressedActionKeys.size(); i++) {
                        if (pressedActionKeys.get(i)[0] == key) {
                            releasedActionKeys.add(pressedActionKeys.remove(i));
                            break;
                        }
                    }
                }
            }
        });
    }

    private static boolean isActionKey(int key) {
        return true;
    }

    public static void update(double delta) {
        for(Double[] d: pressedActionKeys) {
            d[1] += delta;
        }

        releasedActionKeys.clear();
        glfwPollEvents();

        InputCodeBuilder.update(delta);
    }

    public static void setFocusedView(View v) {
        if(focusedView != null)
            focusedView.setFocused(false);
        focusedView = v;
        //new focused view sets its own focused flag, otherwise would create an infinite loop
    }

    public static ArrayList<Double[]> getPressedActionKeys() {
        return pressedActionKeys;
    }

    public static ArrayList<Double[]> getReleasedActionKeys() {
        return releasedActionKeys;
    }

    public static View getFocusedView() {
        return focusedView;
    }
}
