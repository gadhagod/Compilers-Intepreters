package scanner;

/**
 * Represents a Token that is a a keyword
 * @author  Aarav Borthakur
 * @version 8/31/23
 */
public class KeywordToken extends Token
{
    /**
     * Types of keywords
     */
    public static enum Keyword
    {
        VAR,
        PROCEDURE,
        BEGIN,
        RETURN,
        END,
        WHILE,
        DO,
        WRITELN,
        READLN,
        EOF,
        /*
        AND,
        OR,
        NOT,
        */
        mod
    }

    private String inpToken;
    private Keyword keyword;

    /**
     * Constructs a KeywordToken given its String
     * representation
     * @param inpToken  The String representation of the Token
     */
    public KeywordToken(String inpToken)
    {
        this.inpToken = inpToken;
        this.keyword = Keyword.valueOf(inpToken);
    }

    /**
     * Checks whether an identifier is a Keyword
     * @param identifier   The identifier
     */
    public static boolean isKeyword(String identifier)
    {
        try 
        {
            Keyword.valueOf(identifier);
        }
        catch (IllegalArgumentException err)
        {
            return false;
        }
        return true;
    }

    /*
    public boolean isLogicalOperator()
    {
        return keyword.equals(Keyword.AND) || keyword.equals(Keyword.NOT) || keyword.equals(Keyword.OR);
    }
    */

    /**
     * Gets the keyword the Token represents
     * @return  The value of the Token
     */
    public Keyword getValue()
    {
        return keyword;
    }

    /**
     * Gets the string representation of the token
     * @return  The String representation of the token
     */
    public String toString()
    {
        return inpToken;
    }

    /**
     * Checks whether this KeywordToken represents a Keyword
     * @param other The Keyword to compare to
     * @return      Whether this KeywordToken is a Keyword
     */
    public boolean equals(Keyword other)
    {
        return keyword.equals(other);
    }
}
