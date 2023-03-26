package shank;

import java.util.ArrayList;

public class ForNode extends StatementNode{

    private ArrayList<StatementNode> statements;
    private Node from;
    private Node to;
    private VariableReferenceNode variable;

    /**
     * Empty Constructor
     */
    public ForNode(){}

    public ForNode(Node from, Node to, ArrayList<StatementNode> statements, VariableReferenceNode variable) {
        this.from = from;
        this.to = to;
        this.statements = statements;
        this.variable = variable;
    }

    @Override
    public String toString() {
        String fullStatement = "From: " + from.toString() + " To: " + to.toString() + " Variable " +
                variable.toString() + " Statements: " + statements.toString();
        for(int i=0; i<statements.size(); i++){
            fullStatement += "\n" + statements.get(i).toString();
        }
        return fullStatement;
    }
}
