import java.io.FileReader;
import java.io.Reader;

/**
 * Methods to test the scanner
 * 
 * @author  Aarav Borthakur
 * @verison 9/12/23
 */
public class Tester
{
    /**
     * Tests the Scanner against an input program (if specified
     * from the command line, else ScannerTestAdvanced.txt)
     * @param args  The command line arguments
     */
    public static void main (String[] args) throws Exception
    {
        Reader input = new FileReader(args.length > 0 ? args[0] : "index.xml");
        Scannerabb scanner = new Scannerabb(input);
        String currToken = "";
        while (!currToken.equals("EOF"))
        {
            currToken = scanner.nextToken();
            System.out.println(currToken);
        }
    }
}