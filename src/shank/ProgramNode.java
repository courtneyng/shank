package shank;

import java.util.HashMap;

public class ProgramNode extends Node{
    HashMap<String, FunctionNode> functionNodeHashmap = new HashMap<>();

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

    }
    @Override
    public String toString() {
        return null;
    }
}
