package shank;

//Unterminated strings or characters are invalid and should throw this exception, along with any invalid symbols.
public class SyntaxErrorException extends Exception{
    public SyntaxErrorException(String message){
        super(message);
    }
}
