package scanner;

import exceptions.UnexpectedToken;

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
        SEMICOLON,
        COMMA
    }

    private String inpToken;
    private Seperator seperator;

    /**
     * Constructs a Seperator token with its String representation
     * @param inpToken  The String representation of the token
     */
    public SeperatorToken(String inpToken) throws UnexpectedToken
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
        else if (inpToken.equals(","))
        {
            seperator = Seperator.COMMA;
        }
        else
        {
            throw new UnexpectedToken(inpToken);
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

    /**
     * Checks whether this Token represents a Seperator
     * @param other The Seperator to compare to
     * @return      Whether this Token represents other
     */
    public boolean equals(Seperator other)
    {
        return seperator.equals(other);
    }
}
