package scanner;

/**
 * Represents a Token that is an identifier
 * @author  Aarav Borthakur
 * @version 8/31/23
 */
public class IdentifierToken extends Token
{
    private String inpToken;

    /**
     * Constructs an IdentifierToken
     * @param inpToken  The String representation of the Token
     */
    public IdentifierToken(String inpToken)
    {
        this.inpToken = inpToken;
    }

    /**
     * Gets the value of the Token
     * @return  The name of the identifier
     */
    public String getValue()
    {
        return inpToken;
    }

    /**
     * The String representation of the Token
     * @return  The String representation of the Token
     */
    public String toString()
    {
        return inpToken;
    }
}
