package shank;

import java.util.ArrayList;

public class BuiltInStart extends FunctionNode{
    public BuiltInStart(){}

    public void execute(ArrayList<InterpreterDataType> data) throws SyntaxErrorException{
        if(data.get(0) instanceof ArrayDataType && ((ArrayDataType) data.get(0)).isChangeable()){
            ArrayDataType arrVal = (ArrayDataType) data.get(0);
            IntegerDataType startIndex = new IntegerDataType(arrVal.getStartIndex(), false);
            data.set(0, startIndex);
        }
        else throw new SyntaxErrorException("[BuiltInStart: execute] Does not contain the correct arguments for function");
    }
}
