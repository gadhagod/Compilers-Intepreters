package scanner;

public class IdentifierToken extends Token
{
    private String inpToken;

    public IdentifierToken(String inpToken)
    {
        this.inpToken = inpToken;
    }

    public String getValue()
    {
        return inpToken;
    }

    public String toString()
    {
        return inpToken;
    }
}
