package shank;

import java.util.ArrayList;

public class BuiltInIntegerToReal extends FunctionNode{

    /**
     * Default constructor
     * @param name - name
     * @param variables - variables
     */
    public BuiltInIntegerToReal(String name, ArrayList<VariableNode> variables) {
        super(name, variables);
    }

    void execute(ArrayList<InterpreterDataType> data){
        for(int i=0; i<data.size();i=i+2){
            data.set(i, new RealDataType(Float.parseFloat(data.get(i).toString())));
        }
    }

    /*
    @Override
    public String toString() {
        String str = "IntegerToReal: ";
        for(int i=0; i<data.size();i=i+2){
            str += "int: (" + variables.get(i).toString() + ")" + "to variable: (" + variables.get(i).toString();
        }
        str += ")";
        return str;
    }
    */
}
