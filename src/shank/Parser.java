package shank;

import java.util.ArrayList;

public class Parser {
    private ArrayList<Token> tokens;

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
    public Node Parse() throws SyntaxErrorException {
        Node node = expression();
        if(node == null){
            throw new SyntaxErrorException("Problem initializing node");
        }
        else{
            return node;
        }
    }


    //TERM { (plus or minus) TERM}
    public Node expression(){
        Node node = term();
        int x = 0;
        if(node == null){
            return null;
        }
        //Then it (in a loop) looks for a + or -
        try{
            if(MathOpNode.MathOp.ADD || MathOpNode.MathOp.SUBTRACT){
                MathOpNode.toString();
            }
        }
        return node;
    }

    //FACTOR { (times or divide or mod) FACTOR}
    public Node term(){
        Node node = factor();
        Token token = token.getType(0);
        if(node == null){
            return null;
        }
        switch(token){
            case MathOpNode.MathOp.MULTIPLY -> {
                matchAndRemove(token);
                node = new MathOpNode(node,term(), MathOpNode.MathOp.MULTIPLY);
            }
            case MathOpNode.MathOp.DIVIDE -> {
                matchAndRemove(token);
                node = new MathOpNode(node, term(), MathOpNode.MathOp.DIVIDE);
            }
            case MathOpNode.MathOp.MODULO -> {
                matchAndRemove(token);
                node = new MathOpNode(node, term(), MathOpNode.MathOp.MODULO);
            }
        }
        return node;
    }
    //{-} number or lparen EXPRESSION rparen
    public Node factor(){
        Node node = expression();

        return new MathOpNode();
    }

    /**
     * accepts a token type. Looks at the next token in the collection:
     *
     * @param type
     * @return If the passed in token type matches the next token’s type, remove that token and return it.
     * @return If the passed in token type DOES NOT match the next token’s type (or there are no more tokens) return null.
     */
    private Token matchAndRemove(Token.tokenType type){
        Token token = tokens.get(0);
        if(token.getType() == token){
            tokens.remove(0);
            return token;
        }
        else{
            return null;
        }
    }

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
        // creeates a new function node calls suboridinate methods

    }

    public void parameterDeclarations(){

    }

}
