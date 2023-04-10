package shank;

import java.io.File;
import java.util.*;
import java.nio.file.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


public class Shank {
    public static void main(String[] args) throws Exception{
        if (args.length != 1) {
            System.out.println("Inappropriate number of arguments.");
            System.exit(0);
        }
        String fileName = args[0];
        Path myPath = Paths.get(fileName);
        List <String> lines = Files.readAllLines(myPath, StandardCharsets.UTF_8);
        // Instantiate Lexer
        Lexer newLexer = new Lexer();
        ArrayList<Token> tokenList = new ArrayList<>();

        lines.forEach(line -> {
            try {
                newLexer.Lex(line);
                //input.add(newLexer.Lex(line);
            } catch (SyntaxErrorException e) {
                //If the file does not go through print error, %s prints the exception error
                System.out.format("Error: %s", e);
            }
        });

        Parser parse = new Parser(tokenList);
        ProgramNode programNode = (ProgramNode) parse.Parse();


    }
}
