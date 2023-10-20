package scanner;

import exceptions.UnexpectedToken;

/**
 * Represents a Token that is a Boolean
 * @author  Aarav Borthakur
 * @version 8/31/23
 */
public class BooleanToken extends Token
{
    private String inpToken;
    private boolean value;

    /**
     * Constructs a BooleanToken
     * @param inpToken          The String representation of the Token
     * @throws UnexpectedToken  When an unexpected Token is encountered
     */
    public BooleanToken(String inpToken)
    {
        this.inpToken = inpToken.toLowerCase();
        if (this.inpToken.equals("true"))
        {
            value = true;
        }
    }

    /**
     * Checks whether an identifier is "true" or "false"
     * @param identifier    The identifier to test
     * @return              Whether the identifier is a boolean
     */
    public static boolean isBoolean(String identifier)
    {
        identifier = identifier.toLowerCase();
        if (identifier.equals("true") || identifier.equals("false"))
        {
            return true;
        }
        return false;
    }

    /**
     * Gets the string value of the boolean
     * @return  string representation of the boolean
     */
    public String toString()
    {
        return inpToken;
    }

    /**
     * Gets the Boolean value of Token
     * @return  The Boolean value of Token
     */
    public Boolean getValue()
    {
        return value;
    }
}