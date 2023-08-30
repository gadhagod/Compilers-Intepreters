package scanner;

public class SeperatorToken extends Token
{
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

    public Seperator getValue()
    {
        return seperator;
    }

    public String toString()
    {
        return inpToken;
    }
}
