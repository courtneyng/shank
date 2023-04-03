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
            if(type instanceof IntegerDataType){
                IntegerDataType integer = (IntegerDataType) type;
                System.out.println("Enter an int value: ");
                integer.setValue(scan.nextInt());
                type = integer;
            } else if (type instanceof RealDataType) {
                RealDataType real = (RealDataType) type;
                System.out.println("Enter an real (float) value: ");
                real.setValue(scan.nextFloat());
                type = real;
            } else if (type instanceof StringDataType) {
                StringDataType str = (StringDataType) type;
                System.out.println("Enter an int value: ");
                str.setValue(scan.nextLine());
                type = str;
            } else if (type instanceof CharacterDataType) {
                CharacterDataType character = (CharacterDataType) type;
                System.out.println("Enter an int value: ");
                character.setValue(scan.nextLine().charAt(0));
                type = character;
            } else if (type instanceof BooleanDataType) {
                BooleanDataType bool = (BooleanDataType) type;
                System.out.println("Enter an int value: ");
                bool.setValue(scan.nextBoolean());
                type = bool;
            } else throw new SyntaxErrorException("[BuiltInRead: execute] Expected: changeable argument");
        }
        scan.close();
    }


}
