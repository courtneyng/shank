package shank;

public class RealNode extends Node{
    private float value; //private member

    //public accessors
    public RealNode(float value){
        this.value = value;
    }

    public float getValue(){
        return value;
    }
    @Override
    public String toString() {
        return Float.toString(value);
    }
}
