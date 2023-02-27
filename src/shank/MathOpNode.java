package shank;

public class MathOpNode extends Node{
    private Node left, right;
    Token.tokenType op;

    //make a constructor that takes token type that converts to enum here?
    enum MathOp{
        ADD, SUBTRACT, MULTIPLY, DIVIDE
    }

    public MathOpNode(Node left, Node right, Token.tokenType op){
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

    public Token.tokenType getOp() {
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
