package exceptions;

/**
 * Thrown when a String is not closed
 * @author  Aarav Borthakur
 * @version 10/2/23
 */
public class UnexpectedEOF extends LanguageException
{
    /**
     * Constrcts an UnexpectedEOF
     */
    public UnexpectedEOF()
    {
        super("Unexpected EOF");
    }
}
