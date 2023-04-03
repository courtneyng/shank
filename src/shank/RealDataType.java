package shank;

public class RealDataType extends InterpreterDataType{

    private float value;
    private boolean changeable;

    public RealDataType(){}

    public RealDataType(float value){
        this.value = value;
    }

    public RealDataType(float value, boolean changeable){
        this.value = value;
        this.changeable = changeable;
    }
    @Override
    public String toString() {
        return "RealDataType: [" + value + "] ";
    }

    @Override
    public void fromString(String input) {
        value = Float.parseFloat(input);
    }
}
