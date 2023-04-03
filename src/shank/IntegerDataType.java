package shank;

public class IntegerDataType extends InterpreterDataType{

    private int value;
    public boolean changeable;

    public IntegerDataType(){}

    public IntegerDataType(int value){
        this.value = value;
    }
    public IntegerDataType(int value, boolean changeable){
        this.value = value;
        this.changeable = changeable;
    }

    public boolean isChangeable() {
        return changeable;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "IntegerDataType: [" + value + "] ";
    }

    @Override
    public void fromString(String input) {
        value = Integer.parseInt(input);
    }
}
