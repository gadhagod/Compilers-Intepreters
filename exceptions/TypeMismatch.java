package exceptions;

import scanner.KeywordToken.Keyword;
import scanner.OperandToken.Operand;

public class TypeMismatch extends LanguageException
{
    public TypeMismatch(String expected, String recieved)
    {
        super("Unexpected type: recieved <" + recieved + ">, expected: <" + expected + ">");
    }
    
    public TypeMismatch(Object type1, Object type2, Operand operator)
    {
        super("Unexpected type: cannot operate " + operator + " on " + type1 + " and " + type2);
    }

    public TypeMismatch(Object type1, Object type2, Keyword operator) // for logical operators
    {
        super("Unexpected type: cannot operate " + operator + " on " + type1 + " and " + type2);
    }
}
