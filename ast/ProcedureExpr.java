package ast;

import java.util.List;

import environment.Environment;
import exceptions.IllegalBreak;
import exceptions.IllegalContinue;
import exceptions.LanguageException;
import jumps.Break;
import jumps.Exit;
import jumps.Jump;

/**
 * Represents a procedure call as a statement with a return value
 * @author  Aarav Borthakur
 * @version 10/25/23
 */
public class ProcedureExpr extends Expression
{
    private String procName;
    private List<Expression> params;

    /**
     * Constructs a ProcedureExpr given the name of the procedure
     * @param procName  The name of the procedure to execute
     */
    public ProcedureExpr(String procName)
    {
        this.procName = procName;
    }
    
    /**
     * Constructs a ProcedureExpr given the name of the procedure and
     * the parameters to pass into it
     * @param procName  The name of the procedure to execute
     * @param params    A List of parameter values to pass into the call
     */
    public ProcedureExpr(String procName, List<Expression> params)
    {
        this.procName = procName;
        this.params = params;
    }

    /**
     * Executes this procedure call and returns the return value of the 
     * procedure, or Number(0) by default
     * @param env   The environment this procedure is in
     * @throws LanguageException
     */
    public Expression eval(Environment env) throws LanguageException
    {
        try
        {
            if (params == null)
            {
                return env.getProcedure(procName).exec();
            }
            return env.getProcedure(procName).exec(params);
        }
        catch (Jump err)
        {
            if (err instanceof Break)
            {
                throw new IllegalBreak();
            }
            else if (err instanceof Exit)
            {
                return ((Exit) (err)).getReturnVal();
            }
            else
            {
                throw new IllegalContinue();
            }
        }
    }

    /**
     * Gets the String value of the procedure call
     * @return  The String representation of the procedure call,
     *          e.g. "procedureName(1, 2)"
     */
    public String toString()
    {
        return procName + "(" + params == null ? "" : params + ")";
    }
}
