package shank;

public class BooleanDataType extends InterpreterDataType{

    private boolean value;
    private boolean changeable;

    public BooleanDataType(){}

    public BooleanDataType(boolean value){
        this.value = value;
    }

    public BooleanDataType(boolean value, boolean changeable){
        this.value = value;
        this.changeable = changeable;
    }

    public boolean isChangeable() {
        return changeable;
    }

    public boolean getValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "BooleanDataType: [" + value + "] ";
    }

    @Override
    public void fromString(String input) {
        value = Boolean.parseBoolean(input);
    }
}
