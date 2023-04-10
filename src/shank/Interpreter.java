package shank;

import java.util.ArrayList;
import java.util.HashMap;

public class Interpreter {

    HashMap <String, InterpreterDataType> localVars = new HashMap<>();

    /**
     * Add interpretFunction(FunctionNode) as a method to a new Interpreter class. Create a hash map
     * (name🡪IDT) for local variables. Loop over the constants and local variables and create new IDTs
     * and add them to the hash map. Finally, pass the hash map and the collection of StatementNode to
     * interpretBlock().
     * @param node - function node
     */
    public void interpretFunction(FunctionNode node, HashMap<String, InterpreterDataType> map){
        ArrayList<VariableNode> params = node.getParameters();
        ArrayList<VariableNode> constants = node.getConstants();
        ArrayList<VariableNode> variables = node.getVariables();
        ArrayList<StatementNode> statements = node.getStatements();


        interpretBlock();
    }

    /**
     * interpretBlock should loop over the StatementNode collection. For each node type (use instanceOf)
     * call a method for that node type (IfNode, VariableReferenceNode,MathOpNode, etc.) Those methods
     * will vary in return type and parameters. InterpretBlock is a little bit of recursive magic – you
     * will be in the middle of this and call it again for loops or conditionals of any kind.
     */
    public void interpretBlock(){}

    private void parameterMap(ArrayList<VariableNode> params, HashMap<String, InterpreterDataType> paramMap){
        if(params != null){
            for(int i=0; i<params.size(); i++){
                VariableNode current = params.get(i);
                String name = current.getName();

                switch (current.getType()){
                    case INTEGER -> {
                        if(current.getValue() == null){
                            IntegerDataType intData = new IntegerDataType(0, current.isChangeable());
                            paramMap.put(name, intData);
                        } else{
                            IntegerNode intNode = (IntegerNode) current.getValue();
                            IntegerDataType intData = new IntegerDataType(intNode.getValue(), current.isChangeable());
                            paramMap.put(name, intData);
                        }
                    }
                    case REAL -> {
                        if(current.getValue() == null){
                            RealDataType realData = new RealDataType(0, current.isChangeable());
                            paramMap.put(name, realData);
                        } else{
                            RealNode realNode = (RealNode) current.getValue();
                            RealDataType realData = new RealDataType(realNode.getValue(), current.isChangeable());
                            paramMap.put(name, realData);
                        }
                    }
                    case CHARACTER -> {
                        if(current.getValue() == null){
                            CharacterDataType charData = new CharacterDataType(' ', current.isChangeable());
                            paramMap.put(name, charData);
                        } else{
                            CharacterNode charNode = (CharacterNode) current.getValue();
                            CharacterDataType charData = new CharacterDataType(charNode.getValue(), current.isChangeable());
                            paramMap.put(name, charData);
                        }
                    }
                    case STRING -> {
                        if(current.getValue() == null){
                            StringDataType strData = new StringDataType("", current.isChangeable());
                            paramMap.put(name, strData);
                        } else{
                            StringNode strNode = (StringNode) current.getValue();
                            StringDataType strData = new StringDataType(strNode.getValue(), current.isChangeable());
                            paramMap.put(name, strData);
                        }
                    }
                    case BOOLEAN -> {
                        if(current.getValue() == null){
                            BooleanDataType boolData = new BooleanDataType(false, current.isChangeable());
                            paramMap.put(name, boolData);
                        } else{
                            BooleanNode boolNode = (BooleanNode) current.getValue();
                            BooleanDataType boolData = new BooleanDataType(boolNode.getValue(), current.isChangeable());
                            paramMap.put(name, boolData);
                        }
                    }
                    case ARRAY -> {
                        int startIndex;
                    }
                }
            }
        }
    }
}
