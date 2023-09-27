package exceptions;

import scanner.OperandToken.Operand;

public class TypeMismatch extends LanguageException
{
    public TypeMismatch(String expected, String recieved)
    {
        super("Unexpected type: recieved <" + recieved + ">, expected: <" + expected + ">");
    }
    
    public TypeMismatch(String type1, String type2, Operand operator)
    {
        super("Unexpected type: cannot operate " + operator + " on " + type1 + " and " + type2);
    }
}
