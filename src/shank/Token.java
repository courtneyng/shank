package shank;

public class Token {
    enum tokenType{
        IDENTIFIER, NUMBER, ENDOFLINE,
        DEFINE, CONSTANTS, VARIABLES, VAR,
        INTEGER, REAL, BOOLEAN, CHARACTER, STRING, ARRAY,
        FOR, FROM, TO, WRITE, NOT, OR,
        IF, ELSIF, ELSE, THEN, REPEAT, UNTIL,
        EQUALS, NOTEQUAL, LESSTHAN, LESSOREQUAL, GREATERTHAN, GREATEROREQUAL,
        PLUS, MINUS, MULTIPLY, DIVIDE, MOD,
        STRINGLITERAL, CHARACTERLITERAL, INDENT, DEDENT
    }

    private tokenType type;
    private String value;

    public int lineNumber;

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

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public int getLineNumber(){
        return this.lineNumber;
    }

    /**
     * To String Overload
     * @return
     */
    public String toString(){
        return "";
    }
}


