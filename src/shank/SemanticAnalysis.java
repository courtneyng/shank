package shank;

import java.util.*;

public class SemanticAnalysis {

    public SemanticAnalysis(){}
    public void CheckAssignments(ProgramNode programNode) throws SyntaxErrorException {
        ArrayList<AssignmentNode> nodeArrayList = programNode.getNodeArray();
        for(AssignmentNode node : nodeArrayList){
            VariableNode.varType left, right;
            left = getLeftType(node);
            right = getRightType(node);
            if(left == null) throw new SyntaxErrorException("[SemanticAnalysis CheckAssignments] Exception: Unknown data type");
            if(right == null) throw new SyntaxErrorException("[SemanticAnalysis CheckAssignments] Exception: Unknown data type");
            if(left != right) throw new SyntaxErrorException("[SemanticAnalysis CheckAssignments] Exception: Data types do not match");
        }
    }

    private VariableNode.varType getLeftType(AssignmentNode assignmentNode) throws SyntaxErrorException{
        if(!assignmentNode.getTarget().getReferencedVariable().isChangeable())
            throw new SyntaxErrorException("[SemanticAnalysis getLeftType] Exception: A variable is a constant");
        return assignmentNode.getTarget().getType();
    }

    private VariableNode.varType getRightType(AssignmentNode assignmentNode) throws SyntaxErrorException{
        Node value = assignmentNode.getValue();
        if(value instanceof MathOpNode mathOpNode){
            return mathOpNode.getDataType();
        }
        else if(value instanceof IntegerNode) return VariableNode.varType.INTEGER;
        else if(value instanceof RealNode) return VariableNode.varType.REAL;
        else if(value instanceof StringNode) return VariableNode.varType.STRING;
        else if(value instanceof CharacterNode) return VariableNode.varType.CHARACTER;
        else if(value instanceof BooleanNode) return VariableNode.varType.BOOLEAN;
        return null;
    }
}
