package exceptions;

public abstract class LanguageException extends Exception 
{
    private static int line = 1;

    public static void addLine()
    {
        line++;
    }

    LanguageException(String msg)
    {
        super("Line " + line + ": " + msg);
    }
}
