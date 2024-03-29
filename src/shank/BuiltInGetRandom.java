package shank;

import java.util.*;

public class BuiltInGetRandom extends FunctionNode{
    public BuiltInGetRandom(){}

    public void execute(ArrayList<InterpreterDataType> data) throws SyntaxErrorException{
        if(data.get(0) instanceof IntegerDataType && data.size() == 1 && ((IntegerDataType) data.get(0)).isChangeable()){
            Random rand = new Random();
            IntegerDataType intData = (IntegerDataType) data.get(0);
            int randNum = rand.nextInt(101); // number between 0 and 100
            intData.setValue(randNum);
            data.set(0, intData);
        }
        else throw new SyntaxErrorException("[BuiltInGetRandom: execute] Does not contain the correct arguments for function");
    }
}
