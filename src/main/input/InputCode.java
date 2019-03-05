package main.input;

public class InputCode {
    private InputType[] inputs;

    public InputCode(InputType... inputs) {
        this.inputs = inputs;
    }

    public boolean equals(InputCode other) {
        InputType[] otherInputs = other.getInputs();

        if(inputs.length != otherInputs.length)
            return false;

        for(int i = 0; i < inputs.length; i++) {
            if(!inputs[i].equals(otherInputs[i]))
                return false;
        }

        return true;
    }

    public InputCode add(InputType i) {
        InputType[] newInputs = new InputType[inputs.length+1];
        System.arraycopy(inputs, 0, newInputs, 0, inputs.length);
        newInputs[newInputs.length-1] = i;
        return new InputCode(newInputs);
    }

    public InputType[] getInputs() {
        return inputs;
    }

    public InputType get(int i) {
        return inputs[i];
    }

    public int length() {
        return inputs.length;
    }

    public String toString() {
        String s = "";
        for(InputType i: inputs) {
            s += i + " ";
        }
        return s;
    }
}
