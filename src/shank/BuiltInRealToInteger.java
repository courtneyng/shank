package shank;

import java.util.ArrayList;

public class BuiltInRealToInteger extends FunctionNode{

    public BuiltInRealToInteger() {}

    public void execute(ArrayList<InterpreterDataType> data) throws SyntaxErrorException{
        if(data.get(0) instanceof RealDataType && data.size() == 2 && ((RealDataType) data.get(0)).isChangeable()){
            RealDataType realData = (RealDataType) data.get(0);
            float real = realData.getValue();
            int integer = (int) real;

            IntegerDataType intData = (IntegerDataType) data.get(1);
            intData.setValue(integer);
            data.set(1, intData);
        }
        else throw new SyntaxErrorException("[BuiltInRealToInteger: execute] Does not contain the correct arguments for function");
    }
}
