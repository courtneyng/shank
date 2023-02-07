package shank;

public class Token {
    enum tokenType{
        WORD, NUMBER, ENDOFLINE
    }

    private tokenType type;
    private String value;

    /**
     * Public accessor for getting type
     * @return - token type
     */
    public tokenType getType(){
        return this.type;
    }

    /**
     * Public accessor for value
     * @return - value
     */
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


