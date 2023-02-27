package shank;

public class MathOpNode extends Node{
    private Node left, right;
    MathOpNode.MathOp op;

    //make a constructor that takes token type that converts to enum here?
    enum MathOp{
        ADD, SUBTRACT, MULTIPLY, DIVIDE, MODULO
    }

    public MathOpNode(Node left, Node right, MathOpNode.MathOp op){
        this.left = left;
        this.right = right;
        this.op = op;
    }


    public Node getLeft(){
        return left;
    }

    public Node getRight(){
        return right;
    }

    public MathOp getOp() {
        return op;
    }

    public String getOperator(){
        return op.toString();
    }

    @Override
    public String toString() {
        return "("+ left.toString() + " " + op.toString() + " " + right.toString() + ")";
    }
}
