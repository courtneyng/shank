package shank;

public class CharacterDataType extends InterpreterDataType{

    private char value;

    public CharacterDataType(){}

    public CharacterDataType(char value){
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
