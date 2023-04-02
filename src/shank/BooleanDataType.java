package shank;

public class BooleanDataType extends InterpreterDataType{

    private boolean value;

    public BooleanDataType(){}

    public BooleanDataType(boolean value){
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
