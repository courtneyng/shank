package shank;

public class VariableNode extends Node{
    private String name;
    private Token.tokenType type;
    private Node changeable;

    // Public Accessors

    // empty constructor
    public VariableNode(){}

    public VariableNode(String name, Token.tokenType type, Node changeable){
        this.name = name;
        this.type = type;
        this.changeable = changeable;
    }

    public String getName(){
        return name;
    }

    public Token.tokenType getType(){
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
