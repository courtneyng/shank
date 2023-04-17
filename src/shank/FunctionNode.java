package shank;

import java.util.*;
public class FunctionNode extends Node{
    private String name;
    private ArrayList<VariableNode> parameters;
    private ArrayList<VariableNode> constants;
    private ArrayList<VariableNode> variables;
    private ArrayList<StatementNode> statements;

    public FunctionNode(){};

    public FunctionNode(String name, ArrayList<VariableNode> variables){
        this.name = name;
        this.variables = variables;
    }

    public FunctionNode(String name) {
        this.name = name;
    }

    public ArrayList<VariableNode> getParameters(){
        return parameters;
    }

    public void setParameters(ArrayList<VariableNode> parameters){
        this.parameters = parameters;
    }

    public ArrayList<VariableNode> getConstants(){
        return constants;
    }

    public void setConstants(ArrayList<VariableNode> constants) {
        this.constants = constants;
    }

    public ArrayList<VariableNode> getVariables(){
        return variables;
    }

    public void setVariables(ArrayList<VariableNode> variables){
        this.variables = variables;
    }

    public ArrayList<StatementNode> getStatements(){
        return statements;
    }

    public void setStatements(ArrayList<StatementNode> statements){
        this.statements = statements;
    }

    public String getName() {
        return name;
    }

    public void updateArguments(ArrayList<InterpreterDataType> arguments) throws SyntaxErrorException{
        if (arguments != null) {
            for (int i = 0; i < arguments.size(); i++) {
                if (arguments.get(i) instanceof IntegerDataType) {
                    IntegerDataType intArgs = (IntegerDataType) arguments.get(i);
                    IntegerDataType newIntArgs = (IntegerDataType) arguments.get(i);
                    intArgs.setValue(newIntArgs.getValue());
                    arguments.set(i, intArgs);
                } else if (arguments.get(i) instanceof RealDataType) {
                    RealDataType realArgs = (RealDataType) arguments.get(i);
                    RealDataType newRealArgs = (RealDataType) arguments.get(i);
                    realArgs.setValue(newRealArgs.getValue());
                    arguments.set(i, realArgs);
                } else if (arguments.get(i) instanceof StringDataType) {
                    StringDataType stringArgs = (StringDataType) arguments.get(i);
                    StringDataType newStringArgs = (StringDataType) arguments.get(i);
                    stringArgs.setValue(newStringArgs.getValue());
                    arguments.set(i, stringArgs);
                } else if (arguments.get(i) instanceof CharacterDataType) {
                    CharacterDataType charArgs = (CharacterDataType) arguments.get(i);
                    CharacterDataType newCharArgs = (CharacterDataType) arguments.get(i);
                    charArgs.setValue(newCharArgs.getValue());
                    arguments.set(i, charArgs);
                } else if (arguments.get(i) instanceof BooleanDataType) {
                    BooleanDataType boolArgs = (BooleanDataType) arguments.get(i);
                    BooleanDataType newBoolArgs = (BooleanDataType) arguments.get(i);
                    boolArgs.setValue(newBoolArgs.getValue());
                    arguments.set(i, boolArgs);
                } else throw new SyntaxErrorException("[FuctionNode updateArguments] Exception: Unknown data type");
            }
        }
    }

    public boolean isVariadic(){
        if(this.name == "read" || this.name == "write") return true;
        else return false;
    }

    @Override
    public String toString() {
        return null;
    }
}
