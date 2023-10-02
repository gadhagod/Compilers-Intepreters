package scanner;

/**
 * A Token representing a string
 * @author Aarav Borthakur
 * @version 8/31/23
 */
public class StringToken extends Token
{
    String val;

    /**
     * Constructs a StringToken
     * @param val   The string value of the StringToken
     */
    public StringToken(String val)
    {
        this.val = val;
    }

    /**
     * Gets the value of the StringToken
     * @return  The value of the StringToken
     */
    public String getValue()
    {
        return val;
    }
}
