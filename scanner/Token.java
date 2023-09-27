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

    public boolean equals(String other)
    {
        return other.equals(toString());
    }

    public boolean equals(Token other)
    {
        return equals(other.toString());
    }
}