import java.io.File;
import scanner.Scanner;

public class ScannerTester 
{
    public static void main (String[] args) throws Exception
    {
        String exp = "";
        java.util.Scanner reader = new java.util.Scanner(new File("ScannerTestAdvanced.txt"));
        while (reader.hasNextLine())
        {
            exp += reader.nextLine() + "\n";
        }
        Scanner scanner = new Scanner(exp);
        while (scanner.hasNext())
        {
            //scanner.nextToken();
            System.out.println(scanner.nextToken());
        }
    }
}
