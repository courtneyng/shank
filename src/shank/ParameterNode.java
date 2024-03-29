package shank;

public class ParameterNode extends StatementNode{
    private VariableReferenceNode variableIdentifier;
    private Node expression;
    private boolean isChangeable;

    public ParameterNode(){}

    public ParameterNode(VariableReferenceNode variableIdentifier, Node expression){
        this.variableIdentifier = variableIdentifier;
        this.expression = expression;
    }

    public ParameterNode(Node expression){
        this.expression = expression;
        this.variableIdentifier = null;
    }

    public boolean isChangeable() {
        return isChangeable;
    }

    @Override
    public String toString() {
        String str = "\n ParameterNode (";
        if(variableIdentifier != null) str += "Variable Identifier: " + variableIdentifier.toString();
        if(expression != null) str += "Expression: " + expression.toString();
        str += ")";
        return str;
    }
}
