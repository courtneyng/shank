package shank;

import java.util.*;

public class RepeatNode extends StatementNode{
    private ArrayList<StatementNode> statements;
    private BooleanCompareNode condition;

    public RepeatNode(){}

    public RepeatNode(ArrayList<StatementNode> statements, BooleanCompareNode condition){
        this.statements = statements;
        this.condition = condition;
    }

    public ArrayList<StatementNode> getStatements(){
        return statements;
    }

    public BooleanCompareNode getCondition() {
        return condition;
    }

    @Override
    public String toString() {
        String str = "[RepeatNode] Condition: (" + condition.toString() + ") \nStatements: ";
        for(int i=0; i< statements.size(); i++){
            str += "\n " + statements.get(i).toString();
        }
        str += ")";

        return str;
    }
}
