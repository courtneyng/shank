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

    /**
     * parse() should call function() in its loop. Every FunctionNode returned should go into the ProgramNode
     * (there will be only one of these). null should end the parse() loop. parse() should return the ProgramNode.
     * @return
     * @throws SyntaxErrorException
     */
    public ProgramNode Parse() throws SyntaxErrorException {
        ProgramNode node = new ProgramNode();
        return node;
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
        if(tokens.get(0).getType().equals("NUMBER")) {
            Token numberToken = matchAndRemove(Token.tokenType.NUMBER);
            if(tokens.get(0).getValue().contains(".")){ // if contains "." indicates decimal; float/real node
                return new RealNode(Float.parseFloat(tokens.get(0).getValue()));
            }
            else{
                return new IntegerNode(Integer.parseInt(tokens.get(0).getValue()));
            }
        }

//        } else if(tokens.get(0).getType().equals("LPAREN")){
//            Node expr = expression();
//            matchAndRemove(Token.tokenType.RPAREN);
//        }
        return null;
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

    /**
     * function() processes a function.
         * It expects a define token.
         * Then an identifier (the name).
         * Then a left paren.
         * Then a list of 0 or more variable declarations.
         * Then a right paren.
         * Then an endOfLine.
         * Then constants and variables.
         * Then an indent.
         * Then statements.
         * Then a dedent.
         * It returns a FunctionNode or null.
     * function() should expect indent, then call expression() until it returns null and print the
     * resultant expressions (just to make sure that the parsing is still correct) then expect dedent.
     * Since the expressions are just temporary, we won’t store them in our FunctionNode().
     *
     * @return functionNode (function)
     * @throws SyntaxErrorException - if expected tokens are not met
     */
    public FunctionNode function() throws SyntaxErrorException{
        ArrayList<VariableNode> parameters = new ArrayList<>();
        ArrayList<VariableNode> constants = new ArrayList<>();
        ArrayList<VariableNode> variables = new ArrayList<>();
        ArrayList<StatementNode> statements = new ArrayList<>();

        // creates a new function node calls suboridinate methods
        // Expects define token, otherwise throw syntax error
        if(matchAndRemove(Token.tokenType.DEFINE) == null) throw new SyntaxErrorException("[Parser] Expected: define");

        // Expects identifier token, otherwise throw syntax error
        Token nameIdentifier = matchAndRemove(Token.tokenType.IDENTIFIER);
        if(nameIdentifier == null) throw new SyntaxErrorException("[Parser] Expected: identifier name");

        String functionName = nameIdentifier.getNameIdentifier();

        // Expects left parenthesis token, otherwise throw syntax error
        if(matchAndRemove(Token.tokenType.LPAREN) == null)  throw new SyntaxErrorException("[Parser] Expected: '(' ");

        // Expects a list of 0 or more variable declarations, otherwise throw syntax error
        if(tokens.get(0).getType() == Token.tokenType.IDENTIFIER) parameters = parameters();

        // Expects right parenthesis token, otherwise throw syntax error
        if(matchAndRemove(Token.tokenType.RPAREN) == null) throw new SyntaxErrorException("[Parser] Expected: ')' ");


        // Expects EOL token, otherwise throw syntax error
        if(matchAndRemove(Token.tokenType.ENDOFLINE) == null) throw new SyntaxErrorException("[Parser] Expected: End of line");


        /* start constants check */
        if(matchAndRemove(Token.tokenType.CONSTANTS) != null){
            if(matchAndRemove(Token.tokenType.ENDOFLINE) == null){
                throw new SyntaxErrorException("[Parser] Expected: End of Line. Constants should have its own line");
                constants.add(constants());
                while(!(tokens.isEmpty()) && tokens.get(0).getType().equals(Token.tokenType.IDENTIFIER)){
                    constants.add(constants());
                }
            }
        }
        /* end constants check */

        /* start variable check */
        if(matchAndRemove(Token.tokenType.VARIABLES) != null){
            if(matchAndRemove(Token.tokenType.ENDOFLINE) == null)
                throw new SyntaxErrorException("[Parser] Expected: End of Line. Variables should have its own line");

            ArrayList<VariableNode> oneLineVar = variables();

            for(int i=0; i<oneLineVar.size(); i++){
                variables.add(oneLineVar.get(i));
            }

            while(tokens.get(0).getType().equals(Token.tokenType.IDENTIFIER)){
                oneLineVar = variables();
                for(int i=0; i<oneLineVar.size(); i++){
                    oneLineVar.add(oneLineVar.get(i));
                }
            }
        }
        /* end variable check */

        if(matchAndRemove(Token.tokenType.INDENT) == null) throw new SyntaxErrorException("[Parser] Expected: Indent");

        statements = body();

        // initialize new function node to return
        FunctionNode functionNode = new FunctionNode(functionName);

        //Add parameters to function if not null
        if(parameters != null){
            functionNode.setParameters(parameters);
        }
        //Add constants to function if not null
        if(constants != null){
            functionNode.setConstants(constants);
        }
        //Add variables to function if not null
        if(variables != null){
            functionNode.setVariables(variables);
        }
        //Add statements to function if not null
        if(statements != null){
            functionNode.setStatements(statements);
        }

        // Expects dedent token, otherwise throw syntax error
        if(matchAndRemove(Token.tokenType.DEDENT) == null){
            throw new SyntaxErrorException("[Parser] Expected: Dedent");
        }

        System.out.println(functionNode.toString());
        return functionNode;
    }

    /**
     * parameterDeclarations() process the parameters and returns a collection of VariableNode.
     * Remember that a parameter may or may not be var. Remember that there may not be any parameters.
     * @return all function parameters
     */
    public ArrayList<VariableNode> parameterDeclarations(){
        ArrayList<VariableNode> variableNodeArrayList = new ArrayList<>();
        ArrayList<VariableNode> params = new ArrayList<>();
        while(peek(0).getType() != Token.tokenType.RPAREN){
            if(matchAndRemove(Token.tokenType.VAR) != null){

            }
            else{

            }
        }

        return params;
    }

    public Node boolCompare() throws SyntaxErrorException {
        if(!peek(0).getType().equals(Token.tokenType.NUMBER) && !peek(0).getType().equals(Token.tokenType.IDENTIFIER))
            throw new SyntaxErrorException("[Parser] Expected: Number or Identifier");

        Node leftBool = expression();
        Token comparator = getComparator();

        if(comparator == null) throw new SyntaxErrorException("[Parser] Expected: Comparator (< , <=, >, >=, =, <>)");


    }

    // An assignment is an identifier (with possible array index) followed by := followed by a boolCompare.
    public AssignmentNode assignment() throws SyntaxErrorException{
        Node target =
    }

    /**
     * Condensed version to get comparators for boolCompare
     * @return token of comparator; null if not a comparator
     */
    private Token getComparator(){
        if(matchAndRemove(Token.tokenType.LESSTHAN) != null) return new Token(Token.tokenType.LESSTHAN);
        else if (matchAndRemove(Token.tokenType.LESSOREQUAL) != null) return new Token(Token.tokenType.LESSOREQUAL);
        else if(matchAndRemove(Token.tokenType.GREATERTHAN) != null) return new Token(Token.tokenType.GREATERTHAN);
        else if(matchAndRemove(Token.tokenType.GREATEROREQUAL) != null) return new Token(Token.tokenType.GREATEROREQUAL);
        else if(matchAndRemove(Token.tokenType.EQUALS) != null) return new Token(Token.tokenType.EQUALS);
        else if(matchAndRemove(Token.tokenType.NOTEQUAL) != null) return new Token(Token.tokenType.NOTEQUAL);
        else return null;
    }

}
