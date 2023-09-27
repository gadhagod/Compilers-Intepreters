package exceptions;

public class UnexpectedEOF extends LanguageException
{
    public UnexpectedEOF()
    {
        super("Unexpected EOF");
    }
}
