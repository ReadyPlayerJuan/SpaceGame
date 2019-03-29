package main.input;

import main.views.View;

import java.util.ArrayList;

public class InputCodeBuilder {
    private static final double INPUT_SPEED_SHORT = 0.12;       //anything shorter than this will be considered 'short'
    private static final double INPUT_SPEED_MEDIUM = 0.20;      //anything shorter than this can be considered either 'short' or 'long'
    private static final double INPUT_SPEED_LONG = 0.50;        //anything shorter than this will be considered 'long',
                                                                //and anything longer than this will be considered 'very long'

    public static String debugString1 = "";
    public static String debugString2 = "";
    private static InputCode submitActionCode = new InputCode(InputType.PAUSE_VERY_LONG); //submit action after a very long pause

    private static boolean buildingCode = false;
    private static boolean trailedByLongPause = false;
    private static boolean lastAddedPause = false;
    private static boolean justFinishedBuilding = false;

    private static double prevTimeSinceActionRelease = 0;
    private static double timeSinceActionRelease = 0;
    private static ArrayList<InputType> currentInputString = new ArrayList<InputType>();

    public static void update(double delta) {
        ArrayList<Double[]> pressedActionKeys = InputManager.getPressedActionKeys();
        ArrayList<Double[]> releasedActionKeys = InputManager.getReleasedActionKeys();

        prevTimeSinceActionRelease = timeSinceActionRelease;
        timeSinceActionRelease += delta;
        if(pressedActionKeys.size() > 0)
            timeSinceActionRelease = 0;

        if(!buildingCode) {
            if(justFinishedBuilding) {
                if(pressedActionKeys.size() == 0) {
                    justFinishedBuilding = false;
                }
            } else {
                if(pressedActionKeys.size() > 0) {
                    buildingCode = true;
                    trailedByLongPause = false;
                    lastAddedPause = true;
                }
            }
        } else {
            if(timeSinceActionRelease >= INPUT_SPEED_LONG) {
                addLongPause();
            } else {
                removeLongPause();
            }

            //check if a pause should be added
            if(pressedActionKeys.size() > 0 && timeSinceActionRelease == 0 && !lastAddedPause) {
                removeLongPause();

                //started pressing a key, so add pause to string
                addPause(prevTimeSinceActionRelease);

                lastAddedPause = true;
                //System.out.println(timeSinceActionPress + " " + lastTimeSinceActionPress);
            }

            //check if an input should be added
            if(releasedActionKeys.size() > 0) {
                removeLongPause();

                //add input to string for each key released
                for(Double[] keyReleaseInfo: releasedActionKeys) {
                    //add a short pause between last input and this one
                    if(!lastAddedPause) {
                        addPause(0);
                    }

                    //add input to string
                    addInput(keyReleaseInfo[1]);
                    lastAddedPause = false;
                }
                //System.out.println(timeSinceActionPress + " " + lastTimeSinceActionPress);
            }

            updateDebugText();

            //check if code ends with a valid submit code
            if(currentStringEndsWith(submitActionCode)) {
                //submit action code
                removeFromEndOfCurrentString(submitActionCode);

                updateDebugText();
                debugString2 = "SUBMIT " + debugString1;

                InputType[] data = new InputType[currentInputString.size()];
                for(int i = 0; i < currentInputString.size(); i++) {
                    data[i] = currentInputString.get(i);
                }
                submitInputCode(new InputCode(data));

                buildingCode = false;
                justFinishedBuilding = true;
                currentInputString.clear();
            }
        }
    }

    private static void submitInputCode(InputCode code) {
        View focusedView = InputManager.getFocusedView();

        if(focusedView != null)
            focusedView.processInput(code);
    }

    private static void updateDebugText() {
        debugString1 = "";
        for(InputType i: InputCodeBuilder.getCurrentInputString()) {
            debugString1 += i + " ";
        }
    }

    private static void addPause(double length) {
        if(length < INPUT_SPEED_SHORT) {
            currentInputString.add(InputType.PAUSE_SHORT);
        } else if(length < INPUT_SPEED_MEDIUM) {
            currentInputString.add(InputType.PAUSE_MEDIUM);
        } else {//if(length < INPUT_SPEED_LONG) {
            currentInputString.add(InputType.PAUSE_LONG);
        }
        //no 'very long' pause, its instead added when there's no input for a while
        //System.out.println("add pause");
    }

    private static void addInput(double length) {
        if(length < INPUT_SPEED_SHORT) {
            currentInputString.add(InputType.INPUT_SHORT);
        } else if(length < INPUT_SPEED_MEDIUM) {
            currentInputString.add(InputType.INPUT_MEDIUM);
        } else if(length < INPUT_SPEED_LONG) {
            currentInputString.add(InputType.INPUT_LONG);
        } else {
            currentInputString.add(InputType.INPUT_VERY_LONG);
        }
        //System.out.println("add input");
    }

    public static ArrayList<InputType> getCurrentInputString() {
        return currentInputString;
    }

    private static boolean currentStringEndsWith(InputCode code) {
        if(code.length() > currentInputString.size())
            return false;

        for(int i = 0; i < code.length(); i++) {
            //System.out.println(i + " " + (currentInputString.size() - code.length() + i) + " " + currentInputString.size());
            if(!code.get(i).equals(currentInputString.get(currentInputString.size() - code.length() + i)))
                return false;
        }
        return true;
    }

    private static void removeFromEndOfCurrentString(InputCode code) {
        for(int i = 0; i < code.length(); i++) {
            currentInputString.remove(currentInputString.size()-1);
        }
    }

    private static void addLongPause() {
        if(!trailedByLongPause) {
            currentInputString.add(InputType.PAUSE_VERY_LONG);
            trailedByLongPause = true;
        }
    }

    private static void removeLongPause() {
        if(trailedByLongPause) {
            currentInputString.remove(currentInputString.size()-1);
            trailedByLongPause = false;
        }
    }
}
