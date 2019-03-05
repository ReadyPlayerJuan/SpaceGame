package main.input;

public enum InputType {
    INPUT_SHORT (true, 0),
    INPUT_MEDIUM (true, 1),
    INPUT_LONG (true, 0),
    INPUT_VERY_LONG (true, -1),
    PAUSE_SHORT (false, 0),
    PAUSE_MEDIUM (false, 1),
    PAUSE_LONG (false, 0),
    PAUSE_VERY_LONG (false, -1);

    public boolean isInput;
    public int i;
    InputType(boolean isInput, int i) {
        this.isInput = isInput;
        this.i = i;
    }

    public boolean equals(InputType other) {
        if(this == other)
            return true;

        //medium can equal short or long here
        return isInput == other.isInput &&
                (i == 1 && other.i == 0 ||
                i == 0 && other.i == 1);
    }
}
