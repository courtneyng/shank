package shank;

public class Lexer {

    public void Lex(String inputLine) throws Exception {
        char[] expression = inputLine.toCharArray();
        char token;
        String state = "start";
        boolean hasDecimal = false;
        StringBuilder expressionLine = new StringBuilder();

        for(char i : expression){
            token = i;

            //expressionLine.append(token);
            // State Machine
            switch (state) {
                case "start" -> {
                    if(token == '.'){
                        expressionLine.append(Token.tokenType.NUMBER + " (");
                        //System.out.print(token); testing purposes
                        expressionLine.append(token);
                        //expressionLine.append(hasDecimal); testing purposes
                        if (hasDecimal) throw new Exception("Already has decimal");
                        else { // if no decimal already
                            hasDecimal = true;
                            state = "decimal";
                        }
                    }
                    else if (Character.isWhitespace(token)){
                        expressionLine.append(") ");
                        state = "start";
                    }
                    else if (Character.isLetter(token)) {
                        expressionLine.append(Token.tokenType.WORD + " (");
                        expressionLine.append(token);
                        state = "word";
                    }
                    else if (Character.isDigit(token)){
                        expressionLine.append(Token.tokenType.NUMBER + " (");
                        expressionLine.append(token);
                        state = "number";
                    }
                    else{
                        System.out.print("[" + token + "]");
                        throw new Exception("The token in brackets is not accepted");
                    }

                }

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
                case "decimal" -> {
                    if (Character.isDigit(token)) {
                        //System.out.print(token); testing purposes
                        //expressionLine.append(token + "!");
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
                case "number" -> {
                    if (Character.isDigit(token)) {
                        //expressionLine.append(token + "?");
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
            }
        }
        expressionLine.append(") " + Token.tokenType.ENDOFLINE);
        System.out.println(expressionLine);
    }
}