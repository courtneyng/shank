package shank;

public class BooleanNode extends Node{

    private boolean value; // private member

    // public accessors
    public BooleanNode(boolean value){
        this.value = value;
    }

    public boolean getValue(){
        return value;
    }
    @Override
    public String toString() {
        return Boolean.toString(value);
    }
}
