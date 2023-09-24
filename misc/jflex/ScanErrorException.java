package jflex;
/**
 * ScanErrorException is a sub class of Exception and is thrown to indicate a 
 * scanning error.  Usually, the scanning error is the result of an illegal 
 * character in the input stream.  The error is also thrown when the expected
 * value of the character stream does not match the actual value.
 * @author Mr. Page, Aarav
 * @version 062014
 *
 */
public class ScanErrorException extends Exception
{
    /**
     * Constructs a ScanErrorException ScanErrorObjects
     */
    public ScanErrorException()
    {
        super();
    }
    
    /**
     * Constructor for ScanErrorObjects that includes a reason for the error
     * @param reason  The reason for the error
     */
    public ScanErrorException(String reason)
    {
        super(reason);
    }
    
    /**
     * Constructor for ScanErrorObjects that includes a reason for the error
     * and its line number
     * @param reason  The reason for the error
     * @param line    The line number causing the error
     */
    public ScanErrorException(String reason, int line, int col)
    {
        super("Line " + line + ": " + reason);
    }
}
