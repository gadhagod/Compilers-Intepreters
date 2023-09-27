package scanner;

public class StringToken extends Token
{
    String val;

    public StringToken(String val)
    {
        this.val = val;
    }

    public String getValue()
    {
        return val;
    }
}
