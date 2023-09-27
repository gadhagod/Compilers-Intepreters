package exceptions;

public class VariableNotDefined extends LanguageException
{
    public VariableNotDefined(String varName)
    {
        super("Variable " + varName + " not defined");
    }
}
