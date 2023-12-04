package testers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import environment.Environment;
import exceptions.LanguageException;
import parser.Parser;
import scanner.Scanner;

public class CompilerTester 
{
    public static void main(String[] args) throws FileNotFoundException, LanguageException, IOException
    {
        String exp = "";
        java.util.Scanner reader = new java.util.Scanner(
            new File(args.length > 0 ? args[0] : "program.txt")
        );
        while (reader.hasNextLine())
        {
            exp += reader.nextLine() + "\n";
        }
        Scanner scanner = new Scanner(exp);
        Environment env = new Environment();
        Parser parser = new Parser(scanner, env);
        parser.compile("program.asm");
    }
}
