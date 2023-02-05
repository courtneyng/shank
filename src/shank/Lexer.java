package shank;

import java.util.ArrayList;

public class Lexer {

    public void Lex(String inputLine) throws Exception {
        ArrayList<Token> list = new ArrayList<>();
        char[] expression = inputLine.toCharArray();
        char token;
        String state = "start";
        boolean hasDecimal = false;
        StringBuilder expressionLine = new StringBuilder();

        for (char i : expression) {
            token = i;

            //Start State Machine
            switch (state) {
                case "start":
                    if (Character.isLetter(token)) { // WORD CASE
                        expressionLine.append(token);
                        state = "word";
                    } else if (Character.isDigit(token)) { // NUMBER CASE
                        expressionLine.append(token);
                        state = "number";
                    } else if (token == '.') {  // DECIMAL CASE
                        System.out.print(hasDecimal + " ");
                        expressionLine.append(token);
                        if (!hasDecimal) { // if hasDecimal is false
                            hasDecimal = true;
                            state = "decimal";
                        } else {
                            System.out.print("did we reach?");
                            throw new Exception("Already a decimal in number");
                        }
                    } else if (token == ' ') { // SPACE
                        expressionLine.append(token);
                        state = "start";
                    } else { // "ANYTHING ELSE"
                        System.out.print("[" + token + "] ");
                        throw new Exception("The token in the brackets is not accepted");
                    }
                case "word":
                    if (Character.isLetter(token) || Character.isDigit(token)) {
                        //System.out.print("!");
                        state = "word";
                    } else{
                        state = "start";
                    }
                case "decimal":
                    if (Character.isDigit(token)) {
                        state = "decimal";
                    } else {
                        state = "start";
                    }
                case "number":
                    if (Character.isDigit(token)) {
                        expressionLine.append(token);
                        state = "number";
                    } else if (token == '.') {
                        //expressionLine.append(token);
                        hasDecimal = true;
                        state = "decimal";
                    } else {
                        state = "start";
                    }
            }

        } // END FOR LOOP
        expressionLine.append(" " + Token.tokenType.ENDOFLINE);
        System.out.println(expressionLine);
    }
}