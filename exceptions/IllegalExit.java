package exceptions;

/**
 * Thrown when an Exit statement is called outside of a Procedure
 * @author  Aarav Borthakur
 * @version 10/26/23
 */
public class IllegalExit extends LanguageException
{
    /**
     * Constructs and IllegalExit 
     */
    public IllegalExit()
    {
        super("Illegal exit statement outside a procedure");
    }
}
