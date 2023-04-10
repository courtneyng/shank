package shank;

public class VariableNode extends Node{
    enum varType{ INTEGER, REAL, CHARACTER, STRING, BOOLEAN, ARRAY }
    private String name;
    private Node value;
    private boolean changeable;
    private varType type;

    // indexing
    private int fromInt;
    private int toInt;
    private float fromReal;
    private float toReal;

    // arrays
    private int[] intArr;
    private float[] realArr;
    private char[] charArr;
    private String[] strArr;
    private boolean[] boolArr;


    // Public Accessors

    // empty constructor
    public VariableNode(){}

    public VariableNode(String name, varType type, Node value){
        this.name = name;
        this.type = type;
        this.value = value;
    }

    public VariableNode(String name, varType type, boolean changeable){
        this.name = name;
        this.type = type;
        this.changeable = changeable;
    }

    public VariableNode(String name, varType type, Node value, boolean changeable){
        this.name = name;
        this.type = type;
        this.value = value;
        this.changeable = changeable;
    }

    public VariableNode(String name, int fromInt, int toInt, varType array){
        this.name = name;
        this.fromInt = fromInt;
        this.toInt = toInt;
        this.type = array;
        changeable = true;

        // set up arrays
        intArr = new int[toInt - fromInt];
        realArr = new float[toInt - fromInt];
        charArr = new char[toInt - fromInt];
        strArr = new String[toInt - fromInt];
        boolArr = new boolean[toInt - fromInt];
    }

    public String getName(){
        return name;
    }

    public varType getType(){
        return type;
    }

    public boolean isChangeable(){
        return changeable;
    }

    public Node getValue() {
        return value;
    }

    public void setValue(Node value) {
        if(changeable == true) this.value = value;
    }

    public int getFromInt() {
        return fromInt;
    }

    public int getToInt() {
        return toInt;
    }

    public float getFromReal() {
        return fromReal;
    }

    public float getToReal() {
        return toReal;
    }

    public Node getArrValue(int index, varType type){
        switch(type){
            case INTEGER -> {
                if(intArr.length <= index) return null;
                int value = intArr[index];
                return new IntegerNode(value);
            }
            case REAL -> {
                if(realArr.length <= index) return null;
                float value = realArr[index];
                return new RealNode(value);
            }
            case CHARACTER -> {
                if(charArr.length <= index) return null;
                char value = charArr[index];
                return new CharacterNode(value);
            }
            case STRING -> {
                if(strArr.length <= index) return null;
                String value = strArr[index];
                return new StringNode(value);
            }
            case BOOLEAN -> {
                if(boolArr.length <= index) return null;
                boolean value = boolArr[index];
                return new BooleanNode(value);
            }
            default -> {return null;}
        }
    }

    public void setArrValue(int index, Node node, varType type) throws SyntaxErrorException{
        switch (type){
            case INTEGER -> {
                IntegerNode intNode = (IntegerNode) node;
                int value = intNode.getValue();
                intArr[index] = value;
            }
            case REAL -> {
                RealNode realNode = (RealNode) node;
                float value = realNode.getValue();
                realArr[index] = value;
            }
            case CHARACTER -> {
                CharacterNode charNode = (CharacterNode) node;
                char value = charNode.getValue();
                charArr[index] = value;
            }
            case STRING -> {
                StringNode strNode = (StringNode) node;
                String value = strNode.getValue();
                strArr[index] = value;
            }
            case BOOLEAN -> {
                BooleanNode boolNode = (BooleanNode) node;
                boolean value = boolNode.getValue();
                boolArr[index] = value;
            }
            default -> throw new SyntaxErrorException("[VariableNode setArrValue] Exception: Needs proper data type (int, real, char, string, bool)");
        }
    }

    @Override
    public String toString() {
        return name + changeable + type;
    }
}
