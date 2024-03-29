package shank;

import java.util.ArrayList;

public class BuiltInSquareRoot extends FunctionNode{
    public BuiltInSquareRoot(){}

    public void execute(ArrayList<InterpreterDataType> data) throws SyntaxErrorException{
        if(data.get(0) instanceof StringDataType && ((RealDataType) data.get(0)).isChangeable()){
            RealDataType realVal = (RealDataType) data.get(0);
            RealDataType endVal = (RealDataType)  data.get(1);

            float real = realVal.getValue();
            float result = (float)Math.sqrt(real);
            endVal.setValue(result);
            data.set(1, endVal);
        }else throw new SyntaxErrorException("[BuiltInSquareRoot: execute] Does not contain the correct arguments for function");
    }
}
