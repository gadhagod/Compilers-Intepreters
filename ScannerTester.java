import java.io.File;
import scanner.Scanner;

/**
 * Methods to test the scanner
 */
public class ScannerTester 
{
    /**
     * Tests the Scanner against an input program (if specified
     * from the command line, else ScannerTestAdvanced.txt)
     * @param args  The command line arguments
     */
    public static void main (String[] args) throws Exception
    {
        String exp = "";
        java.util.Scanner reader = new java.util.Scanner(
            new File(args.length > 0 ? args[0] : "ScannerTestAdvanced.txt")
        );
        while (reader.hasNextLine())
        {
            exp += reader.nextLine() + "\n";
        }
        Scanner scanner = new Scanner(exp);
        while (scanner.hasNext())
        {
            System.out.println(scanner.nextToken());
        }
    }
}
