package shank;

public abstract class InterpreterDataType {
    public abstract boolean isChangeable();

    public abstract String toString();

    public abstract void fromString(String input);

}
