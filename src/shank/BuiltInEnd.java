package shank;

import java.util.ArrayList;

public class BuiltInEnd extends FunctionNode{

    public BuiltInEnd(){}

    public void execute(ArrayList<InterpreterDataType> data) throws SyntaxErrorException{
        if(data.get(0) instanceof ArrayDataType && data.size() == 1 && ((ArrayDataType) data.get(0)).isChangeable()){
            ArrayDataType dataArr = (ArrayDataType) data.get(0);
            IntegerDataType endIndex = new IntegerDataType(dataArr.getEndIndex(), false);
            data.set(0, endIndex);
        }
        else throw new SyntaxErrorException("[BuiltInEnd: execute] Does not contain the correct arguments for function");
    }

}
