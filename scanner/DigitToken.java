package scanner;

public class DigitToken extends Token
{
    private String inpToken;
    private int digit;

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

    public Integer getValue()
    {
        return digit;
    }

    public String toString()
    {
        return inpToken;
    }
}
