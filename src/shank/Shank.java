package shank;

import java.io.File;
import java.util.*;
import java.nio.file.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


public class Shank {
    public static void main(String[] args) throws Exception{
        // Instantiate Lexer
        Lexer newLexer = new Lexer();

        if(0 < args.length){
            //Creating file path
            List<String> lines = Files.readAllLines(Paths.get(args[0]), StandardCharsets.UTF_8);
            try{
                //Goes through each line from file and puts it through lexer
                for(String line: lines){
                    newLexer.Lex(line);
                }
            } catch(IOException exception){
                //If the file does not go through print error, %s prints the exception error
                System.out.format("Error: %s", exception);
            }
        } else{
            // print how to use
            System.out.println("Try: java Shank [filename]");
            System.exit(1);
        }


    }
}
