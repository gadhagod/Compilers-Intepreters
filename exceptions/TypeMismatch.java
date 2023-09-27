package exceptions;

public class TypeMismatch extends LanguageException
{
    public TypeMismatch(String expected, String recieved)
    {
        super("Unexpected type: recieved <" + recieved + ">, expected: <" + expected + ">");
    }
}
