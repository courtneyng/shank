package shank;

public class IntegerNode extends Node{
    private int value; // private member

    // public accessor
    public IntegerNode(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }
    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
