package scanner;

/**
 * Represents a Token of an input stream
 */
public abstract class Token 
{
    /**
     * Gets the value of the Token
     * @return value of the Token
     */
    public abstract Object getValue();

    /**
     * Checks whether this Token is equal to a string
     * @param other The string to compare to
     * @return      Whether this Token is equal to other
     */
    public boolean equals(String other)
    {
        return other.equals(toString());
    }

    /**
     * Checks whether this Token is equal to a Token
     * @param other The Token to compare to
     * @return      Whether this Token is equal to other
     */
    public boolean equals(Token other)
    {
        return equals(other.toString());
    }
}