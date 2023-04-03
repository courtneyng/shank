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

    public void execute(ArrayList<InterpreterDataType> data) throws SyntaxErrorException{
        Scanner scan = new Scanner(System.in);
        for(InterpreterDataType type : data){
            switch (type) {
                case IntegerDataType integer -> {
                    System.out.println("Enter an int value: ");
                    integer.setValue(scan.nextInt());
                }
                case RealDataType real -> {
                    System.out.println("Enter an real (float) value: ");
                    real.setValue(scan.nextFloat());
                }
                case StringDataType str -> {
                    System.out.println("Enter an int value: ");
                    str.setValue(scan.nextLine());
                }
                case CharacterDataType character -> {
                    System.out.println("Enter an int value: ");
                    character.setValue(scan.nextLine().charAt(0));
                }
                case BooleanDataType bool -> {
                    System.out.println("Enter an int value: ");
                    bool.setValue(scan.nextBoolean());
                }
                case null, default ->
                        throw new SyntaxErrorException("[BuiltInRead: execute] Expected: changeable argument");
            }
        }
        scan.close();
    }


}
