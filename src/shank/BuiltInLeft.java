package shank;

import java.util.ArrayList;

public class BuiltInLeft extends FunctionNode{
    /**
     * Default Constructor from function node
     * @param name - name
     * @param variables - variables
     */
    public BuiltInLeft(String name, ArrayList<VariableNode> variables) {
        super(name, variables);
    }

    public void execute(ArrayList<InterpreterDataType> data) throws SyntaxErrorException{
        if(data.get(0) instanceof StringDataType && ((StringDataType) data.get(0)).isChangeable()){
           StringDataType strData = (StringDataType) data.get(0);
           IntegerDataType dataLength = (IntegerDataType) data.get(1);
           StringDataType string = (StringDataType) data.get(2);

           int length = dataLength.getValue();
           String str = strData.getValue();
           StringBuilder finalStr = new StringBuilder();
           for(int i=0; i<length; i++){
               finalStr.append(str.charAt(i));
           }
           string.setValue(finalStr.toString());
           data.set(2, string);
        }
        else throw new SyntaxErrorException("[BuiltInLeft: execute] Does not contain the correct arguments for function");
    }
}
