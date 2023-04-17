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

    public VariableNode.varType getDataType() throws SyntaxErrorException{
        VariableNode.varType left = null, right = null;
        if(this.left instanceof VariableReferenceNode || this.right instanceof VariableReferenceNode){
            if(this.left instanceof VariableReferenceNode && this.right instanceof VariableReferenceNode){
                VariableReferenceNode leftRef = (VariableReferenceNode) this.left;
                VariableReferenceNode rightRef = (VariableReferenceNode) this.right;
                left = leftRef.getType();
                right = rightRef.getType();
            } else if (this.left instanceof VariableReferenceNode) {
                VariableReferenceNode leftRefNode = (VariableReferenceNode) this.left;
                left = leftRefNode.getType();
            } else if(this.right instanceof VariableReferenceNode) {
                VariableReferenceNode rightRefNode = (VariableReferenceNode) this.right;
                right = rightRefNode.getType();
            }
        }
        if(this.left instanceof MathOpNode){
            MathOpNode leftMathOp = (MathOpNode) this.left;
            left = leftMathOp.getDataType();
            if(this.right instanceof MathOpNode) {
                MathOpNode rightMathOp = (MathOpNode) this.right;
                right = rightMathOp.getDataType();
            }
            else if(this.right instanceof IntegerNode) right = VariableNode.varType.INTEGER;
            else if(this.right instanceof RealNode) right = VariableNode.varType.REAL;
            else if(this.right instanceof StringNode) right = VariableNode.varType.STRING;
        } else if(this.right instanceof MathOpNode){
            MathOpNode rightMathOp = (MathOpNode) this.right;
            right = rightMathOp.getDataType();
            if(this.left instanceof MathOpNode){
                MathOpNode leftMathOp = (MathOpNode) this.left;
                left = leftMathOp.getDataType();
            }
            else if(this.left instanceof IntegerNode) left = VariableNode.varType.INTEGER;
            else if(this.left instanceof RealNode) left = VariableNode.varType.REAL;
            else if(this.left instanceof StringNode) left = VariableNode.varType.STRING;
        }
        if(this.left instanceof IntegerNode) left = VariableNode.varType.INTEGER;
        if(this.left instanceof RealNode) left = VariableNode.varType.REAL;
        if(this.left instanceof StringNode) left = VariableNode.varType.STRING;
        if(this.right instanceof IntegerNode) right = VariableNode.varType.INTEGER;
        if(this.right instanceof RealNode) right = VariableNode.varType.REAL;
        if(this.right instanceof StringNode) right = VariableNode.varType.STRING;
        if(left == right) return left;
        else throw new SyntaxErrorException("[MathOpNode getDataType] getDataType");
    }

    public String getOperator(){
        return op.toString();
    }

    @Override
    public String toString() {
        return "("+ left.toString() + " " + op.toString() + " " + right.toString() + ")";
    }
}
