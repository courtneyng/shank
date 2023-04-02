package shank;

import java.util.*;
import java.util.ArrayList;

public class ArrayDataType extends InterpreterDataType{

    enum arrayDataType{INTEGER, REAL, STRING, CHARACTER, BOOLEAN}
    private arrayDataType type;
    private ArrayList<InterpreterDataType> data;
    private boolean changeable;
    private int startIndex, endIndex = 0;

    public ArrayDataType(){}

    public ArrayDataType(ArrayList<InterpreterDataType> data, boolean changeable){
        this.data = data;
        this.changeable = changeable;
    }

    public ArrayDataType(arrayDataType type, int startIndex, int endIndex, boolean changeable) throws SyntaxErrorException {
        this.type = type;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.changeable = changeable;

        switch(type){
            case INTEGER -> {
                for(int i=0;i<endIndex; i++){
                    IntegerDataType intData = new IntegerDataType(0);
                    this.data.add(intData);
                }
            }

            case REAL -> {
                for(int i=0;i<endIndex; i++){
                    RealDataType realData = new RealDataType(0);
                    this.data.add(realData);
                }
            }

            case STRING -> {
                for(int i=0;i<endIndex; i++){
                    StringDataType strData = new StringDataType("");
                    this.data.add(strData);
                }
            }

            case CHARACTER -> {
                for(int i=0;i<endIndex; i++){
                    CharacterDataType charData = new CharacterDataType(' ');
                    this.data.add(charData);
                }
            }

            case BOOLEAN -> {
                for(int i=0;i<endIndex; i++){
                    BooleanDataType boolData = new BooleanDataType(true);
                    this.data.add(boolData);
                }
            }
            case default -> {
                throw new SyntaxErrorException("[ArrayDataType] Expected: Data Type {int, real, bool, string, char}");
            }
        }
    }

    public ArrayList<InterpreterDataType> getData() {
        return data;
    }

    public void setData(ArrayList<InterpreterDataType> data) {
        this.data = data;
    }

    public arrayDataType getType() {
        return type;
    }

    public boolean isChangeable() {
        return changeable;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setIndex(int index, InterpreterDataType data) {
        this.data.set(index, data);
    }

    public int getEndIndex() {
        return endIndex;
    }

    public void setRange(int startIndex, int endIndex){
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    @Override
    public String toString() {
        return null;
    }

    @Override
    public void fromString(String input) {

    }
}
