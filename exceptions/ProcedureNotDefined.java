package exceptions;

/**
 * Thrown when a procedure is called before it is defined
 * @author  Aarav Borthakur
 * @version 10/2/23
 */
public class ProcedureNotDefined extends LanguageException
{
    /**
     * Constructs a ProcedureNotDefined given the procedure name
     * @param procName  The name of the procedure
     */
    public ProcedureNotDefined(String procName)
    {
        super("Procedure " + procName + " not defined");
    }
}
