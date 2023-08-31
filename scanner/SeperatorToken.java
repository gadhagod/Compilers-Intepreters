package scanner;

/**
 * A Token representing a seperator
 * @author Aarav Borthakur
 * @version 8/31/23
 */
public class SeperatorToken extends Token
{
    /**
     * Types of seperators
     */
    public static enum Seperator
    {
        OPEN_PAREN,
        CLOSE_PAREN,
        OPEN_BRACE,
        CLOSE_BRACE,
        SEMICOLON
    }

    private String inpToken;
    private Seperator seperator;

    /**
     * Constructs a Seperator token with its String representation
     * @param inpToken  The String representation of the token
     */
    public SeperatorToken(String inpToken) throws ScanErrorException
    {
        this.inpToken = inpToken;
        if (inpToken.equals("("))
        {
            seperator = Seperator.OPEN_PAREN;
        }
        else if (inpToken.equals(")"))
        {
            seperator = Seperator.CLOSE_PAREN;
        }
        else if (inpToken.equals(";"))
        {
            seperator = Seperator.SEMICOLON;
        }
        else if (inpToken.equals("{"))
        {
            seperator = Seperator.OPEN_BRACE;
        }
        else if (inpToken.equals("}"))
        {
            seperator = Seperator.CLOSE_BRACE;
        }
        else
        {
            throw new ScanErrorException();
        }
    }

    /**
     * Gets the value of the token
     * @return  the value of the token
     */
    public Seperator getValue()
    {
        return seperator;
    }

    /**
     * Gets the provided String representation of the token
     * @return the String representation of the token
     */
    public String toString()
    {
        return inpToken;
    }
}
