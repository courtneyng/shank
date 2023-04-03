package shank;

import java.util.ArrayList;

public class BuiltInRight extends FunctionNode{
    /**
     * Default constructor
     * @param name - name
     * @param variables - variables
     */
    public BuiltInRight(String name, ArrayList<VariableNode> variables) {
        super(name, variables);
    }

    public void execute(ArrayList<InterpreterDataType> data) throws SyntaxErrorException{
        if(data.get(0) instanceof StringDataType && ((StringDataType) data.get(0)).isChangeable() == true){
            StringDataType strData = (StringDataType) data.get(0);
            IntegerDataType dataLength = (IntegerDataType) data.get(1);
            StringDataType string = (StringDataType) data.get(2);

            int length = dataLength.getValue();
            String str = strData.getValue();
            String finalStr = "";
            for(int i=0; i<str.length(); i++){
                finalStr += str.charAt(i);
            }
            string.setValue(finalStr);
            data.set(2, string);
        }
        else throw new SyntaxErrorException("[BuiltInRight: execute] Does not contain the correct arguments for function");
    }
}