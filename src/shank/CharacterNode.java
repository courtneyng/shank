package shank;

public class CharacterNode extends Node{

    private char value; // private member

    // public accessors
    public CharacterNode(char value){
        this.value = value;
    }

    public char getValue(){
        return value;
    }

    @Override
    public String toString() {
        return Character.toString(value);
    }
}
