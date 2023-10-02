package exceptions;

import scanner.KeywordToken.Keyword;
import scanner.OperandToken.Operand;

/**
 * Thrown when types don't match
 * @author  Aarav Borthakur
 * @version 10/2/23
 */
public class TypeMismatch extends LanguageException
{
    /**
     * Constructs a TypeMismatch given expected and recieved type
     * @param expected  The expected type
     * @param recieved  The recieved type
     */
    public TypeMismatch(String expected, String recieved)
    {
        super("Unexpected type: recieved <" + recieved + ">, expected: <" + expected + ">");
    }
    
    /**
     * Constructs a TypeMismatch given the objects and the operator as an Operand
     * @param expected  The expected type
     * @param recieved  The recieved type
     * @param operator  The operation done on the incompatible types
     */
    public TypeMismatch(Object type1, Object type2, Operand operator)
    {
        super("Unexpected type: cannot operate " + operator + " on " + type1 + " and " + type2);
    }

    /**
     * Constructs a TypeMismatch given the objects and the operator as a Keyword
     * @param expected  The expected type
     * @param recieved  The recieved type
     * @param operator  The operation done on the incompatible types
     */
    public TypeMismatch(Object type1, Object type2, Keyword operator) // for logical operators
    {
        super("Unexpected type: cannot operate " + operator + " on " + type1 + " and " + type2);
    }
}
