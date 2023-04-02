package shank;

public class StringDataType extends InterpreterDataType{

    private String value;

    public StringDataType(){}

    public StringDataType(String value){
        this.value = value;
    }
    @Override
    public String toString() {
        return "StringDataType: [" + value + "] ";
    }

    @Override
    public void fromString(String input) {
        value = input;
    }
}
