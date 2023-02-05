package shank;

import java.util.ArrayList;

public class Lexer {

    public ArrayList<Token> Lex(String inputLine) throws Exception {
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
                    // If starts with letter becomes word
                    if (Character.isLetter(token)) {
                        //expressionLine.append(Token.tokenType.WORD);
                        expressionLine.append(token);
                        state = "word";
                    } else if (Character.isDigit(token)) {
                        state = "number";
                    } else if (token == '.') {
                        if (!hasDecimal) {
                            
                            hasDecimal = true;
                            state = "decimal";
                        } else {
                            throw new Exception("Already a decimal in number");
                        }
                    } else if (token == ' ') {
                        state = "start";
                    } else {
                        System.out.println("[" + token + "]");
                        throw new Exception("The token in the brackets is not accepted");
                    }
                case "word":
                    if (Character.isLetter(token) || Character.isDigit(token)) {
                        //System.out.print("!");
                        state = "word";
                    } else if(token == ' '){
                        //expressionLine.append(Token.tokenType.WORD);
                    } else{
                        state = "start";
                    }
                case "decimal":
                    if (Character.isDigit(token)) {
                        
                        state = "decimal";
                    } else {
                        state = "start";
                    }
                    break;
                case "number":
                    if (Character.isDigit(token)) {
                        
                        state = "number";
                    } else if (token == '.') {
                        hasDecimal = true;
                        state = "decimal";
                    } else {
                        state = "start";
                    }
                case "endofline":
                    break;
            }

        } // END FOR LOOP
        expressionLine.append(" " + Token.tokenType.ENDOFLINE);
        System.out.println(expressionLine);
        return list;
    }
}