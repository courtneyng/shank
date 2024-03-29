package shank;

import java.util.ArrayList;

public class Parser {
    private ArrayList<Token> tokens;

    public Parser(ArrayList<Token> tokens){
        this.tokens = tokens;
    }

    /**
     * parse() should call function() in its loop. Every FunctionNode returned should go into the ProgramNode
     * (there will be only one of these). null should end the parse() loop. parse() should return the ProgramNode.
     * @return Program Node - program node
     * @throws SyntaxErrorException - error
     */
    public ProgramNode Parse() throws SyntaxErrorException {
        Node current;
        ProgramNode node = new ProgramNode();

        Token eolNode;

        do{
            current = expression();
            if(current != null){
                switch (current) {
                    case MathOpNode expr -> System.out.println(expr);
                    case IntegerNode integer -> System.out.println(integer);
                    case RealNode real -> System.out.println(real);
                    case null, default -> throw new SyntaxErrorException("[Parser: parse] Error");
                }
            } else{
                current = function();
                if(current != null){
                    FunctionNode functionNode = (FunctionNode) current;
                    node.addMap(functionNode);
                }
            }
            eolNode = expectsEOL();
        } while(current != null && eolNode != null);
        return node;
    }


    //TERM { (plus or minus) TERM}
    public Node expression(){
        Node firstNode = term();
        Node secondNode;
        MathOpNode.MathOp operator;
        //Then it (in a loop) looks for a + or -
        while(peek(0).getType().equals(Token.tokenType.PLUS) || peek(0).getType().equals(Token.tokenType.MINUS)){
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
        Node secondNode;
        MathOpNode.MathOp operator;

        while(peek(0).getType().equals(Token.tokenType.TIMES) || peek(0).getType().equals(Token.tokenType.DEFINE) || peek(0).getType().equals(Token.tokenType.MOD)){
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
    // or variableReference or variableReference [ EXPRESSION ]
    public Node factor(){
        if(tokens.get(0).getType().equals(Token.tokenType.NUMBER)) {
            Token numberToken = matchAndRemove(Token.tokenType.NUMBER);
            if(tokens.get(0).getValue().contains(".")) // if contains "." indicates decimal; float/real node
                return new RealNode(Float.parseFloat(tokens.get(0).getValue()));
            else if(matchAndRemove(Token.tokenType.LPAREN) != null){
                Node expr = expression(); // calls until number found
                matchAndRemove(Token.tokenType.RPAREN);
                return expr;
            }
            else{
                return new IntegerNode(Integer.parseInt(tokens.get(0).getValue()));
            }
        }


        return null;
    }

    /**
     * accepts a token type. Looks at the next token in the collection:
     *
     * @param type - the type of the token
     * @return If the passed in token type matches the next token’s type, remove that token and return it.
     * return null.
     */
    private Token matchAndRemove(Token.tokenType type){
        Token token = tokens.get(0);
        if(type.equals(token.getType())){
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
     * @throws SyntaxErrorException - if missing expected
     */
    private Token expectsEOL() throws SyntaxErrorException{
        while (!tokens.isEmpty() && tokens.get(0).getType() == Token.tokenType.ENDOFLINE) {
            tokens.remove(0);
        }
        throw new SyntaxErrorException("[Parser expectsEOL] Expected: EOL");
    }

    /**
     * peek – accepts an integer and looks ahead that many tokens and returns that token.
     * @param x - index
     * @return Returns null if there aren’t enough tokens to fulfill the request.
     */
    private Token peek(int x){
        return tokens.get(x);
    }

    /**
     * Handle single statements
     * @return statement
     * @throws SyntaxErrorException - if missing expected
     */
    private StatementNode statement() throws SyntaxErrorException {
        AssignmentNode assignment = assignment();
        if(assignment != null) return assignment;

        IfNode ifStatement = parseIf();
        if(ifStatement != null) return ifStatement;

        WhileNode whileStatement = parseWhile();
        if(whileStatement != null) return whileStatement;

        RepeatNode repeatStatement = parseRepeat();
        if(repeatStatement != null) return repeatStatement;

        ForNode forStatement = parseFor();
        if(forStatement != null) return forStatement;

        FunctionCallNode functionCallStatement = parseFunctionCalls();
        return functionCallStatement;
    }


    /**
     *  Handles all statements
     * @return statement nodes
     * @throws SyntaxErrorException - error
     */
    private ArrayList<StatementNode> statements() throws SyntaxErrorException {
        ArrayList<StatementNode> statements = new ArrayList<>();
        while(!peek(0).toString().equals(Token.tokenType.ENDOFLINE)){
            StatementNode statementNode = statement();
            statements.add(statementNode);
        }
        return statements;
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

        // creates a new function node calls subordinate methods
        // Expects define token, otherwise throw syntax error
        if(matchAndRemove(Token.tokenType.DEFINE) == null) throw new SyntaxErrorException("[Parser] Expected: define");

        // Expects identifier token, otherwise throw syntax error
        Token nameIdentifier = matchAndRemove(Token.tokenType.IDENTIFIER);
        if(nameIdentifier == null) throw new SyntaxErrorException("[Parser] Expected: identifier name");

        String functionName = nameIdentifier.getNameIdentifier();

        // Expects left parenthesis token, otherwise throw syntax error
        if(matchAndRemove(Token.tokenType.LPAREN) == null)  throw new SyntaxErrorException("[Parser] Expected: '(' ");

        // Expects a list of 0 or more variable declarations, otherwise throw syntax error
        if(tokens.get(0).getType() == Token.tokenType.IDENTIFIER) parameters = parameterDeclarations();

        // Expects right parenthesis token, otherwise throw syntax error
        if(matchAndRemove(Token.tokenType.RPAREN) == null) throw new SyntaxErrorException("[Parser] Expected: ')' ");


        // Expects EOL token, otherwise throw syntax error
        if(matchAndRemove(Token.tokenType.ENDOFLINE) == null) throw new SyntaxErrorException("[Parser] Expected: End of line");


        /* start constants check */
        if(matchAndRemove(Token.tokenType.CONSTANTS) != null){
            if(matchAndRemove(Token.tokenType.ENDOFLINE) == null) // expects EOL after character
                throw new SyntaxErrorException("[Parser] Expected: End of Line. Constants should have its own line");
            constants.add(getConstants());
            while(!(tokens.isEmpty()) && tokens.get(0).getType().equals(Token.tokenType.IDENTIFIER)){ // while not empty
                constants.add(getConstants());
            }

        }
        /* end constants check */

        /* start variable check */
        if(matchAndRemove(Token.tokenType.VARIABLES) != null){
            if(matchAndRemove(Token.tokenType.ENDOFLINE) == null)
                throw new SyntaxErrorException("[Parser] Expected: End of Line. Variables should have its own line");

            ArrayList<VariableNode> oneLineVar = getVariables();

            for (VariableNode variableNode : oneLineVar) {
                variables.add(variableNode);
            }

            while(tokens.get(0).getType().equals(Token.tokenType.IDENTIFIER)){
                oneLineVar = getVariables();
                for(int i=0; i<oneLineVar.size(); i++){
                    oneLineVar.add(oneLineVar.get(i));
                }
            }
        }
        /* end variable check */

        if(matchAndRemove(Token.tokenType.INDENT) == null) throw new SyntaxErrorException("[Parser] Expected: Indent");

        //statements
        statements = statements();

        // initialize new function node to return
        FunctionNode functionNode = new FunctionNode(functionName);

        //Add parameters to function if not null
        if(parameters != null) functionNode.setParameters(parameters);

        //Add constants to function if not null
        if(constants != null) functionNode.setConstants(constants);

        //Add variables to function if not null
        if(variables != null) functionNode.setVariables(variables);

        //Add statements to function if not null
        if(statements != null) functionNode.setStatements(statements);

        // Expects dedent token, otherwise throw syntax error
        if(matchAndRemove(Token.tokenType.DEDENT) == null) throw new SyntaxErrorException("[Parser] Expected: Dedent");

        System.out.println(functionNode);
        return functionNode;
    }

    /**
     * parameterDeclarations() process the parameters and returns a collection of VariableNode.
     * Remember that a parameter may or may not be var. Remember that there may not be any parameters.
     * @return all function parameters
     */
    public ArrayList<VariableNode> parameterDeclarations() throws SyntaxErrorException{
        ArrayList<VariableNode> variableNodeArrayList = new ArrayList<>();
        ArrayList<VariableNode> params = new ArrayList<>();

        for(int i=0; i<params.size(); i++){
            variableNodeArrayList.add(params.get(i));
        }

        // names in parameters are separated using semicolons:
        // define someFunction(readOnly:integer; var changeable : integer; alsoReadOnly : integer)
        while(matchAndRemove(Token.tokenType.SEMICOLON) != null){
            params = multiParam();
            for(int i=0; i<params.size(); i++){
                variableNodeArrayList.add(params.get(i));
            }
        }

        return variableNodeArrayList;
    }

    /**
     * Identifies multi parameter functions
     * @return parameter nodes
     * @throws SyntaxErrorException - missing definition
     */
    public ArrayList<VariableNode> multiParam() throws SyntaxErrorException{
        ArrayList<Token> paramTokens = new ArrayList<>();
        ArrayList<VariableNode> paramNodes = new ArrayList<>();

        paramTokens.add(matchAndRemove(Token.tokenType.IDENTIFIER));

        while(matchAndRemove(Token.tokenType.COMMA) != null) paramTokens.add(matchAndRemove(Token.tokenType.IDENTIFIER));
        if(matchAndRemove(Token.tokenType.COLON) != null) throw new SyntaxErrorException("[Parser: multiParam()] Expected: ':'");
        if(matchAndRemove(Token.tokenType.INTEGER) != null){
            for (Token paramToken : paramTokens) {
                IntegerNode intVal = new IntegerNode(0);
                VariableNode varNode = new VariableNode(paramToken.getNameIdentifier(), Token.tokenType.INTEGER, intVal);
                paramNodes.add(varNode);
            }
        } else if(matchAndRemove(Token.tokenType.REAL) != null){
            for (Token paramToken : paramTokens) {
                RealNode realVal = new RealNode(0);
                VariableNode varNode = new VariableNode(paramToken.getNameIdentifier(), Token.tokenType.REAL, realVal);
            }
        } else throw new SyntaxErrorException("[Parser: multiParam()] Expected: Data Type (Integer, Real)");

        return paramNodes;
    }

    /**
     * BOOLCOMPARE = EXPRESSION [ (<,>,<=,>=,=,<>) EXPRESSION] – note this is 0 or 1, not 0 or more
     * @return
     * @throws SyntaxErrorException
     */
    public Node boolCompare() throws SyntaxErrorException {
        if(!peek(0).getType().equals(Token.tokenType.NUMBER) && !peek(0).getType().equals(Token.tokenType.IDENTIFIER))
            throw new SyntaxErrorException("[Parser] Expected: Number or Identifier");

        Node leftBool = expression();
        BooleanCompareNode.comparisonType comparator = getComparator();

        if(comparator == null) throw new SyntaxErrorException("[Parser] Expected: Comparator (< , <=, >, >=, =, <>)");

        if(!peek(0).getType().equals(Token.tokenType.NUMBER) && !peek(0).getType().equals(Token.tokenType.IDENTIFIER))
            throw new SyntaxErrorException("[Parser] Expected: Number or Identifier");

        Node rightBool = expression();
        BooleanCompareNode booleanCompare = new BooleanCompareNode(leftBool, comparator, rightBool);

        return booleanCompare;
    }

    // An assignment is an identifier (with possible array index) followed by := followed by a boolCompare.
    public AssignmentNode assignment() throws SyntaxErrorException{
        if(matchAndRemove(Token.tokenType.ASSIGN) == null) throw new SyntaxErrorException("[Parser assignment] Expected: Assign");
        if(!peek(0).getType().equals(Token.tokenType.NUMBER) && !peek(0).getType().equals(Token.tokenType.IDENTIFIER))
            throw new SyntaxErrorException("[Parser: assignment()] Expected: Number");

        Node expr = boolCompare();
        VariableReferenceNode name = new VariableReferenceNode(peek(0).getNameIdentifier());
        AssignmentNode assignment = new AssignmentNode(name, expr);

        if(matchAndRemove(Token.tokenType.ENDOFLINE) == null) throw new SyntaxErrorException("[Parser: assignment()] Expected: End of line");
        return assignment;
    }

    /**
     * Condensed version to get comparators for boolCompare
     * @return enum type of comparator; null if not a comparator
     */
    private BooleanCompareNode.comparisonType getComparator(){
        BooleanCompareNode.comparisonType comparator;
        if(matchAndRemove(Token.tokenType.LESSTHAN) != null) return comparator = BooleanCompareNode.comparisonType.LESSTHAN;
        else if (matchAndRemove(Token.tokenType.LESSOREQUAL) != null) return comparator = BooleanCompareNode.comparisonType.LESSOREQUAL;
        else if(matchAndRemove(Token.tokenType.GREATERTHAN) != null) return comparator = BooleanCompareNode.comparisonType.GREATERTHAN;
        else if(matchAndRemove(Token.tokenType.GREATEROREQUAL) != null) return comparator = BooleanCompareNode.comparisonType.GREATEROREQUAL;
        else if(matchAndRemove(Token.tokenType.EQUALS) != null) return comparator = BooleanCompareNode.comparisonType.EQUAL;
        else if(matchAndRemove(Token.tokenType.NOTEQUAL) != null) return comparator = BooleanCompareNode.comparisonType.NOTEQUAL;
        else return null;
    }

    /**
     * IDENTIFIER := NUMBER
     * @return constants
     * @throws SyntaxErrorException - if missing members of definition
     */
    private VariableNode getConstants() throws SyntaxErrorException{
        Token name = matchAndRemove(Token.tokenType.IDENTIFIER);
        if(name == null) throw new SyntaxErrorException("[Parser: getConstants()] Expected: Identifier");
        if(matchAndRemove(Token.tokenType.EQUALS) == null) throw new SyntaxErrorException("[Parser: getConstants()] Expected: Equals");

        Token num = matchAndRemove(Token.tokenType.NUMBER);
        if(num == null) throw new SyntaxErrorException("[Parser: getConstants()] Expected: Number");

        VariableNode constant = new VariableNode();

        if(num.getValue().contains("."))
            constant = new VariableNode(name.getNameIdentifier(), Token.tokenType.REAL, new RealNode(Float.parseFloat(num.getValue())));
        else constant = new VariableNode(name.getNameIdentifier(), Token.tokenType.INTEGER, new IntegerNode(Integer.parseInt(num.getValue())));

        if(matchAndRemove(Token.tokenType.ENDOFLINE) == null)
            throw new SyntaxErrorException("[Parser: getConstants()] Expected: End of line");

        return constant;
    }

    /**
     * Gets the variables for boolCompare
     * @return variables
     * @throws SyntaxErrorException - if missing expected
     */
    private ArrayList<VariableNode> getVariables() throws SyntaxErrorException{
        ArrayList<Token> names = new ArrayList<>();
        ArrayList<VariableNode> variables = new ArrayList<>();

        Token name = matchAndRemove(Token.tokenType.IDENTIFIER);
        names.add(name);

        // if creating multiple variables on same line, names are separated by commas
        while(matchAndRemove(Token.tokenType.COMMA) != null){
            name = matchAndRemove(Token.tokenType.IDENTIFIER);
            names.add(name);
        }

        if(matchAndRemove(Token.tokenType.COLON) == null) throw new SyntaxErrorException("[Parser: getVariables()] Expected: ':'");

        if(matchAndRemove(Token.tokenType.INTEGER) != null){
            for (Token token : names) variables.add(new VariableNode(token.getNameIdentifier(), Token.tokenType.INTEGER, new IntegerNode(0)));
        }
        else if(matchAndRemove(Token.tokenType.REAL) != null){
            for (Token token : names) variables.add(new VariableNode(token.getNameIdentifier(), Token.tokenType.REAL, new IntegerNode(0)));
        }
        else if(matchAndRemove(Token.tokenType.STRING) != null){
            for (Token token : names) variables.add(new VariableNode(token.getNameIdentifier(), Token.tokenType.STRING, new StringNode("")));
        }
        else if(matchAndRemove(Token.tokenType.CHARACTER) != null){
            for (Token token : names) variables.add(new VariableNode(token.getNameIdentifier(), Token.tokenType.CHARACTER, new CharacterNode(' ')));
        }
        else throw new SyntaxErrorException("[Parser: getVariables()] Expected: Data Type (Integer, Real, String, Character)");

        if(matchAndRemove(Token.tokenType.ENDOFLINE) == null)
            throw new SyntaxErrorException("[Parser: getVariables] Expected: End of line");

        return variables;
    }

    /**
     * Parses for blocks
     * Syntax: for IDENTIFIER from # to # EOL (body)
     * @return ForNode
     * @throws SyntaxErrorException - if missing expected
     */
    public ForNode parseFor() throws SyntaxErrorException{
        if(matchAndRemove(Token.tokenType.FOR) == null) throw new SyntaxErrorException("[Parser: parseFor] Expected: 'for'");

        Token name = matchAndRemove(Token.tokenType.IDENTIFIER);

        if(matchAndRemove(Token.tokenType.IDENTIFIER) == null) throw new SyntaxErrorException("[Parser: parseFor] Expected: identifier name");
        if(matchAndRemove(Token.tokenType.FROM) == null) throw new SyntaxErrorException("[Parser: parseFor] Expected: 'from'");

        Token num = matchAndRemove(Token.tokenType.NUMBER);
        if(num == null) throw new SyntaxErrorException("[Parser: parseFar] Expected: number");
        Node from = new IntegerNode(Integer.parseInt(num.getValue()));
        //Node from = expression();

        if(matchAndRemove(Token.tokenType.TO) == null) throw  new SyntaxErrorException("[Parser: parseFor] Expected: 'to'");

        Token secondNum = matchAndRemove(Token.tokenType.NUMBER);
        if(secondNum == null) throw new SyntaxErrorException("[Parser: parseFor] Expected: number");
        Node to = new IntegerNode(Integer.parseInt(secondNum.getValue()));

        VariableReferenceNode variableNode = new VariableReferenceNode(name.getNameIdentifier());

        if(matchAndRemove(Token.tokenType.ENDOFLINE) == null) throw new SyntaxErrorException("[Parser: parseFor] Expected: EOL");

        ArrayList<StatementNode> statements = statements();

        return new ForNode(from, to, variableNode, statements);
    }

    /**
     * Parses while blocks
     * Syntax: while BoolExpr EOL (body)
     * @return while node
     * @throws SyntaxErrorException - if missing expected
     */
    public WhileNode parseWhile() throws SyntaxErrorException{
        ArrayList<StatementNode> statements = new ArrayList<>();
        if(matchAndRemove(Token.tokenType.WHILE) == null) throw new SyntaxErrorException("[Parser parseWhile] Expected: 'while'");
        Node booleanCompare = boolCompare();
        if(booleanCompare == null) throw new SyntaxErrorException("[Parser parseWhile] Expected: boolean expression");
        if(matchAndRemove(Token.tokenType.ENDOFLINE) == null) throw new SyntaxErrorException("[Parser parseWhile] Expected: EOL");
        statements = statements();

        WhileNode whileNode = new WhileNode(statements, (BooleanCompareNode) booleanCompare);
        return whileNode;
    }

    /**
     * Parses if block
     * Syntax if BoolExpr then EOL (body)
     *      elsif BoolExpr then EOL (body)
     *      else EOL (body)
     * @return if node
     * @throws SyntaxErrorException - if missing expected
     */
    public IfNode parseIf() throws SyntaxErrorException{
        IfNode current = new IfNode();
        if(matchAndRemove(Token.tokenType.IF) == null) throw new SyntaxErrorException("[Parser parseIf] Expected: 'if'");
        Node booleanCompare = boolCompare();

        if(booleanCompare == null) throw new SyntaxErrorException("[Parser parseIf] Expected: boolean expression");
        if(matchAndRemove(Token.tokenType.THEN) == null) throw new SyntaxErrorException("[Parser parseIf] Expected: 'then'");
        if(matchAndRemove(Token.tokenType.ENDOFLINE) == null) throw new SyntaxErrorException("[Parser parseIf] Expected: EOL");

        ArrayList<StatementNode> ifStatements = statements();
        IfNode ifNode = new IfNode(ifStatements, (BooleanCompareNode) booleanCompare);
        current = ifNode;

        // if contains else if
        while(matchAndRemove(Token.tokenType.ELSIF) != null){
            Node elseIf = boolCompare();
            if(elseIf == null) throw new SyntaxErrorException("[Parser parseIf] Expected: boolean expression");
            if(matchAndRemove(Token.tokenType.THEN) == null) throw new SyntaxErrorException("[Parser parseIf] Expected: 'then'");
            if(matchAndRemove(Token.tokenType.ENDOFLINE) == null) throw new SyntaxErrorException("[Parser parseIf] Expected: EOL");
            ArrayList<StatementNode> elseIfStatements = statements();
            IfNode elseIfNode= new IfNode(elseIfStatements, (BooleanCompareNode) elseIf);
            current.setNext(elseIfNode);
            current = elseIfNode;
        }

        // if contains else
        if(matchAndRemove(Token.tokenType.ELSE) != null){
            if(matchAndRemove(Token.tokenType.ENDOFLINE) == null) throw new SyntaxErrorException("[Parser parseIf] Expected: EOL");
            ArrayList<StatementNode> elseStatements =  statements();
            IfNode elseNode = new IfNode(elseStatements);
            current.setNext(elseNode);
            current = elseNode;
        }
        return ifNode;
    }

    /**
     * Parses repeat block
     * Syntax: repeat EOL (body) until BoolExpr EOL
     * @return repeat node
     * @throws SyntaxErrorException - if missing expected
     */
    public RepeatNode parseRepeat() throws SyntaxErrorException{
        if(matchAndRemove(Token.tokenType.REPEAT) == null) throw new SyntaxErrorException("[Parser parseRepeat] Expected: 'repeat'");
        if(matchAndRemove(Token.tokenType.ENDOFLINE) == null) throw new SyntaxErrorException("[Parser parseIf] Expected: EOL");
        ArrayList<StatementNode> statements = statements();
        if(matchAndRemove(Token.tokenType.UNTIL) == null) throw new SyntaxErrorException("[Parser parseRepeat] Expected: 'until'");
        Node booleanCompare = boolCompare();
        if(booleanCompare == null) throw new SyntaxErrorException("[Parser parseIf] Expected: boolean expression");
        if(matchAndRemove(Token.tokenType.ENDOFLINE) == null) throw new SyntaxErrorException("[Parser parseIf] Expected: EOL");
        return new RepeatNode(statements, (BooleanCompareNode) booleanCompare);
    }

    /**
     * Function Call single param
     * var | identifier | num
     * define start (args : array of string)
     * constants pi=3.141
     * variables a,b,c : integer
         * a := 1
         * b := 2
         * c := 3
     * @return parameter node
     * @throws SyntaxErrorException - if missing expected
     */
    public ParameterNode parseFunctionCall() throws SyntaxErrorException{
        Node param;

        if(peek(0).getType() .equals(Token.tokenType.VAR)){
            if(matchAndRemove(Token.tokenType.VAR) == null) throw new SyntaxErrorException("[Parser parseFunctionCall] Expected: 'var'");
            Token name = matchAndRemove(Token.tokenType.IDENTIFIER);
            if(name == null) throw new SyntaxErrorException("[Parser parseFunctionCall] Expected: identifier name");
            param = new VariableReferenceNode(name.getNameIdentifier());
        } else if (peek(0).getType().equals(Token.tokenType.NUMBER)) {
            Token num = matchAndRemove(Token.tokenType.NUMBER);
            if(num == null) throw new SyntaxErrorException("[Parser parseFunctionCall] Expected: number");

            // Float if contains decimal
            if(num.getValue().contains(".")) param = new RealNode(Float.parseFloat(num.getValue()));
            else param = new IntegerNode(Integer.parseInt(num.getValue()));
        } else{
            throw new SyntaxErrorException("[Parser parseFunctionCall] Expected: parameters");
        }
        return (ParameterNode) param;
    }

    /**
     * Parses function calls with multiple params
     * Syntax: param {comma, param, ...}
     * @return function call node
     * @throws SyntaxErrorException - if missing expected
     */
    public FunctionCallNode parseFunctionCalls() throws SyntaxErrorException{
        ArrayList<ParameterNode> functionCallParams = new ArrayList<>();
        String functionName ="";
        Token current = matchAndRemove(Token.tokenType.IDENTIFIER);
        if(current != null) functionName = current.getValue();

        functionCallParams.add(parseFunctionCall());

        // multiple parameters, call until all done
        while(matchAndRemove(Token.tokenType.COMMA) != null) functionCallParams.add(parseFunctionCall());

        if(matchAndRemove(Token.tokenType.ENDOFLINE) == null) throw new SyntaxErrorException("[Parser parseFunctionCall] Expected: EOL");
        FunctionCallNode functionCallNode = new FunctionCallNode(functionName, functionCallParams);

        return functionCallNode;
    }
}
