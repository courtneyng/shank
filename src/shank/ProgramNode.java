package shank;

import java.util.HashMap;

public class ProgramNode extends Node{
    HashMap<String, FunctionNode> functionNodeMap = new HashMap<>();

    private BuiltInRead builtInRead = new BuiltInRead();
    private BuiltInWrite builtInWrite = new BuiltInWrite();
    private BuiltInLeft builtInLeft = new BuiltInLeft();
    private BuiltInRight builtInRight = new BuiltInRight();
    private BuiltInSubstring builtInSubstring = new BuiltInSubstring();
    private BuiltInSquareRoot builtInSquareRoot = new BuiltInSquareRoot();
    private BuiltInGetRandom builtInGetRandom = new BuiltInGetRandom();
    private BuiltInIntegerToReal builtInIntegerToReal = new BuiltInIntegerToReal();
    private BuiltInRealToInteger builtInRealToInteger = new BuiltInRealToInteger();
    private BuiltInStart builtInStart = new BuiltInStart();
    private BuiltInEnd builtInEnd = new BuiltInEnd();

    public ProgramNode(){
        addMap(builtInRead);
        addMap(builtInWrite);
        addMap(builtInLeft);
        addMap(builtInRight);
        addMap(builtInSubstring);
        addMap(builtInSquareRoot);
        addMap(builtInGetRandom);
        addMap(builtInIntegerToReal);
        addMap(builtInRealToInteger);
        addMap(builtInStart);
        addMap(builtInEnd);
    }

    public void addMap(FunctionNode functionNode){
        functionNodeMap.put(functionNode.getName(), functionNode);
    }

    public HashMap<String, FunctionNode> getMap(){
        return functionNodeMap;
    }

    /**
     * is it a function
     * @param str - the input name
     * @return true/false if is a function
     */
    public boolean isFunction(String str){
        return functionNodeMap.containsKey(str);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for(String key: functionNodeMap.keySet()){
            str.append(functionNodeMap.get(key).toString()).append("\n\n");
        }
        return str.toString();
    }
}
