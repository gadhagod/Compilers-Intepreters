package exceptions;

import scanner.OperandToken.Operand;

public class TypeException extends LanguageException
{
    public TypeException(String type1, String type2, Operand operation)
    {
        super("Cannot execute operation " + operation + " on " + type1 + " and " + type2);
    }
}
