package shank;

public class Token {
    enum tokenType{
        IDENTIFIER, NUMBER, ENDOFLINE,
        DEFINE, CONSTANTS, VARIABLES, OPEN_CURLY, CLOSE_CURLY,
        INTEGER, REAL, BOOLEAN, CHARACTER, STRING, ARRAY,
        FOR, FROM, TO, WRITE,
        IF, ELSIF, ELSE, THEN, REPEAT, UNTIL,
        EQUALS, NOTEQUAL, LESSTHAN, LESSOREQUAL, GREATERTHAN, GREATEROREQUAL,
        PLUS, MINUS, MULTIPLY, DIVIDE, MOD,
        NOT, OR
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


