package shank;

public class StringNode extends Node{

    private String value; // private member

    // public accessors
    public StringNode(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }
    @Override
    public String toString() {
        return value;
    }
}
