package shank;

public class VariableReferenceNode extends Node{
    private String name;
    private Node arrayIndexExpr = null;
    private VariableNode referencedNode = null;
    private VariableNode.varType type = null;

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

    public Node getIndex(){
        if(arrayIndexExpr instanceof IntegerNode){
            IntegerNode intNode = (IntegerNode) arrayIndexExpr;
            return intNode;
        } else if(arrayIndexExpr instanceof MathOpNode){
            MathOpNode mathOP = (MathOpNode) arrayIndexExpr;
            return mathOP;
        } else if(arrayIndexExpr instanceof VariableReferenceNode){
            VariableReferenceNode varRef = (VariableReferenceNode) arrayIndexExpr;
            return varRef;
        }
        return null;
    }

    public void setIndex(IntegerNode intNode){
        arrayIndexExpr = intNode;
    }

    public VariableNode getReferencedVariable(){
        return referencedNode;
    }

    public VariableNode.varType getType(){
        return  type;
    }

    @Override
    public String toString() {
        return "Variable Reference: " + name;
    }
}
