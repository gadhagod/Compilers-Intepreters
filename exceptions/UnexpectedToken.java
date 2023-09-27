package exceptions;

import scanner.Token;

public class UnexpectedToken extends LanguageException
{
    public UnexpectedToken(String token)
    {
        super("Unexpected token: " + "<" + token + ">");
    }
    
    public UnexpectedToken(String recieved, String expected)
    {
        super("Unexpected token: <" + recieved + ">, expected: <" + expected + ">");
    }
    
    public UnexpectedToken(char recieved)
    {
        super("Unexpected token: <" + recieved + ">");
    }

    public UnexpectedToken(char recieved, char expected)
    {
        super("Unexpected token: <" + recieved + ">, expected: <" + expected + ">");
    }
    
    public UnexpectedToken(Token recieved, Token expected)
    {
        super("Unexpected token: <" + recieved + ">, expected: <" + expected + ">");
    }
}
