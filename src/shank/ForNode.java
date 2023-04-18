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

    public ForNode(Node from, Node to, VariableReferenceNode variable, ArrayList<StatementNode> statements) {
        this.from = from;
        this.to = to;
        this.statements = statements;
        this.variable = variable;
    }

    public Node getFrom() {
        return from;
    }

    public Node getTo() {
        return to;
    }

    public void setFrom(Node from) {
        this.from = from;
    }

    public void setTo(Node to) {
        this.to = to;
    }

    public VariableReferenceNode getVariable() {
        return variable;
    }

    public ArrayList<StatementNode> getStatements() {
        return statements;
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
