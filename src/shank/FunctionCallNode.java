package shank;

import java.util.*;

public class FunctionCallNode extends StatementNode{

    private ArrayList<ParameterNode> parameters;
    private String functionName;

    public FunctionCallNode(){}

    public FunctionCallNode(String functionName, ArrayList<ParameterNode> parameters){
        this.functionName = functionName;
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        String str = "\n[FunctionCallNode] Function name: " + functionName + "\n Parameters: \n";
        for(int i=0; i<parameters.size(); i++){
            str += parameters.get(i).toString() + "\n";
        }
        str += ")";
        return str;
    }
}
