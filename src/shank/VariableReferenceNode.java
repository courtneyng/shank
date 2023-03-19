package shank;

public class VariableReferenceNode extends Node{
    private String name;
    private Node arrayIndexExpr = null;

    public VariableReferenceNode(String name){
        this.name = name;
    }

    public VariableReferenceNode(String name, Node arrayIndexExpr){
        this.name = name;
        this.arrayIndexExpr = arrayIndexExpr;
    }

    public String getName(){
        return name;
    }

    @Override
    public String toString() {
        return "Variable Reference: " + name;
    }
}
