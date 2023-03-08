package shank;

import java.util.ArrayList;

public class Parser {
    private ArrayList<Token> tokens;
    private Node root;

    public Parser(ArrayList<Token> tokens){
        this.tokens = tokens;
    }

    public ArrayList<Token> getTokens() {
        return tokens;
    }

    public void setTokens(ArrayList<Token> tokens) {
        this.tokens = tokens;
    }

    //returns program node
    public ProgramNode Parse() throws SyntaxErrorException {
        ProgramNode node;
        function();
        if(node == null){
            throw new SyntaxErrorException("Problem initializing node");
        }
        else{
            return node;
        }
    }


    //TERM { (plus or minus) TERM}
    public Node expression(){
        Node firstNode = term();
        Node secondNode = null;
        MathOpNode.MathOp operator;
        //Then it (in a loop) looks for a + or -
        while(peek(0).toString() == "PLUS" || peek(0).toString() == "MINUS"){
            if(matchAndRemove(Token.tokenType.PLUS) != null){
                operator = MathOpNode.MathOp.ADD;
                secondNode = term();
                firstNode = new MathOpNode (firstNode, secondNode, operator);

            } else if (matchAndRemove(Token.tokenType.MINUS) != null){
                operator = MathOpNode.MathOp.SUBTRACT;
                secondNode = term();
                firstNode = new MathOpNode(firstNode, secondNode, operator);
            }
        }

        return firstNode;
    }

    //FACTOR { (times or divide or mod) FACTOR}
    public Node term(){
        Node firstNode = factor();
        Node secondNode = null;
        MathOpNode.MathOp operator;

        while(peek(0).toString() == "TIMES" || peek(0).toString() == "DIVIDE" || peek(0).toString() == "MOD"){
            if(matchAndRemove(Token.tokenType.TIMES) != null){
                operator = MathOpNode.MathOp.MULTIPLY;
                secondNode = factor();
                firstNode = new MathOpNode(firstNode, secondNode, operator);
            } else if(matchAndRemove(Token.tokenType.DIVIDE) != null){
                operator = MathOpNode.MathOp.DIVIDE;
                secondNode = factor();
                firstNode = new MathOpNode(firstNode, secondNode, operator);
            } else if(matchAndRemove(Token.tokenType.MOD) != null){
                operator = MathOpNode.MathOp.MODULO;
                secondNode = factor();
                firstNode = new MathOpNode(firstNode, secondNode, operator);
            }
        }

        return firstNode;
    }
    //{-} number or lparen EXPRESSION rparen
    public Node factor(){
        Node node;

        return new MathOpNode();
    }

    /**
     * accepts a token type. Looks at the next token in the collection:
     *
     * @param type
     * @return If the passed in token type matches the next token’s type, remove that token and return it.
     * @return If the passed in token type DOES NOT match the next token’s type (or there are no more tokens)
     * return null.
     */
    private Token matchAndRemove(Token.tokenType type){
        Token token = tokens.get(0);
        if(token.equals(token.getType())){
            tokens.remove(0);
            return token;
        }
        else{
            return null;
        }
    }

    /**
     * uses matchAndRemove to match and discard one or more ENDOFLINE tokens.
     * Throw a SyntaxErrorException if no ENDOFLINE was found.
     * @throws SyntaxErrorException
     */
    private void expectsEOL() throws SyntaxErrorException{
        while (!tokens.isEmpty() && tokens.get(0).getType() == Token.tokenType.ENDOFLINE) {
            tokens.remove(0);
        }
        throw new SyntaxErrorException("No EOL found.");
    }

    /**
     * peek – accepts an integer and looks ahead that many tokens and returns that token.
     * @param x
     * @return Returns null if there aren’t enough tokens to fulfill the request.
     */
    private Token peek(int x){
        return tokens.get(x);
    }

    public void function(){
        // creates a new function node calls suboridinate methods

    }

    /**
     * parameterDeclarations() process the parameters and returns a collection of VariableNode.
     * Remember that a parameter may or may not be var. Remember that there may not be any parameters.
     */
    public void parameterDeclarations(){

    }

}
