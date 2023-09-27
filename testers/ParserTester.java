package testers;
import java.io.File;

import scanner.Scanner;
import parser.Parser;

public class ParserTester 
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
        Parser parser = new Parser(scanner);
        while (scanner.hasNext())
        {
            parser.parseStatement();
        }
    }
}
