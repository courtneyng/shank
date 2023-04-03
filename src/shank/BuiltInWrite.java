package shank;

import java.util.ArrayList;

public class BuiltInWrite extends FunctionNode{
    /**
     * Default constructor for function node
     * @param name - name
     * @param variables - variables
     */
    public BuiltInWrite(String name, ArrayList<VariableNode> variables) {
        super(name, variables);
    }

    public void execute(ArrayList<InterpreterDataType> data) throws SyntaxErrorException{
        for (InterpreterDataType type : data) {
            if (type instanceof ArrayDataType) {
                ArrayDataType arr = (ArrayDataType) type;
                StringBuilder str = new StringBuilder("Array [");
                ArrayList<InterpreterDataType> arrVals = arr.getData();
                for (InterpreterDataType arrVal : arrVals) {
                    str.append(arrVal).append(",");
                }
                str.append("]");
                System.out.println(str);
            }
            else {
                switch (type) {
                    case IntegerDataType integer -> System.out.println(integer.getValue() + " ");
                    case RealDataType real -> System.out.println(real.getValue() + " ");
                    case StringDataType str -> System.out.println(str.getValue() + " ");
                    case CharacterDataType character -> System.out.println(character.getValue() + " ");
                    case BooleanDataType bool -> System.out.println(bool.getValue() + " ");
                    case null, default -> throw new SyntaxErrorException("[BuiltInWrite: execute] Does not contain the correct arguments for function");
                }
            }
        }
    }
}
