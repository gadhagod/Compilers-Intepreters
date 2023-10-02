package exceptions;

/**
 * An error with the Pascal program
 * @author  Aarav Borthakur
 * @version 10/2/23
 */
public abstract class LanguageException extends Exception 
{
    private static int line = 1;

    /**
     * Increments the line count by one, used when reporting
     * error line numbers
     */
    public static void addLine()
    {
        line++;
    }

    /**
     * Constucts a LanguageException 
     * @param msg   The error message
     */
    public LanguageException(String msg)
    {
        super("Line " + line + ": " + msg);
    }
}
