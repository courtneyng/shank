package shank;

import java.util.*;
import java.nio.file.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


public class Shank {
    public static void main(String[] args) throws Exception{
        // Instantiate Lexer
        Lexer newLexer = new Lexer();
        ArrayList<Token> tokenList = new ArrayList<>();

        //Creating file path
        Path filePath = Paths.get("C:\\Users\\court\\Desktop\\cs\\shank\\shank\\src\\shank\\expressions.txt");
        try{
            List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
            //Goes through each line from file and puts it through lexer
            for(String line: lines){
                newLexer.Lex(line);
                //System.out.println(" [" + Token.tokenType.ENDOFLINE + "]"); //prints end of line after line is finished
            }
        } catch(IOException exception){
            //If the file does not go through print error, %s prints the exception error
            System.out.format("Error: %s", exception);
        }
    }
}
