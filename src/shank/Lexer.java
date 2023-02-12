package shank;

import java.util.*;

public class Lexer {
    HashMap<String, Token.tokenType> knownWords = new HashMap<String, Token.tokenType>();
    public boolean startComment = false;
    public void Lex(String inputLine) throws Exception {
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
        knownWords.put("=", Token.tokenType.EQUALS);
        knownWords.put("<>", Token.tokenType.NOTEQUAL);
        knownWords.put("<", Token.tokenType.LESSTHAN);
        knownWords.put("<=", Token.tokenType.LESSOREQUAL);
        knownWords.put(">", Token.tokenType.GREATERTHAN);
        knownWords.put(">=", Token.tokenType.GREATEROREQUAL);
        knownWords.put("+", Token.tokenType.PLUS);
        knownWords.put("-", Token.tokenType.MINUS);
        knownWords.put("*", Token.tokenType.MULTIPLY);
        knownWords.put("/", Token.tokenType.DIVIDE);
        knownWords.put("mod", Token.tokenType.MOD);
        knownWords.put("not", Token.tokenType.NOT);
        knownWords.put("or", Token.tokenType.OR);

        char[] expression = inputLine.toCharArray();
        char token;
        String state = "start";
        boolean hasDecimal = false;
        StringBuilder expressionLine = new StringBuilder();
        StringBuilder checkWord = new StringBuilder();

        for(char i : expression){
            token = i;

            // State Machine
            switch (state) {
                case "start" -> {
                    //First decimal case
                    if(token == '.'){
                        expressionLine.append(Token.tokenType.NUMBER + " ");
                        expressionLine.append(token);
                        if (hasDecimal) throw new Exception("Already has decimal");
                        else { // if no decimal already
                            hasDecimal = true;
                            state = "decimal";
                        }
                    }
                    // white space or emptyline
                    else if (Character.isWhitespace(token)){
                        state = "start";
                    }
                    // First token is letter
                    else if (Character.isLetter(token)) {
                        if(!startComment){
                            expressionLine.append(Token.tokenType.IDENTIFIER + " ");
                            expressionLine.append(token);
                            checkWord.append(token);
                            state = "word";
                        } else{
                            state = "comment";
                        }
                    }
                    // First token is digit
                    else if (Character.isDigit(token)){
                        expressionLine.append(Token.tokenType.NUMBER + " ");
                        expressionLine.append(token);
                        state = "number";
                    }
                    // First token is "
                    else if (token == '"') {
                        expressionLine.append(Token.tokenType.STRINGLITERAL + " ");
                        state = "stringliteral";
                    }
                    else if (token == '{') {
                        startComment = true;
                        state = "comment";
                    }
                    else{
                        if(!startComment){
                            System.out.print("[" + token + "]");
                            throw new Exception("The token in brackets is not accepted");
                        } else{
                            state = "comment";
                        }
                    }


                }
                // Word state
                case "word" -> {
                    if (Character.isLetterOrDigit(token)) {
                        expressionLine.append(token);
                        state = "word";
                    } else if (Character.isWhitespace(token)){
                        expressionLine.append(") ");
                        state = "start";
                    }else {
                        state = "start";
                    }
                }
                // Decimal state
                case "decimal" -> {
                    if (Character.isDigit(token)) {
                        expressionLine.append(token);
                        state = "decimal";
                    } else if (token == '.') {
                        if(hasDecimal) throw new Exception("There's already a decimal in this number");
                    } else if (Character.isWhitespace(token)){
                        expressionLine.append(") ");
                        state = "start";
                    }else {
                        expressionLine.append(" not a digit ");
                        state = "start";
                    }
                }
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
                        expressionLine.append(") ");
                        state = "start";
                    }else {
                        state = "start";
                    }
                }
                case "stringliteral" -> {
                    if(token != '"'){
                        expressionLine.append(token);
                        state = "stringliteral";
                    } else{
                        state = "start";
                    }

                }
                case "comment" ->{
                    if(token != '}'){
                        state = "comment";
                    } else{
                        startComment = false;
                    }
                }
            }


        } // End for loop

        expressionLine.append(" " + Token.tokenType.ENDOFLINE);
        System.out.println(expressionLine);
    }
}