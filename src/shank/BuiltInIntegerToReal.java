package shank;

import java.util.ArrayList;
import java.util.Random;

public class BuiltInIntegerToReal extends FunctionNode{

    /**
     * Default constructor
     * @param name - name
     * @param variables - variables
     */
    public BuiltInIntegerToReal(String name, ArrayList<VariableNode> variables) {
        super(name, variables);
    }

    public void execute(ArrayList<InterpreterDataType> data) throws SyntaxErrorException{
        if(data.get(0) instanceof RealDataType && data.size() == 2 && ((RealDataType) data.get(0)).isChangeable() == true){
            IntegerDataType intData = (IntegerDataType) data.get(0);
            int integer = intData.getValue();
            float real = integer * (float) 1;

            RealDataType realData = (RealDataType) data.get(1);
            realData.setValue(real);
            data.set(1, realData);
        }
        else throw new SyntaxErrorException("[BuiltInIntegerToReal: execute] Does not contain the correct arguments for function");
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
