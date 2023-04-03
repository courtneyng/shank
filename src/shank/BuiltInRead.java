package shank;

import java.util.ArrayList;
import java.util.Scanner;

public class BuiltInRead extends FunctionNode {

    /**
     * Default constructor
     * @param name - name
     * @param variables - variables
     */
    public BuiltInRead(String name, ArrayList<VariableNode> variables) {
        super(name, variables);
    }

    IntegerDataType execute(){
        Scanner scan = new Scanner(System.in);
        return null;
    }


}
