package shank;

public class CharacterDataType extends InterpreterDataType{

    private char value;
    private boolean changeable;

    public CharacterDataType(){}

    public CharacterDataType(char value){
        this.value = value;
    }

    public CharacterDataType(char value, boolean changeable){
        this.value = value;
        this.changeable = changeable;
    }

    public boolean isChangeable() {
        return changeable;
    }

    public char getValue() {
        return value;
    }

    public void setValue(char value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "CharacterDataType [" + value + "] ";
    }

    @Override
    public void fromString(String input) {
        value = input.charAt(0);
    }
}
