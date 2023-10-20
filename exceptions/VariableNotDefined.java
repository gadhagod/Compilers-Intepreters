package exceptions;

/**
 * Thrown when a variable is referenced before decleration
 * @author  Aarav Borthakur
 * @version 10/19/23
 */
public class VariableNotDefined extends LanguageException
{
    public VariableNotDefined(String varName)
    {
        super("Variable " + varName + " not defined");
    }
}
