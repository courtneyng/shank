package shank;

import java.util.ArrayList;

public class Lexer {

    public void Lex(String inputLine) throws Exception {
        ArrayList<Token> list = new ArrayList<>();
        char[] expression = inputLine.toCharArray();
        char token;
        String state = "start";
        boolean hasDecimal = false, segmentCompleted = false;
        StringBuilder expressionLine = new StringBuilder();

        for(char i : expression){
            token = i;

            //expressionLine.append(token);
            // State Machine
            switch (state) {
                case "start" -> {
                    if(token == '.'){
                        System.out.print(hasDecimal);
                        if (hasDecimal) throw new Exception("Already has decimal");
                        else { // if no decimal already
                            hasDecimal = true;
                            state = "decimal";
                        }
                    }
                    else if (token == ' ') state = "start";
                    else if (Character.isLetter(token)) state = "word";
                    else if (Character.isDigit(token)) state = "number";
                    else{
                        System.out.print("[" + token + "]");
                        throw new Exception("The token in brackets is not accepted");
                    }
                }
                case "word" -> {
                    if (Character.isLetterOrDigit(token)) {
                        expressionLine.append(token);
                        state = "word";
                    } else {
                        state = "start";
                    }
                }
                case "decimal" -> {
                    if (Character.isDigit(token)) {
                        expressionLine.append(token + "!");
                        state = "decimal";
                    } else {
                        state = "start";
                    }
                }
                case "number" -> {
                    if (Character.isDigit(token)) {
                        expressionLine.append(token + "?");
                        state = "decimal";
                    } else if (token == '.') {
                        expressionLine.append(token);
                        hasDecimal = true;
                        state = "decimal";
                    } else {
                        state = "start";
                    }
                }
            }
        }
        expressionLine.append(" " + Token.tokenType.ENDOFLINE);
        System.out.println(expressionLine);
    }
}