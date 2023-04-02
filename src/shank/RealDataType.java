package shank;

public class RealDataType extends InterpreterDataType{

    private float value;

    public RealDataType(){}

    public RealDataType(float value){
        this.value = value;
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
