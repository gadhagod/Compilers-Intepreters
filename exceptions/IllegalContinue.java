package exceptions;

/**
 * Thrown when a continue statement is encountered outside of a 
 * loop
 * @author  Aarav Borthakur
 * @version 10/19/23
 */
public class IllegalContinue extends LanguageException
{
    /**
     * Constructs an IllegalContinue exception
     */
    public IllegalContinue()
    {
        super("Illegal continue statement outside of loop");
    }
}
