package shank;

import java.util.ArrayList;

public class BuiltInSubstring extends FunctionNode{
    public BuiltInSubstring(){}

    public void execute(ArrayList<InterpreterDataType> data) throws SyntaxErrorException{
        if(data.get(0) instanceof StringDataType && ((StringDataType) data.get(0)).isChangeable()){
            StringDataType strData = (StringDataType) data.get(0);
            IntegerDataType indexVal = (IntegerDataType) data.get(1);
            IntegerDataType lengthVal = (IntegerDataType) data.get(2);
            StringDataType str = (StringDataType)  data.get(3);

            int index = indexVal.getValue();
            int length = lengthVal.getValue();

            String string = strData.getValue();
            StringBuilder finalStr = new StringBuilder();

            for(int i=0; i<index+length; i++){
                finalStr.append(string.charAt(i));
            }
            str.setValue(finalStr.toString());
            data.set(3, str);
        }
        else throw new SyntaxErrorException("[BuiltInSubstring: execute] Does not contain the correct arguments for function");
    }
}
