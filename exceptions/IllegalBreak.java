package exceptions;

/**
 * Thrown when a break statement is encountered outside of a 
 * loop
 * @author  Aarav Borthakur
 * @version 10/19/23
 */
public class IllegalBreak extends LanguageException
{
    /**
     * Constructs an IllegalBreak
     */
    public IllegalBreak()
    {
        super("Illegal break statement outside of loop");
    }
}
