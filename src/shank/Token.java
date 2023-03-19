package shank;

public class Token {
    enum tokenType{
        IDENTIFIER, NUMBER, ENDOFLINE,
        DEFINE, CONSTANTS, VARIABLES, VAR,
        INTEGER, REAL, BOOLEAN, CHARACTER, STRING, ARRAY,
        FOR, FROM, TO, WRITE, NOT, OR,
        IF, ELSIF, ELSE, THEN, REPEAT, UNTIL,
        EQUALS, NOTEQUAL, LESSTHAN, LESSOREQUAL, GREATERTHAN, GREATEROREQUAL,
        PLUS, MINUS, TIMES, DIVIDE, MOD,
        STRINGLITERAL, CHARACTERLITERAL, INDENT, DEDENT,
        COMMA, COLON, SEMICOLON, LPAREN, RPAREN
    }

    private tokenType type;
    private String value;
    private String nameIdentifier;
    public int lineNumber;

    /**
     * Public accessor for getting type
     * @return - token type
     */
    public tokenType getType(){
        return type;
    }

    /**
     * Public accessor for value
     * @return - value
     */
    public String getValue(){
        return value;
    }

    /**
     *
     * @param lineNumber
     */

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    /**
     * Gets the line number
     * @return line number
     */
    public int getLineNumber(){
        return lineNumber;
    }

    /**
     * Gets the identifier name (function name)
     * @return the name
     */
    public String getNameIdentifier(){
        return nameIdentifier;
    }

    public Token(tokenType tokenFinder){
        this.value = tokenFinder.toString();
    }
    /**
     * To String Overload
     * @return
     */
    public String toString(){
        return "";
    }
}


