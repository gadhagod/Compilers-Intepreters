package exceptions;

public abstract class LanguageException extends Exception 
{
    LanguageException(String msg)
    {
        super(msg);
    }
    
    LanguageException(String msg, int line)
    {
        super("Line " + line + ": " + msg);
    }
}
