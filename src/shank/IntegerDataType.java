package shank;

public class IntegerDataType extends InterpreterDataType{

    private int value;

    public IntegerDataType(){}

    public IntegerDataType(int value){
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
