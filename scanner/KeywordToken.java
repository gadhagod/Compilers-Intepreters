package scanner;

public class KeywordToken extends Token
{
    public static enum Keyword
    {
        VAR,
        PROCEDURE,
        BEGIN,
        RETURN,
        END,
        WHILE,
        DO,
        WRITELN
    }

    private String inpToken;
    private Keyword keyword;

    public KeywordToken(String inpToken)
    {
        this.inpToken = inpToken;
        this.keyword = Keyword.valueOf(inpToken);
    }

    public Keyword getValue()
    {
        return keyword;
    }

    public String toString()
    {
        return inpToken;
    }
}
