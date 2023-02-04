package shank;

public class Token {
    enum tokenType{
        WORD, NUMBER, ENDOFLINE
    }

    private tokenType type;
    private String value;

    public tokenType getType(){
        return this.type;
    }

    public String getValue(){
        return this.value;
    }


    /**
     * To String Overload
     * @return
     */
    public String toString(){
        return "";
    }
}


