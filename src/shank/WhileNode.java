package shank;

import java.util.*;

public class WhileNode extends StatementNode{
    private ArrayList<StatementNode> statements;
    private BooleanCompareNode condition;

    public WhileNode(){}

    public WhileNode(ArrayList<StatementNode> statements, BooleanCompareNode condition){
        this.statements = statements;
        this.condition = condition;
    }

    @Override
    public String toString() {
        String str = "[While] Condition: (" + condition.toString() + ") Statements:";
        for(int i=0; i<statements.size(); i++){
            str = str + "\n " + statements.get(i).toString();
        }
        str += ")";

        return str;
    }
}
