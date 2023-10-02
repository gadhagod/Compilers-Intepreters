package exceptions;

import scanner.Token;

/**
 * Thrown when an unexpected token is encountered
 * @author  Aarav Borthakur
 * @version 10/2/23
 */
public class UnexpectedToken extends LanguageException
{
    /**
     * Constructs an UnexpectedToken given the unexpected token
     * @param recieved  The unexpected token
     */
    public UnexpectedToken(String recieved)
    {
        super("Unexpected token: " + "<" + recieved + ">");
    }
    
    /**
     * Constructs an UnexpectedToken given the unexpected and expected 
     * tokens
     * @param recieved  The unexpected token
     * @param expected  The expected token
     */
    public UnexpectedToken(String recieved, String expected)
    {
        super("Unexpected token: <" + recieved + ">, expected: <" + expected + ">");
    }
    
    /**
     * Constructs an UnexpectedToken given the unexpected token
     * @param recieved  The unexpected token
     */
    public UnexpectedToken(char recieved)
    {
        super("Unexpected token: <" + recieved + ">");
    }

    /**
     * Constructs an UnexpectedToken given the unexpected and expected 
     * tokens
     * @param recieved  The unexpected token
     * @param expected  The expected token
     */
    public UnexpectedToken(char recieved, char expected)
    {
        super("Unexpected token: <" + recieved + ">, expected: <" + expected + ">");
    }
    
    /**
     * Constructs an UnexpectedToken given the unexpected and expected 
     * tokens
     * @param recieved  The unexpected token
     * @param expected  The expected token
     */
    public UnexpectedToken(Token recieved, Token expected)
    {
        super("Unexpected token: <" + recieved + ">, expected: <" + expected + ">");
    }
    
    /**
     * Constructs an UnexpectedToken given the unexpected token
     * @param recieved  The unexpected token
     */
    public UnexpectedToken(Token recieved)
    {
        super("Unexpected token: <" + recieved + ">");
    }
}
