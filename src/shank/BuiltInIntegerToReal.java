package shank;

import java.util.ArrayList;

public class BuiltInIntegerToReal extends FunctionNode{

    public BuiltInIntegerToReal(){}

    public void execute(ArrayList<InterpreterDataType> data) throws SyntaxErrorException{
        if(data.get(0) instanceof RealDataType && data.size() == 2 && ((RealDataType) data.get(0)).isChangeable()){
            IntegerDataType intData = (IntegerDataType) data.get(0);
            int integer = intData.getValue();
            float real = integer * (float) 1;

            RealDataType realData = (RealDataType) data.get(1);
            realData.setValue(real);
            data.set(1, realData);
        }
        else throw new SyntaxErrorException("[BuiltInIntegerToReal: execute] Does not contain the correct arguments for function");
    }

}
