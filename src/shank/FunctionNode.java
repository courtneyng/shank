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

    public boolean isVariadic(){
        if(this.name == "read" || this.name == "write") return true;
        else return false;
    }

    @Override
    public String toString() {
        return null;
    }
}
