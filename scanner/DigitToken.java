package scanner;

/**
 * Represents a Token that is a digit
 * @author  Aarav Borthakur
 * @version 8/31/23
 */
public class DigitToken extends Token
{
    private String inpToken;
    private int digit;

    /**
     * Constructs a DigitToken
     * @param inpToken  The String representation of the Token
     * @throws ScanErrorException
     */
    public DigitToken(String inpToken) throws ScanErrorException
    {
        this.inpToken = inpToken;
        try 
        {
            this.digit = Integer.parseInt(inpToken);
        }
        catch (NumberFormatException e)
        {
            throw new ScanErrorException();
        }
    }

    /**
     * Gets the integer value of the digit
     * @return  The integer value of the digit
     */
    public Integer getValue()
    {
        return digit;
    }

    /**
     * Gets the String representation of the DigitToken
     * @return  The String representation of the Token
     */
    public String toString()
    {
        return inpToken;
    }
}
