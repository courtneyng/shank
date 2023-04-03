package shank;

import java.util.ArrayList;

public class BuiltInRealToInteger extends FunctionNode{

    /**
     * Default constructor
     * @param name - name
     * @param variables - variables
     */
    public BuiltInRealToInteger(String name, ArrayList<VariableNode> variables) {
        super(name, variables);
    }

    void execute(ArrayList<InterpreterDataType> data){
        for(int i=0; i<data.size();i=i++){
            data.set(i, new RealDataType(Float.parseFloat(data.get(i).toString())));
        }
    }
}
