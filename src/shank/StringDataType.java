package shank;

public class StringDataType extends InterpreterDataType{

    private String value;
    private boolean changeable;

    public StringDataType(){}

    public StringDataType(String value){
        this.value = value;
    }

    public StringDataType(String value, boolean changeable){
        this.value = value;
        this.changeable = changeable;
    }

    public boolean isChangeable() {
        return changeable;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
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
