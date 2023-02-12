package shank;

public class Token {
    public int lineNumber;
    enum tokenType{
        IDENTIFIER, NUMBER, ENDOFLINE,
        DEFINE, CONSTANTS, VARIABLES, VAR,
        INTEGER, REAL, BOOLEAN, CHARACTER, STRING, ARRAY,
        FOR, FROM, TO, WRITE, NOT, OR,
        IF, ELSIF, ELSE, THEN, REPEAT, UNTIL,
        EQUALS, NOTEQUAL, LESSTHAN, LESSOREQUAL, GREATERTHAN, GREATEROREQUAL,
        PLUS, MINUS, MULTIPLY, DIVIDE, MOD,
        STRINGLITERAL, CHARACTERLITERAL
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


