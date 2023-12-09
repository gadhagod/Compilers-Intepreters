package testers;
import java.io.File;

import environment.Environment;
import scanner.Scanner;
import parser.Parser;

public class IntepreterTester 
{
    public static void main(String[] args) throws Exception 
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
        parser.execute();
    }
}
