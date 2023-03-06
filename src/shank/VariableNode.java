package shank;

public class VariableNode extends Node{
    private String name;
    private Token type;
    private Node changeable;

    // Public Accessors
    public VariableNode(String name, Token type, Node changeable){
        this.name = name;
        this.type = type;
        this.changeable = changeable;
    }

    public String getName(){
        return name;
    }

    public Token getType(){
        return type;
    }

    public Node getValue(){
        return changeable;
    }

    @Override
    public String toString() {
        return name + changeable + type;
    }
}
