package shank;

import java.util.*;

public class Lexer {
    HashMap<String, Token.tokenType> knownWords = new HashMap<>();
    // Var outside lex to span across lines
    public boolean startComment = false;
    int lineNumber = 0, currentIndent = 0, prevIndent = 0;
    public void Lex(String inputLine) throws SyntaxErrorException {
        // Setting up reserved words
        knownWords.put("define", Token.tokenType.DEFINE);
        knownWords.put("constants", Token.tokenType.CONSTANTS);
        knownWords.put("variables", Token.tokenType.VARIABLES);
        knownWords.put("var", Token.tokenType.VAR);
        knownWords.put("integer", Token.tokenType.INTEGER);
        knownWords.put("real", Token.tokenType.REAL);
        knownWords.put("boolean", Token.tokenType.BOOLEAN);
        knownWords.put("character", Token.tokenType.CHARACTER);
        knownWords.put("string", Token.tokenType.STRING);
        knownWords.put("array", Token.tokenType.ARRAY);
        knownWords.put("for", Token.tokenType.FOR);
        knownWords.put("from", Token.tokenType.FROM);
        knownWords.put("to", Token.tokenType.TO);
        knownWords.put("write", Token.tokenType.WRITE);
        knownWords.put("if", Token.tokenType.IF);
        knownWords.put("elsif", Token.tokenType.ELSIF);
        knownWords.put("else", Token.tokenType.ELSE);
        knownWords.put("then", Token.tokenType.THEN);
        knownWords.put("repeat", Token.tokenType.REPEAT);
        knownWords.put("until", Token.tokenType.UNTIL);
        knownWords.put("mod", Token.tokenType.MOD);
        knownWords.put("not", Token.tokenType.NOT);
        knownWords.put("or", Token.tokenType.OR);

        char[] expression = inputLine.toCharArray();
        char token, nextToken;
        String state = "start";
        boolean hasDecimal = false, isReservedWord = false;
        int spaceCount = 0;
        StringBuilder expressionLine = new StringBuilder();
        StringBuilder checkWord = new StringBuilder(); // check if word is reserved

        lineNumber++;
        expressionLine.append(lineNumber).append(" ");


        for(int i=0; i<expression.length; i++){
            token = expression[i];
            if(i<expression.length-1) nextToken = expression[i+1]; // peek ahead
            else nextToken = '`'; // placeholder for initialization

            // State Machine
            switch (state) {
                case "start" -> {

                    //First decimal case
                    if(token == '.'){
                        expressionLine.append(Token.tokenType.NUMBER).append(" ");
                        expressionLine.append(token);
                        if (hasDecimal) throw new SyntaxErrorException("Already has decimal");
                        else { // if no decimal already
                            hasDecimal = true;
                            state = "decimal";
                        }
                    }
                    // white space or empty line
                    else if (Character.isWhitespace(token)){
                        if(Character.isWhitespace(nextToken)){
                            spaceCount++;
                            state = "indent";
                        } else{
                            state = "start";
                        }
                    }
                    // First token is letter for word state
                    else if (Character.isLetter(token)) {
                        if(!startComment){
                            checkWord.append(token);

                            state = "word";
                        } else{
                            state = "comment";
                        }
                    }
                    // First token is digit for number state
                    else if (Character.isDigit(token)){
                        expressionLine.append(Token.tokenType.NUMBER).append(" ");
                        expressionLine.append(token);
                        state = "number";
                    }
                    // First token is " for stringliteral
                    else if (token == '"') {
                        expressionLine.append(Token.tokenType.STRINGLITERAL).append(" ");
                        state = "stringliteral";
                    }
                    // for comment state
                    else if (token == '{') {
                        startComment = true;
                        state = "comment";
                    }
                    else{
                        if(!startComment){
                            System.out.print("[" + token + "]");
                            throw new SyntaxErrorException("The token in brackets is not accepted");
                        } else{
                            state = "comment";
                        }
                    }
                } //end start state

                // Word state
                case "word" -> {
                    if (Character.isLetterOrDigit(token)) {
                        //expressionLine.append(token);
                        checkWord.append(token);

                        //Checking if the word is a reserved word
                        for(Map.Entry<String, Token.tokenType> set: knownWords.entrySet()){
                            String reservedWord = set.getKey();
                            if(checkWord.toString().equals(reservedWord)){
                                isReservedWord = true;
                                //Token.tokenType value = set.getValue();
                                expressionLine.append(set.getValue());
                                expressionLine.append("(").append(checkWord).append(") ");
                            }
                        }
                        if(Character.isWhitespace(nextToken)&& !isReservedWord){
                            expressionLine.append(Token.tokenType.IDENTIFIER).append(" (").append(checkWord).append(") ");
                        }

                        isReservedWord = false;
                        state = "word";
                    } else if (Character.isWhitespace(token)){
                        expressionLine.append(" ");
                        checkWord = new StringBuilder();
                        state = "start";
                    }else {
                        state = "start";
                    }
                } //end word state

                // Decimal state
                case "decimal" -> {
                    if (Character.isDigit(token)) {
                        expressionLine.append(token);
                        state = "decimal";
                    } else if (token == '.') {
                        if(hasDecimal) throw new SyntaxErrorException("There's already a decimal in this number");
                    } else if (Character.isWhitespace(token)){
                        expressionLine.append(" ");
                        state = "start";
                    }else {
                        expressionLine.append(" not a digit ");
                        state = "start";
                    }
                } // end decimal state

                // Number state
                case "number" -> {
                    if (Character.isDigit(token)) {
                        expressionLine.append(token);
                        state = "number";
                    } else if (token == '.') {
                        expressionLine.append(token);
                        hasDecimal = true;
                        state = "decimal";
                    } else if (Character.isWhitespace(token)){
                        expressionLine.append(" ");
                        state = "start";
                    }else {
                        state = "start";
                    }
                } // end number state

                // String literal state
                case "stringliteral" -> {
                    if(token != '"'){
                        expressionLine.append(token);
                        state = "stringliteral";
                    } else{
                        state = "start";
                    }
                } //end string literal state
                // Comment state
                case "comment" ->{
                    if(token != '}'){
                        state = "comment";
                    } else{
                        startComment = false;
                    }
                } //end comment state

                // Indent state
                case "indent" -> {
                    // if current & next are space -> indent state, if next token is not space -> exit
                    if(Character.isWhitespace(token) && Character.isWhitespace(nextToken)){
                        spaceCount++;
                        state = "indent";
                    } else{
                        spaceCount++; // needed to count last space for modulo

                        //1 tab or 4 spaces is an indent, counts the amount of spaces, if no remainder it means it has
                        // one or more indents
                        if(spaceCount % 4 == 0){
                            currentIndent = spaceCount / 4;
                        }

                        if (currentIndent > prevIndent) {
                            //add as many indent greater than prev
                            for(int k=prevIndent;k<currentIndent;k++){
                                expressionLine.append(Token.tokenType.INDENT).append(" ");
                            }
                        }
                        if(prevIndent>currentIndent){
                            //add as many dedent token greater than curr
                            for(int j=currentIndent;j<prevIndent;j++){
                                expressionLine.append(Token.tokenType.DEDENT).append(" ");
                            }
                        }
                        state = "start";
                    }
                } //end indent
            }
        } // End for loop

        prevIndent = currentIndent;
        currentIndent = 0; //reset current indent for next line
        expressionLine.append(Token.tokenType.ENDOFLINE);
        System.out.println(expressionLine);
    } // End Lex
}