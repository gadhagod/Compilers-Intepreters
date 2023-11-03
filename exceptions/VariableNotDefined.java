package exceptions;

/**
 * Thrown when a variable is referenced before decleration
 * @author  Aarav Borthakur
 * @version 10/19/23
 */
public class VariableNotDefined extends LanguageException
{
    /**
     * Constructs a VariableNotDefined exception given the name of the
     * attemptedly accessed variable
     * @param varName   The name of the variable
     */
    public VariableNotDefined(String varName)
    {
        super("Variable " + varName + " not defined");
    }
}
