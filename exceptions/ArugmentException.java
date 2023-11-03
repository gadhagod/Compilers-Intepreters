package exceptions;

/**
 * Thrown when a procedure does not recieve its expected number of arguments
 * @author  Aarav Borthakur
 * @version 10/19/23
 */
public class ArugmentException extends LanguageException
{
    /**
     * Constructs an ArgumentException
     * @param procName      The name of the procedure
     * @param expectedCount The expected number of parameters of the procedure
     * @param recievedCount The recieved number of parameters of the procedure
     */
    public ArugmentException(String procName, int expectedCount, int recievedCount)
    {
        super(procName + " expected " + expectedCount + " args; recieved " + recievedCount + " args");
    }
}
