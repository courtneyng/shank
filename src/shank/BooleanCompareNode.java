package shank;

public class BooleanCompareNode extends Node{

    // an enum for what type of comparison and a left and right side.
    enum comparisonType{
    LESSTHAN, GREATERTHAN, LESSOREQUAL, GREATEROREQUAL, EQUAL, NOTEQUAL
    }
    private Node leftExpr;
    private Node rightExpr;
    private comparisonType comparator;

    /**
     * Constructor for all values
     * @param leftExpr
     * @param comparator
     * @param rightExpr
     */
    public BooleanCompareNode(Node leftExpr, comparisonType comparator, Node rightExpr){
        this.leftExpr = leftExpr;
        this.comparator = comparator;
        this.rightExpr = rightExpr;
    }

    /**
     * Gets the left expression
     * @return left expression
     */
    public Node getLeftExpr(){
        return leftExpr;
    }

    /**
     * Gets the right expression
     * @return right expression
     */
    public Node getRightExpr(){
        return rightExpr;
    }

    /**
     * Gets the comparator
     * @return the comparator
     */
    public comparisonType getComparator(){
        return comparator;
    }

    @Override
    public String toString() {
        return "Bool Expression: " + leftExpr.toString() + " " + comparator.toString() + " " + rightExpr.toString();
    }
}
