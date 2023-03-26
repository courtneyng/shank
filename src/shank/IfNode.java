package shank;

import java.util.*;

public class IfNode extends StatementNode{
    private ArrayList<StatementNode> statements;
    private BooleanCompareNode condition;
    private IfNode next;

    /**
     * empty constructor
     */
    public IfNode(){}

    /**
     *  else statement - no conditions or next
     *  else{ body }
     * @param statements
     */
    public IfNode(ArrayList<StatementNode> statements){
        this.statements = statements;
        this.condition = null;
        this.next = null;
    }

    /**
     * Condition for next if or else-if
     * @param statements
     * @param condition
     * @param next
     */
    public IfNode(ArrayList<StatementNode> statements, BooleanCompareNode condition, IfNode next){
        this.statements = statements;
        this.condition = condition;
        this.next = next;
    }

    /**
     * end else-if
     * @param statements
     * @param condition
     */
    public IfNode(ArrayList<StatementNode> statements, BooleanCompareNode condition){
        this.statements = statements;
        this.condition = condition;
        this.next = null;
    }

    public ArrayList<StatementNode> getStatements(){
        return statements;
    }

    public BooleanCompareNode getCondition() {
        return condition;
    }

    public IfNode getNext(){
        return next;
    }

    /**
     * Add elseif in next
     * @param next
     */
    public void setNext(IfNode next){
        this.next = next;
    }

    @Override
    public String toString() {
        String str;

        if(this.condition == null) str = "\n IfNode else statements: ";
        else str = "\n IfNode condition: (" + condition.toString() + ") \n statements:";

        for(int i=0; i<statements.size(); i++){
            str += "\n " + statements.get(i).toString();
        }

        if(this.next == null) return str;
        else return str + next.toString();
    }
}
